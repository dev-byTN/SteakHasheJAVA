import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class IngredientManager {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr";
    private static final String API_KEY = "inspi";
    private List<Ingredient> ingredients;

    // Constructeur
    public IngredientManager() {
        ingredients = new ArrayList<>();
    }

    // Classe interne pour représenter un ingrédient
    public static class Ingredient {
        private int id; // Ajout du champ `id`
        private String name;
        private String value; // Ajout du champ `value`
        private String emoji; // Ajout du champ `emoji`

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getEmoji() {
            return emoji;
        }

        @Override
        public String toString() {
            return "Ingredient{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", emoji='" + emoji + '\'' +
                    '}';
        }
    }

    // Classe interne pour représenter la réponse JSON de l'API
    public static class ApiResponse {
        private List<Ingredient> ingredients;

        public List<Ingredient> getIngredients() {
            return ingredients;
        }
    }

    // Méthode pour récupérer les ingrédients via l'API
    public void fetchIngredients() {
        String endpoint = BASE_URL + "/ingredients?key=" + API_KEY;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                // Lire la réponse JSON
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Afficher la réponse brute pour débogage (optionnel)
                System.out.println("Réponse brute de l'API : " + response.toString());

                // Utiliser Gson pour analyser la réponse
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.toString(), ApiResponse.class);

                // Mettre à jour la liste des ingrédients
                if (apiResponse != null && apiResponse.getIngredients() != null) {
                    ingredients = apiResponse.getIngredients();
                } else {
                    System.err.println("La réponse JSON ne contient pas de données valides.");
                }
            } else {
                System.err.println("Échec de la récupération des ingrédients. Code : " + connection.getResponseCode());
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la récupération des ingrédients : " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les ingrédients
    public void displayIngredients() {
        if (ingredients.isEmpty()) {
            System.out.println("Aucun ingrédient disponible.");
        } else {
            for (Ingredient ingredient : ingredients) {
                System.out.println(ingredient);
            }
        }
    }

    public static void main(String[] args) {
        IngredientManager ingredientManager = new IngredientManager();

        // Récupération et affichage des ingrédients
        ingredientManager.fetchIngredients();
        ingredientManager.displayIngredients();
    }
}
