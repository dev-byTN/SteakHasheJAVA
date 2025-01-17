import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

// Class to simulate data fetching from an external JSON API (e.g., McBroken stats).
public class Mcflurry {
    private static final String JSON_URL = "https://mcbroken.com/stats.json"; // URL for the McBroken stats API.

    // Class to represent the global JSON response structure.
    public static class ApiResponse {
        @SerializedName("broken") // Maps the JSON key "broken" to this field.
        private double globalBroken; // Percentage of broken machines globally.

        // Getter for the global broken percentage.
        public double getGlobalBroken() {
            return globalBroken;
        }
    }

    // Method to fetch JSON data from a given URL.
    public String fetchJsonData(String url) {
        try {
            // Open a connection to the given URL.
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET"); // Set the HTTP method to GET.

            // Check if the response code is 200 (OK).
            if (connection.getResponseCode() == 200) {
                // Read the response from the API.
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line); // Append each line to the response.
                }
                reader.close(); // Close the reader.
                return response.toString(); // Return the response as a string.
            } else {
                // Print an error message if the response code is not OK.
                System.err.println("Error while fetching data. Code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            // Print the stack trace if an exception occurs.
            System.err.println("Exception while fetching data: " + e.getMessage());
        }
        return null; // Return null if data fetching fails.
    }

    // Method to extract the "broken" value from the JSON data.
    public int extractFinalBroken(String jsonData) {
        if (jsonData == null || jsonData.isEmpty()) {
            // Print an error message if the JSON data is empty or null.
            System.err.println("JSON data is empty.");
            return -1; // Return -1 to indicate an error.
        }

        // Parse the JSON data using Gson.
        Gson gson = new Gson();
        ApiResponse response = gson.fromJson(jsonData, ApiResponse.class);

        if (response != null) {
            // If the response is valid, return the broken percentage multiplied by 10.
            return (int) response.getGlobalBroken() * 10;
        } else {
            // Print an error message if the JSON parsing fails.
            System.err.println("Error parsing the JSON data.");
        }
        return -1; // Return -1 to indicate an error.
    }

    // Main method to fetch and display McBroken stats.
    public static void main(String[] args) {
        Mcflurry extractor = new Mcflurry(); // Create an instance of Mcflurry.

        // Fetch the JSON data from the API.
        String jsonData = extractor.fetchJsonData(JSON_URL);

        // Extract and print the "broken" value from the JSON data.
        int brokenPercentage = extractor.extractFinalBroken(jsonData);
        System.out.println("Final 'broken' percentage: " + brokenPercentage);
    }
}
