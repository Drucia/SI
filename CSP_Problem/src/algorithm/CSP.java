package algorithm;

import helpers.IO;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class CSP {
    private static final String FUTOSHIKI_FILLED = "";
    private static final int UNASSIGNED = 0;
    private static HashMap<String, HashMap<String, Integer>> constraints;
    private static int dimension;
    private static int counter;
    private static long totalTime;
    private static long startTime;
    private static long endTime;
    private static ArrayList<HashMap<Integer, ArrayList<Integer>>> scores;

    public static void set_constans(HashMap<String, HashMap<String, Integer>> con, int dim)
    {
        constraints = con;
        dimension = dim;
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
        if ( FUTOSHIKI_FILLED == get_unassigned_location(grid))
        {
            scores.add(makeCopy(grid));

            // search next solution
            return false;
        }

        // Get unassigned variable - heuristic
        String row_and_col = get_unassigned_location(grid);
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

    private static String get_unassigned_location(HashMap<Integer, ArrayList<Integer>> grid)
    {
        for (int i=0; i<dimension; i++)
        {
            for (int j=0; j<dimension; j++)
                if (grid.get(i).get(j) == 0)
                    return i+""+j;
        }

        return "";
    }

    private static boolean canBeHere(int value, int row, int col, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        //return !getColumn(col, matrix).contains(value) && !matrix.get(row).contains(value) && canBeHereByConstFutosh(value, row, col, matrix);
        return !getColumn(col, matrix).contains(value) && !matrix.get(row).contains(value) && canBeHereByConstSky(value, row, col, matrix);
    }

    private static boolean checkByRow(int l, int r, ArrayList<Integer> ro, int col, int value)
    {
        // for left constraint
        int counter = 0;
        int last_seen = 0;

        if (l != UNASSIGNED) {
            for (int i = 0; i < dimension; i++) {
                if (i == col && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (ro.get(i) != UNASSIGNED && ro.get(i) > last_seen) {
                    counter++;
                    last_seen = ro.get(i);
                }
            }

            if (counter != l)
                return false;
        }

        // for right
        counter = 0;
        last_seen = 0;

        if (r != UNASSIGNED) {
            for (int i = dimension - 1; i >= 0; i--) {
                if (i == col && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (ro.get(i) != UNASSIGNED && ro.get(i) > last_seen) {
                    counter++;
                    last_seen = ro.get(i);
                }
            }

            if (counter != r)
                return false;
        }

        return true;
    }

    private static boolean checkByCol(int u, int d, ArrayList<Integer> column, int row, int value)
    {
        int counter = 0;
        int last_seen = 0;

        if (u != UNASSIGNED) {
            for (int i = 0; i < dimension; i++) {
                if (i == row && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (column.get(i) != UNASSIGNED && column.get(i) > last_seen) {
                    counter++;
                    last_seen = column.get(i);
                }
            }

            if (counter != u)
                return false;
        }
        // for down
        counter = 0;
        last_seen = 0;

        if (d != UNASSIGNED) {
            for (int i = dimension - 1; i >= 0; i--) {
                if (i == row && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (column.get(i) != UNASSIGNED && column.get(i) > last_seen) {
                    counter++;
                    last_seen = column.get(i);
                }
            }

            if (counter != d)
                return false;
        }

        return true;
    }

    private static boolean canBeHereByConstSky(int value, int row, int col, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        HashMap<String, Integer> con = constraints.get(row+""+col);
        int u = con.get("G");
        int d = con.get("D");
        int l = con.get("L");
        int r = con.get("P");

        ArrayList<Integer> column = getColumn(col, matrix);
        ArrayList<Integer> ro = matrix.get(row);
        int c_f = Collections.frequency(column, UNASSIGNED);
        int c_r = Collections.frequency(ro, UNASSIGNED);

        if (Collections.frequency(column, UNASSIGNED) == 1 && Collections.frequency(ro, UNASSIGNED) == 1)
            return checkByCol(u, d, column, row, value) && checkByRow(l, r, ro, col, value);
        else if (Collections.frequency(column, UNASSIGNED) == 1)
            return checkByCol(u, d, column, row, value);
        else if (Collections.frequency(ro, UNASSIGNED) == 1)
            return checkByRow(l, r, ro, col, value);

        return true;
    }

    private static boolean canBeHereByConstFutosh(int value, int row, int col, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        boolean if_do = true;
        String curr = row+""+col;
        String next = curr;
        int next_r = row;
        int next_c = col;

        // for upper

        for(int i=0; i<4; i++)
        {
            switch(i)
            {
                case 0:
                    if(if_do = (row != 0))
                    {
                        next_r = row-1;
                        next_c = col;
                    }
                    break;
                case 1:
                    if(if_do = (row != dimension-1))
                    {
                        next_r = row+1;
                        next_c = col;
                    }
                    break;
                case 2:
                    if(if_do = (col != 0))
                    {
                        next_r = row;
                        next_c = col-1;
                    }
                    break;
                case 3:
                    if(if_do = (row != dimension-1))
                    {
                        next_r = row;
                        next_c = col+1;
                    }
                    break;
            }

            next = next_r + "" + next_c;

            if (if_do && constraints.containsKey(curr) && constraints.get(curr).containsKey(next)) {
                if (matrix.get(next_r).get(next_c) != 0) {
                    int con_val = constraints.get(curr).get(next);
                    int next_val = matrix.get(next_r).get(next_c);
                    if (con_val == IO.GRATER_THEN && value <= next_val)
                        return false;
                    if (con_val == IO.SMALLER_THEN && value >= next_val)
                        return false;
                }
            }
        }

        return true;
    }

    private static ArrayList<Integer> getColumn(int colNum, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        ArrayList column = new ArrayList();

        for (int i=0; i<dimension; i++)
            column.add(matrix.get(i).get(colNum));

        return column;
    }
}
