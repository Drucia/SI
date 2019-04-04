package algorithm;

import java.util.ArrayList;
import java.util.HashMap;

public interface Constraint
{
    boolean canBeHereByConstraint(int value, int row, int col, HashMap<Integer, ArrayList<Integer>> matrix);
}
