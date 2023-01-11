package ud3.examples.cinema.client;

import ud3.examples.cinema.models.Film;
import ud3.examples.cinema.models.Request;
import ud3.examples.cinema.models.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CinemaClient {
    private final Socket socket;
    private final Scanner scanner;
    private final ObjectInputStream objIn;
    private final ObjectOutputStream objOut;

    public CinemaClient(String host, int port) throws IOException {
        this.scanner = new Scanner(System.in);
        this.socket = new Socket(host, port);
        this.objOut = new ObjectOutputStream(socket.getOutputStream());
        this.objIn = new ObjectInputStream(socket.getInputStream());
    }

    private void sendFilm(Film f) throws IOException, ClassNotFoundException {
        Request req = new Request(RequestType.POST, f);
        objOut.writeObject(req);
        Request response = (Request) objIn.readObject();
        if(response.getType() == RequestType.ERROR)
            System.err.printf("ERROR: %s\n", response.getMessage());
        else if(response.getType() == RequestType.SUCCESS)
            System.out.printf("%s\n", response.getMessage());
    }
    private Film receiveFilm(int id) throws IOException, ClassNotFoundException {
        Request req = new Request(RequestType.GET, id);
        objOut.writeObject(req);
        Request in = (Request) objIn.readObject();
        if(in.getType() == RequestType.ERROR)
            System.err.printf("ERROR: %s\n", in.getMessage());
        return (Film) in.getObject();
    }

    private void printMenuActions(){
        System.out.println("1. Afegir pel·lícula.");
        System.out.println("2. Obtenir pel·lícula.");
        System.out.println("0. Eixir.");
    }

    private int askUserAction(int max){
        System.out.print("Introdueix la teua elecció: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        while (action < 0 || action > max){
            System.out.print("La elecció introduida no està entre els valors vàlids.");
            System.out.print("Introdueix la teua elecció: ");
            action = scanner.nextInt();
            scanner.nextLine();
        }

        return action;
    }
    public void menu(){
        while(true) {
            printMenuActions();
            int action = askUserAction(2);
            switch (action){
                case 0:
                    return;
                case 1:
                    try {
                        addFilm();
                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Error afegint una pel·lícula.");
                    }
                    break;
                case 2:
                    try {
                        getFilm();
                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Error obtenint una pel·lícula.");
                    }
                    break;
            }
        }
    }

    private void addFilm() throws IOException, ClassNotFoundException {
        System.out.println("AFEGIR PEL·LÍCULA");
        System.out.print("Introdueix el nom de la pel·lícula: ");
        String nom = scanner.nextLine();
        System.out.print("Introdueix el any de publicació de la pel·lícula: ");
        int year = scanner.nextInt();
        System.out.print("Introdueix la duració de la pel·lícula: ");
        int duration = scanner.nextInt();
        Film film = new Film(nom, year, duration);
        sendFilm(film);
    }

    private void getFilm() throws IOException, ClassNotFoundException {
        System.out.println("OBTENIR PEL·LÍCULA");
        System.out.print("Introdueix la id de la pel·lícula: ");
        int id = scanner.nextInt();
        Film film = receiveFilm(id);
        if (film != null)
            System.out.printf("S'ha obtingut la pel·lícula %s.\n", film);
    }

    public static void main(String[] args) {
        System.out.println("Connectant-se amb el servidor...");
        try {
            CinemaClient cinema = new CinemaClient("localhost", 1234);
            cinema.menu();
        } catch (IOException e){
            System.err.println("Error connectant-se amb el servidor.");
        }
    }
}