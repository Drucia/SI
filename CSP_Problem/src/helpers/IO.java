package helpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class IO {
    public static final int GRATER_THEN = 1;
    public static final int SMALLER_THEN = 0;
    private static final String PATH = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\CSP_Problem\\src\\CSP_2019_dane_testowe_v1.0\\";
    public static ArrayList<Integer> domain;
    public static HashMap<Character, ArrayList<Integer>> matrix;
    public static HashMap<String, HashMap<String, Integer>> constraints;

//    public static void readSkyscrapperData(String name) {
//        File file = new File(PATH + name);
//        try {
//            FileReader freader = new FileReader(file);
//            BufferedReader reader = new BufferedReader(freader);
//
//            matrix = new HashMap<>();
//            constraints = new HashMap<>();
//
//            int dimension = Integer.parseInt(reader.readLine());
//
//            domain = new ArrayList<>(IntStream.range(1, dimension+1)
//                    .boxed()
//                    .collect(toList()));
//
//            // fill matrix by zeros
//
//            char rowChar = 'A';
//
//            // read constraints
//
//            String line;
//            while ((line = reader.readLine()) != null)
//            {
//                String[] constr = line.split(";");
//
//                for (int row = 1; row <= dimension; row++, rowChar++)
//                {
//                    HashMap<Integer, Integer> tmp = new HashMap<>();
//
//                    for (int col = 1; col <= dimension; col++)
//                        tmp.put(col, 0);
//
//                    matrix.put(rowChar, tmp);
//                }
//
//                String first = constr[0];
//                String second = constr[1];
//                HashMap<String, Integer> tmp = constraints.get(first);
//
//                if (tmp == null)
//                    tmp = new HashMap<>();
//
//                // first is smaller than second
//                tmp.put(second, SMALLER_THEN);
//
//                if (constraints.containsKey(first))
//                    constraints.replace(first, tmp);
//                else
//                    constraints.put(first, tmp);
//
//                tmp = constraints.get(second);
//
//                if (tmp == null)
//                    tmp = new HashMap<>();
//
//                tmp.put(first, GRATER_THEN);
//
//                if (constraints.containsKey(second))
//                    constraints.replace(second, tmp);
//                else
//                    constraints.put(second, tmp);
//            }
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }

    public static void readFutoshikiData(String name) {
        File file = new File(PATH + name);
        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);

            matrix = new HashMap<>();
            constraints = new HashMap<>();

            int dimension = Integer.parseInt(reader.readLine());

            domain = new ArrayList<>(IntStream.range(1, dimension + 1)
                    .boxed()
                    .collect(toList()));

            reader.readLine(); // START: line

            // read matrix

            char rowChar = 'A';
            for (int row = 1; row <= dimension; row++, rowChar++) {
                String[] line = reader.readLine().split(";");
                //HashMap<Integer, Integer> tmp = new HashMap<>();
                ArrayList<Integer> tmp = new ArrayList<>();

                for (int col = 1; col <= dimension; col++)
                    tmp.add(Integer.parseInt(line[col - 1]));
                    //tmp.put(col, Integer.parseInt(line[col - 1]));

                matrix.put(rowChar, tmp);
            }

            reader.readLine(); // REL: line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] constr = line.split(";");
                String first = constr[0];
                String second = constr[1];
                HashMap<String, Integer> tmp = constraints.get(first);

                if (tmp == null)
                    tmp = new HashMap<>();

                // first is smaller than second
                tmp.put(second, SMALLER_THEN);

                if (constraints.containsKey(first))
                    constraints.replace(first, tmp);
                else
                    constraints.put(first, tmp);

                tmp = constraints.get(second);

                if (tmp == null)
                    tmp = new HashMap<>();

                tmp.put(first, GRATER_THEN);

                if (constraints.containsKey(second))
                    constraints.replace(second, tmp);
                else
                    constraints.put(second, tmp);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
