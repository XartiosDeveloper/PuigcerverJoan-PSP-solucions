package ud4.examples.jsse;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateServer {
    public static void main(String[] args) {
        try {
            int port = 1234;
            System.out.println("Creant el Socket servidor en el port: " + port);

            System.setProperty("javax.net.ssl.keyStore", "files/ud4/KeyStore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            System.setProperty("javax.net.ssl.trustStore", "files/ud4/truststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");

            // Abans
            // ServerSocket server = new ServerSocket(port);
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket server = (SSLServerSocket) sslserversocketfactory.createServerSocket(1234);

            System.out.println("Esperant connexions...");
            // Aquest Socket es de tipus SSLSocket
            SSLSocket connexio = (SSLSocket) server.accept();
            System.out.println("Connectat amb el client!");

            BufferedReader in = new BufferedReader(new InputStreamReader(connexio.getInputStream()));
            // Activem l'opció autoFlush
            PrintWriter out = new PrintWriter(connexio.getOutputStream(), true);

            System.out.println("Esperant missatge des del client...");
            String missatge = in.readLine();
            System.out.println("Sha rebut el missatge:");
            System.out.println(missatge);

            String resposta = "Rebut!";
            System.out.println("S'ha enviat el missatge: " + resposta);
            out.println(resposta);

            System.out.println("Tancant el servidor...");
            connexio.close();
            server.close();
            System.out.println("Tancat.");
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
