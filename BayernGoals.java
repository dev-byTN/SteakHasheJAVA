import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BayernGoals {
    private static final String API_URL = "https://www.thesportsdb.com/api/v1/json/3/eventslast.php?id=133604";

    public static void main(String[] args) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray events = jsonResponse.getAsJsonArray("events");

                int totalGoals = 0;
                for (int i = 0; i < events.size(); i++) {
                    JsonObject event = events.get(i).getAsJsonObject();
                    int homeGoals = event.get("intHomeScore").getAsInt();
                    int awayGoals = event.get("intAwayScore").getAsInt();
                    String homeTeam = event.get("strHomeTeam").getAsString();
                    String awayTeam = event.get("strAwayTeam").getAsString();

                    if (homeTeam.equals("Bayern Munich")) {
                        totalGoals += homeGoals;
                    } else if (awayTeam.equals("Bayern Munich")) {
                        totalGoals += awayGoals;
                    }
                }

                System.out.println("Nombre total de buts marqués par le Bayern : " + totalGoals);
            } else {
                System.err.println("Erreur API : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
