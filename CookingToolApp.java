import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

// Classe représentant un ustensile
class CookingTool {
    private int id;
    private String name;
    private String emoji;

    public CookingTool(int id, String name, String emoji) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
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

    @Override
    public String toString() {
        return emoji + " " + name + " (ID: " + id + ")";
    }
}

// Classe pour représenter la réponse JSON de l'API
class ApiResponse {
    @SerializedName("cooking_tools") // Correspond à la clé JSON réelle
    private List<CookingTool> tools;

    public List<CookingTool> getTools() {
        return tools;
    }

    public void setTools(List<CookingTool> tools) {
        this.tools = tools;
    }
}

// Gestionnaire des ustensiles
class CookingToolManager {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr/cooking_tools";
    private static final String API_KEY = "inspi"; // Remplacez par votre clé API
    private List<CookingTool> tools;

    public CookingToolManager() {
        tools = new ArrayList<>();
    }

    // Méthode pour récupérer tous les ustensiles
    public void fetchCookingTools() {
        try {
            String endpoint = BASE_URL + "?key=" + API_KEY;
            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Log de la réponse brute
                System.out.println("Réponse brute de l'API : " + response.toString());

                // Parsing de la réponse
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.toString(), ApiResponse.class);

                // Vérification des données parsées
                if (apiResponse != null && apiResponse.getTools() != null) {
                    tools = apiResponse.getTools();
                    System.out.println("Nombre d'ustensiles récupérés : " + tools.size());
                } else {
                    System.err.println("La réponse JSON ne contient pas de données valides.");
                }
            } else {
                System.err.println("Échec de la récupération des ustensiles. Code : " + connection.getResponseCode());
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la récupération des ustensiles : " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les ustensiles
    public void displayTools() {
        if (tools.isEmpty()) {
            System.out.println("Aucun ustensile disponible.");
        } else {
            System.out.println("Liste des ustensiles :");
            for (CookingTool tool : tools) {
                System.out.println(tool);
            }
        }
    }

    // Méthode pour récupérer un ustensile par son ID
    public CookingTool getToolById(int id) {
        for (CookingTool tool : tools) {
            if (tool.getId() == id) {
                return tool;
            }
        }
        return null;
    }

    // Méthode pour exécuter une action sur un ustensile
    public void performAction(Action action, int toolId) {
        CookingTool tool = getToolById(toolId);
        if (tool != null) {
            action.execute(tool);
        } else {
            System.out.println("Aucun ustensile trouvé avec l'ID : " + toolId);
        }
    }
}

// Classe abstraite représentant une action faisable
abstract class Action {
    public abstract void execute(CookingTool tool);
}

// Exemple d'action : Utiliser un ustensile
class UseToolAction extends Action {
    @Override
    public void execute(CookingTool tool) {
        System.out.println("Utilisation de l'ustensile : " + tool.getName() + " " + tool.getEmoji());
    }
}

// Application principale
public class CookingToolApp {
    public static void main(String[] args) {
        CookingToolManager toolManager = new CookingToolManager();

        // Récupération des ustensiles depuis l'API
        toolManager.fetchCookingTools();

        // Affichage des ustensiles
        toolManager.displayTools();

        // Exemple : Obtenir un ustensile par ID et effectuer une action
        int idToSearch = 1; // Exemple d'ID
        Action action = new UseToolAction();
        toolManager.performAction(action, idToSearch);
    }
}
