package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SkyScrapperConstraint implements Constraint {
    private HashMap<String, HashMap<String, Integer>> constraints;

    public SkyScrapperConstraint(HashMap<String, HashMap<String, Integer>> constraints) {
        this.constraints = constraints;
    }

    @Override
    public boolean canBeHereByConstraint(int value, int row, int col, HashMap<Integer, ArrayList<Integer>> matrix) {
        HashMap<String, Integer> con = constraints.get(row+""+col);
        int u = con.get("G");
        int d = con.get("D");
        int l = con.get("L");
        int r = con.get("P");

        ArrayList<Integer> column = getColumn(col, matrix);
        ArrayList<Integer> ro = matrix.get(row);
        //int c_f = Collections.frequency(column, CSP.UNASSIGNED);
        //int c_r = Collections.frequency(ro, CSP.UNASSIGNED);

        if (Collections.frequency(column, CSP.UNASSIGNED) == 1 && Collections.frequency(ro, CSP.UNASSIGNED) == 1)
            return checkByCol(u, d, column, row, value) && checkByRow(l, r, ro, col, value);
        else if (Collections.frequency(column, CSP.UNASSIGNED) == 1)
            return checkByCol(u, d, column, row, value);
        else if (Collections.frequency(ro, CSP.UNASSIGNED) == 1)
            return checkByRow(l, r, ro, col, value);

        return true;
    }

    @Override
    public int getNumberOfConstraint(String var) {
        if (!constraints.containsKey(var))
            return 0;

        HashMap<String, Integer> con = constraints.get(var);
        int c = 0;
        for(String s : con.keySet())
            if (con.get(s) == 0)
                c++;

        return c;
    }

    private ArrayList<Integer> getColumn(int colNum, HashMap<Integer, ArrayList<Integer>> matrix)
    {
        int dimension = matrix.size();
        ArrayList column = new ArrayList();

        for (int i=0; i<dimension; i++)
            column.add(matrix.get(i).get(colNum));

        return column;
    }

    private boolean checkByRow(int l, int r, ArrayList<Integer> ro, int col, int value)
    {
        int dimension = ro.size();

        // for left constraint
        int counter = 0;
        int last_seen = 0;

        if (l != CSP.UNASSIGNED) {
            for (int i = 0; i < dimension; i++) {
                if (i == col && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (ro.get(i) != CSP.UNASSIGNED && ro.get(i) > last_seen) {
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

        if (r != CSP.UNASSIGNED) {
            for (int i = dimension - 1; i >= 0; i--) {
                if (i == col && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (ro.get(i) != CSP.UNASSIGNED && ro.get(i) > last_seen) {
                    counter++;
                    last_seen = ro.get(i);
                }
            }

            if (counter != r)
                return false;
        }

        return true;
    }

    private boolean checkByCol(int u, int d, ArrayList<Integer> column, int row, int value)
    {
        int dimension = column.size();
        int counter = 0;
        int last_seen = 0;

        if (u != CSP.UNASSIGNED) {
            for (int i = 0; i < dimension; i++) {
                if (i == row && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (column.get(i) != CSP.UNASSIGNED && column.get(i) > last_seen) {
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

        if (d != CSP.UNASSIGNED) {
            for (int i = dimension - 1; i >= 0; i--) {
                if (i == row && value > last_seen)
                {
                    counter++;
                    last_seen = value;
                }
                else if (column.get(i) != CSP.UNASSIGNED && column.get(i) > last_seen) {
                    counter++;
                    last_seen = column.get(i);
                }
            }

            if (counter != d)
                return false;
        }

        return true;
    }
}
