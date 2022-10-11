package ud1.exercises;

import java.io.IOException;
import java.util.ArrayList;

public class ConsumeLineMultiProcess {
    public static void main(String[] args) {
        ArrayList<ProcessBuilder> builders = new ArrayList<>();
        builders.add(new ProcessBuilder("wsl.exe", "files/consume_line.sh", "files/colors.txt", "3").inheritIO());
        builders.add(new ProcessBuilder("wsl.exe", "files/consume_line.sh", "files/concessionari.csv", "1").inheritIO());
        builders.add(new ProcessBuilder("wsl.exe", "files/consume_line.sh", "files/lorem.txt", "2").inheritIO());

        ArrayList<Process> processes = new ArrayList<>();
        try {
            for (ProcessBuilder pb : builders)
                processes.add(pb.start());

            for (Process p : processes)
                p.waitFor();

            System.out.println("Els 3 processos han acabat.");
        } catch (IOException ex) {
            System.err.println("Excepció d'E/S.");
            System.out.println(ex.getMessage());
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("El procés fill ha finalitzat de manera incorrecta.");
            System.exit(-1);
        }
    }
}
