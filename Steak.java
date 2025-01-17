// Class representing the recipe for making a Steak, extending AbstractAction2.
public class Steak extends AbstractAction2 {

    // Method to handle ingredient addition.
    @Override
    public String hand(String input, String ingredient) {
        // Concatenates the input string with the given ingredient.
        return input + ingredient;
    }

    // Method to generate the steps for making a Steak.
    @Override
    public String getOrder(String client) {
        // Create an instance of RecipeFetcher to fetch recipe arguments.
        RecipeFetcher recipe = new RecipeFetcher();
        recipe.fetchAndDisplayRecipes(); // Fetch and display recipes.

        // Step 1: Add steak to the client string.
        String input = hand(client, "st3aK");

        // Step 2: Cook the steak using hashing for a number of iterations based on
        // recipe arguments.
        for (int i = 0; i < recipe.getArg(1) + recipe.getArg(2); i++) {
            input = md5(input); // Apply MD5 hashing at each iteration.
        }

        // Return the final prepared recipe string.
        return input;
    }

    // Main method to execute the Steak recipe preparation.
    public static void main(String[] args) {
        Steak steak = new Steak(); // Create an instance of Steak.
        // Print the result of the Steak preparation for a given client string.
        System.out.println(steak.getOrder("C2lZKoJQvKjNrJxY"));
    }
}
