import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

public class MainManager
{
    private static String path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\Zadania\\TTF\\src\\data\\student\\";
    private ArrayList<String> files;

    public MainManager()
    {
        files = new ArrayList<>();
        for (int i=0; i<5; i++)
        {
            files.add("easy_" + i + ".ttp");
            files.add("hard_" + i + ".ttp");
            files.add("medium_" + i + ".ttp");
        }
        files.add("trivial_0");
        files.add("trivial_1");
        Collections.sort(files);
    }

    public ArrayList<ArrayList<Double>> runAlg(int chosen_file, int pop, int gen, int tour, int best, double px, double pm)
    {
        Loader.readData(path + files.get(chosen_file));
        DistanceManager.initializeMatrix(Loader.towns);

        GA.setConsts(pop, gen, tour, best, px, pm);

        ArrayList<ArrayList<Double>> all_pops;
        List<TTF> start_pop = GA.initialise();

        GA.evaluate(start_pop);
        all_pops = GA.score;

//        for (int i=0; i<all_pops.size(); i++)
//        {
//            ArrayList<Double> curr_pop = all_pops.get(i);
//            System.out.println("Numer populacji: " + i + ", najlepszy: " + curr_pop.get(0) + ", srednia: " + curr_pop.get(1) + ", najgorszy: " + curr_pop.get(2));
//        }
//
//        System.out.println("KONIEC");

        return all_pops;
    }
    public static void main(String[] args)
    {
        Loader.readData(path + "hard_1.ttp");
        DistanceManager.initializeMatrix(Loader.towns);

        ArrayList<ArrayList<Double>> all_pops;
        List<TTF> start_pop = GA.initialise();
        GA.evaluate(start_pop);
        all_pops = GA.score;

        for (ArrayList<Double> d: all_pops)
        {
            System.out.println("Numer populacji: " + d.get(0) + ", najlepszy: " + d.get(1) + ", srednia: " + d.get(2) + ", najgorszy: " + d.get(3));
        }

        System.out.println("KONIEC");
    }
}
