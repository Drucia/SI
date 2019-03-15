import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class KNP
{
    public static double calcObjectiveFunction(HashMap<Integer, List<Pair<Item, Integer>>> items_by_towns)
    {
        double obj_fun = 0;

        for (int j=1; j <= items_by_towns.size(); j++) {
            List<Pair<Item, Integer>> items = items_by_towns.get(j);
            for (int i = 0; i < items.size(); i++) {
                Pair<Item, Integer> it = items.get(i);
                Item item = it.getKey();
                int is_chosen = it.getValue();
                obj_fun += item.getProfit() * is_chosen;
            }
        }
        return obj_fun;
    }

    public static int calcActualCapacity(List<Pair<Item, Integer>> items_by_towns)
    {
        int act_cap = 0;

            for (int i = 0; i < items_by_towns.size(); i++) {
                Pair<Item, Integer> it = items_by_towns.get(i);
                Item item = it.getKey();
                int is_chosen = it.getValue();
                act_cap += item.getWeight() * is_chosen;
            }

        return act_cap;
    }
}
