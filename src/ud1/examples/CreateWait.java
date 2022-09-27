package ud1.examples;

import java.io.IOException;
import java.util.Arrays;

public class CreateWait {
    public static void main(String[] args) {
        if(args.length <= 0) {
            System.err.println("Es necesita un programa a que executar.");
            System.exit(-1);
        }

        ProcessBuilder pb = new ProcessBuilder(args);
        try {
            Process process = pb.start();
            int returnCode = process.waitFor();
            System.out.println(
              "L'execució de " + Arrays.toString(args)
              + " retorna " + returnCode
            );
        } catch (IOException ex) {
            System.err.println("Excepció d'E/S");
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("El procés fill ha finalizat de manera incorrecta.");
        }
    } 
}

