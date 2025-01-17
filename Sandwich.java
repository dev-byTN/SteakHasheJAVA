// Class representing the recipe for making a Sandwich, extending AbstractAction2.
public class Sandwich extends AbstractAction2 {

    // Method to handle ingredient addition.
    @Override
    public String hand(String input, String ingredient) {
        // Concatenates the input string with the given ingredient.
        return input + ingredient;
    }

    // Method to generate the steps for making a Sandwich.
    @Override
    public String getOrder(String client) {
        // Create an instance of RecipeFetcher to fetch recipe arguments.
        RecipeFetcher recipe = new RecipeFetcher();
        recipe.fetchAndDisplayRecipes(); // Fetch and display recipes.

        // Step 1: Add lettuce to the recipe.
        String input = hand(client, "s4l4d");

        // Step 2: Add tomato to the recipe.
        input = hand(input, "t0m4t0");

        // Step 3: Add ham to the recipe.
        input = hand(input, "h4m");

        // Step 4: Slice the mixture using a knife based on a recipe-defined argument.
        input = knife(input, recipe.getArg(14));

        // Step 5: Add bread to complete the sandwich.
        input = hand(input, "Br3ad");

        // Return the final prepared recipe string.
        return input;
    }

    // Main method to execute the Sandwich recipe preparation.
    public static void main(String[] args) {
        Sandwich sandwich = new Sandwich(); // Create an instance of Sandwich.
        // Print the result of the Sandwich preparation for a given client string.
        System.out.println(sandwich.getOrder("tdRZTXV6vZ04IbIW"));
    }
}
