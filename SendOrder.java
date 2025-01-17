import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SendOrder {

    public void sendOneOrder(String result, int id) {

        String urlString = "https://steak-hashe.esiea.fr/send_order/" + id + "/" + result +"?key=inspi";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SendOrder order = new SendOrder();
        order.sendOneOrder("tdRZTXBr3ad", 22464);
    }
}