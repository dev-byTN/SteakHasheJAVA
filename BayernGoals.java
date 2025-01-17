import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Class to fetch and calculate total goals scored by Bayern Munich from an API.
public class BayernGoals {
    // Constant URL for the API endpoint to fetch recent events of Bayern Munich.
    private static final String API_URL = "https://www.thesportsdb.com/api/v1/json/3/eventslast.php?id=133604";

    public static void main(String[] args) {
        try {
            // Step 1: Create a connection to the API URL.
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Setting the HTTP method as GET.

            // Step 2: Check the response code from the server.
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // If the response code is 200 (OK):
                // Read the response content from the API.
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // Append each line of the response to the StringBuilder.
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close(); // Close the reader.

                // Step 3: Parse the JSON response to extract event details.
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray events = jsonResponse.getAsJsonArray("events");

                // Step 4: Calculate the total goals scored by Bayern Munich.
                int totalGoals = 0;
                for (int i = 0; i < events.size(); i++) {
                    JsonObject event = events.get(i).getAsJsonObject();
                    int homeGoals = event.get("intHomeScore").getAsInt(); // Extract home team goals.
                    int awayGoals = event.get("intAwayScore").getAsInt(); // Extract away team goals.
                    String homeTeam = event.get("strHomeTeam").getAsString(); // Home team name.
                    String awayTeam = event.get("strAwayTeam").getAsString(); // Away team name.

                    // Add goals to the total if Bayern Munich is the home or away team.
                    if (homeTeam.equals("Bayern Munich")) {
                        totalGoals += homeGoals;
                    } else if (awayTeam.equals("Bayern Munich")) {
                        totalGoals += awayGoals;
                    }
                }

                // Step 5: Print the total number of goals scored by Bayern Munich.
                System.out.println("Total goals scored by Bayern Munich: " + totalGoals);
            } else {
                // Print an error message if the API response code is not 200.
                System.err.println("API error: " + responseCode);
            }
        } catch (Exception e) {
            // Print the stack trace in case of any exception.
            e.printStackTrace();
        }
    }
}
