import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

// Class to manage and process orders from an API.
public class ScrapOrders {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr"; // Base URL of the API.
    private static final String API_KEY = "inspi"; // API key for authentication.
    private List<Order> orders; // List to store fetched orders.

    // Constructor to initialize the ScrapOrders class.
    public ScrapOrders() {
        orders = new ArrayList<>(); // Initialize the list of orders.
    }

    // Inner class representing an order.
    public static class Order {
        private int id; // Unique identifier for the order.
        private int points; // Points associated with the order.
        private String date; // Date of the order.
        private String client; // Client who placed the order.
        private Recipe recipe; // Recipe associated with the order.

        public int getId() {
            return id;
        }

        public int getPoints() {
            return points;
        }

        public String getDate() {
            return date;
        }

        public String getClient() {
            return client;
        }

        public Recipe getRecipe() {
            return recipe;
        }

        @Override
        public String toString() {
            // Return a string representation of the order.
            return "Order{" +
                    "id=" + id +
                    ", points=" + points +
                    ", date='" + date + '\'' +
                    ", client='" + client + '\'' +
                    ", recipe=" + recipe +
                    '}';
        }
    }

    // Inner class representing a recipe associated with an order.
    public static class Recipe {
        private int id; // Unique identifier for the recipe.
        private String name; // Name of the recipe.
        private String emoji; // Emoji representing the recipe.

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmoji() {
            return emoji;
        }

        @Override
        public String toString() {
            // Return a string representation of the recipe.
            return "Recipe{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", emoji='" + emoji + '\'' +
                    '}';
        }
    }

    // Inner class representing the API response containing orders.
    public static class ApiResponse {
        private List<Order> orders; // List of orders in the API response.

        public List<Order> getOrders() {
            return orders;
        }
    }

    // Method to fetch orders from the API.
    public void fetchOrders() {
        String endpoint = BASE_URL + "/orders?key=" + API_KEY; // Construct the API endpoint.

        try {
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

                // Parse the JSON response using Gson.
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.toString(), ApiResponse.class);

                // Update the list of orders if the response is valid.
                if (apiResponse != null && apiResponse.getOrders() != null) {
                    orders = apiResponse.getOrders();
                } else {
                    System.err.println("The JSON response does not contain valid data.");
                }
            } else {
                System.err.println("Failed to fetch orders. Code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            System.err.println("Error while fetching orders: " + e.getMessage());
        }
    }

    // Method to display all fetched orders.
    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            for (Order order : orders) {
                System.out.println(order); // Print each order.
            }
        }
    }

    // Method to determine the type of an order based on its recipe name.
    public String checkOrderType(Order order) {
        String result = "";
        if (order.getRecipe() != null) {
            String name = order.getRecipe().getName().toLowerCase(); // Get the recipe name in lowercase.

            // Check for specific recipe types and process them.
            if (name.contains("hamburger")) {
                Hamburger hamburger = new Hamburger();
                result = hamburger.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Hamburger";
            } else if (name.contains("steak hashé")) {
                Steak steak = new Steak();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Steak";
            }
            else if (name.contains("pizza")) {
                    Pizza pizza = new Pizza();
                    result = pizza.getOrder(order.getClient());
                    SendOrder sendOrder = new SendOrder();
                    sendOrder.sendOneOrder(result, order.getId());
                    return "Pizza";
            }
            else if (name.contains("sandwich")) {
                Sandwich sandwich = new Sandwich();
                result = sandwich.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Sandwich";
            }else if (name.contains("sushi")) {
                Sushi steak = new Sushi();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Sushi";
            } else if (name.contains("wrap")) {
                Wrap steak = new Wrap();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Wrap";
            }else if (name.contains("kebab")) {
                Kebab steak = new Kebab();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Kebab";
            } else if (name.contains("fries")) {
                Fries steak = new Fries();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Fries";
            } else if (name.contains("cesar salad")) {
                CesarSalad steak = new CesarSalad();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Cesar Salad";
            } else if (name.contains("croque madame")) {
                CroqueMadame steak = new CroqueMadame();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Croque Madame";
            } else if (name.contains("tikka masala")) {
                TikkaMasala steak = new TikkaMasala();
                result = steak.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Tikka Masala";
            }
            // Additional checks for other recipe types can follow...
        }
        return "Unknown"; // Return "Unknown" if the recipe type is not identified.
    }

    // Main method to fetch and process orders.
    public static void main(String[] args) {
        ScrapOrders scrapOrders = new ScrapOrders(); // Create an instance of ScrapOrders.

        // Fetch and display all orders.
        scrapOrders.fetchOrders();
        scrapOrders.displayOrders();

        // Process each order and determine its type.
        for (Order temp : scrapOrders.orders) {
            System.out.println("The order of " + temp.getClient() + " is a " + scrapOrders.checkOrderType(temp));
            try {
                Thread.sleep(5000); // Wait for 5 seconds between processing orders.
            } catch (InterruptedException e) {
                System.err.format("InterruptedException: %s%n", e);
            }
        }
    }
}
