import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

// Class to manage ingredients using data fetched from an API.
public class IngredientManager {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr"; // Base URL of the API.
    private static final String API_KEY = "inspi"; // API key to authenticate requests.
    private List<Ingredient> ingredients; // List to store the fetched ingredients.

    // Constructor to initialize the IngredientManager.
    public IngredientManager() {
        ingredients = new ArrayList<>(); // Initialize the list of ingredients.
    }

    // Inner class to represent an ingredient.
    public static class Ingredient {
        private int id; // Unique identifier for the ingredient.
        private String name; // Name of the ingredient.
        private String value; // Additional value associated with the ingredient.
        private String emoji; // Emoji representing the ingredient.

        // Getter for the name.
        public String getName() {
            return name;
        }

        // Getter for the value.
        public String getValue() {
            return value;
        }

        // Getter for the emoji.
        public String getEmoji() {
            return emoji;
        }

        // Override toString to display the ingredient details.
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

    // Inner class to represent the API response for ingredients.
    public static class ApiResponse {
        private List<Ingredient> ingredients; // List of ingredients in the API response.

        // Getter for the list of ingredients.
        public List<Ingredient> getIngredients() {
            return ingredients;
        }
    }

    // Method to fetch ingredients from the API.
    public void fetchIngredients() {
        String endpoint = BASE_URL + "/ingredients?key=" + API_KEY; // Construct the API endpoint.

        try {
            // Open a connection to the API.
            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET"); // Set the HTTP method to GET.

            // Check if the response code is 200 (OK).
            if (connection.getResponseCode() == 200) {
                // Read the response from the API.
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line); // Append each line to the response.
                }
                reader.close(); // Close the reader.

                // Print the raw API response for debugging.
                System.out.println("Raw API response: " + response.toString());

                // Parse the JSON response using Gson.
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.toString(), ApiResponse.class);

                // Update the list of ingredients if the response is valid.
                if (apiResponse != null && apiResponse.getIngredients() != null) {
                    ingredients = apiResponse.getIngredients();
                } else {
                    System.err.println("The JSON response does not contain valid data.");
                }
            } else {
                System.err.println("Failed to fetch ingredients. Code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            System.err.println("Error while fetching ingredients: " + e.getMessage());
        }
    }

    // Method to display all the ingredients.
    public void displayIngredients() {
        if (ingredients.isEmpty()) {
            System.out.println("No ingredients available.");
        } else {
            // Print each ingredient in the list.
            for (Ingredient ingredient : ingredients) {
                System.out.println(ingredient);
            }
        }
    }

    // Main method to execute the ingredient management operations.
    public static void main(String[] args) {
        IngredientManager ingredientManager = new IngredientManager(); // Create an instance of IngredientManager.

        // Fetch ingredients from the API and display them.
        ingredientManager.fetchIngredients();
        ingredientManager.displayIngredients();
    }
}
