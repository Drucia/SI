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

    public static ArrayList<Integer> getTravel(boolean is_greedy)
    {
        if (!is_greedy) {
            ArrayList<Integer> towns_id = new ArrayList<>(matrix.keySet());
            Collections.shuffle(towns_id);
            return towns_id;
        }
        
        return getGreedyTravel();
    }

    private static ArrayList<Integer> getGreedyTravel() {
        // get the nearest
        ArrayList<Integer> towns_id = new ArrayList<>(matrix.keySet());
        int loop = towns_id.size();
        ArrayList<Integer> travel = new ArrayList<>();
        Integer t_id = towns_id.get(0);
        travel.add(t_id);
        towns_id.remove(t_id);

        for (int i=1; i<loop; i++)
        {
            HashMap<Integer, Integer> towns_to = matrix.get(travel.get(i-1));
            Integer id = towns_id.get(0);
            int min_dis = towns_to.get(id);

            for (int j=1; j<towns_id.size(); j++) {
                Integer town_to_id = towns_id.get(j);
                int dis = towns_to.get(town_to_id);
                if (dis < min_dis)
                {
                    min_dis = dis;
                    id = town_to_id;
                }
            }
            travel.add(id);
            towns_id.remove(id);
        }
        return travel;
//        Collections.shuffle(towns_id);
//        return towns_id;
    }

    public static int getDistanceBetween(int x1, int x2)
    {
        return matrix.get(x1).get(x2);
    }
}
