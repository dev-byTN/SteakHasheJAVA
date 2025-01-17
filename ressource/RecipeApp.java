import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.HashMap;
import com.google.gson.annotations.SerializedName;

// Classe représentant un ingrédient d'une recette
class Ingredient {
    private int id;
    private String emoji;

    public Ingredient(int id, String emoji) {
        this.id = id;
        this.emoji = emoji;
    }

    public int getId() {
        return id;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public String toString() {
        return emoji + " (ID: " + id + ")";
    }
}

// Classe pour gérer la correspondance ID → Nom des ingrédients
class IngredientDictionary {
    private static final Map<Integer, String> ingredientNames = new HashMap<>();

    static {
        // Ajoutez ici la correspondance entre les IDs et les noms des ingrédients
        ingredientNames.put(1, "Steak Haché");
        ingredientNames.put(2, "Laitue");
        ingredientNames.put(3, "Tomate");
        ingredientNames.put(4, "Poulet");
        ingredientNames.put(5, "Pain");
        ingredientNames.put(6, "Saumon");
        ingredientNames.put(7, "Riz");
        ingredientNames.put(8, "Algues");
        ingredientNames.put(10, "Viande");
        ingredientNames.put(11, "Fromage");
        ingredientNames.put(12, "Sauce");
        ingredientNames.put(13, "Frites");
        ingredientNames.put(15, "Crème");
        ingredientNames.put(16, "Maïs");
        ingredientNames.put(17, "Eau");
        ingredientNames.put(18, "Pâte");
    }

    // Méthode pour récupérer le nom d'un ingrédient par son ID
    public static String getIngredientName(int id) {
        return ingredientNames.getOrDefault(id, "Inconnu");
    }
}

// Classe représentant une recette
class Recipee {
    private int id;
    private String name;
    private String emoji;
    @SerializedName("default_points")
    private int defaultPoints;
    private List<Ingredient> ingredients;

    public Recipee(int id, String name, String emoji, int defaultPoints, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.defaultPoints = defaultPoints;
        this.ingredients = ingredients;
    }

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
        return defaultPoints;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(emoji).append(" ").append(name).append(" (ID : ").append(id).append(")\n")
                .append("Points par défaut : ").append(defaultPoints).append("\n")
                .append("Ingrédients :\n");

        if (ingredients != null && !ingredients.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                String ingredientName = IngredientDictionary.getIngredientName(ingredient.getId());
                builder.append("  - ").append(ingredient.getEmoji()).append(" ")
                        .append(ingredientName).append(" (ID : ").append(ingredient.getId()).append(")\n");
            }
        } else {
            builder.append("  Aucun ingrédient spécifié.\n");
        }

        return builder.toString();
    }
    public static void main(String[] args) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "🍔"));
        ingredients.add(new Ingredient(2, "🥬"));
        ingredients.add(new Ingredient(3, "🍅"));

        Recipee recipe = new Recipee(1, "Burger", "🍔", 100, ingredients);

        System.out.println(recipe);
    }
}