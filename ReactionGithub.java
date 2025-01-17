import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReactionGithub {

    private static final String API_URL = "https://api.github.com/repos/home-assistant/core/releases/latest";
    private static final String TOKEN = "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"; // Remplacez par votre token

    public int getReaction() {
        int totalReactions = 0;
        try {
            // Envoyer une requête GET à l'API GitHub
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            

            // Ajouter les en-têtes nécessaires
            connection.setRequestProperty("Accept", "application/vnd.github.squirrel-girl-preview+json");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Lire la réponse JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Analyser la réponse JSON
                JsonObject release = JsonParser.parseString(response.toString()).getAsJsonObject();

                // Récupérer le nom de la release et les réactions
                String tagName = release.get("tag_name").getAsString();
                JsonObject reactions = release.get("reactions").getAsJsonObject();
                totalReactions = reactions.get("total_count").getAsInt() - 6;

                // Afficher les informations
                System.out.println("Dernière Release: " + tagName);
                System.out.println("Total Reactions: " + totalReactions);
            } else {
                System.err.println("Échec de la requête. Code de réponse : " + responseCode);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalReactions;
    }
}
