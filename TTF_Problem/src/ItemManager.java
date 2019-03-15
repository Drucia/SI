import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ItemManager {
    private static int capacity;
    private static ArrayList<Item> items;
    private static int number_of_towns;

    private static ArrayList<Item> getByBestWeight(ArrayList<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        return items;
    }

    private static ArrayList<Pair<Item,Integer>> chooseItems(int capacity, ArrayList<Item> items){
        ArrayList<Item> all_items = getByBestRatio(items);
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

    public static HashMap<Integer, List<Pair<Item, Integer>>> getChosenItems(){
        capacity = Loader.capacity;
        items = Loader.items;
        number_of_towns = Loader.dimension;
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

    private static ArrayList<Item> getByBestProfit(ArrayList<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getProfit() - o2.getProfit();
            }
        });
        return items;
    }

    private static ArrayList<Item> getByBestRatio(ArrayList<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getProfit() / o1.getWeight() - o2.getProfit() / o2.getWeight();
            }
        });
        return items;
    }
}
