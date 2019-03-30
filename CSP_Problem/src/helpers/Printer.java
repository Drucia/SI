package helpers;

import java.util.HashMap;

public class Printer {
    public static void printFutoshiki(HashMap<Character, HashMap<Integer, Integer>> matrix, HashMap<String, HashMap<String, Character>> constraints)
    {
        StringBuilder line_builder, line_con_builder;
        StringBuilder variable_builder, next_variable;
        char rowChar = 'A';
        int dimension = matrix.size();

        for (int i=1; i<=dimension; i++, rowChar++) // row loop
        {
            HashMap<Integer, Integer> row = matrix.get(rowChar);
            line_builder = new StringBuilder("|  ");
            line_con_builder = new StringBuilder("- ");

            for (int j=1; j<=dimension; j++) // col loop
            {
                line_builder.append(row.get(j) + "  ");
                variable_builder = new StringBuilder();
                variable_builder.append(rowChar);
                variable_builder.append(j);
                HashMap<String, Character> cons;
                String variable = variable_builder.toString();

                if ((cons=constraints.get(variable)) != null && j != dimension)
                {
                    next_variable = new StringBuilder();
                    next_variable.append(rowChar);
                    next_variable.append(j+1);
                    String next_var = next_variable.toString();

                    if(cons.containsKey(next_var))
                        line_builder.append(cons.get(next_var) + "  ");
                    else
                        line_builder.append("|  ");
                }
                else
                    line_builder.append("|  ");

                if (cons != null && i != dimension)
                {
                    next_variable = new StringBuilder();
                    char tmp = rowChar;
                    tmp++;
                    next_variable.append(tmp);
                    next_variable.append(j);
                    String next_var = next_variable.toString();

                    if(cons.containsKey(next_var))
                        line_con_builder.append((cons.get(next_var).equals(IO.GRATER_THEN) ? "\\ / - " : "/ \\ - "));
                    else
                        line_con_builder.append(" -  - ");
                }
                else
                    line_con_builder.append(" -  - ");
            }
            System.out.println(line_builder);

            if (i != dimension)
                System.out.println(line_con_builder);
        }
    }
}
