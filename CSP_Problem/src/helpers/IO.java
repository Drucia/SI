package helpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class IO {
    public static final char GRATER_THEN = '>';
    public static final char SMALLER_THEN = '<';
    private static final String PATH = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\CSP_Problem\\src\\CSP_2019_dane_testowe_v1.0\\";
    public static ArrayList<Integer> domain;
    public static HashMap<Character, HashMap<Integer, Integer>> matrix;
    public static HashMap<String, String> constraints;

    public void readFutoshikiData(String name) {
        File file = new File(PATH + name);
        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);

            matrix = new HashMap<>();
            constraints = new HashMap<>();

            int dimension = Integer.parseInt(reader.readLine());

            domain = new ArrayList<>(IntStream.range(1, dimension)
                    .boxed()
                    .collect(toList()));

            reader.readLine();

            // read matrix

            char rowChar = 'A';
            for (int row = 1; row <= dimension; row++, rowChar++)
            {
                String[] line = reader.readLine().split(";");
                HashMap<Integer, Integer> tmp = new HashMap<>();

                for (int col = 1; col <= dimension; col++)
                    tmp.put(col, Integer.parseInt(line[col-1]));

                matrix.put(rowChar, tmp);
            }



        } catch (Exception e) {
            System.out.println(e.toString());

        }
    }
}
