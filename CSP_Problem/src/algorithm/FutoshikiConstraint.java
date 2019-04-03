package algorithm;

import helpers.IO;

import java.util.ArrayList;
import java.util.HashMap;

public class FutoshikiConstraint implements Constraint {
    private HashMap<String, HashMap<String, Integer>> constraints;

    public FutoshikiConstraint(HashMap<String, HashMap<String, Integer>> constraints) {
        this.constraints = constraints;
    }

    @Override
    public boolean canBeHereByConstraint(int value, int row, int col, HashMap<Integer, ArrayList<Integer>> matrix) {
        boolean if_do = true;
        int dimension = matrix.size();
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
                if (matrix.get(next_r).get(next_c) != CSP.UNASSIGNED) {
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
}
