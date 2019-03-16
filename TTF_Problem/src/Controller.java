import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    @FXML
    private ComboBox<String> choose_file;

    @FXML
    private LineChart<?, ?> chart;

    @FXML
    private TextField pop_size;

    @FXML
    private TextField tour;

    @FXML
    private TextField gen;

    @FXML
    private TextField best;

    @FXML
    private TextField px;

    @FXML
    private TextField pm;

    @FXML
    private Label winner;

    @FXML
    private Label error;

    @FXML
    private Label pop_size_l;

    @FXML
    private Label tour_size_l;

    @FXML
    private Label gen_l;

    @FXML
    private Label best_l;

    @FXML
    private Label px_l;

    @FXML
    private Label pm_l;


    @FXML
    private ChoiceBox<String> choice;

    @FXML
    private RadioButton weight;

    @FXML
    private RadioButton profit;

    @FXML
    private RadioButton ratio;

    private ArrayList<String> files;
    private ToggleGroup group;
    private boolean is_greedy = false;

    @FXML
    public void initialize() {
        files = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            files.add("easy_" + i + ".ttp");
            files.add("hard_" + i + ".ttp");
            files.add("medium_" + i + ".ttp");
        }
        files.add("trivial_0.ttp");
        files.add("trivial_1.ttp");
        Collections.sort(files);

        ObservableList<String> list = FXCollections.observableList(files);
        choose_file.setItems(list);
        choose_file.setValue("easy_0.ttp");

        group = new ToggleGroup();

        ratio.setToggleGroup(group);
        ratio.setSelected(true);

        weight.setToggleGroup(group);

        profit.setToggleGroup(group);

        choice.getItems().addAll("zachlanny", "genetyczny");
        choice.setValue("genetyczny");

        choice.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    is_greedy = newValue.equals("zachlanny");
                    if (is_greedy)
                    {
                        pop_size.setVisible(false);
                        tour.setVisible(false);
                        gen.setVisible(false);
                        best.setVisible(false);
                        px.setVisible(false);
                        pm.setVisible(false);
                        pop_size_l.setVisible(false);
                        tour_size_l.setVisible(false);
                        gen_l.setVisible(false);
                        best_l.setVisible(false);
                        px_l.setVisible(false);
                        pm_l.setVisible(false);
                    }
                    else
                    {
                        pop_size.setVisible(true);
                        tour.setVisible(true);
                        gen.setVisible(true);
                        best.setVisible(true);
                        px.setVisible(true);
                        pm.setVisible(true);
                        pop_size_l.setVisible(true);
                        tour_size_l.setVisible(true);
                        gen_l.setVisible(true);
                        best_l.setVisible(true);
                        px_l.setVisible(true);
                        pm_l.setVisible(true);

                    }});
    }

    public void onSubmitClicked() {
        error.setVisible(false);
        try {
            int chosen_file = files.indexOf(choose_file.getValue());
            int pop = Integer.parseInt(pop_size.getText());
            int g = Integer.parseInt(gen.getText());
            int t = Integer.parseInt(tour.getText());
            int b = Integer.parseInt(best.getText());
            double x = Double.parseDouble(px.getText());
            double m = Double.parseDouble(pm.getText());
            int greedy = KNP.BY_RATIO_ID;
            String gr = ((RadioButton) group.getSelectedToggle()).getId();

            switch (gr) {
                case "ratio":
                    greedy = KNP.BY_RATIO_ID;
                    break;
                case "weight":
                    greedy = KNP.BY_WEIGHT_ID;
                    break;
                case "profit":
                    greedy = KNP.BY_PROFIT_ID;
                    break;
            }

            MainManager manager = new MainManager();
            ArrayList<ArrayList<Double>> score;

            if (is_greedy)
            {
                score = manager.runAlg(chosen_file, greedy, is_greedy);
            }
            else
                score = manager.runAlg(chosen_file, pop, g, t, b, x, m, greedy);

            chart.setTitle("Najlepsze i najgorsze fitness dla danych populacji");

            XYChart.Series series_1 = new XYChart.Series();
            series_1.setName("Najlepszy");
            XYChart.Series series_2 = new XYChart.Series();
            series_2.setName("Srednia");
            XYChart.Series series_3 = new XYChart.Series();
            series_3.setName("Najgorszy");

            for (int i = 0; i < score.size(); i++) {
                ArrayList<Double> res = score.get(i);
                String X = (i + 1) + "";
                series_1.getData().add(new XYChart.Data(X, res.get(0)));
                series_2.getData().add(new XYChart.Data(X, res.get(1)));
                series_3.getData().add(new XYChart.Data(X, res.get(2)));

                if (i == score.size() - 1)
                    winner.setText("\t" + res.get(0) + "");
            }
            chart.getData().clear();
            chart.getData().addAll(series_1, series_2, series_3);
        } catch (Exception e) {
            chart.getData().clear();
            error.setVisible(true);
            e.printStackTrace();
        }
    }
}