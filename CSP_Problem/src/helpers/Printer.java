package helpers;

import app.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Printer {
    public static void printFutoshiki(HashMap<Integer, ArrayList<Integer>> matrix, HashMap<String, HashMap<String, Integer>> constraints)
    {
        StringBuilder line_builder, line_con_builder;
        StringBuilder variable_builder, next_variable;

        int dimension = matrix.size();

        for (int i=0; i<dimension; i++) // row loop
        {
            ArrayList<Integer> row = matrix.get(i);
            line_builder = new StringBuilder("|  ");
            line_con_builder = new StringBuilder("- ");

            for (int j=0; j<dimension; j++) // col loop
            {
                line_builder.append(row.get(j) + "  ");
                variable_builder = new StringBuilder();
                variable_builder.append(i);
                variable_builder.append(j);
                HashMap<String, Integer> cons;
                String variable = variable_builder.toString();

                if ((cons=constraints.get(variable)) != null && j != dimension-1)
                {
                    next_variable = new StringBuilder();
                    next_variable.append(i);
                    next_variable.append(j+1);
                    String next_var = next_variable.toString();

                    if(cons.containsKey(next_var))
                        line_builder.append(cons.get(next_var).equals(IO.GRATER_THEN) ? ">  " : "<  ");
                    else
                        line_builder.append("|  ");
                }
                else
                    line_builder.append("|  ");

                if (cons != null && i != dimension-1)
                {
                    next_variable = new StringBuilder();
                    next_variable.append(i+1);
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

            if (i != dimension-1)
                System.out.println(line_con_builder);
        }
    }

    public static void printSkyscrapper(HashMap<Integer, ArrayList<Integer>> matrix, HashMap<String, HashMap<String, Integer>> constraints)
    {
        StringBuilder first_line_builder = new StringBuilder("  | ");
        StringBuilder last_line_builder = new StringBuilder("  | ");
        StringBuilder sep_line_builder = new StringBuilder("-- ");
        StringBuilder line_builder, variable_builder;
        int dimension = matrix.size();

        boolean is_done = false;

        for (int i=0; i<dimension; i++)
        {
            line_builder = new StringBuilder();

            ArrayList<Integer> row_val = matrix.get(i);

            for (int j=0; j<dimension;j++)
            {
                variable_builder = new StringBuilder();
                variable_builder.append(i);
                variable_builder.append(j);
                HashMap<String, Integer> cons = constraints.get(variable_builder.toString());

                if (!is_done)
                {
                    first_line_builder.append(cons.get("G") + " | ");
                    last_line_builder.append(cons.get("D") + " | ");
                    sep_line_builder.append(" -  ");
                }

                if (j == 0)
                    line_builder.append(cons.get("L") + " | ");

                line_builder.append(row_val.get(j) + " | ");

                if (j == dimension-1)
                    line_builder.append(cons.get("P"));

            }

            if(!is_done)
            {
                is_done = true;
                System.out.println(first_line_builder);
                sep_line_builder.append("--");
                System.out.println(sep_line_builder);
            }

            System.out.println(line_builder);
            System.out.println(sep_line_builder);
        }

        System.out.println(last_line_builder);
    }

    public static void printSkyscrapperHtml(String name, ArrayList<HashMap<Integer,ArrayList<Integer>>> scores, HashMap<String, HashMap<String, Integer>> constraints)
    {
        try {
            // only for research
            File file = new File(Controller.main_path + "\\html\\" + name);
            file.mkdir();
            for (int i=0; i<scores.size(); i++) {
                HashMap<Integer,ArrayList<Integer>> score = scores.get(i);
                file = new File(Controller.main_path + "\\html\\" + name + "\\" + i + ".html");
                boolean is_created = file.createNewFile();

                if(is_created) {
                    FileWriter f_writer = new FileWriter(file);
                    BufferedWriter writer = new BufferedWriter(f_writer);

                    StringBuilder s_res = new StringBuilder();
                    s_res.append("<!DOCTYPE html>\n");
                    s_res.append("<html>\n");
                    s_res.append("<table border='1' style='border-collapse:separate; width: 15%; text-align: center;'>\n");

                    // place for score
                    for (int r = 0; r < score.size() + 2; r++) {
                        s_res.append("<tr>\n");
                        for (int c = 0; c < score.size() + 2; c++) {
                            if (r == 0) // for first row
                            {
                                if (c != 0 && c != score.size() + 1) // only constraints
                                {
                                    s_res.append("<td>\n");
                                    s_res.append(constraints.get(r + "" + (c - 1)).get("G") + "\n");
                                    s_res.append("</td>\n");
                                }
                                else
                                {
                                    s_res.append("<td>\n");
                                    s_res.append("</td>\n");
                                }
                            } else if (r == score.size() + 1) // for last row
                            {
                                if (c != 0 && c != score.size() + 1) // only constraints
                                {
                                    s_res.append("<td>\n");
                                    s_res.append(constraints.get((r-2) + "" + (c - 1)).get("D") + "\n");
                                    s_res.append("</td>\n");
                                }
                                else
                                {
                                    s_res.append("<td>\n");
                                    s_res.append("</td>\n");
                                }
                            } else // for main data
                            {
                                s_res.append("<td>\n");
                                if (c == 0)
                                    s_res.append(constraints.get((r-1) + "" + c).get("L") + "\n");
                                else if (c == score.size() + 1)
                                    s_res.append(constraints.get((r-1) + "" + (c-2)).get("P") + "\n");
                                else
                                    s_res.append(score.get(r-1).get(c - 1));
                                s_res.append("</td>\n");
                            }
                        }
                        s_res.append("</tr>\n");
                    }

                    s_res.append("</table>\n");
                    s_res.append("</body>\n");
                    s_res.append("</html>");
                    writer.write(String.valueOf(s_res));
                    writer.close();
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void printFutoshikiHtml(String name, ArrayList<HashMap<Integer,ArrayList<Integer>>> scores, HashMap<String, HashMap<String, Integer>> constraints)
    {
        try {
            // only for research
            File file = new File(Controller.main_path + "\\html\\" + name);
            file.mkdir();
            for (int i=0; i<scores.size(); i++) {
                HashMap<Integer,ArrayList<Integer>> score = scores.get(i);
                file = new File(Controller.main_path + "\\html\\" + name + "\\" + i + ".html");
                boolean is_created = file.createNewFile();

                if(is_created) {
                    FileWriter f_writer = new FileWriter(file);
                    BufferedWriter writer = new BufferedWriter(f_writer);

                    StringBuilder s_res = new StringBuilder();
                    s_res.append("<!DOCTYPE html>\n");
                    s_res.append("<html>\n");
                    s_res.append("<table border='1' style='border-collapse:separate; width: 100%; text-align: center;'>\n");

                    // place for score
                    StringBuilder line_builder, line_con_builder;
                    StringBuilder variable_builder, next_variable;

                    int dimension = score.size();

                    for (int r=0; r<dimension; r++) // row loop
                    {
                        ArrayList<Integer> row = score.get(r);
                        line_builder = new StringBuilder();
                        line_con_builder = new StringBuilder();

                        s_res.append("<tr>\n");

                        for (int j=0; j<dimension; j++) // col loop
                        {
                            line_builder.append("<td>\n");
                            line_builder.append(row.get(j) + "\n");
                            line_builder.append("</td>\n");
                            variable_builder = new StringBuilder();
                            variable_builder.append(r);
                            variable_builder.append(j);
                            HashMap<String, Integer> cons;
                            String variable = variable_builder.toString();

                            if ((cons=constraints.get(variable)) != null && j != dimension-1)
                            {
                                next_variable = new StringBuilder();
                                next_variable.append(r);
                                next_variable.append(j+1);
                                String next_var = next_variable.toString();

                                line_builder.append("<td>\n");

                                if(cons.containsKey(next_var))
                                    line_builder.append(cons.get(next_var).equals(IO.GRATER_THEN) ? ">\n" : "<\n");
                                else
                                    line_builder.append("|\n");
                                line_builder.append("</td>\n");
                            }
                            else if (j != dimension-1) {
                                line_builder.append("<td>");
                                line_builder.append("|\n");
                                line_builder.append("</td>");
                            }

                            if (cons != null && r != dimension-1)
                            {
                                next_variable = new StringBuilder();
                                next_variable.append(r+1);
                                next_variable.append(j);
                                String next_var = next_variable.toString();

                                line_con_builder.append("<td>\n");

                                if(cons.containsKey(next_var))
                                    line_con_builder.append((cons.get(next_var).equals(IO.GRATER_THEN) ? "\\/\n" : "/\\\n"));
                                else
                                    line_con_builder.append("-\n");

                                line_con_builder.append("</td>\n");

                                if (j != dimension-1) {
                                    line_con_builder.append("<td>\n");
                                    line_con_builder.append("-\n");
                                    line_con_builder.append("</td>\n");
                                }
                            }
                            else {
                                line_con_builder.append("<td>\n");
                                line_con_builder.append("-\n");
                                line_con_builder.append("</td>\n");

                                if (j != dimension-1) {
                                    line_con_builder.append("<td>\n");
                                    line_con_builder.append("-\n");
                                    line_con_builder.append("</td>\n");
                                }
                            }
                        }

                        s_res.append(line_builder);
                        s_res.append("</tr>\n");

                        if (r != dimension-1)
                        {
                            s_res.append("<tr>\n");
                            s_res.append(line_con_builder);
                            s_res.append("</tr>\n");
                        }
                    }

                    s_res.append("</table>\n");
                    s_res.append("</body>\n");
                    s_res.append("</html>");
                    writer.write(String.valueOf(s_res));
                    writer.close();
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
