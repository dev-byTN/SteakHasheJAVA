import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// Class to manage the connection to a restaurant API and check its status.
public class RestaurantConnection {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr"; // Base URL of the restaurant API.
    private static final String API_KEY = "inspi"; // API key for authentication.

    // Method to establish a connection with the restaurant API and verify the
    // response.
    public boolean connectToRestaurant() {
        String endpoint = BASE_URL + "/hello?key=" + API_KEY; // API endpoint for connection check.
        HttpURLConnection connection = null;
        try {
            // Open a connection to the API endpoint.
            connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET"); // Set the HTTP method to GET.
            int responseCode = connection.getResponseCode(); // Get the HTTP response code.

            if (responseCode == 200) { // If the response code is OK (200):
                // Read the response body using a Scanner.
                try (Scanner scanner = new Scanner(connection.getInputStream())) {
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine()); // Append each line to the response.
                    }

                    // Check the response content for errors.
                    String responseBody = response.toString();
                    if (responseBody.contains("\"error\"")) {
                        System.err.println("Connection failed: " + responseBody); // Print an error message if the
                                                                                  // response contains "error".
                        return false;
                    } else {
                        System.out.println("Successfully connected to the restaurant!"); // Print success message.
                        return true;
                    }
                }
            } else {
                System.err.println("Connection failed. Response code: " + responseCode); // Print an error message for
                                                                                         // non-200 response codes.
            }
        } catch (IOException e) {
            System.err.println("Error while connecting: " + e.getMessage()); // Print an error message if an exception
                                                                             // occurs.
        } finally {
            if (connection != null) {
                connection.disconnect(); // Disconnect the connection.
            }
        }
        return false; // Return false if the connection fails.
    }

    // Method to check the current connection status with the restaurant API.
    public String checkConnectionStatus() {
        String endpoint = BASE_URL + "/player?key=" + API_KEY; // API endpoint for connection status.
        HttpURLConnection connection = null;
        try {
            // Open a connection to the API endpoint.
            connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET"); // Set the HTTP method to GET.

            // If the response code is OK (200), read and return the response body.
            if (connection.getResponseCode() == 200) {
                try (Scanner scanner = new Scanner(connection.getInputStream())) {
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine()); // Append each line to the response.
                    }
                    return response.toString(); // Return the response as a string.
                }
            } else {
                // Return the response code as a message if it's not OK.
                return "Connection status: " + connection.getResponseCode();
            }
        } catch (IOException e) {
            // Return an error message if an exception occurs.
            return "Error while checking connection status: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect(); // Disconnect the connection.
            }
        }
    }

    public static void main(String[] args) {
        RestaurantConnection restaurant = new RestaurantConnection(); // Create an instance of RestaurantConnection.

        // Attempt to connect to the restaurant API.
        if (restaurant.connectToRestaurant()) {
            // If the connection is successful, check and print the connection status.
            String status = restaurant.checkConnectionStatus();
            System.out.println("Connection status: " + status);
        }
    }
}
