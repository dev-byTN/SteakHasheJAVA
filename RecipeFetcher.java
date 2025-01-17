import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

// Class representing a recipe with its details such as ID, name, emoji, points, and ingredients.
class Recipe {
    private int id; // Unique identifier for the recipe.
    private String name; // Name of the recipe.
    private String emoji; // Emoji representing the recipe.
    private int default_points; // Default points associated with the recipe.
    private List<Ingredients> ingredients; // List of ingredients required for the recipe.

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
        return emoji + " " + name; // Return a string representation of the recipe.
    }
}

// Class representing an ingredient with its ID and emoji.
class Ingredients {
    private int id; // Unique identifier for the ingredient.
    private String emoji; // Emoji representing the ingredient.

    public int getId() {
        return id;
    }

    public String getEmoji() {
        return emoji;
    }
}

// Class representing a recipe step, which may contain argument values.
class Step {
    @SerializedName("arguments_values") // Maps the JSON key "arguments_values" to this field.
    private Object argumentsValues; // Can be a list or a string.

    // Method to extract argument values as a list of strings.
    public List<String> getArgumentsValues() {
        List<String> result = new ArrayList<>();
        if (argumentsValues instanceof List) { // If it's a list:
            for (Object item : (List<?>) argumentsValues) {
                if (item instanceof String) {
                    result.add((String) item); // Add string elements to the result.
                }
            }
        } else if (argumentsValues instanceof String) { // If it's a single string:
            result.add((String) argumentsValues);
        }
        return result;
    }

    public void setArgumentsValues(Object argumentsValues) {
        this.argumentsValues = argumentsValues;
    }
}

// Class representing the details of a recipe, including a list of steps.
class RecipeDetails {
    private List<Step> steps; // List of steps in the recipe.

    public List<Step> getSteps() {
        return steps;
    }
}

// Class representing the API response that contains a list of recipes.
class RecipeResponse {
    private List<Recipe> recipes; // List of recipes in the API response.

    public List<Recipe> getRecipes() {
        return recipes;
    }
}

// Main class to fetch and display recipes and their details.
public class RecipeFetcher {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr/recipes"; // Base URL for fetching recipes.
    private static final String RECIPE_DETAIL_URL = "https://steak-hashe.esiea.fr/recipe/"; // URL to fetch recipe
                                                                                            // details.
    List<Integer> listt = new ArrayList<>(); // Final list of integers containing arguments from recipes.

    // Method to fetch all recipes and display their details.
    public List<Integer> fetchAndDisplayRecipes() {
        try {
            // Establish a connection to the API.
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set the HTTP method to GET.
            connection.setRequestProperty("Accept", "application/json");

            // Check if the response code is 200 (OK).
            if (connection.getResponseCode() == 200) {
                // Read the response from the API.
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close(); // Close the reader.

                // Parse the JSON response into Java objects.
                Gson gson = new Gson();
                RecipeResponse recipeResponse = gson.fromJson(response.toString(), RecipeResponse.class);

                // Iterate over each recipe and process its details.
                if (recipeResponse != null && recipeResponse.getRecipes() != null) {
                    for (Recipe recipe : recipeResponse.getRecipes()) {
                        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
                            for (Ingredients ingredient : recipe.getIngredients()) {
                                // Ingredients processing can be logged if needed.
                            }
                        }
                        // Fetch and process the arguments for each recipe step.
                        List<String> arguments = fetchRecipeDetails(recipe.getId());
                        listt.addAll(convertAndDisplay(arguments)); // Add arguments to the final list.
                    }
                } else {
                    System.err.println("No recipes available in the response.");
                }
            } else {
                System.err.println("Failed to fetch recipes. HTTP Code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Error while fetching recipes: " + e.getMessage());
        }
        return listt; // Return the final list of arguments.
    }

    // Method to fetch details of a specific recipe by its ID.
    public List<String> fetchRecipeDetails(int recipeId) {
        try {
            URL url = new URL(RECIPE_DETAIL_URL + recipeId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                // Read the response from the API.
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close(); // Close the reader.

                // Parse the JSON response into Java objects.
                Gson gson = new Gson();
                RecipeDetails recipeDetails = gson.fromJson(response.toString(), RecipeDetails.class);

                // Collect all arguments from the steps of the recipe.
                List<String> allArguments = new ArrayList<>();
                if (recipeDetails.getSteps() != null) {
                    for (Step step : recipeDetails.getSteps()) {
                        if (step.getArgumentsValues() != null) {
                            allArguments.addAll(step.getArgumentsValues());
                        }
                    }
                }
                return allArguments; // Return the list of arguments.
            } else {
                System.err.println("Failed to fetch recipe details for ID: " + recipeId);
            }
        } catch (Exception e) {
            System.err.println("Error while fetching recipe details for ID: " + recipeId + ". " + e.getMessage());
        }
        return null; // Return null if an error occurs.
    }

    // Method to convert a list of argument strings to integers and display them.
    public List<Integer> convertAndDisplay(List<String> input) {
        List<Integer> result = new ArrayList<>(); // List to store the converted integers.

        for (String subList : input) {
            subList = subList.replace("[", "").replace("]", ""); // Remove brackets.
            String[] elements = subList.split(",\\s*");

            // Convert each element to an integer and add it to the result list.
            for (String element : elements) {
                try {
                    if (element.isEmpty()) {
                        result.add(0); // Add 0 if the element is empty.
                    } else {
                        result.add(Integer.parseInt(element));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Unable to convert: " + element + ", replaced by 0.");
                    result.add(0); // Add 0 if conversion fails.
                }
            }
        }

        return result; // Return the list of integers.
    }

    // Method to retrieve a specific argument by index.
    public int getArg(int arg) {
        return listt.get(arg); // Return the argument at the given index.
    }

    public static void main(String[] args) {
        RecipeFetcher recipeArg = new RecipeFetcher(); // Create an instance of RecipeFetcher.
        recipeArg.fetchAndDisplayRecipes(); // Fetch and display all recipes.
        System.out.println(recipeArg.getArg(79)); // Example usage to get a specific argument.
    }
}
