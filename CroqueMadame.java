import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CroqueMadame {

    public String hashSHA384(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOrder(String client) {
        String input = client;

        // Étape 1 : Prendre un œuf
        input += "gg3gg"; // ID: 11

        // Étape 2 : Cuire l'œuf avec la poêle
        for (int i = 0; i < 90 + 98; i++) { // STEP 2 //
            input = md5(input);
        }

        // Étape 3 : Prendre le pain
        input += "Br3ad"; // ID: 5

        // Étape 4 : Prendre le jambon
        input += "h4m"; // ID: 4

        // Étape 5 : Ajouter du fromage
        input += "ch333s3"; // ID: 15

        // Étape 6 : Cuire le tout au four
        for (int i = 0; i < 50 + 60; i++) { // STEP 2 //
            input = hashSHA384(input);
        }

        return input;
    }

    public static void main(String[] args) {
        CroqueMadame client = new CroqueMadame();
        System.out.println(client.getOrder("YmT5kypEANVhqLNz"));
    }
}
