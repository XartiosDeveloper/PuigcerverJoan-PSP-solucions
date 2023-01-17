package ud4.examples;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class AsymetricRSA {

    public static KeyPair generateKeyPair(int len) throws Exception {
        if (!(len == 1024 || len == 2048))
            throw new Exception("La mida de la clau no és vàlida");

        KeyPair keys = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(len);
            keys = keyGen.genKeyPair();
        } catch (Exception ex) {
            System.err.println("Generador no disponible.");
        }
        return keys;
    }

    public static String crypt(PublicKey key, String str){
        try {
            byte[] data = crypt(key, str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception ex){
            System.err.println("Error xifrant les dades: " + ex);
        }
        return null;
    }
    public static String decrypt(PrivateKey key, String str){
        try {
            byte[] data = Base64.getDecoder() .decode(str);
            byte[] decrypted = decrypt(key, data);
            return new String(decrypted);
        } catch (Exception ex){
            System.err.println("Error desxifrant les dades: " + ex);
        }
        return null;
    }

    public static byte[] crypt(PublicKey key, byte[] data) throws Exception {
        return rsa(key, data, Cipher.ENCRYPT_MODE);
    }
    public static byte[] decrypt(PrivateKey key, byte[] data) throws Exception {
        return rsa(key, data, Cipher.DECRYPT_MODE);
    }
    private static byte[] rsa(Key key, byte[] data, int opmode) throws Exception {
        byte[] encryptedData = null;
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding","SunJCE");
        cipher.init(opmode, key);
        encryptedData =  cipher.doFinal(data);
        return encryptedData;
    }

    public static void main(String[] args) {
        String message = "Aquest és un missatge super secret";
        KeyPair keys = null;
        try {
            keys = generateKeyPair(1024);

            System.out.printf("Public key: %s\n", keys.getPublic());
            System.out.printf("Private key: %s\n", keys.getPrivate());
            System.out.printf("Message: %s\n", message);
            String encrypted = crypt(keys.getPublic(), message);
            System.out.printf("Encrypted message: %s\n", encrypted);
            String decrypted = decrypt(keys.getPrivate(), encrypted);
            System.out.printf("Decrypted message: %s\n", decrypted);
            System.out.println();
        } catch (Exception e) {
            System.err.println("Error creant el parell de claus: " + e);
        }
    }
}
