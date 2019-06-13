package helpers;

import algorithm.ImageProcessor;
import app.Controller;
import app.WinController;
import javafx.util.Pair;
import org.ejml.simple.SimpleMatrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static app.Controller.SAMPLES_FOR_AFFINE;
import static app.Controller.SAMPLES_FOR_OUTLOOK;

public class Main {
    private static final int I_AMOUNT_OF_ITER = 10;
    private static final int I_NEIGHT_SIZE = 100;
    private static final int I_NEIGHT_LIM = 10;
    private static final String S_SCORE_PATH = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\Images_Similarity\\src\\scores\\";

    public static void main(String args[]){
        ArrayList<String> img_1 = new ArrayList<>(Arrays.asList("natalia_1.png", "box_1.png", "cup_1.png", "pringles_1.png"));//, "pizza_1.png", "pringles_1.png"));
        ArrayList<String> img_2 = new ArrayList<>(Arrays.asList("natalia_2.png", "box_2.png", "cup_2.png", "pringles_2.png"));//, "pizza_2.png", "pringles_2.png"));
        ArrayList<Integer> params;

                // ----- BADANIA TRANSOFRMAT -----

//        params = new ArrayList<>(Arrays.asList(4,8,12,16,20,24));
//
//        // error - iter is const 10 200
//        for (int j=0; j<img_1.size(); j++) {
//            for (int i = 0; i < params.size(); i++)
//                if (i == 0)
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_200, params.get(i), true, true);
//                else
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_200, params.get(i), true, false);
//        }
//
//        System.out.println("------------> KONIEC ERROR AFFINE");
//
//        for (int j=0; j<img_1.size(); j++) {
//            for (int i = 0; i < params.size(); i++)
//                if (i == 0)
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_200, params.get(i), false, true);
//                else
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_200, params.get(i), false, false);
//        }
//
//        System.out.println("------------> KONIEC ERROR PERSPECTIVE");
//
//        params = new ArrayList<>(Arrays.asList(10,20,50,100,1000,10000));
//
//        // iter - error is const 20
//        for (int j=0; j<img_1.size(); j++) {
//            for (int i = 0; i < params.size(); i++)
//                if (i == 0)
//                    runAlgForResearch(img_1.get(j), img_2.get(j), params.get(i), 20, true, true);
//                else
//                    runAlgForResearch(img_1.get(j), img_2.get(j), params.get(i), 20, true, false);
//        }
//
//        System.out.println("------------> KONIEC ITER AFFINE");
//
//        for (int j=0; j<img_1.size(); j++) {
//            for (int i = 0; i < params.size(); i++)
//                if (i == 0)
//                    runAlgForResearch(img_1.get(j), img_2.get(j), params.get(i), 20, false, true);
//                else
//                    runAlgForResearch(img_1.get(j), img_2.get(j), params.get(i), 20, false, false);
//        }

        // ----- BADANIA HEURYSTYK -----

        // distance heuristic
//        for (int j=0; j<img_1.size(); j++) {
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, true, true, (double) (WinController.HEIGHT + WinController.WIDTH), false, null);
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, false, true, (double) (WinController.HEIGHT + WinController.WIDTH), false, null);
//        }
//
//        System.out.println("------------> KONIEC DIST");
//
//        // distr heuristic
//        for (int j=0; j<img_1.size(); j++) {
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, true, true, null, true, null);
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, false, true, null, true, null);
//        }
//
//        System.out.println("------------> KONIEC DISTR");

//        params = new ArrayList<>(Arrays.asList(60,70,80,90,100));
//
//        // iterations heuristic
//        for (int j=0; j<img_1.size(); j++) {
//            for (int i=0; i<params.size(); i++) {
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, true, true, null, false, params.get(i));
//                    runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, false, true, null, false, params.get(i));
//            }
//        }
//
//        System.out.println("------------> KONIEC ITER");

        // all heuristic
