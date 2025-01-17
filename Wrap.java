import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.time.chrono.*;
import java.time.temporal.ChronoUnit;

public class Wrap extends AbstractAction2 {
    
    public static double getDogecoinPriceWithGson() {
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=dogecoin&vs_currencies=usd";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(content.toString(), JsonObject.class);
            return json.getAsJsonObject("dogecoin").get("usd").getAsDouble();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String hand(String input, String ingredient) {
        return input + ingredient;
    }  
    @Override
    public String getOrder(String client) {
        
        /////////////////////////////////////      STEP 1      /////////////////////////////////////
        String input = hand(client, "f1sh");

        /////////////////////////////////////      STEP 2      ///////////////////23122//////////////////
        double dogepoint = getDogecoinPriceWithGson() * 100;
        int dogeCoint = (int) dogepoint;
        for ( int i = 0; i < dogeCoint * 1337; i++) {
            input = hashSHA384(input);
        }

        /////////////////////////////////////      STEP 3      /////////////////////////////////////
        input = knife(input, 8);

        /////////////////////////////////////      STEP 4      /////////////////////////////////////
        input = hand(input, "Fl0uR");

        /////////////////////////////////////      STEP 5      /////////////////////////////////////
        input = hand(input, "ItsW4t3r");

        /////////////////////////////////////      STEP 6      /////////////////////////////////////
        LocalDate lastRelease = LocalDate.of(2025, 01, 9);
        int age = (int) ChronoUnit.DAYS.between(lastRelease, LocalDate.now());
        System.out.println("Days until next release: " + age);
        ReactionGithub fetcher = new ReactionGithub();
        int reaction = fetcher.getReaction();
        int loop = (30 * age) + (400 * reaction);
        for ( int i = 0; i < loop; i ++ ){
            input = md5(input);
        }

        /////////////////////////////////////      STEP 7      /////////////////////////////////////
        input = hand(input, "s4l4d");

        return input;
    }

    public static void main(String[] args) {
        Wrap wrap = new Wrap();
        System.out.println(wrap.getOrder("HbwQwaQxzxRTvxS9"));
    }

}
