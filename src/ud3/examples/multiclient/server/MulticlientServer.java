package ud3.examples.multiclient.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MulticlientServer extends Thread {
    ServerSocket server;

    List<HandleClient> clients;
    boolean running;

    public MulticlientServer(int port) throws IOException {
        server = new ServerSocket(port);
        clients = new ArrayList<>();
        running = true;
    }

    public void close(){
        running = false;
        for (HandleClient client : clients) {
            try {
                client.close();
            } catch (IOException ignored) {
            }
            client.interrupt();
        }
        this.interrupt();
    }

    @Override
    public void run() {
        while (running){
            try {
                Socket client = server.accept();
                System.out.println("Nou client acceptat.");
                HandleClient handleClient = new HandleClient(client);
                clients.add(handleClient);
                handleClient.start();
            } catch (IOException e) {
                System.err.println("Error while accepting new connection");
                System.err.println(e.getMessage());
            }
        }
    }


}
