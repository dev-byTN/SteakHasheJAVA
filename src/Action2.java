import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Definition of the Action2 interface, used to define common actions for all recipes.
interface Action2 {

    // Default method to calculate the MD5 hash of an input string.
    default String md5(String input) {
        try {
            // Create a MessageDigest instance for the MD5 algorithm.
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            // Compute the digest of the input string into bytes.
            byte[] array = md.digest(input.getBytes());
            // Use a StringBuffer to assemble the hash as a hexadecimal string.
            StringBuffer sb = new StringBuffer();
            // Convert each byte into a hexadecimal value.
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString(); // Return the MD5 hash as a hexadecimal string.
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace(); // Print any errors to the console.
        }
        return null; // Return null in case of an exception.
    }

    // Default method to calculate the SHA-256 hash of an input string.
    default String hashSHA256(String input) {
        try {
            // Create a MessageDigest instance for the SHA-256 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Compute the digest of the input string into bytes.
            byte[] messageDigest = md.digest(input.getBytes());
            // Use a StringBuilder to assemble the hash as a hexadecimal string.
            StringBuilder sb = new StringBuilder();
            // Convert each byte into a hexadecimal value.
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // Return the SHA-256 hash as a hexadecimal string.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw a runtime exception if the algorithm is not found.
        }
    }

    // Default method to calculate the SHA-512 hash of an input string.
    default String hashSHA512(String input) {
        try {
            // Create a MessageDigest instance for the SHA-512 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // Compute the digest of the input string into bytes.
            byte[] messageDigest = md.digest(input.getBytes());
            // Use a StringBuilder to assemble the hash as a hexadecimal string.
            StringBuilder sb = new StringBuilder();
            // Convert each byte into a hexadecimal value.
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // Return the SHA-512 hash as a hexadecimal string.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw a runtime exception if the algorithm is not found.
        }
    }

    // Default method to calculate the SHA-384 hash of an input string.
    default String hashSHA384(String input) {
        try {
            // Create a MessageDigest instance for the SHA-384 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            // Compute the digest of the input string into bytes.
            byte[] messageDigest = md.digest(input.getBytes());
            // Use a StringBuilder to assemble the hash as a hexadecimal string.
            StringBuilder sb = new StringBuilder();
            // Convert each byte into a hexadecimal value.
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // Return the SHA-384 hash as a hexadecimal string.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw a runtime exception if the algorithm is not found.
        }
    }

    // Default method to calculate the SHA-1 hash of an input string.
    default String hashSHA1(String input) {
        try {
            // Create a MessageDigest instance for the SHA-1 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // Compute the digest of the input string into bytes.
            byte[] messageDigest = md.digest(input.getBytes());
            // Use a StringBuilder to assemble the hash as a hexadecimal string.
            StringBuilder sb = new StringBuilder();
            // Convert each byte into a hexadecimal value.
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // Return the SHA-1 hash as a hexadecimal string.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw a runtime exception if the algorithm is not found.
        }
    }

    // Default method to cut a string into parts using the knife metaphor.
    default String knife(String input, int part) {
        // Divide the input string into a specified number of parts and return the first
        // part.
        return input.substring(0, input.length() / part);
    }

    // Abstract method that requires implementation for handling an input and an
    // ingredient.
    String hand(String input, String ingredient);
}

// Abstract class implementing the Action2 interface.
abstract class AbstractAction2 implements Action2 {
    // Abstract method to get the order associated with a client.
    public abstract String getOrder(String client);
}
