package helpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class IO {
    public static final int GRATER_THEN = 1;
    public static final int SMALLER_THEN = 0;
    public static final int TEST = 0;
    public static final int RESEARCH = 1;
    private static final String TEST_PATH = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\CSP_Problem\\src\\CSP_2019_dane_testowe_v1.0\\";
    private static final String RESEARCH_PATH = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\CSP_Problem\\src\\CSP_2019_dane_badawcze_v1.2\\";
    public static int dimension;
    public static HashMap<Integer, ArrayList<Integer>> matrix;
    public static HashMap<String, HashMap<String, Integer>> constraints;

    public static void readSkyscrapperData(int path, String name) {
        File file;
        if (path == TEST)
            file = new File(TEST_PATH + name);
        else
            file = new File(RESEARCH_PATH + name);
        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);

            matrix = new HashMap<>();
            constraints = new HashMap<>();

            dimension = Integer.parseInt(reader.readLine());

            ArrayList<String[]> lines = new ArrayList<>();
            String curr_line;

            while ((curr_line = reader.readLine()) != null)
            {
                lines.add(curr_line.split(";"));
            }

                for (int row = 0; row < dimension; row++)
                {
                    ArrayList<Integer> tmp = new ArrayList<>();

                    for (int col = 0; col < dimension; col++)
                    {
                        tmp.add(0);

                        StringBuilder variable_builder = new StringBuilder();
                        variable_builder.append(row);
                        variable_builder.append(col);
                        String var = variable_builder.toString();
                        HashMap<String, Integer> temp = new HashMap<>();

                        for (int l = 0; l<lines.size(); l++)
                        {
                            String con = lines.get(l)[0];

                            if (con.equals("G") || con.equals("D"))
                                temp.put(con, Integer.parseInt(lines.get(l)[col+1]));
                            else
                                temp.put(con, Integer.parseInt(lines.get(l)[row+1]));
                        }
                        constraints.put(var, temp);
                    }

                    matrix.put(row, tmp);
                }

                reader.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void readFutoshikiData(int path, String name) {
        File file;
        if (path == TEST)
            file = new File(TEST_PATH + name);
        else
            file = new File(RESEARCH_PATH + name);
        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);

            matrix = new HashMap<>();
            constraints = new HashMap<>();

            dimension = Integer.parseInt(reader.readLine());

            reader.readLine(); // START: line

            // read matrix

            for (int row = 0; row < dimension; row++) {
                String[] line = reader.readLine().split(";");
                ArrayList<Integer> tmp = new ArrayList<>();

                for (int col = 0; col < dimension; col++) {
                    int value = Integer.parseInt(line[col]);
                    tmp.add(value);
                }
                matrix.put(row, tmp);
            }

            reader.readLine(); // REL: line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] constr = line.split(";");
                String first = ((int) (constr[0].charAt(0)) - 65) + "" + (constr[0].charAt(1) - '1');
                String second = ((int) (constr[1].charAt(0)) - 65) + "" + (constr[1].charAt(1) - '1');
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

            reader.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
