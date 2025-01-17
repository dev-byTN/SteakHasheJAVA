import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class ScrapOrders {
    private static final String BASE_URL = "https://steak-hashe.esiea.fr";
    private static final String API_KEY = "inspi";
    private List<Order> orders;

    // Constructeur
    public ScrapOrders() {
        orders = new ArrayList<>();
    }

    // Classe interne pour représenter une commande
    public static class Order {
        private int id;
        private int points;
        private String date;
        private String client;
        private Recipe recipe;

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
            return "Order{" +
                    "id=" + id +
                    ", points=" + points +
                    ", date='" + date + '\'' +
                    ", client='" + client + '\'' +
                    ", recipe=" + recipe +
                    '}';
        }
    }

    // Classe interne pour représenter une recette
    public static class Recipe {
        private int id;
        private String name;
        private String emoji;

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
            return "Recipe{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", emoji='" + emoji + '\'' +
                    '}';
        }
    }

    // Classe interne pour représenter la réponse JSON de l'API
    // USAGE DE LA COMPOSITION AVEC ORDER DANS APIRESPONSE
    public static class ApiResponse {
        private List<Order> orders;

        public List<Order> getOrders() {
            return orders;
        }
    }

    // Méthode pour récupérer les commandes via l'API
    public void fetchOrders() {
        String endpoint = BASE_URL + "/orders?key=" + API_KEY;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                // Lire la réponse JSON
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Afficher la réponse brute pour débogage (optionnel)
                System.out.println("Réponse brute de l'API : " + response.toString());

                // Utiliser Gson pour analyser la réponse
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.toString(), ApiResponse.class);

                // Mettre à jour la liste des commandes
                if (apiResponse != null && apiResponse.getOrders() != null) {
                    orders = apiResponse.getOrders();
                } else {
                    System.err.println("La réponse JSON ne contient pas de données valides.");
                }
            } else {
                System.err.println("Échec de la récupération des commandes. Code : " + connection.getResponseCode());
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la récupération des commandes : " + e.getMessage());
        }
    }

    // Méthode pour afficher toutes les commandes
    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("Aucune commande disponible.");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }
    // Méthode pour check si la commande est un Steak ou Hamburger ...
    public String checkOrderType(Order order) {
        String result = "";
        if (order.getRecipe() != null) {
            String name = order.getRecipe().getName().toLowerCase();
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
            } else if (name.contains("sandwich")) {
                Sandwich sandwich = new Sandwich();
                result = sandwich.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Sandwich";
            } else if (name.contains("sushi")) {
                Sushi sushi = new Sushi();
                result = sushi.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Sushi";
            } else if (name.contains("fries")) {
                Fries fries = new Fries();
                result = fries.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                System.out.println(result);
                return "Fries";
            } else if (name.contains("croque madame")) {
                CroqueMadame croque = new CroqueMadame();
                result = croque.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Croque Madame";
            } else if (name.contains("tikka masala")) {
                TikkaMasala tikka = new TikkaMasala();
                result = tikka.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Tikka Masala";
            } else if (name.contains("cesar salad")) {
                CesarSalad cesar = new CesarSalad();
                result = cesar.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Cesar salad";
            } else if (name.contains("pizza")) {
                Pizza pizza = new Pizza();
                result = pizza.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Pizza";
            } else if (name.contains("kebab")) {
                Kebab kebab = new Kebab();
                result = kebab.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());
                return "Kebab";
            } else if (name.contains("smoked salmon wrap")) {
                /*Wrap wrap = new Wrap();
                result = wrap.getOrder(order.getClient());
                SendOrder sendOrder = new SendOrder();
                sendOrder.sendOneOrder(result, order.getId());*/
                return "Wrap";
            }else {
                return "Inconnu";
            }
        }
        return "Inconnu";
    }

    public static void main(String[] args) {
        ScrapOrders test = new ScrapOrders();

        // Récupération et affichage des commandes
        test.fetchOrders();
        test.displayOrders();

        for ( Order temp : test.orders) {
            System.out.println("LA commande de " + temp.getClient() + " est un " + test.checkOrderType(temp));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.err.format("InterruptedException : %s%n", e);
            }
        }
    }
}