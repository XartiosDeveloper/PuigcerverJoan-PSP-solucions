package ud4.examples;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyPair;

public class SaveKeyFile {
    public static void saveKeyToFile(Key key, String path){
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new FileOutputStream(path));
            out.write(key.getEncoded());
            out.flush();
        } catch (Exception e) {
            System.out.println("Error al guardar la clau: " + e);
        }
    }
    public static Key loadKeyToFile(String path){
        return null;
    }

    public static void main(String[] args) {
        try {
            KeyPair pair = AsymetricRSA.generateKeyPair(2048);
        } catch (Exception e) {
            System.out.println("Error generant el parell de claus: " + e);
        }
    }
}
