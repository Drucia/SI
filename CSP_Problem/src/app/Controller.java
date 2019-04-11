package app;

import algorithm.CSP;
import algorithm.FutoshikiConstraint;
import algorithm.SkyScrapperConstraint;
import helpers.IO;
import helpers.Printer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller {

    public static final String main_path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\Badania\\CSP\\";

    @FXML
    private WebView before;

    @FXML
    private WebView after;

    @FXML
    private RadioButton futo_radio;

    @FXML
    private RadioButton sky_radio;

    @FXML
    private RadioButton tes_radio;

    @FXML
    private RadioButton res_radio;

    @FXML
    private ChoiceBox<String> files;

    @FXML
    private ChoiceBox<String> heuristic;

    @FXML
    private Label amount;
    @FXML
    private Label time;
    @FXML
    private Label count;

    @FXML
    private Label amountf;
    @FXML
    private Label timef;
    @FXML
    private Label countf;

    @FXML
    private Button start;

    private ToggleGroup group_games;

    private ToggleGroup group_path;

    //private ToggleGroup group_heu;

    private static ArrayList<String> heu = new ArrayList<>(Arrays.asList("left_up", "min_domain", "max_constraint", "rand"));

    private static ArrayList<String> test_fut = new ArrayList<>(Arrays.asList("futoshiki_4_0.txt", "futoshiki_4_1.txt"
            , "futoshiki_4_2.txt", "futoshiki_4_3.txt", "futoshiki_4_4.txt", "futoshiki_5_0.txt", "futoshiki_5_1.txt",
            "futoshiki_5_2.txt", "futoshiki_5_3.txt", "futoshiki_5_4.txt"));
    private static ArrayList<String> test_sky = new ArrayList<>(Arrays.asList("skyscrapper_4_0.txt", "skyscrapper_4_1.txt"
            , "skyscrapper_4_2.txt", "skyscrapper_4_3.txt", "skyscrapper_4_4.txt", "skyscrapper_5_0.txt", "skyscrapper_5_1.txt",
            "skyscrapper_5_2.txt", "skyscrapper_5_3.txt", "skyscrapper_5_4.txt"));
    private static ArrayList<String> res_fut = new ArrayList<>(Arrays.asList("test_futo_4_0.txt", "test_futo_4_1.txt"
            , "test_futo_4_2.txt", "test_futo_5_0.txt", "test_futo_5_1.txt", "test_futo_5_2.txt", "test_futo_6_0.txt",
            "test_futo_6_1.txt", "test_futo_6_2.txt", "test_futo_7_0.txt", "test_futo_7_1.txt", "test_futo_7_2.txt"
            , "test_futo_8_0.txt", "test_futo_8_1.txt", "test_futo_8_2.txt", "test_futo_9_0.txt", "test_futo_9_1.txt"
            , "test_futo_9_2.txt"));
    private static ArrayList<String> res_sky = new ArrayList<>(Arrays.asList("test_sky_4_0.txt", "test_sky_4_1.txt"
            , "test_sky_4_2.txt", "test_sky_4_3.txt", "test_sky_4_4.txt", "test_sky_5_0.txt", "test_sky_5_1.txt"
            , "test_sky_5_2.txt", "test_sky_5_3.txt", "test_sky_5_4.txt", "test_sky_6_0.txt", "test_sky_6_1.txt"
            , "test_sky_6_2.txt", "test_sky_6_3.txt", "test_sky_6_4.txt"));

    @FXML
    public void initialize() {

        group_games = new ToggleGroup();
        group_path = new ToggleGroup();
        //group_heu = new ToggleGroup();

        futo_radio.setToggleGroup(group_games);
        sky_radio.setToggleGroup(group_games);
        futo_radio.setSelected(true);

        futo_radio.setOnAction(event -> {
            ObservableList<String> list;
            if (tes_radio.isSelected())
                list = FXCollections.observableList(test_fut);
            else
                list = FXCollections.observableList(res_fut);
            files.setItems(list);
            files.getSelectionModel().selectFirst();
        });

        sky_radio.setOnAction(event -> {
            ObservableList<String> list;
            if (tes_radio.isSelected())
                list = FXCollections.observableList(test_sky);
            else
                list = FXCollections.observableList(res_sky);
            files.setItems(list);
            files.getSelectionModel().selectFirst();
        });

        tes_radio.setToggleGroup(group_path);
        res_radio.setToggleGroup(group_path);
        tes_radio.setSelected(true);

        tes_radio.setOnAction(event -> {
            ObservableList<String> list;
            if (futo_radio.isSelected())
                list = FXCollections.observableList(test_fut);
            else
                list = FXCollections.observableList(test_sky);
            files.setItems(list);
            files.getSelectionModel().selectFirst();
        });

        res_radio.setOnAction(event -> {
            ObservableList<String> list;
            if (futo_radio.isSelected())
                list = FXCollections.observableList(res_fut);
            else
                list = FXCollections.observableList(res_sky);
            files.setItems(list);
            files.getSelectionModel().selectFirst();
        });

        ObservableList<String> list = FXCollections.observableList(heu);
        heuristic.setItems(list);
        heuristic.getSelectionModel().selectFirst();

        list = FXCollections.observableList(test_fut);
        files.setItems(list);
        files.getSelectionModel().selectFirst();
    }

    private static void writeToFile(String game, String name, String heuristic, String alg, ArrayList<HashMap<Integer, ArrayList<Integer>>> scores, int how_many, int counter, long time)
    {
        try {
            // only for research

            File file = new File(main_path + game + "\\" + heuristic + ".csv");
            boolean is_created = file.createNewFile();

            FileWriter f_writer = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(f_writer);

            if (is_created)
                writer.write("Nazwa pliku, Nazwa algorytmu, Liczba rozwiazan, Licznik, Czas\n");

            StringBuilder s_res = new StringBuilder();
            s_res.append(name);
            s_res.append(",");
            s_res.append(alg);
            s_res.append(",");
            s_res.append(how_many);
            s_res.append(",");
            s_res.append(counter);
            s_res.append(",");
            s_res.append(time);
            s_res.append("\n");
            writer.write(String.valueOf(s_res));

            writer.close();

            // only for html score

            if (game.equals("skyscraper"))
                Printer.printSkyscrapperHtml(name, scores, IO.constraints);
            else
                Printer.printFutoshikiHtml(name, scores, IO.constraints);
            System.out.println("KONIEC");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        // research for test_futo
//        for(String s : test_fut)
//        {
//            IO.readFutoshikiData(IO.TEST, s);
//            FutoshikiConstraint fc = new FutoshikiConstraint(IO.constraints);
//
//            for (int i=0; i<4; i++)
//            {
//                CSP.set_constans(fc, IO.dimension, i);
//                ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
//                writeToFile("futoshiki", s, heu.get(i), "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//                scores = CSP.runForwarding(IO.matrix);
//                writeToFile("futoshiki", s, heu.get(i), "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//            }
//        }

        // research for test_sky
//        for(String s : test_sky)
//        {
//            IO.readSkyscrapperData(IO.TEST, s);
//            SkyScrapperConstraint sc = new SkyScrapperConstraint(IO.constraints);
//
//            for (int i=0; i<4; i++)
//            {
//                CSP.set_constans(sc, IO.dimension, i);
//                ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
//                writeToFile("skyscraper", s, heu.get(i), "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//                scores = CSP.runForwarding(IO.matrix);
//                writeToFile("skyscraper", s, heu.get(i), "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//            }
//        }

        // research for res_futo
//        for(int s=0; s<15; s++)
//        {
//            IO.readFutoshikiData(IO.RESEARCH, res_fut.get(s));
//            FutoshikiConstraint fc = new FutoshikiConstraint(IO.constraints);
//
//            for (int i=0; i<4; i++)
//            {
//                CSP.set_constans(fc, IO.dimension, i);
//                ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
//                writeToFile("futoshiki", res_fut.get(s), heu.get(i), "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//                scores = CSP.runForwarding(IO.matrix);
//                writeToFile("futoshiki", res_fut.get(s), heu.get(i), "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//            }
//        }

//            IO.readFutoshikiData(IO.RESEARCH, "test_futo_6_1.txt");
//            FutoshikiConstraint fc = new FutoshikiConstraint(IO.constraints);
//            CSP.set_constans(fc, IO.dimension, CSP.MAX_HEURISTIC);
//            ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runForwarding(IO.matrix);
//            writeToFile("futoshiki", "test_futo_6_1.txt", heu.get(2), "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//
//            IO.readFutoshikiData(IO.RESEARCH, "test_futo_7_0.txt");
//            fc = new FutoshikiConstraint(IO.constraints);
//
//            for (int i=0; i<4; i++)
//            {
//                CSP.set_constans(fc, IO.dimension, i);
//                scores = CSP.runBackTracking(IO.matrix);
//                writeToFile("futoshiki", "test_futo_7_0.txt", heu.get(i), "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//                scores = CSP.runForwarding(IO.matrix);
//                writeToFile("futoshiki", "test_futo_7_0.txt", heu.get(i), "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//            }
//
//            IO.readFutoshikiData(IO.RESEARCH, "test_futo_8_0.txt");
//            fc = new FutoshikiConstraint(IO.constraints);
//
//            for (int i=0; i<4; i++)
//            {
//                CSP.set_constans(fc, IO.dimension, i);
//                scores = CSP.runBackTracking(IO.matrix);
//                writeToFile("futoshiki", "test_futo_8_0.txt", heu.get(i), "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//                scores = CSP.runForwarding(IO.matrix);
//                writeToFile("futoshiki", "test_futo_8_0.txt", heu.get(i), "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//            }

        // research for res_sky
//        for(int s=0; s<15; s++)
//        {
//            IO.readSkyscrapperData(IO.RESEARCH, res_sky.get(s));
//            SkyScrapperConstraint sc = new SkyScrapperConstraint(IO.constraints);
//
//            for (int i=0; i<4; i++)
//            {
//                CSP.set_constans(sc, IO.dimension, i);
//                ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
//                writeToFile("skyscraper", res_sky.get(s), heu.get(i), "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//                scores = CSP.runForwarding(IO.matrix);
//                writeToFile("skyscraper", res_sky.get(s), heu.get(i), "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());
//            }
//        }
    }

    public void onStartClicked()
    {
        int path;

        if (tes_radio.isSelected())
            path = IO.TEST;
        else
            path = IO.RESEARCH;

        String name = files.getValue();

        String heu = heuristic.getValue();
        int heuristic = 0;

        switch(heu)
        {
            case "left_up":
                heuristic = CSP.FIRST_HEURISTIC;
                break;
            case "min_domain":
                heuristic = CSP.MIN_HEURISTIC;
                break;
            case "max_constraint":
                heuristic = CSP.MAX_HEURISTIC;
                break;
            case "rand":
                heuristic = CSP.RAND_HEURISTIC;
                break;
        }

        if (sky_radio.isSelected()) // make for sky
        {
            // read from file
            IO.readSkyscrapperData(path, name);

//            System.out.println("\n------------------START PACK------------------\n");
//            Printer.printSkyscrapper(IO.matrix, IO.constraints);

            Printer.printSkyscrapperHtml(name+"_start", new ArrayList<>(Arrays.asList(IO.matrix)), IO.constraints);
            WebEngine webEngine = before.getEngine();
            File file = new File(Controller.main_path + "\\html\\" + name+"_start" + "\\0.html");
            URL url= null;
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            webEngine.load(url.toString());

            SkyScrapperConstraint sc = new SkyScrapperConstraint(IO.constraints);
            CSP.set_constans(sc, IO.dimension, heuristic);
//            System.out.println("\n-----------------BACKTRACKING-----------------\n");
            ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
//            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
//                Printer.printSkyscrapper(m, IO.constraints);
//                System.out.println("\n");
//            }
//            System.out.println("Liczba rozwiązań -> " + scores.size());
//            System.out.println("Counter -> " + CSP.getCounter());
//            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());

            amount.setText(scores.size()+"");
            count.setText(CSP.getCounter()+"");
            time.setText(CSP.getTotalTime()+"");
            // write to file
            writeToFile("skyscraper", name, heu, "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());

//            System.out.println("\n-----------------FORWARDING-----------------\n");
            scores = CSP.runForwarding(IO.matrix);
//            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
//                Printer.printSkyscrapper(m, IO.constraints);
//                System.out.println("\n");
//            }
//            System.out.println("Liczba rozwiązań -> " + scores.size());
//            System.out.println("Counter -> " + CSP.getCounter());
//            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());

            amountf.setText(scores.size()+"");
            countf.setText(CSP.getCounter()+"");
            timef.setText(CSP.getTotalTime()+"");
            writeToFile("skyscraper", name, heu, "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());

            webEngine = after.getEngine();
            file = new File(Controller.main_path + "\\html\\" + name + "\\0.html");
            url= null;
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            webEngine.load(url.toString());
//            System.out.println("\n-------------------KONIEC-------------------\n");
        }
        else // make for futoshiki
        {
            // read from file
            IO.readFutoshikiData(path, name);

//            System.out.println("\n------------------START PACK------------------\n");
//            Printer.printFutoshiki(IO.matrix, IO.constraints);
            Printer.printFutoshikiHtml(name+"_start", new ArrayList<>(Arrays.asList(IO.matrix)), IO.constraints);
            WebEngine webEngine = before.getEngine();
            File file = new File(Controller.main_path + "\\html\\" + name+"_start" + "\\0.html");
            URL url= null;
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            webEngine.load(url.toString());

            FutoshikiConstraint fc = new FutoshikiConstraint(IO.constraints);
            CSP.set_constans(fc, IO.dimension, heuristic);
//            System.out.println("\n-----------------BACKTRACKING-----------------\n");
            ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
//            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
//                Printer.printFutoshiki(m, IO.constraints);
//                System.out.println("\n");
//            }
//            System.out.println("Liczba rozwiązań -> " + scores.size());
//            System.out.println("Counter -> " + CSP.getCounter());
//            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());

            amount.setText(scores.size()+"");
            count.setText(CSP.getCounter()+"");
            time.setText(CSP.getTotalTime()+"");
            // write to file
            writeToFile("futoshiki", name, heu, "backtracking", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());


//            System.out.println("\n-----------------FORWARDING-----------------\n");
            scores = CSP.runForwarding(IO.matrix);
//            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
//                Printer.printFutoshiki(m, IO.constraints);
//                System.out.println("\n");
//            }
//            System.out.println("Liczba rozwiązań -> " + scores.size());
//            System.out.println("Counter -> " + CSP.getCounter());
//            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());
            amountf.setText(scores.size()+"");
            countf.setText(CSP.getCounter()+"");
            timef.setText(CSP.getTotalTime()+"");
            writeToFile("futoshiki", name, heu, "forwarding", scores, scores.size(), CSP.getCounter(), CSP.getTotalTime());

            webEngine = after.getEngine();
            file = new File(Controller.main_path + "\\html\\" + name + "\\0.html");
            url= null;
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            webEngine.load(url.toString());
//            System.out.println("\n-------------------KONIEC-------------------\n");
        }
    }
}
