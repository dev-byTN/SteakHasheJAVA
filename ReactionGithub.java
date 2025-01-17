import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Class to interact with the GitHub API and retrieve reaction data for the latest release.
public class ReactionGithub {

    private static final String API_URL = "https://api.github.com/repos/home-assistant/core/releases/latest"; // URL for
                                                                                                              // the
                                                                                                              // latest
                                                                                                              // release.
    private static final String TOKEN = "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"; // Personal
                                                                                                                                         // Access
                                                                                                                                         // Token
                                                                                                                                         // (replace
                                                                                                                                         // with
                                                                                                                                         // your
                                                                                                                                         // own).

    // Method to fetch and count reactions from the GitHub API.
    public int getReaction() {
        int totalReactions = 0; // Initialize the total reactions counter.
        try {
            // Open a connection to the GitHub API URL.
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set HTTP method to GET.

            // Add necessary headers for the API request.
            connection.setRequestProperty("Accept", "application/vnd.github.squirrel-girl-preview+json"); // Include
                                                                                                          // reactions
                                                                                                          // in the
                                                                                                          // response.
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN); // Add the authorization token.

            // Check the response code from the API.
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // If the response is OK (200):
                // Read the API response using a BufferedReader.
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line); // Append each line of the response.
                }
                reader.close(); // Close the reader.

                // Parse the JSON response to extract relevant data.
                JsonObject release = JsonParser.parseString(response.toString()).getAsJsonObject();

                // Get the tag name of the release.
                String tagName = release.get("tag_name").getAsString();

                // Get the reactions object and calculate the total count.
                JsonObject reactions = release.get("reactions").getAsJsonObject();
                totalReactions = reactions.get("total_count").getAsInt() - 6; // Adjust the count if necessary.

                // Debugging (can uncomment to see details in the console).
                // System.out.println("Latest Release: " + tagName);
                // System.out.println("Total Reactions: " + totalReactions);
            } else {
                // Print an error message if the response code is not 200.
                System.err.println("Request failed. Response code: " + responseCode);
            }

        } catch (Exception e) {
            // Print the stack trace in case of an exception.
            e.printStackTrace();
        }
        return totalReactions; // Return the total reactions.
    }
}
