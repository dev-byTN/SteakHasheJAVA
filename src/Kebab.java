import org.bouncycastle.crypto.digests.Blake2bDigest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Kebab extends AbstractAction2 {

    // Méthode pour appliquer un hachage BLAKE2b
    public String hashBLAKE2b(String input) {
        try {
            Blake2bDigest digest = new Blake2bDigest(256); // Longueur du hachage : 256 bits
            byte[] inputBytes = input.getBytes();
            digest.update(inputBytes, 0, inputBytes.length);
            byte[] output = new byte[digest.getDigestSize()];
            digest.doFinal(output, 0);
            StringBuilder sb = new StringBuilder();
            for (byte b : output) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Retourne null en cas d'erreur
        }
    }

    // Vérifier s'il y a des incidents sur la ligne 7
    public boolean hasIncidentsOnLine7() {
        try {
            String apiUrl = "https://api-ratp.pierre-grimaud.fr/v4/traffic/metros/7";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                connection.disconnect();

                // Rechercher les mots-clés "incident" ou "perturbation"
                return response.toString().contains("incident") || response.toString().contains("perturbation");
            } else {
                System.err.println("Erreur lors de la requête : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Vérifier si le restaurant est ouvert
    public boolean isChezMarwanOpen() {
        Map<DayOfWeek, LocalTime[]> openingHours = new HashMap<>();
        openingHours.put(DayOfWeek.MONDAY, new LocalTime[] { LocalTime.of(11, 0), LocalTime.of(0, 0) });
        openingHours.put(DayOfWeek.TUESDAY, new LocalTime[] { LocalTime.of(11, 0), LocalTime.of(0, 0) });
        openingHours.put(DayOfWeek.WEDNESDAY, new LocalTime[] { LocalTime.of(11, 0), LocalTime.of(0, 0) });
        openingHours.put(DayOfWeek.THURSDAY, new LocalTime[] { LocalTime.of(11, 0), LocalTime.of(0, 0) });
        openingHours.put(DayOfWeek.FRIDAY, new LocalTime[] { LocalTime.of(11, 0), LocalTime.of(0, 0) });
        openingHours.put(DayOfWeek.SATURDAY, new LocalTime[] { LocalTime.of(11, 0), LocalTime.of(0, 0) });
        openingHours.put(DayOfWeek.SUNDAY, new LocalTime[] { LocalTime.of(11, 0), LocalTime.of(0, 0) });

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek today = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        if (openingHours.containsKey(today)) {
            LocalTime[] hours = openingHours.get(today);
            LocalTime openingTime = hours[0];
            LocalTime closingTime = hours[1];

            // Gestion des fermetures après minuit
            if (closingTime.equals(LocalTime.MIDNIGHT)) {
                closingTime = LocalTime.of(23, 59);
            }

            return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime);
        }

        return false;
    }
    @Override
    public String hand(String input, String ingredient) {
        return input + ingredient;
    }  

    @Override
    public String getOrder(String client) {
        RecipeFetcher recipe = new RecipeFetcher();
        recipe.fetchAndDisplayRecipes();
        String input = hand(client, "Ch1cK3n");
        input = hand(input, "sp!!!!!!c1es");

        // Étape 3 : Hacher avec le couteau
        if (hasIncidentsOnLine7() == false) {
            int sliceLength = input.length() / 3;
            input = knife(input, sliceLength); // Garde uniquement la première tranche
        }

        // Étape 4 : Cuire avec le rôtisseur
        int speed = recipe.getArg(47); // Exemple de valeur pour la vitesse
        int duration = recipe.getArg(48); // Exemple de valeur pour la durée
        int roasterIterations;

        if (isChezMarwanOpen()) {
            roasterIterations = speed + duration;
        } else {
            roasterIterations = speed * duration;
        }

        for (int i = 0; i < roasterIterations; i++) {
            input = hashBLAKE2b(input);
        }

        input = hand(input, "t0m4t0");
        input = hand(input, "s4l4d");
        input = hand(input, "t0r");
        input = knife(input, recipe.getArg(52));
        input = hand(input, "Br3ad");

        return input;
    }

    public static void main(String[] args) {
        Kebab kebab = new Kebab();
        System.out.println(kebab.getOrder("NA39oWJgI1XuGJZ7"));
    }
}
