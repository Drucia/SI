package algorithm;

import helpers.IO;

import java.util.ArrayList;
import java.util.HashMap;

public class CSP {
    private ArrayList<Integer> domain;
    private ArrayList<ArrayList<Integer>> columns;
    private HashMap<String, ArrayList<Integer>> already_there_per_col_row;
    private HashMap<Character, ArrayList<Integer>> matrix;
    private HashMap<String, HashMap<String, Integer>> constraints;
    private boolean isFutoshiki;
    private int dimension;
    private int counter;

    public CSP(ArrayList<Integer> domain, HashMap<Character, ArrayList<Integer>> matrix, HashMap<String, HashMap<String, Integer>> constraints, boolean isFutoshiki) {
        this.domain = domain;
        this.matrix = matrix;
        this.constraints = constraints;
        this.isFutoshiki = isFutoshiki;
        dimension = domain.size();
        columns = new ArrayList<>();
        counter = 0;
    }

    public HashMap<Character, HashMap<Integer, Integer>> runForwarding(HashMap<String, ArrayList<Integer>> already_there_per_col_row)
    {
        this.already_there_per_col_row = already_there_per_col_row;

        char rowChar = 'A';
        for (int row=0; row<dimension; row++)
        {
            for(int col=0; col<dimension; col++)
            {
                int curr_val = matrix.get(rowChar).get(col);
                if (curr_val == 0)
                {
                    ArrayList<Integer> curr_domain = domain;
                    curr_domain.removeAll(already_there_per_col_row.get(rowChar+""));
                    curr_domain.removeAll(already_there_per_col_row.get(col+""));
                }
            }
        }
        return null;
    }

    private void updateDomains(int col, String row, int value)
    {
        ArrayList<Integer> tmp_dom;
        if (already_there_per_col_row.containsKey(row))
            tmp_dom = already_there_per_col_row.get(row);
        else
            tmp_dom = new ArrayList<>(domain);

        tmp_dom.add(value);
        already_there_per_col_row.put(row, tmp_dom);

        if (already_there_per_col_row.containsKey(col+""))
            tmp_dom = already_there_per_col_row.get(col+"");
        else
            tmp_dom = new ArrayList<>(domain);

        tmp_dom.add(value);
        already_there_per_col_row.put(col+"", tmp_dom);
    }

    public HashMap<Character, HashMap<Integer, Integer>> runBackTracking()
    {
        return null;
    }

    private boolean canBeHereFor(int value, char row, int col)
    {
        counter++;
        boolean score = true;
        StringBuilder curr = new StringBuilder(row);
        curr.append(col);
        String cur = curr.toString();
        StringBuilder next = new StringBuilder();
        String nex;

        // for upper

        if(row != 'A') {
            char next_row = row;
            next_row++;
            next.append(next_row);
            next.append(col);
            nex = next.toString();

            if (constraints.get(cur).containsKey(nex))
            {
                if (constraints.get(cur).get(nex) == IO.GRATER_THEN && value <= matrix.get(next_row).get(col))
                    return false;
                if (constraints.get(cur).get(nex) == IO.SMALLER_THEN && value >= matrix.get(next_row).get(col))
                    return false;
            }
        }

        //if (row != '')

    }

    private ArrayList<Integer> getColumn(int colNum)
    {
        if (columns.get(colNum-1) != null)
            return columns.get(colNum-1);

        ArrayList column = new ArrayList();

        char row = 'A';
        for (int i=0; i<dimension; i++, row++)
            column.add(matrix.get(row).get(colNum));

        return column;
    }
}
