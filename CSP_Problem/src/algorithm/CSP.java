package algorithm;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class CSP {
    public static final int FIRST_HEURISTIC = 0;
    public static final int MIN_HEURISTIC = 1;
    private static final String FUTOSHIKI_FILLED = "";
    public static final int UNASSIGNED = 0;
    private static Constraint constraints;
    private static int dimension;
    private static int counter;
    private static long totalTime;
    private static long startTime;
    private static long endTime;
    private static ArrayList<HashMap<Integer, ArrayList<Integer>>> scores;
    private static int heuristic;

    public static void set_constans(Constraint con, int dim, int heu)
    {
        constraints = con;
        dimension = dim;
        heuristic = heu;
    }

    public static int getCounter() {
        return counter;
    }

    public static long getTotalTime() {
        return totalTime;
    }

    public static ArrayList<HashMap<Integer, ArrayList<Integer>>> runForwarding(HashMap<Integer, ArrayList<Integer>> grid)
    {
        counter = 0;
        scores = new ArrayList<>();
        startTime = System.nanoTime();
        solve_futoshiki(makeCopy(grid), true);
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        return scores;
    }

    private static HashMap<Integer, ArrayList<Integer>> makeCopy(HashMap<Integer,ArrayList<Integer>> grid) {
        HashMap<Integer, ArrayList<Integer>> copy = new HashMap<>();

        for (Integer i : grid.keySet())
        {
            ArrayList<Integer> tmp = new ArrayList<>(grid.get(i));
            copy.put(i, tmp);
        }

        return copy;
    }

    public static ArrayList<HashMap<Integer, ArrayList<Integer>>> runBackTracking(HashMap<Integer, ArrayList<Integer>> grid)
    {
        counter = 0;
        scores = new ArrayList<>();
        startTime = System.nanoTime();
        solve_futoshiki(makeCopy(grid), false);
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        return scores;
    }

    private static boolean solve_futoshiki(HashMap<Integer, ArrayList<Integer>> grid, boolean isForwarding)
    {
        if ( FUTOSHIKI_FILLED == get_unassigned_location(isForwarding, grid))
        {
            scores.add(makeCopy(grid));

            // search next solution
            return false;
        }

        // Get unassigned variable - heuristic
        String row_and_col = get_unassigned_location(isForwarding, grid);
        int row = Integer.parseInt(row_and_col.substring(0,1));
        int col = Integer.parseInt(row_and_col.substring(1));

        // Get domain, if is forwarding - get current domain
        ArrayList<Integer> cur_domain = getDomain(col, row, isForwarding, grid);

        for (int num = 0; num < cur_domain.size(); num++)
        {
            // count instructions
            counter++;

            // check if this value is valid
            if (canBeHere(cur_domain.get(num), row, col, grid))
            {
                // assign for now value
                grid.get(row).set(col, cur_domain.get(num));

                // Do the same thing again recursively. If we go
                // through all of the recursions, and in the end
                // return true, then all of our number placements
                // on the Futoshiki grid are valid and we have fully
                // solved it
                if (solve_futoshiki(grid, isForwarding))
                {
                    return true;
                }

                // As we were not able to validly go through all
                // of the recursions, we must have an invalid number
                // placement somewhere. Lets go back and try a
                // different number for this particular unassigned location
                grid.get(row).set(col, UNASSIGNED);
            }
        }

        // If we have gone through all possible numbers for the current unassigned
        // location, then we probably assigned a bad number early. Lets backtrack
        // and try a different number for the previous unassigned locations.
        return false;
    }

    private static ArrayList<Integer> getDomain(int col, int row, boolean isForwarding, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        ArrayList<Integer> domain = new ArrayList<>(IntStream.range(1, dimension + 1)
            .boxed()
            .collect(toList()));

        if (!isForwarding) {
            return domain;
        }

        ArrayList<Integer> score = new ArrayList<>();

        // get all from row
        for (int i=0; i<dimension; i++)
        {
            int val_r = matrix.get(i).get(col);
            int val_c = matrix.get(row).get(i);

            if (val_r != 0 && !score.contains(val_r))
                score.add(val_r);

            if (val_c != 0 && !score.contains(val_c))
                score.add(val_c);
        }

        domain.removeAll(score);

        return domain;
    }

    private static String get_unassigned_location(boolean isForwarding, HashMap<Integer, ArrayList<Integer>> grid)
    {
        if (heuristic == FIRST_HEURISTIC) {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++)
                    if (grid.get(i).get(j) == UNASSIGNED)
                        return i + "" + j;
            }
        }

        if (heuristic == MIN_HEURISTIC)
        {
            // first variable, second size of domain
            ArrayList<Pair<String, Integer>> variables = new ArrayList<>();
            for (int i = 0; i < dimension; i++)
            {
                ArrayList<Integer> col = new ArrayList<>();
                ArrayList<Integer> row = grid.get(i);
                for (int j = 0; j < dimension; j++)
                    if (row.get(j) == UNASSIGNED)
                        col.add(j);

                for (int j = 0; j < col.size(); j++)
                {
                    // added variable with its domain size
                    Pair<String, Integer> var = new Pair<>(i+""+col.get(j), getDomain(col.get(j), i, isForwarding, grid).size());
                    variables.add(var);
                }
            }

            Collections.sort(variables, new Comparator<Pair<String, Integer>>() {
                @Override
                public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                    return o1.getValue() - o2.getValue();
                }
            });

            if (!variables.isEmpty())
                return variables.get(0).getKey();
        }
        return "";
    }

    private static boolean canBeHere(int value, int row, int col, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        return !getColumn(col, matrix).contains(value) && !matrix.get(row).contains(value) && constraints.canBeHereByConstraint(value, row, col, matrix);
    }

    private static ArrayList<Integer> getColumn(int colNum, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        ArrayList column = new ArrayList();

        for (int i=0; i<dimension; i++)
            column.add(matrix.get(i).get(colNum));

        return column;
    }
}
