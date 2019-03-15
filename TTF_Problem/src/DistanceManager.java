import java.util.*;

public class DistanceManager
{
    private static HashMap<Integer, HashMap<Integer, Integer>> matrix;

    public static void initializeMatrix(ArrayList<Town> towns)
    {
        matrix = makeMatrix(towns);
    }

    private static HashMap<Integer, HashMap<Integer, Integer>> makeMatrix(ArrayList<Town> towns)
    {
        HashMap<Integer, HashMap<Integer, Integer>> distance_matrix = new HashMap<>();

        for (int i=1; i<=towns.size(); i++) {
            Town t1 = towns.get(i-1);
            HashMap<Integer, Integer> distances = new HashMap<>();
            for (int j = 1; j <= towns.size(); j++) {
                distances.put(j, t1.calcDistance(towns.get(j-1)));
            }
            distance_matrix.put(i, distances);
        }
        return distance_matrix;
    }

    public static ArrayList<Integer> getTravel()
    {
        ArrayList<Integer> towns_id = new ArrayList<>(matrix.keySet());
        Collections.shuffle(towns_id);
        return towns_id;
    }

    public static int getDistanceBetween(int x1, int x2)
    {
        return matrix.get(x1).get(x2);
    }
}
