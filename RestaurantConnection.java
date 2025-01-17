import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class RestaurantConnection {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr";
    private static final String API_KEY = "inspi";

    // Méthode pour établir une connexion et vérifier la réponse
    public boolean connectToRestaurant() {
        String endpoint = BASE_URL + "/hello?key=" + API_KEY;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                // Lire le corps de la réponse
                try (Scanner scanner = new Scanner(connection.getInputStream())) {
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }
                    // Vérifier le contenu de la réponse
                    String responseBody = response.toString();
                    if (responseBody.contains("\"error\"")) {
                        System.err.println("Échec de la connexion : " + responseBody);
                        return false;
                    } else {
                        System.out.println("Connexion réussie au restaurant !");
                        return true;
                    }
                }
            } else {
                System.err.println("Échec de la connexion. Code de réponse : " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    // Méthode pour vérifier l'état de la connexion
    public String checkConnectionStatus() {
        String endpoint = BASE_URL + "/player?key=" + API_KEY;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                try (Scanner scanner = new Scanner(connection.getInputStream())) {
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }
                    return response.toString();
                }
            } else {
                return "Statut de connexion : " + connection.getResponseCode();
            }
        } catch (IOException e) {
            return "Erreur lors de la vérification du statut : " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static void main(String[] args) {
        RestaurantConnection restaurant = new RestaurantConnection();

        if (restaurant.connectToRestaurant()) {
            String status = restaurant.checkConnectionStatus();
            System.out.println("Statut de la connexion : " + status);
        }
    }
}
