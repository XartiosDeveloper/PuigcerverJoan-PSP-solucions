package ud4.examples;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class AES {

    public static SecretKey keygenKeyGeneration(int keySize) {
        SecretKey sKey = null;
        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();

            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return sKey;
    }

    public static SecretKey passwordKeyGeneration(String password, int keySize) {
        SecretKey sKey = null;
        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                byte[] data = password.getBytes(StandardCharsets.UTF_8);
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize/8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
            }
        }
        return sKey;
    }

    public static String encrypt(SecretKey key, String str){
        try {
            byte[] data = encrypt(key, str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception ex){
            System.err.println("Error xifrant les dades: " + ex);
        }
        return null;
    }
    public static String decrypt(SecretKey key, String str){
        try {
            byte[] data = Base64.getDecoder() .decode(str);
            byte[] decrypted = decrypt(key, data);
            return new String(decrypted);
        } catch (Exception ex){
            System.err.println("Error desxifrant les dades: " + ex);
        }
        return null;
    }

    public static byte[] encrypt(SecretKey key, byte[] data) throws Exception {
        return aes(key, data, Cipher.ENCRYPT_MODE);
    }
    public static byte[] decrypt(SecretKey key, byte[] data) throws Exception {
        return aes(key, data, Cipher.DECRYPT_MODE);
    }
    private static byte[] aes(SecretKey key, byte[] data, int opmode) throws Exception {
        byte[] encryptedData = null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(opmode, key);
        encryptedData =  cipher.doFinal(data);
        return encryptedData;
    }

    public static void main(String[] args) {
        String message = "Aquest és un missatge super secret";
        ArrayList<SecretKey> keys = new ArrayList<>();

        String password = "veryDifficultPassword";
        keys.add(passwordKeyGeneration("veryDifficultPassword", 256));
        System.out.printf("Key generated with password: %s\n", password);

        keys.add(keygenKeyGeneration(256));

        for (SecretKey key : keys) {
            System.out.printf("Key: %s\n", key);
            System.out.printf("Message: %s\n", message);
            String encrypted = encrypt(key, message);
            System.out.printf("Encrypted message: %s\n", encrypted);
            String decrypted = decrypt(key, encrypted);
            System.out.printf("Decrypted message: %s\n", decrypted);
            System.out.println();
        }
    }
}
