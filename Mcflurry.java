import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Mcflurry {
    private static final String JSON_URL = "https://mcbroken.com/stats.json"; // Remplacez par l'URL réelle

    // Classe pour représenter la réponse JSON globale
    public static class ApiResponse {
        @SerializedName("broken")
        private double globalBroken;

        public double getGlobalBroken() {
            return globalBroken;
        }
    }

    // Méthode pour récupérer les données JSON depuis l'API
    public String fetchJsonData(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                System.err.println("Erreur lors de la récupération des données. Code : " + connection.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Exception lors de la récupération des données : " + e.getMessage());
        }
        return null;
    }

    // Méthode principale pour extraire le "broken" final
    public int extractFinalBroken(String jsonData) {
        if (jsonData == null || jsonData.isEmpty()) {
            System.err.println("Les données JSON sont vides.");
            return -1;
        }

        // Parse JSON avec Gson
        Gson gson = new Gson();
        ApiResponse response = gson.fromJson(jsonData, ApiResponse.class);

        // Afficher la valeur "broken" globale
        if (response != null) {
            //System.out.println("Valeur 'broken' finale : " + response.getGlobalBroken());
        } else {
            System.err.println("Erreur lors de l'analyse du JSON.");
        }
        System.out.println("Valeur 'broken' finale : " + (int) response.getGlobalBroken());
        int broken = (int) response.getGlobalBroken();
        return broken *10 ;
    }

    public static void main(String[] args) {
        Mcflurry extractor = new Mcflurry();

        // Récupérer les données JSON
        String jsonData = extractor.fetchJsonData(JSON_URL);

        // Extraire et afficher le "broken" final
        extractor.extractFinalBroken(jsonData);
    }
}
