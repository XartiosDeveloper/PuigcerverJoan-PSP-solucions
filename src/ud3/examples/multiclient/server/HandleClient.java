package ud3.examples.multiclient.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandleClient extends Thread {
    private Socket client;
    private String nom;
    private BufferedReader in;
    private PrintWriter out;

    public HandleClient(Socket client) throws IOException {
        this.client = client;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        nom = null;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void close() throws IOException {
        client.close();
    }
    @Override
    public void run() {
        try {
            setNom(in.readLine());
            System.out.printf("%s s'ha identificat.\n", getNom());

            String message;
            while((message = in.readLine()) != null){
                System.out.printf("%s: %s\n", getNom(), message);
            }
            System.out.printf("%s has disconnected.\n");
            client.close();
        } catch (IOException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
        }
    }
}
