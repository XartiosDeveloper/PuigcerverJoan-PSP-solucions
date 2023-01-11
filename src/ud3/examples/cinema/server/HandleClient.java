package ud3.examples.cinema.server;

import ud3.examples.cinema.models.Film;
import ud3.examples.cinema.models.Request;
import ud3.examples.cinema.models.RequestType;

import java.io.*;
import java.net.Socket;

/**
 * Classe que gestiona la comunicació del servidor
 * amb un únic client en un fil d'execució independent.
 */
public class HandleClient extends Thread {
    private final Socket client;
    private final CinemaServer server;
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;

    public HandleClient(Socket client, CinemaServer server) throws IOException {
        this.client = client;
        this.server = server;
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
            Request req;
            while((req = (Request) objIn.readObject()) != null){
                if (req.getType() == RequestType.POST){
                    Film film = (Film) req.getObject();
                    server.addFilms(film);

                    String message = String.format("Film %s added.", film);
                    Request response = new Request(RequestType.SUCCESS, null, message);
                    System.out.println(message);
                    objOut.writeObject(response);

                } else if (req.getType() == RequestType.GET) {
                    int id = (Integer) req.getObject();

                    Request response;
                    if(id >= server.getFilms().size())
                        response = new Request(RequestType.ERROR, null,
                                String.format("No s'ha trobat cap pel·licula amb id %d", id));
                    else
                        response = new Request(RequestType.SUCCESS, server.getFilm(id));

                    objOut.writeObject(response);
                }
            }
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while handling client.");
            System.err.println(e.getMessage());
        }
    }
}
