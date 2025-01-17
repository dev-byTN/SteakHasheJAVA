import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pizza extends AbstractAction2 {

    public boolean pushToProduction() {
        return true;
    }

    public static int wheater() {
        //  Using another API to get weather information
        String anotherApiKey = "003bb08d0768b7106a0b136ac3bdb30f"; // Replace with your actual API key
        String location = "Ivry-sur-Seine,FR";
        String anotherUrlString = "http://api.weatherstack.com/current?access_key=" + anotherApiKey + "&query=" + location;

        String anotherTemperature = "";
        try {
            URL anotherUrl = new URL(anotherUrlString);
            HttpURLConnection anotherConn = (HttpURLConnection) anotherUrl.openConnection();
            anotherConn.setRequestMethod("GET");
            BufferedReader anotherIn = new BufferedReader(new InputStreamReader(anotherConn.getInputStream()));
            String anotherInputLine;
            StringBuilder anotherContent = new StringBuilder();
            while ((anotherInputLine = anotherIn.readLine()) != null) {
            anotherContent.append(anotherInputLine);
            }
            anotherIn.close();
            anotherConn.disconnect();

            // Parse JSON response from the second API
            String anotherJsonResponse = anotherContent.toString();
            int anotherTempIndex = anotherJsonResponse.indexOf("\"temperature\":") + 14;
            int anotherTempEndIndex = anotherJsonResponse.indexOf(",", anotherTempIndex);
            anotherTemperature = anotherJsonResponse.substring(anotherTempIndex, anotherTempEndIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(anotherTemperature);
    }

    @Override
    public String hand(String input, String ingredient) {
        return input + ingredient;
    }
    @Override
    public String getOrder(String client) {

        /////////////////////////////////////      STEP 1      /////////////////////////////////////
        String input = hand(client, "Fl0uR");

        /////////////////////////////////////      STEP 2      /////////////////////////////////////
        input = hand(input, "ItsW4t3r");

        /////////////////////////////////////      STEP 3      /////////////////////////////////////
        input = hand(input, "Not-a-hash");

        /////////////////////////////////////      STEP 4      /////////////////////////////////////
        boolean pushToProd = pushToProduction();
        if (pushToProd == true){ 
            for ( int i = 0; i < 200; i ++){
                input = hashSHA256(input);
            }
        }

        /////////////////////////////////////      STEP 5      /////////////////////////////////////
        int temperature = wheater();
        if (temperature > 0) { 
            for ( int i = 0; i < 100 * 5; i ++){
                input = hashSHA1(input);
            }
        }
        else {
            for ( int i = 0; i < 100; i ++){
                input = hashSHA1(input);
            }
        }

        /////////////////////////////////////      STEP 6      /////////////////////////////////////
        input = hand(input, "t0m4t0");

        /////////////////////////////////////      STEP 7      /////////////////////////////////////
        input = hand(input, "h4m");

        /////////////////////////////////////      STEP 8      /////////////////////////////////////
        input = hand(input, "ch333s3");

        /////////////////////////////////////      STEP 9      /////////////////////////////////////
        for (int i = 0; i < 300 + 60; i++) {
            input = hashSHA384(input);
        }

        return input;
    }

    public static void main(String[] args) {
        Pizza pizza = new Pizza();
        System.out.println(pizza.getOrder("FVfodn9kRa5cxyL7"));
    }
}
