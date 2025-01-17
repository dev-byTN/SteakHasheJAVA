import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Class to simulate the preparation of a Croque Madame recipe using hashing and transformations.
public class CroqueMadame {

    // Method to compute the SHA-384 hash of an input string.
    public String hashSHA384(String input) {
        try {
            // Create a MessageDigest instance for the SHA-384 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            // Convert the input string to bytes and calculate the hash.
            byte[] messageDigest = md.digest(input.getBytes());
            // Use a StringBuilder to assemble the hash as a hexadecimal string.
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b)); // Append each byte in hexadecimal format.
            }
            return sb.toString(); // Return the hash as a string.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw a runtime exception if SHA-384 is unavailable.
        }
    }

    // Method to compute the MD5 hash of an input string.
    public String md5(String input) {
        try {
            // Create a MessageDigest instance for the MD5 algorithm.
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            // Convert the input string to bytes and calculate the hash.
            byte[] array = md.digest(input.getBytes());
            // Use a StringBuffer to assemble the hash as a hexadecimal string.
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString(); // Return the hash as a string.
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs.
        }
        return null; // Return null if the hash calculation fails.
    }

    // Method to generate the recipe steps for a Croque Madame.
    public String getOrder(String client) {
        // Initialize the recipe with the client string.
        String input = client;

        // Create an instance of RecipeFetcher to fetch recipe arguments.
        RecipeFetcher recipe = new RecipeFetcher();
        recipe.fetchAndDisplayRecipes(); // Fetch and display the recipes.

        // Step 1: Add an egg to the recipe.
        input += "gg3gg"; // Represents the ingredient ID: 11.

        // Step 2: Cook the egg using a pan.
        for (int i = 0; i < recipe.getArg(30) + recipe.getArg(31); i++) { // Loop based on recipe arguments.
            input = md5(input); // Hash the input at each iteration using MD5.
        }

        // Step 3: Add bread to the recipe.
        input += "Br3ad"; // Represents the ingredient ID: 5.

        // Step 4: Add ham to the recipe.
        input += "h4m"; // Represents the ingredient ID: 4.

        // Step 5: Add cheese to the recipe.
        input += "ch333s3"; // Represents the ingredient ID: 15.

        // Step 6: Bake the mixture in the oven.
        for (int i = 0; i < recipe.getArg(35) + recipe.getArg(36); i++) { // Loop based on recipe arguments.
            input = hashSHA384(input); // Hash the input at each iteration using SHA-384.
        }

        // Return the final prepared recipe.
        return input;
    }

    // Main method to execute the Croque Madame recipe preparation.
    public static void main(String[] args) {
        CroqueMadame client = new CroqueMadame(); // Create an instance of CroqueMadame.
        // Print the result of the Croque Madame preparation for a given client string.
        System.out.println(client.getOrder("YmT5kypEANVhqLNz"));
    }
}
