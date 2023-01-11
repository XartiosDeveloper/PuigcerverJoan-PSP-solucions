package ud3.examples.cinema.server;

import ud3.examples.cinema.models.Film;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CinemaServer {
    private final ServerSocket server;
    private final List<HandleClient> clients;
    private final List<Film> films;
    private boolean running;

    /**
     * Crea un servidor ServerSocket a partir del port.
     *
     * @param port Port on escoltarà el servidor
     * @throws IOException Excepcions del constructor ServerSocket
     */
    public CinemaServer(int port) throws IOException {
        server = new ServerSocket(port);
        clients = new ArrayList<>();
        films = new ArrayList<>();
        running = true;
    }

    public void addFilms(Film f){
        films.add(f);
    }

    public List<Film> getFilms(){
        return films;
    }
    public Film getFilm(int i){
        return films.get(i);
    }

    /**
     * Fil d'execució del servidor.
     * <p>
     * El servidor escolta el port i espera noves connexions.
     * Quan una nou client es connecta, es crea un objecte HandleClient,
     * que gestionarà la comunicació amb aquest client en un fil distint.
     * <p>
     * D'aquesta manera, el servidor pot continuar escoltant i esperant
     * noves connexions mentres cada fil gestiona la comunicació
     * amb cada client.
     */
    public void run() {
        while (running){
            try {
                Socket client = server.accept();
                System.out.println("Nou client acceptat.");
                HandleClient handleClient = new HandleClient(client, this);
                clients.add(handleClient);
                handleClient.start();
            } catch (IOException e) {
                System.err.println("Error while accepting new connection");
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            CinemaServer server = new CinemaServer(1234);
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
