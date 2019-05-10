package sample;

import java.util.ArrayList;

public class Node {
    public static final int I_NO_MILL = 0;
    public static final int I_CLOSED_MILL = 1;
    public static final int I_DOUBLE_MILL = 2;
    private double value;
    private int millType;
    private ArrayList<Integer> board;

    public Node(double value, int mill, ArrayList<Integer> board) {
        this.value = value;
        this.millType = mill;
        this.board = board;
    }

    public double getValue() {
        return value;
    }

    public int getMill() {
        return millType;
    }

    public ArrayList<Integer> getBoard() {
        return board;
    }
}
