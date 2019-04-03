package app;

import algorithm.CSP;
import algorithm.FutoshikiConstraint;
import algorithm.SkyScrapperConstraint;
import helpers.IO;
import helpers.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Controller {

    @FXML
    private RadioButton futo_radio;

    @FXML
    private RadioButton sky_radio;

    @FXML
    private RadioButton tes_radio;

    @FXML
    private RadioButton res_radio;

    @FXML
    private RadioButton first_radio;

    @FXML
    private RadioButton min_domain_radio;

    @FXML
    private ChoiceBox<String> files;

    @FXML
    private Button start;

    private ToggleGroup group_games;

    private ToggleGroup group_path;

    private ToggleGroup group_heu;

    private ArrayList<String> test_fut = new ArrayList<>(Arrays.asList("futoshiki_4_0.txt", "futoshiki_4_1.txt"
            , "futoshiki_4_2.txt", "futoshiki_4_3.txt", "futoshiki_4_4.txt", "futoshiki_5_0.txt", "futoshiki_5_1.txt",
            "futoshiki_5_2.txt", "futoshiki_5_3.txt", "futoshiki_5_4.txt"));
    private ArrayList<String> test_sky = new ArrayList<>(Arrays.asList("skyscrapper_4_0.txt", "skyscrapper_4_1.txt"
            , "skyscrapper_4_2.txt", "skyscrapper_4_3.txt", "skyscrapper_4_4.txt", "skyscrapper_5_0.txt", "skyscrapper_5_1.txt",
            "skyscrapper_5_2.txt", "skyscrapper_5_3.txt", "skyscrapper_5_4.txt"));
    private ArrayList<String> res_fut = new ArrayList<>(Arrays.asList("test_futo_4_0.txt", "test_futo_4_1.txt"
            , "test_futo_4_2.txt", "test_futo_5_0.txt", "test_futo_5_1.txt", "test_futo_5_2.txt", "test_futo_6_0.txt",
            "test_futo_6_1.txt", "test_futo_6_2.txt", "test_futo_7_0.txt", "test_futo_7_1.txt", "test_futo_7_2.txt"
            , "test_futo_8_0.txt", "test_futo_8_1.txt", "test_futo_8_2.txt", "test_futo_9_0.txt", "test_futo_9_1.txt"
            , "test_futo_9_2.txt"));
    private ArrayList<String> res_sky = new ArrayList<>(Arrays.asList("test_sky_4_0.txt", "test_sky_4_1.txt"
            , "test_sky_4_2.txt", "test_sky_4_3.txt", "test_sky_4_4.txt", "test_sky_5_0.txt", "test_sky_5_1.txt"
            , "test_sky_5_2.txt", "test_sky_5_3.txt", "test_sky_5_4.txt", "test_sky_6_0.txt", "test_sky_6_1.txt"
            , "test_sky_6_2.txt", "test_sky_6_3.txt", "test_sky_6_4.txt"));

    @FXML
    public void initialize() {

        group_games = new ToggleGroup();
        group_path = new ToggleGroup();
        group_heu = new ToggleGroup();

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

        first_radio.setToggleGroup(group_heu);
        min_domain_radio.setToggleGroup(group_heu);
        first_radio.setSelected(true);

        ObservableList<String> list = FXCollections.observableList(test_fut);
        files.setItems(list);
        files.getSelectionModel().selectFirst();
    }


    public void onStartClicked()
    {
        int path;

        if (tes_radio.isSelected())
            path = IO.TEST;
        else
            path = IO.RESEARCH;

        String name = files.getValue();
        int heuristic = first_radio.isSelected() ? CSP.FIRST_HEURISTIC : CSP.MIN_HEURISTIC;

        if (sky_radio.isSelected()) // make for sky
        {
            // read from file
            IO.readSkyscrapperData(path, name);

            System.out.println("\n------------------START PACK------------------\n");
            Printer.printSkyscrapper(IO.matrix, IO.constraints);
            SkyScrapperConstraint sc = new SkyScrapperConstraint(IO.constraints);
            CSP.set_constans(sc, IO.dimension, heuristic);
            System.out.println("\n-----------------BACKTRACKING-----------------\n");
            ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
                Printer.printSkyscrapper(m, IO.constraints);
                System.out.println("\n");
            }
            System.out.println("Liczba rozwiązań -> " + scores.size());
            System.out.println("Counter -> " + CSP.getCounter());
            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());
            System.out.println("\n-----------------FORWARDING-----------------\n");
            scores = CSP.runForwarding(IO.matrix);
            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
                Printer.printSkyscrapper(m, IO.constraints);
                System.out.println("\n");
            }
            System.out.println("Liczba rozwiązań -> " + scores.size());
            System.out.println("Counter -> " + CSP.getCounter());
            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());
            System.out.println("\n-------------------KONIEC-------------------\n");
        }
        else // make for futoshiki
        {
            // read from file
            IO.readFutoshikiData(path, name);

            System.out.println("\n------------------START PACK------------------\n");
            Printer.printFutoshiki(IO.matrix, IO.constraints);
            FutoshikiConstraint fc = new FutoshikiConstraint(IO.constraints);
            CSP.set_constans(fc, IO.dimension, heuristic);
            System.out.println("\n-----------------BACKTRACKING-----------------\n");
            ArrayList<HashMap<Integer, ArrayList<Integer>>> scores = CSP.runBackTracking(IO.matrix);
            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
                Printer.printFutoshiki(m, IO.constraints);
                System.out.println("\n");
            }
            System.out.println("Liczba rozwiązań -> " + scores.size());
            System.out.println("Counter -> " + CSP.getCounter());
            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());
            System.out.println("\n-----------------FORWARDING-----------------\n");
            scores = CSP.runForwarding(IO.matrix);
            for (HashMap<Integer, ArrayList<Integer>> m : scores) {
                Printer.printFutoshiki(m, IO.constraints);
                System.out.println("\n");
            }
            System.out.println("Liczba rozwiązań -> " + scores.size());
            System.out.println("Counter -> " + CSP.getCounter());
            System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());
            System.out.println("\n-------------------KONIEC-------------------\n");
        }
    }
}
