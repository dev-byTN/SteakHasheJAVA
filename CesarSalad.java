import java.security.MessageDigest;

public class CesarSalad {

    // Méthode de hachage MD5
    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOrder(String client) {
        String input = client;

        // Étape 1 : Prendre le poulet
        input += "Ch1cK3n"; // ID: 10

        // Étape 2 : Trancher le poulet en petits morceaux avec le couteau
        input = input.substring(0, input.length() / 2); // Troncature à la moitié de la chaîne

        // Étape 3 : Cuire avec la poêle (10 + 20 itérations)
        for (int i = 0; i < 10 + 20; i++) {
            input = md5(input);
        }

        // Étape 4 : Ajouter la salade
        input += "S4lad"; // ID: 2

        // Étape 5 : Ajouter le pain
        input += "Br3ad"; // ID: 5

        // Étape 6 : Découper en petits morceaux avec le couteau
        input = input.substring(0, input.length() / 8); // Troncature à un huitième de la chaîne

        // Retour du résultat final
        return input;
    }

    public static void main(String[] args) {
        CesarSalad cesarSalad = new CesarSalad();
        System.out.println(cesarSalad.getOrder("5wVZjMgD5t4l8oPj"));
    }
}

