package ud4.exercises.sslchat.servidor;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer extends Thread {
    ServerSocket server;

    List<HandleClient> clients;
    boolean running;

    public ChatServer(int port) throws IOException {
        System.setProperty("javax.net.ssl.keyStore", "files/ud4/server_keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.server = sslserversocketfactory.createServerSocket(1234);

        clients = new ArrayList<>();
        running = true;
    }

    public void close(){
        running = false;
        this.interrupt();
    }

    public synchronized void removeClient(HandleClient hc){
        clients.remove(hc);
    }

    public void sendMessage(String msg){
        for(HandleClient hc : clients) {
            System.out.println(msg);
            hc.sendMessage(msg);
        }
    }

    @Override
    public void run() {
        while (running){
            try {
                Socket client = server.accept();
                HandleClient handleClient = new HandleClient(client, this);
                clients.add(handleClient);
                handleClient.start();
                System.out.println("Nova connexió acceptada.");
            } catch (IOException e) {
                System.err.println("Error while accepting new connection");
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            ChatServer server = new ChatServer(1234);
            server.start();

            scanner.nextLine();

            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
