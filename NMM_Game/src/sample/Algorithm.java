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
}
