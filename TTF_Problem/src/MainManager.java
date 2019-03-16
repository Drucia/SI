import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainManager
{
    private static final String path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\TTF_Problem\\src\\data\\student\\";
    private ArrayList<String> files;
    private static final String score_path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\Badania\\TTF\\";

    public MainManager()
    {
        files = new ArrayList<>();
        for (int i=0; i<5; i++)
        {
            files.add("easy_" + i + ".ttp");
            files.add("hard_" + i + ".ttp");
            files.add("medium_" + i + ".ttp");
        }
        files.add("trivial_0.ttp");
        files.add("trivial_1.ttp");
        Collections.sort(files);
    }

    public ArrayList<ArrayList<Double>> runAlg(int chosen_file, int pop, int gen, int tour, int best, double px, double pm, int greedy_knp)
    {
        Loader.readData(path + files.get(chosen_file));
        DistanceManager.initializeMatrix(Loader.towns);

        GA.setConsts(pop, gen, tour, best, px, pm, greedy_knp);

        ArrayList<ArrayList<Double>> all_pops;
        List<TTF> start_pop = GA.initialise();

        GA.evaluate(start_pop);
        all_pops = GA.score;

        Double px_d = px*100;
        Double pm_d = pm*100;

        StringBuilder res_path = new StringBuilder();
        res_path.append(score_path);
        res_path.append(files.get(chosen_file));
        res_path.append("_");
        res_path.append(pop);
        res_path.append("_");
        res_path.append(gen);
        res_path.append("_");
        res_path.append(tour);
        res_path.append("_");
        res_path.append(best);
        res_path.append("_");
        res_path.append(px_d.intValue());
        res_path.append("_");
        res_path.append(pm_d.intValue());
        res_path.append("_");
        res_path.append(greedy_knp);
        res_path.append(".csv");
        String absoluteFilePath = String.valueOf(res_path);
        writeToFile(absoluteFilePath, all_pops);

        return all_pops;
    }

    private void writeToFile(String absoluteFilePath, ArrayList<ArrayList<Double>> all_pops)
    {
        try {
            File file = new File(absoluteFilePath);
            file.createNewFile();
            FileWriter f_writer = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(f_writer);
            writer.write("Numer populacji, Najlepszy, Srednia, Najgorszy\n");

            for (int i = 0; i < all_pops.size(); i++) {
                ArrayList<Double> curr_pop = all_pops.get(i);
                //System.out.println("Numer populacji: " + i + ", najlepszy: " + curr_pop.get(0) + ", srednia: " + curr_pop.get(1) + ", najgorszy: " + curr_pop.get(2));
                StringBuilder s_res = new StringBuilder();
                s_res.append(i+1);
                s_res.append(",");
                s_res.append(curr_pop.get(0));
                s_res.append(",");
                s_res.append(curr_pop.get(1));
                s_res.append(",");
                s_res.append(curr_pop.get(2));
                s_res.append("\n");
                writer.write(String.valueOf(s_res));
            }

            writer.close();
            System.out.println("KONIEC");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        Loader.readData(path + "easy_0.ttp");
        DistanceManager.initializeMatrix(Loader.towns);

        GA.setConsts(100   , 100, 5, 2, 0.7, 0.01, KNP.BY_RATIO_ID);

        ArrayList<ArrayList<Double>> all_pops;
        List<TTF> start_pop = GA.initialise();
        GA.evaluate(start_pop);
        all_pops = GA.score;
        int i = 1;

        for (ArrayList<Double> d: all_pops)
        {
            System.out.println("Numer populacji: " + i + ", najlepszy: " + d.get(0) + ", srednia: " + d.get(1) + ", najgorszy: " + d.get(2));
            i++;
        }

        System.out.println("KONIEC");
    }

    public Double runAlg(int chosen_file, int knp) {
        Loader.readData(path + files.get(chosen_file));
        DistanceManager.initializeMatrix(Loader.towns);

        TTF os = new TTF(knp, true);
        double score = os.calcObjectiveFunction();

        StringBuilder res_path = new StringBuilder();
        res_path.append(score_path);
        res_path.append(files.get(chosen_file));
        res_path.append("_");
        res_path.append(knp);
        res_path.append("_greedy_tsp.csv");
        String absoluteFilePath = String.valueOf(res_path);

        try {
            File file = new File(absoluteFilePath);
            file.createNewFile();
            FileWriter f_writer = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(f_writer);
            writer.write("Fitness\n" + score);
            writer.close();
            System.out.println("KONIEC");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return os.calcObjectiveFunction();
    }
}
