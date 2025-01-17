import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

// Classe représentant une recette
class Recipe {
    private int id;
    private String name;
    private String emoji;
    private int default_points;
    private List<Ingredients> ingredients;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getDefaultPoints() {
        return default_points;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return emoji + " " + name;
    }
}

// Classe représentant un ingrédient
class Ingredients {
    private int id;
    private String emoji;

    public int getId() {
        return id;
    }

    public String getEmoji() {
        return emoji;
    }
}

// Classe représentant une étape de recette
// Classe représentant une étape de recette
class Step {
    @SerializedName("arguments_values")
    private Object argumentsValues; // Peut être une liste ou une chaîne

    public List<String> getArgumentsValues() {
        List<String> result = new ArrayList<>();
        if (argumentsValues instanceof List) {
            for (Object item : (List<?>) argumentsValues) {
                if (item instanceof String) {
                    result.add((String) item);
                }
            }
        } else if (argumentsValues instanceof String) {
            result.add((String) argumentsValues);
        }
        return result;
    }

    public void setArgumentsValues(Object argumentsValues) {
        this.argumentsValues = argumentsValues;
    }
}


// Classe représentant les détails de la recette
class RecipeDetails {
    private List<Step> steps;

    public List<Step> getSteps() {
        return steps;
    }
}

// Classe pour représenter la réponse JSON contenant la liste des recettes
class RecipeResponse {
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }
}

// Classe principale pour récupérer et afficher les recettes
public class RecipeFetcher {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr/recipes";
    private static final String RECIPE_DETAIL_URL = "https://steak-hashe.esiea.fr/recipe/";

    // Méthode pour récupérer les recettes et afficher leurs détails
    public void fetchAndDisplayRecipes() {
        try {
            // Connexion à l'API des recettes
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                // Lire la réponse
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Convertir la réponse JSON en objets Java
                Gson gson = new Gson();
                RecipeResponse recipeResponse = gson.fromJson(response.toString(), RecipeResponse.class);

                // Afficher les noms des recettes, leurs ingrédients et les arguments_values
                if (recipeResponse != null && recipeResponse.getRecipes() != null) {
                    System.out.println("=== Recettes ===");
                    for (Recipe recipe : recipeResponse.getRecipes()) {
                        System.out.println(recipe + " (Points: " + recipe.getDefaultPoints() + ")");
                        System.out.print("  Ingrédients : ");
                        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
                            for (Ingredients ingredient : recipe.getIngredients()) {
                                System.out.print(ingredient.getEmoji() + " ");
                            }
                        } else {
                            System.out.print("Aucun ingrédient trouvé.");
                        }
                        System.out.println();

                        // Récupérer et afficher les arguments des étapes
                        List<String> arguments = fetchRecipeDetails(recipe.getId());
                        if (arguments != null && !arguments.isEmpty()) {
                            System.out.println("  Liste d'arguments : " + arguments);
                        } else {
                            System.out.println("  Aucun argument trouvé pour les étapes.");
                        }
                    }
                } else {
                    System.err.println("Aucune recette disponible dans la réponse.");
                }
            } else {
                System.err.println("Échec de la récupération des recettes. Code HTTP : " + connection.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des recettes : " + e.getMessage());
        }
    }

    // Méthode pour récupérer les détails d'une recette spécifique
    // Méthode pour récupérer les détails d'une recette spécifique
    public  List<String> fetchRecipeDetails(int recipeId) {
        try {
            URL url = new URL(RECIPE_DETAIL_URL + recipeId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                // Lire la réponse
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Convertir la réponse JSON en objet Java
                Gson gson = new Gson();
                RecipeDetails recipeDetails = gson.fromJson(response.toString(), RecipeDetails.class);

                // Collecter tous les arguments_values des étapes
                List<String> allArguments = new ArrayList<>();
                if (recipeDetails.getSteps() != null) {
                    for (Step step : recipeDetails.getSteps()) {
                        if (step.getArgumentsValues() != null) {
                            for (Object argument : step.getArgumentsValues()) {
                                // Vérifier si l'argument est une chaîne ou un autre type
                                if (argument instanceof String) {
                                    allArguments.add((String) argument);
                                } else {
                                    System.err.println("Argument inattendu non traité : " + argument);
                                }
                            }
                        }
                    }
                }
                convertAndDisplay(allArguments);
                return allArguments;
            } else {
                System.err.println("Échec de la récupération des détails pour la recette ID : " + recipeId);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des détails de la recette ID : " + recipeId + ". " + e.getMessage());
        }
        return null;
    }

    public void convertAndDisplay(List<String> input) {
        // Créer une liste de listes d'entiers
        List<List<Integer>> result = new ArrayList<>();
        
        // Parcourir la liste d'entrées
        for (String subList : input) {
            // Supprimer les crochets et séparer les éléments
            subList = subList.replace("[", "").replace("]", "");
            String[] elements = subList.split(",\\s*");
            
            // Créer une nouvelle liste d'entiers pour la sous-liste actuelle
            List<Integer> integerSubList = new ArrayList<>();
            for (String element : elements) {
                integerSubList.add(Integer.parseInt(element));
            }
            
            // Ajouter la sous-liste d'entiers à la liste principale
            result.add(integerSubList);
        }
        
        // Afficher les résultats sous le format listX[Y] = valeur
        for (int i = 0; i < result.size(); i++) {
            List<Integer> list = result.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.println("j" + i + " = " + list.get(j));
            }
        }
    }
    public static void main(String[] args) {
        RecipeFetcher reci = new RecipeFetcher();
        reci.fetchAndDisplayRecipes();
    }
}
