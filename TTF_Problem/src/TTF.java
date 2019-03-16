import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TTF implements Comparable<TTF>
{
    private int actual_capacity;
    private double actual_speed;
    private double speed_min;
    private double speed_max;
    private int capacity;
    private int dimension;
    private int greedy_knp;
    private ArrayList<Integer> travel;
    private HashMap<Integer, List<Pair<Item, Integer>>> items_plan;

    public ArrayList<Integer> getTravel() {
        return travel;
    }
    public int getGreedy_knp() {return greedy_knp;}

    public HashMap<Integer, List<Pair<Item, Integer>>> getItems_plan() {
        return items_plan;
    }

    public void setTravel(ArrayList<Integer> travel) {
        this.travel = travel;
    }

    public TTF(int greedy_knp, boolean is_greedy_tsp) {
        this.greedy_knp = greedy_knp;
        this.travel = DistanceManager.getTravel(is_greedy_tsp);
        KNP greedy = new KNP(greedy_knp);
        items_plan = greedy.getChosenItems();
        setConstants();
        calcActualSpeed();
    }

    private void setConstants() {
        actual_capacity = 0;
        speed_min = Loader.min_speed;
        speed_max = Loader.max_speed;
        capacity = Loader.capacity;
        dimension = Loader.dimension;
    }

    public TTF(TTF other)
    {
        this.greedy_knp = other.getGreedy_knp();
        this.travel = new ArrayList<>();
        this.items_plan = other.getItems_plan();
        setConstants();
        calcActualSpeed();
    }

    private double calcObjectiveFunctionTSP()
    {
        double obj_fun = 0;
        for (int i=0; i < dimension - 1; i++)
        {
            int t1_id = travel.get(i);
            int t2_id = travel.get(i+1);

            calcActualCapacity(items_plan.get(t1_id));
            calcActualSpeed();
            obj_fun += calcTime(t1_id, t2_id);
        }

        // dodanie ostatniego czasu - do miasta poczatkowego
        calcActualCapacity(items_plan.get(dimension-1));
        calcActualSpeed();
        obj_fun += calcTime(travel.get(dimension-1), travel.get(0));

        return obj_fun;
    }

    public double calcObjectiveFunction()
    {
        double obj_fun = KNP.calcObjectiveFunction(items_plan) - calcObjectiveFunctionTSP();

        return obj_fun;
    }

    private void calcActualCapacity(List<Pair<Item, Integer>> list_of_items) {
        KNP.calcActualCapacity(list_of_items);
    }

    private void calcActualSpeed() {
        actual_speed = speed_max - actual_capacity * ((speed_max - speed_min)/capacity);
        actual_speed = actual_speed < speed_min ? speed_min : actual_speed > speed_max ? speed_max : actual_speed;
    }

    private double calcTime(int x1, int x2) {
        return DistanceManager.getDistanceBetween(x1, x2)/actual_speed;
    }

    @Override
    public int compareTo(TTF o) {
        double diff = this.calcObjectiveFunction() - o.calcObjectiveFunction();
        return  diff < 0 ? -1 : diff == 0 ? 0 : 1;
    }
}
