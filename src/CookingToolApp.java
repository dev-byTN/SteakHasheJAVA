import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

// Class representing a cooking tool.
class CookingTool {
    private int id; // Unique identifier for the tool.
    private String name; // Name of the tool.
    private String emoji; // Emoji representation of the tool.

    // Constructor to initialize a cooking tool.
    public CookingTool(int id, String name, String emoji) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
    }

    // Getter for the ID.
    public int getId() {
        return id;
    }

    // Getter for the name.
    public String getName() {
        return name;
    }

    // Getter for the emoji.
    public String getEmoji() {
        return emoji;
    }

    // Override toString to display the tool details.
    @Override
    public String toString() {
        return emoji + " " + name + " (ID: " + id + ")";
    }
}

// Class representing the API response for cooking tools.
class ApiResponse {
    @SerializedName("cooking_tools") // Maps the JSON key "cooking_tools" to this field.
    private List<CookingTool> tools;

    // Getter for the list of cooking tools.
    public List<CookingTool> getTools() {
        return tools;
    }

    // Setter for the list of cooking tools.
    public void setTools(List<CookingTool> tools) {
        this.tools = tools;
    }
}

// Manager class to handle cooking tools.
class CookingToolManager {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr/cooking_tools"; // API endpoint.
    private static final String API_KEY = "inspi"; // API key (replace with a valid key).
    private List<CookingTool> tools; // List of cooking tools.

    // Constructor to initialize the manager.
    public CookingToolManager() {
        tools = new ArrayList<>();
    }

    // Method to fetch cooking tools from the API.
    public void fetchCookingTools() {
        try {
            // Construct the API endpoint with the API key.
            String endpoint = BASE_URL + "?key=" + API_KEY;
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

                // Print the raw API response (for debugging).
                System.out.println("Raw API response: " + response.toString());

                // Parse the JSON response using Gson.
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.toString(), ApiResponse.class);

                // Update the list of cooking tools if the response is valid.
                if (apiResponse != null && apiResponse.getTools() != null) {
                    tools = apiResponse.getTools();
                    System.out.println("Number of cooking tools retrieved: " + tools.size());
                } else {
                    System.err.println("The JSON response does not contain valid data.");
                }
            } else {
                System.err.println("Failed to fetch cooking tools. Code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            System.err.println("Error while fetching cooking tools: " + e.getMessage());
        }
    }

    // Method to display all the cooking tools.
    public void displayTools() {
        if (tools.isEmpty()) {
            System.out.println("No cooking tools available.");
        } else {
            System.out.println("List of cooking tools:");
            for (CookingTool tool : tools) {
                System.out.println(tool);
            }
        }
    }

    // Method to get a cooking tool by its ID.
    public CookingTool getToolById(int id) {
        for (CookingTool tool : tools) {
            if (tool.getId() == id) {
                return tool; // Return the tool if its ID matches.
            }
        }
        return null; // Return null if no tool matches the ID.
    }

    // Method to perform an action on a cooking tool.
    public void performAction(Action action, int toolId) {
        // Retrieve the tool by its ID.
        CookingTool tool = getToolById(toolId);
        if (tool != null) {
            action.execute(tool); // Execute the action if the tool exists.
        } else {
            System.out.println("No cooking tool found with ID: " + toolId);
        }
    }
}

// Abstract class representing an executable action on a cooking tool.
abstract class Action {
    public abstract void execute(CookingTool tool); // Abstract method to be implemented.
}

// Example action: Using a cooking tool.
class UseToolAction extends Action {
    @Override
    public void execute(CookingTool tool) {
        // Print the usage of the tool.
        System.out.println("Using the tool: " + tool.getName() + " " + tool.getEmoji());
    }
}

// Main application to manage and use cooking tools.
public class CookingToolApp {
    public static void main(String[] args) {
        CookingToolManager toolManager = new CookingToolManager(); // Create a manager instance.

        // Fetch the cooking tools from the API.
        toolManager.fetchCookingTools();

        // Display the list of tools.
        toolManager.displayTools();

        // Example: Retrieve a tool by ID and perform an action.
        int idToSearch = 1; // Example tool ID.
        Action action = new UseToolAction(); // Create a specific action.
        toolManager.performAction(action, idToSearch); // Perform the action on the tool.
    }
}
