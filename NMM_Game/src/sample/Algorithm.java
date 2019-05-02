package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm
{
    public static void minimax()
    {

    }

    public static void alphabeta()
    {

    }

    public static ArrayList<Integer> getNeighbours(ArrayList<Integer> board, int field)
    {
        ArrayList<Integer> neigh = new ArrayList<>();
        switch(field)
        {
            case 0:
                neigh.addAll(Arrays.asList(1,9));
                break;
            case 1:
                neigh.addAll(Arrays.asList(0,2,4));
                break;
            case 2:
                neigh.addAll(Arrays.asList(1,14));
                break;
            case 3:
                neigh.addAll(Arrays.asList(4,10));
                break;
            case 4:
                neigh.addAll(Arrays.asList(1,3,5,7));
                break;
            case 5:
                neigh.addAll(Arrays.asList(4,13));
                break;
            case 6:
                neigh.addAll(Arrays.asList(7,11));
                break;
            case 7:
                neigh.addAll(Arrays.asList(4,6,8));
                break;
            case 8:
                neigh.addAll(Arrays.asList(7,12));
                break;
            case 9:
                neigh.addAll(Arrays.asList(0,10,21));
                break;
            case 10:
                neigh.addAll(Arrays.asList(3,9,11,18));
                break;
            case 11:
                neigh.addAll(Arrays.asList(6,10,15));
                break;
            case 12:
                neigh.addAll(Arrays.asList(8,13,17));
                break;
            case 13:
                neigh.addAll(Arrays.asList(5,12,14,20));
                break;
            case 14:
                neigh.addAll(Arrays.asList(2,13,23));
                break;
            case 15:
                neigh.addAll(Arrays.asList(11,16));
                break;
            case 16:
                neigh.addAll(Arrays.asList(15,17,19));
                break;
            case 17:
                neigh.addAll(Arrays.asList(12,16));
                break;
            case 18:
                neigh.addAll(Arrays.asList(10,19));
                break;
            case 19:
                neigh.addAll(Arrays.asList(16,18,20,22));
                break;
            case 20:
                neigh.addAll(Arrays.asList(13,19));
                break;
            case 21:
                neigh.addAll(Arrays.asList(9,22));
                break;
            case 22:
                neigh.addAll(Arrays.asList(19,21,23));
                break;
            case 23:
                neigh.addAll(Arrays.asList(14,22));
                break;
        }

        return neigh;
    }

    public static boolean isMill(ArrayList<Integer> board, int field)
    {
        int value = board.get(field);

        switch(field)
        {
            case 0:
                if ((board.get(1) == value && board.get(2) == value) || (board.get(9) == value &&
                        board.get(21) == value))
                    return true;
                break;
            case 1:
                if ((board.get(0) == value && board.get(2) == value) || (board.get(4) == value &&
                        board.get(7) == value))
                    return true;
                break;
            case 2:
                if ((board.get(1) == value && board.get(0) == value) || (board.get(14) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 3:
                if ((board.get(4) == value && board.get(5) == value) || (board.get(10) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 4:
                if ((board.get(1) == value && board.get(7) == value) || (board.get(3) == value &&
                        board.get(5) == value))
                    return true;
                break;
            case 5:
                if ((board.get(3) == value && board.get(4) == value) || (board.get(13) == value &&
                        board.get(20) == value))
                    return true;
                break;
            case 6:
                if ((board.get(7) == value && board.get(8) == value) || (board.get(11) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 7:
                if ((board.get(6) == value && board.get(8) == value) || (board.get(1) == value &&
                        board.get(4) == value))
                    return true;
                break;
            case 8:
                if ((board.get(6) == value && board.get(7) == value) || (board.get(12) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 9:
                if ((board.get(0) == value && board.get(21) == value) || (board.get(10) == value &&
                        board.get(11) == value))
                    return true;
                break;
            case 10:
                if ((board.get(9) == value && board.get(11) == value) || (board.get(3) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 11:
                if ((board.get(9) == value && board.get(10) == value) || (board.get(6) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 12:
                if ((board.get(8) == value && board.get(17) == value) || (board.get(13) == value &&
                        board.get(14) == value))
                    return true;
                break;
            case 13:
                if ((board.get(12) == value && board.get(14) == value) || (board.get(5) == value &&
                        board.get(20) == value))
                    return true;
                break;
            case 14:
                if ((board.get(12) == value && board.get(13) == value) || (board.get(2) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 15:
                if ((board.get(6) == value && board.get(11) == value) || (board.get(16) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 16:
                if ((board.get(19) == value && board.get(22) == value) || (board.get(15) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 17:
                if ((board.get(8) == value && board.get(12) == value) || (board.get(16) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 18:
                if ((board.get(19) == value && board.get(20) == value) || (board.get(3) == value &&
                        board.get(10) == value))
                    return true;
                break;
            case 19:
                if ((board.get(16) == value && board.get(22) == value) || (board.get(20) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 20:
                if ((board.get(18) == value && board.get(19) == value) || (board.get(5) == value &&
                        board.get(13) == value))
                    return true;
                break;
            case 21:
                if ((board.get(9) == value && board.get(0) == value) || (board.get(22) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 22:
                if ((board.get(21) == value && board.get(23) == value) || (board.get(16) == value &&
                        board.get(19) == value))
                    return true;
                break;
            case 23:
                if ((board.get(21) == value && board.get(22) == value) || (board.get(2) == value &&
                        board.get(14) == value))
                    return true;
                break;
        }
        return false;
    }
}
