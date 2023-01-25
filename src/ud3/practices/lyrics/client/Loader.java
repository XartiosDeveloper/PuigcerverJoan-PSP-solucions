package ud3.practices.lyrics.client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Loader extends Thread {
    private final LyricsPlayer lyricsPlayer;
    private final PrintWriter out;
    private final BufferedReader in;


    public Loader(LyricsPlayer lyricsPlayer, String host, int port) throws IOException {
        this.lyricsPlayer = lyricsPlayer;
        Socket socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            int i = 0;
            boolean ended = false;
            while(!ended){
                out.printf("GET %d\n", i);
                String response = in.readLine();
                if(response.startsWith("ERROR:")){
                    ended = true;
                } else {
                    this.lyricsPlayer.addLine(response);
                    System.err.printf(" (Line %d loaded) ", i);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                }
                i++;
            }
            this.lyricsPlayer.setEnd(true);
        } catch (InterruptedException | IOException e) {
             System.err.println(e.getMessage());
        }
    }
}