//        for (int j=0; j<img_1.size(); j++) {
//                runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, true, true, (double) (WinController.HEIGHT + WinController.WIDTH), true, 80);
//                runAlgForResearch(img_1.get(j), img_2.get(j), 10_000, 20, false, true, (double) (WinController.HEIGHT + WinController.WIDTH), true, 80);
//        }
//
//        System.out.println("------------> KONIEC ALL");
//
//        System.out.println("KONIEC BADAN");
    }

    public static void runAlgForResearch(String chosen_file_1, String chosen_file_2, int iter, int error, boolean isAffine, boolean isTitle, Double size_of_image, boolean is_distr_heu, Integer iter_prob)
    {
        ArrayList<Pair<Integer, Double>> score = new ArrayList<>();
        StringBuilder res_path = new StringBuilder();
        res_path.append(S_SCORE_PATH);
        res_path.append(chosen_file_1);
        res_path.append("_");
        res_path.append(chosen_file_2);
        res_path.append("_isAffine=");
        res_path.append(isAffine);
        res_path.append("_");
        res_path.append(size_of_image);
        res_path.append("_isDistrHeu=");
        res_path.append(is_distr_heu);
        res_path.append("_");
        res_path.append(iter_prob == null ? null : "iter");
        res_path.append(".csv");
        String absoluteFilePath = String.valueOf(res_path);

        // prepare data for image
        ArrayList<helpers.Image> data = IO.readImagesData(chosen_file_1, chosen_file_2);

        Integer iterations = null;

        for (int i=0; i< I_AMOUNT_OF_ITER; i++)
        {
            // set ImageProcessor data

            ImageProcessor.initialize(data.get(0), data.get(1));

            long startTime, endTime, totalTime;

            startTime = System.nanoTime();
            ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

            int amount_before = s.size();

            // filter

            s = ImageProcessor.getConsistentPairs(I_NEIGHT_SIZE, I_NEIGHT_LIM, s);

            int amount_after = s.size();

            // go ransac

            if (iter_prob != null)
                iterations = Controller.calculateProbability(amount_after, amount_before, iter_prob, isAffine ? SAMPLES_FOR_AFFINE : SAMPLES_FOR_OUTLOOK);

            Pair<SimpleMatrix, ArrayList<Pair<Integer, Integer>>> best_model = ImageProcessor.goRansac(s, iter, isAffine ? SAMPLES_FOR_AFFINE : SAMPLES_FOR_OUTLOOK, isAffine, error, size_of_image, is_distr_heu, iterations);

            endTime = System.nanoTime();
            totalTime = endTime - startTime;

            System.out.println("\nWYBRANA METODA: " + (isAffine ? "AFINICZNA" : "PERSPEKTYWICZNA"));
            System.out.println("Czas realizacji zadania [s]: " + (double)totalTime / 1_000_000_000.0);
            System.out.println("Liczba par przed selekcją: " + amount_before);
            System.out.println("Liczba par po użyciu metody: " + best_model.getValue().size());

            score.add(new Pair(best_model.getValue().size(), (double)totalTime / 1_000_000_000.0));
        }

        Pair<Integer, Double> avg_score = score.get(0);

        for (int i=1; i<I_AMOUNT_OF_ITER; i++) {
            Pair<Integer, Double> curr = score.get(i);

            if(i == I_AMOUNT_OF_ITER-1) {
                avg_score = new Pair<>((avg_score.getKey()+curr.getKey())/I_AMOUNT_OF_ITER, (avg_score.getValue()+curr.getValue())/I_AMOUNT_OF_ITER);
            }
            else
            {
                avg_score = new Pair<>(avg_score.getKey()+curr.getKey(), avg_score.getValue()+curr.getValue());
            }
        }

        writeToFile(absoluteFilePath, avg_score, iterations, error, isAffine, isTitle);
    }

    private static void writeToFile(String absoluteFilePath, Pair<Integer, Double> score, Integer iter, int error, boolean isAffine, boolean isTitle)
    {
        try {
            File file = new File(absoluteFilePath);
            if (!file.exists())
                file.createNewFile();
            FileWriter f_writer = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(f_writer);

            if (isTitle)
                writer.write("Iteracje, Blad, Czy Afiniczna, Pary, Czas\n");

            StringBuilder s = new StringBuilder();
            s.append(iter);
            s.append(", ");
            s.append(error);
            s.append(", ");
            s.append(isAffine);
            s.append(", ");
            s.append(score.getKey());
            s.append(", ");
            s.append(score.getValue());
            s.append("\n");

            writer.write(String.valueOf(s));

            writer.close();
            System.out.println("KONIEC ZAPISU DO PLIKU");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
