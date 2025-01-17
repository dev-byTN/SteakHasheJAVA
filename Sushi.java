import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sushi extends AbstractAction2 { 

    /////////////////////////////////////      STEP 3      /////////////////////////////////////

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
        
        String input = hand(client, "r1c3");

        /////////////////////////////////////      STEP 2      /////////////////////////////////////
        
        for (int i = 0; i < 300000; i++) {
            input = hashSHA1(input);
        }

        /////////////////////////////////////      STEP 3      /////////////////////////////////////
    
        int temperature = wheater();
        if (temperature > 0) { 
            for ( int i = 0; i < 100000 * 5; i ++){
                input = hashSHA1(input);
            }
        }
        else {
            for ( int i = 0; i < 100000; i ++){
                input = hashSHA1(input);
            }
        }

        /////////////////////////////////////     STEP 4      /////////////////////////////////////
        
        input = hand(input, "f1sh");

        /////////////////////////////////////     STEP 5      /////////////////////////////////////
        
        input = hand(input, "sn00p");

        /////////////////////////////////////     STEP 6      /////////////////////////////////////
        
        input = knife(input, 10);

        return input;
    }

    public static void main(String[] args) {

        Sushi sushi = new Sushi();
        System.out.println(sushi.getOrder("wqaebABaPAcQ3FpE"));
    }
        
}