import java.security.MessageDigest;

// Class to simulate the preparation of a Caesar Salad recipe using hashing and transformations.
public class CesarSalad {

    // Method to compute the MD5 hash of an input string.
    public String md5(String input) {
        try {
            // Create a MessageDigest instance for the MD5 algorithm.
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Convert the input string to bytes and calculate the hash.
            byte[] array = md.digest(input.getBytes("UTF-8"));
            // Use a StringBuilder to build the hash as a hexadecimal string.
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b)); // Append each byte in hexadecimal format.
            }
            return sb.toString(); // Return the hash as a string.
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an exception.
        }
        return null; // Return null if the hash calculation fails.
    }

    // Method to generate the recipe steps for a Caesar Salad.
    public String getOrder(String client) {
        // Initialize the recipe with the client string.
        String input = client;

        // Create an instance of RecipeFetcher to fetch recipe arguments.
        RecipeFetcher recipe = new RecipeFetcher();
        recipe.fetchAndDisplayRecipes(); // Fetch and display the recipes.

        // Step 1: Add chicken to the recipe.
        input += "Ch1cK3n"; // Represents the ingredient ID: 10.

        // Step 2: Slice the chicken into smaller pieces using the knife.
        // Truncate the string to half its length based on a recipe argument.
        input = input.substring(0, input.length() / recipe.getArg(38));

        // Step 3: Cook the chicken using a pan (10 + 20 iterations).
        for (int i = 0; i < recipe.getArg(39) + recipe.getArg(40); i++) {
            input = md5(input); // Hash the input at each iteration using MD5.
        }

        // Step 4: Add lettuce to the recipe.
        input += "S4lad"; // Represents the ingredient ID: 2.

        // Step 5: Add bread to the recipe.
        input += "Br3ad"; // Represents the ingredient ID: 5.

        // Step 6: Slice the mixture into smaller pieces using the knife.
        // Truncate the string to one-eighth of its length based on a recipe argument.
        input = input.substring(0, input.length() / recipe.getArg(43));

        // Return the final prepared recipe.
        return input;
    }

    // Main method to execute the Caesar Salad recipe preparation.
    public static void main(String[] args) {
        CesarSalad cesarSalad = new CesarSalad(); // Create an instance of CesarSalad.
        // Print the result of the Caesar Salad preparation for a given client string.
        System.out.println(cesarSalad.getOrder("5wVZjMgD5t4l8oPj"));
    }
}
