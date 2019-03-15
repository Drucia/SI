import java.util.*;

public class GA
{
    static private Random random = new Random();
    static private final int limes = 100;
    static public int gen = 100;
    static public int pop_size = 100;
    static public double px = 0.7*limes;
    static public double pm = 0.01*limes;
    static public int tour = 5;
    static public int best_in_tour = 2;
    static private int t = 0;
    static public ArrayList<ArrayList<Double>> score;

    private static TTF mutation(TTF os)
    {
        ArrayList<Integer> travel = os.getTravel();
        int dimension = travel.size();
        int a = random.nextInt(dimension);
        int b = random.nextInt(dimension);
        Collections.swap(travel, a, b);
        TTF new_ttf = new TTF(os);
        new_ttf.setTravel(travel);
        return new_ttf;
    }

    public static void setConsts(int p_s, int g, int t, int best, double x, double m)
    {
        gen =g;
        pop_size = p_s;
        px = x*limes;
        pm = m*limes;
        tour = t;
        best_in_tour = best;
    }

    public static List<TTF> initialise()
    {
        ArrayList<TTF> pop = new ArrayList<>();
        score = new ArrayList<>();

        for (int i=0; i < pop_size; i++)
        {
            TTF ttf = new TTF(DistanceManager.getTravel());
            pop.add(ttf);
        }

        TTF best = Collections.max(pop);
        TTF worst = Collections.min(pop);
        OptionalDouble average = pop
                .stream()
                .mapToDouble(a -> a.calcObjectiveFunction())
                .average();
        ArrayList<Double> result = new ArrayList<>();
        result.add(best.calcObjectiveFunction());
        result.add(average.getAsDouble());
        result.add(worst.calcObjectiveFunction());
        score.add(result);
        t++;
        return pop;
    }

    public static void evaluate(List<TTF> pop)
    {
        while(t<gen)
        {
            List<TTF> new_pop = GA.selection(pop, best_in_tour);
            new_pop = GA.crossover(new_pop);
            new_pop = GA.mutation(new_pop);
            t++;
            TTF best = Collections.max(new_pop);
            TTF worst = Collections.min(new_pop);
            OptionalDouble average = new_pop
                    .stream()
                    .mapToDouble(a -> a.calcObjectiveFunction())
                    .average();
            ArrayList<Double> result = new ArrayList<>();
            result.add(best.calcObjectiveFunction());
            result.add(average.getAsDouble());
            result.add(worst.calcObjectiveFunction());
            score.add(result);
            evaluate(new_pop);
        }
    }

    public static List<TTF> selection(List<TTF> pop, int best)
    {
        ArrayList<TTF> score = new ArrayList<>();
        while (score.size() != pop_size) {
            ArrayList<TTF> tournament = new ArrayList<>();
            List<TTF> winners;
            List<TTF> tmp_pop = new ArrayList<>(pop);
            for (int i = 0; i < tour; i++) {
                int idx = random.nextInt(tmp_pop.size());
                tournament.add(tmp_pop.get(idx));
                tmp_pop.remove(idx);
            }

            Collections.sort(tournament, Collections.reverseOrder());
            winners = tournament.subList(0, best);
            score.addAll(winners);
        }

        return score;
    }

    public static List<TTF> crossover(List<TTF> pop)
    {
        ArrayList<TTF> new_pop = new ArrayList<>();

        for(int i=0; i<pop_size; i+=2) {
            if (i+1 == pop_size)
                new_pop.add(pop.get(i));
            else {
                List<TTF> pair = pop.subList(i, i + 2);

                if (random.nextInt(limes) < px) {
                    new_pop.addAll(crossover(pair.get(0), pair.get(1)));
                } else
                    new_pop.addAll(pair);
            }
        }

        return  new_pop;
    }

    public static List<TTF> mutation(List<TTF> pop)
    {
        ArrayList<TTF> result = new ArrayList<>();
        for (TTF osobnik : pop) {
            if (random.nextInt(limes) < pm) {
                result.add(mutation(osobnik));
            }
            else
                result.add(osobnik);
        }

        return result;
    }

    private static List<TTF> crossover(TTF os1, TTF os2)
    {
        TTF first_child = new TTF(os1);
        TTF second_child = new TTF(os2);

        int size = os1.getTravel().size();

        int cut_point_1 = random.nextInt(size);
        int cut_point_2 = random.nextInt(size-1);

        if (cut_point_1 == cut_point_2)
            cut_point_2 = size - 1;
        else if (cut_point_1 > cut_point_2) // swap
        {
            int tmp = cut_point_1;
            cut_point_1 = cut_point_2;
            cut_point_2 = tmp;
        }

        Integer[] parent_1 = new Integer[size];
        parent_1 = os1.getTravel().toArray(parent_1);

        Integer[] parent_2 = new Integer[size];
        parent_2 = os2.getTravel().toArray(parent_2);

        Integer[] offspring_1 = new Integer[size];
        Integer[] offspring_2 = new Integer[size];
        int[] mapping_1 = new int[size+1];
        int[] mapping_2 = new int[size+1];

        Arrays.fill(mapping_1, -1);
        Arrays.fill(mapping_2, -1);

        for (int i = cut_point_1; i <= cut_point_2; i++) // change between cut points (cut points included)
        {
            offspring_1[i] = parent_2[i];
            mapping_1[parent_2[i]] = parent_1[i];

            offspring_2[i] = parent_1[i];
            mapping_2[parent_1[i]] = parent_2[i];
        }

        for (int i=0; i < size; i++) // fill rest
        {
            if ((i < cut_point_1) || i > cut_point_2) // fill to first cut point and fill from second cut point to end
            {
                int value_1 = parent_1[i];
                int map_val_1 = mapping_1[value_1];
                int value_2 = parent_2[i];
                int map_val_2 = mapping_2[value_2];

                while(map_val_1 != -1) // we already have this value
                {
                    value_1 = map_val_1;
                    map_val_1 = mapping_1[value_1];
                }

                while(map_val_2 != -1)
                {
                    value_2 = map_val_2;
                    map_val_2 = mapping_2[value_2];
                }

                offspring_1[i] = value_1;
                offspring_2[i] = value_2;
            }
        }

        ArrayList<Integer> new_travel = new ArrayList<>(Arrays.asList(offspring_1));
        first_child.setTravel(new_travel);
        new_travel = new ArrayList<>(Arrays.asList(offspring_2));
        second_child.setTravel(new_travel);

        ArrayList<TTF> result = new ArrayList<>();
        result.add(first_child);
        result.add(second_child);

        return result;
    }
}
