package ud2.practices.summatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumMatrix {
    public static List<List<Integer>> readMatrixFromCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines()
                    .map(
                        x -> Arrays.stream(x.split(","))
                             .mapToInt(Integer::parseInt)
                             .boxed()
                             .toList()
                    ).toList();
        } catch (Exception e) {
            System.out.printf("Error reading CSV file: %s\n", path);
        }
        return null;
    }

    public static void main(String[] args) {
        String CSVPath = "files/ud2/data_matrix.csv";
        List<List<Integer>> matrix = readMatrixFromCSV(CSVPath);

        // TODO

        for(List<Integer> row : matrix){
            // TODO: Create thread
            // TODO: Start thread
        }

        // TODO: Wait for the threads to finish

        int result = 0;
        // TODO: Sum the result of each thread

        System.out.printf("La suma dels valors en \"%s\" és %d\n", CSVPath, result);
    }
}
