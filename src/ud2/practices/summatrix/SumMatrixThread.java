package ud2.practices.summatrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SumMatrixThread {
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
        List<List<Integer>> matrix = readMatrixFromCSV("files/ud2/data_matrix.csv");
        System.out.println(matrix);
    }
}
