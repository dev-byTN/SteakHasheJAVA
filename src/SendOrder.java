import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

// Class to send processed orders to the server via an API.
public class SendOrder {

    // Method to send a single order to the server.
    public void sendOneOrder(String result, int id) {
        // Construct the API endpoint URL with the order ID and result.
        String urlString = "https://steak-hashe.esiea.fr/send_order/" + id + "/" + result + "?key=inspi";

        try {
            // Open a connection to the API endpoint.
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set the HTTP method to GET.

            // Get the response code from the API.
            int responseCode = connection.getResponseCode();

            // No further action is taken with the response code, but it can be logged or
            // used for debugging.

        } catch (IOException e) {
            // Print the stack trace if an exception occurs during the API call.
            e.printStackTrace();
        }
    }

    // Main method to test the SendOrder functionality.
    public static void main(String[] args) {
        SendOrder order = new SendOrder(); // Create an instance of SendOrder.
        // Example: Send an order with a specific result and ID.
        order.sendOneOrder("tdRZTXBr3ad", 22464);
    }
}
