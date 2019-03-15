import java.util.ArrayList;

public class TSP
{
    private double speed;
    private int dimension;


    public TSP(double speed, int dimension){
        this.speed = speed;
        this.dimension = dimension;
    }


    public double calcObjectiveFunction(ArrayList<Integer> travel)
    {
        double obj_fun = 0;
        for (int i=0; i < dimension; i++)
        {
            int t1_id = travel.get(i);
            int t2_id = travel.get(i+1);

            obj_fun += calcTime(t1_id, t2_id);
        }

        // dodanie ostatniego czasu - do miasta poczatkowego
        obj_fun += calcTime(travel.get(dimension-1), travel.get(0));

        return obj_fun;
    }

    private double calcTime(int x1, int x2)
    {
        return DistanceManager.getDistanceBetween(x1, x2)/speed;
    }
}
