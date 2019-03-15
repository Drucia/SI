import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class KNP
{
    public static final int BY_WEIGHT_ID = 0;
    public static final int BY_PROFIT_ID = 1;
    public static final int BY_RATIO_ID = 2;

    private int capacity;
    private ArrayList<Item> items;
    private int number_of_towns;
    private int chosen_greedy;

    public KNP(int greedy)
    {
        chosen_greedy = greedy;
        capacity = Loader.capacity;
        items = Loader.items;
        number_of_towns = Loader.dimension;
    }

    private ArrayList<Item> getByBestWeight(ArrayList<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        return items;
    }

    private ArrayList<Pair<Item,Integer>> chooseItems(int capacity, ArrayList<Item> items){
        ArrayList<Item> all_items;

        switch (chosen_greedy)
        {
            case BY_PROFIT_ID:
                all_items = getByBestProfit(items);
                break;
            case BY_WEIGHT_ID:
                all_items = getByBestWeight(items);
                break;
            case BY_RATIO_ID:
                all_items = getByBestRatio(items);
                break;
                default:
                    all_items  = new ArrayList<>();
                    break;
        }

        ArrayList<Pair<Item, Integer>> chosen_items = new ArrayList<>();

        int actual_capacity = 0;
        int all_items_size = all_items.size();

        for (int i = 0; i < all_items_size; i++) {
            Item item = all_items.get(i);

            if (actual_capacity + item.getWeight() <= capacity) {
                chosen_items.add(new Pair<>(item, 1));
                actual_capacity += item.getWeight();
            } else
                chosen_items.add(new Pair<>(item, 0));
        }

        return chosen_items;
    }

    public HashMap<Integer, List<Pair<Item, Integer>>> getChosenItems(){
        ArrayList<Pair<Item, Integer>> all_items_with_choices = chooseItems(capacity, items);
        HashMap<Integer, List<Pair<Item, Integer>>> items_in_towns = new HashMap<>();

        for (int i=1; i <= number_of_towns; i++)
        {
            int finalI = i;
            List<Pair<Item, Integer>> items_of_town = all_items_with_choices.stream().filter(it -> it.getKey().getNode_idx() == finalI).collect(Collectors.toList());
            items_in_towns.put(i, items_of_town);
        }

        return items_in_towns;
    }

    private ArrayList<Item> getByBestProfit(ArrayList<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getProfit() - o2.getProfit();
            }
        });
        return items;
    }

    private ArrayList<Item> getByBestRatio(ArrayList<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getProfit() / o1.getWeight() - o2.getProfit() / o2.getWeight();
            }
        });
        return items;
    }

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
