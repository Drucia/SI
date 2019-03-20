import java.util.*;

public class GA
{
    static private Random random = new Random();
    static private final int limes = 100;
    static private int gen;
    static private int pop_size;
    static private double px;
    static private double pm;
    static private int tour;
    static private int best_in_tour;
    static private int t;
    static private int knp;
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

    public static void setConsts(int p_s, int g, int to, int best, double x, double m, int greedy_knp)
    {
        gen =g;
        pop_size = p_s;
        px = x*limes;
        pm = m*limes;
        tour = to;
        t = 0;
        best_in_tour = best;
        knp = greedy_knp;
    }

    public static List<TTF> initialise()
    {
        ArrayList<TTF> pop = new ArrayList<>();
        score = new ArrayList<>();

        for (int i=0; i < pop_size; i++)
        {
            TTF ttf = new TTF(knp, false);
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
                TTF tmp = new TTF(tmp_pop.get(idx));
                tmp.setTravel(tmp_pop.get(idx).getTravel());
                tournament.add(tmp);
                tmp_pop.remove(idx);
            }

            Collections.sort(tournament, Collections.reverseOrder());

            if (tour != 0) {
                winners = tournament.subList(0, best);
            }
            else
                winners = tmp_pop;
            score.addAll(winners);
        }

        return score;
    }

    public static List<TTF> crossover(List<TTF> pop)
    {
        ArrayList<TTF> new_pop = new ArrayList<>();

        for(int i=0; i<pop_size; i+=2) {
            if (i+1 == pop_size) {
                TTF old = pop.get(i);
                TTF tmp = new TTF(old);
                tmp.setTravel(old.getTravel());
                new_pop.add(tmp);
            }else {
                List<TTF> pair = pop.subList(i, i + 2);

                if (random.nextInt(limes) < px) {
                    new_pop.addAll(crossover(pair.get(0), pair.get(1)));
                } else {
                    TTF old = pair.get(0);
                    TTF tmp = new TTF(old);
                    tmp.setTravel(old.getTravel());
                    new_pop.add(tmp);
                    old = pair.get(1);
                    tmp = new TTF(old);
                    tmp.setTravel(old.getTravel());
                    new_pop.add(tmp);
                    //new_pop.addAll(pair);
                }
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
            else {
                TTF new_ttf = new TTF(osobnik);
                new_ttf.setTravel(osobnik.getTravel());
                result.add(new_ttf);
                //result.add(osobnik);
            }
        }

        return result;
    }

    public static List<TTF> crossover(TTF os1, TTF os2)
    {

        ArrayList<Integer> parent_1 = new ArrayList(os1.getTravel());
        ArrayList<Integer> parent_2 = new ArrayList(os2.getTravel());

        int cut_point = random.nextInt(parent_1.size()-1) + 1;

        ArrayList<Integer> offspring_1 = new ArrayList<>(parent_1.subList(0, cut_point));
        ArrayList<Integer> offspring_2 = new ArrayList<>(parent_2.subList(0, cut_point));

        parent_2.removeAll(offspring_1);
        parent_1.removeAll(offspring_2);

        offspring_1.addAll(parent_2);
        offspring_2.addAll(parent_1);

        ArrayList<TTF> result = new ArrayList<>();
        TTF res = new TTF(os1);
        res.setTravel(offspring_1);
        result.add(res);
        res = new TTF(os2);
        res.setTravel(offspring_2);
        result.add(res);

        return result;
    }
}
