package ud3.exercises.cinema.server;

import ud3.exercises.cinema.models.Film;

import java.io.*;
import java.net.Socket;

/**
 * Classe que gestiona la comunicació del servidor
 * amb un únic client en un fil d'execució independent.
 */
public class HandleClient extends Thread {
    private final Socket client;
    private final CinemaServer server;
    private final BufferedReader in;
    private final ObjectInputStream objIn;
    private final PrintWriter out;
    private final ObjectOutputStream objOut;

    public HandleClient(Socket client, CinemaServer server) throws IOException {
        this.client = client;
        this.server = server;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        objIn = new ObjectInputStream(client.getInputStream());
        objOut = new ObjectOutputStream(client.getOutputStream());
    }

    /**
     * Fil d'execució independent per cada client.
     * <p>
     * Abans que res, el client s'identifica amb un nom.
     * Després, el servidor mostra els missatges que cada client ha enviat.
     */
    @Override
    public void run() {
        try {
            String message;
            while((message = in.readLine()) != null){
                if (message.startsWith("POST")){
                    try {
                        Film film = (Film) objIn.readObject();
                        server.addFilms(film);
                        System.out.printf("Film %s added.", film);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (message.startsWith("GET")) {
                    int id = Integer.parseInt(message.split(" ")[1]);
                    if(id >= server.getFilms().size()) {
                        objOut.writeObject(null);
                    } else
                        objOut.writeObject(server.getFilm(id));
                }
            }
            client.close();
        } catch (IOException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
        }
    }
}
