package algorithm;

import java.util.ArrayList;
import java.util.HashMap;

public class CSP {
    private ArrayList<Integer> domain;
    private HashMap<Character, HashMap<Integer, Integer>> matrix;
    private HashMap<String, HashMap<String, Integer>> constraints;

    public CSP(ArrayList<Integer> domain, HashMap<Character, HashMap<Integer, Integer>> matrix, HashMap<String, HashMap<String, Integer>> constraints) {
        this.domain = domain;
        this.matrix = matrix;
        this.constraints = constraints;
    }

    public HashMap<Character, HashMap<Integer, Integer>> run(boolean isFutoshiki)
    {
        return null;
    }
}
