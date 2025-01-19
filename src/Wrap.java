import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Class representing the recipe for making a Wrap, extending AbstractAction2..
public class Wrap extends AbstractAction2 {

    // Method to fetch the current Dogecoin price in USD using the CoinGecko API.
    public static double getDogecoinPriceWithGson() {
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=dogecoin&vs_currencies=usd";
        try {
            // Open a connection to the CoinGecko API.
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET"); // Set the HTTP method to GET.

            // Read the response from the API.
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            // Append each line of the response.
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close(); // Close the reader.
            connection.disconnect(); // Disconnect the connection.

            // Parse the JSON response using Gson.
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(content.toString(), JsonObject.class);

            // Extract the Dogecoin price in USD.
            return json.getAsJsonObject("dogecoin").get("usd").getAsDouble();
        } catch (Exception e) {
            // Throw a runtime exception if an error occurs during the API call.
            throw new RuntimeException(e);
        }
    }

    // Method to handle ingredient addition.
    @Override
    public String hand(String input, String ingredient) {
        // Concatenates the input string with the given ingredient.
        return input + ingredient;
    }

    // Method to generate the steps for making a Wrap.
    @Override
    public String getOrder(String client) {
        // Create an instance of RecipeFetcher to fetch recipe arguments.
        RecipeFetcher recipe = new RecipeFetcher();
        recipe.fetchAndDisplayRecipes(); // Fetch and display recipes.

        ///////////////////////////////////// STEP 1
        ///////////////////////////////////// /////////////////////////////////////
        // Add fish to the client string.
        String input = hand(client, "f1sh");

        ///////////////////////////////////// STEP 2
        ///////////////////////////////////// /////////////////////////////////////
        // Fetch the current Dogecoin price, multiply it by 100, and cast it to an
        ///////////////////////////////////// integer.
        double dogepoint = getDogecoinPriceWithGson() * 100;
        int dogeCount = (int) dogepoint;

        // Perform SHA-384 hashing for a number of iterations based on Dogecoin price
        // and recipe arguments.
        for (int i = 0; i < dogeCount * recipe.getArg(55); i++) {
            input = hashSHA384(input);
        }

        ///////////////////////////////////// STEP 3
        ///////////////////////////////////// /////////////////////////////////////
        // Slice the mixture using a knife based on a recipe-defined argument.
        input = knife(input, recipe.getArg(56));

        ///////////////////////////////////// STEP 4
        ///////////////////////////////////// /////////////////////////////////////
        // Add flour to the recipe.
        input = hand(input, "Fl0uR");

        ///////////////////////////////////// STEP 5
        ///////////////////////////////////// /////////////////////////////////////
        // Add water to the recipe.
        input = hand(input, "ItsW4t3r");

        ///////////////////////////////////// STEP 6
        ///////////////////////////////////// /////////////////////////////////////
        // Calculate the number of days since the last release date.
        LocalDate lastRelease = LocalDate.of(2025, 1, 9); // Define the last release date.
        int age = (int) ChronoUnit.DAYS.between(lastRelease, LocalDate.now()); // Calculate the days since the release.

        // Fetch the number of reactions from GitHub using ReactionGithub.
        ReactionGithub fetcher = new ReactionGithub();
        int reaction = fetcher.getReaction();

        // Calculate the number of iterations based on age, reactions, and recipe
        // arguments.
        int loop = (recipe.getArg(59) * age) + (recipe.getArg(60) * reaction);

        // Perform MD5 hashing for the calculated number of iterations.
        for (int i = 0; i < loop; i++) {
            input = md5(input);
        }

        ///////////////////////////////////// STEP 7
        ///////////////////////////////////// /////////////////////////////////////
        // Add lettuce to the recipe.
        input = hand(input, "s4l4d");

        // Return the final prepared recipe string.
        return input;
    }

    // Main method to execute the Wrap recipe preparation.
    public static void main(String[] args) {
        Wrap wrap = new Wrap(); // Create an instance of Wrap.
        // Print the result of the Wrap preparation for a given client string.
        System.out.println(wrap.getOrder("HbwQwaQxzxRTvxS9"));
    }
}
