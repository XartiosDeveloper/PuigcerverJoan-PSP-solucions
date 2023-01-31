package ud4.examples.jsse;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;

public class CreateClient {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            System.out.println("Creant el Socket client.");

            System.setProperty("javax.net.ssl.trustStore", "files/ud4/client_truststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");

            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) sslsocketfactory.createSocket("localhost", 1234);
            // Socket socket = new Socket(host, 1234);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Es pot utilitzar l'opció autoflush per forçar l'enviament de dades
            // després de cada print()
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String missatge = "Aquest missatge ha segut enviat des del client.";
            out.println(missatge);
            out.flush(); // Aquesta línia no és necessària amb l'opció autoFlush
            System.out.println("S'ha enviat el missatge.");

            System.out.println("Esperant resposta");
            String resposta = in.readLine();

            System.out.println("Resposta del servidor:");
            System.out.println(resposta);

            System.out.println("Tancant el socket...");
            socket.close();
            System.out.println("Tancat");
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
