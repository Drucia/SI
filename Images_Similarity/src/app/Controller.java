package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    @FXML
    private ImageView image_a;

    @FXML
    private ImageView image_b;

    @FXML
    private ChoiceBox<String> choice_a;

    @FXML
    private ChoiceBox<String> choice_b;

    private ArrayList<String> photos_a = new ArrayList<>(Arrays.asList("mousee_1","box_1"));
    private ArrayList<String> photos_b = new ArrayList<>(Arrays.asList("mousee_2","box_2"));

    @FXML
    public void initialize()
    {
        ObservableList<String> olist = FXCollections.observableList(photos_a);
        choice_a.setItems(olist);
        choice_a.getSelectionModel().selectFirst();

        choice_a.setOnAction(event -> {image_a.setImage(new Image("@../../photos/" + choice_a.getValue() + ".png"));});

        olist = FXCollections.observableList(photos_b);
        choice_b.setItems(olist);
        choice_b.getSelectionModel().selectFirst();

        choice_b.setOnAction(event -> {image_b.setImage(new Image("@../../photos/" + choice_b.getValue() + ".png"));});
    }

    public void onStartClicked() {
    }
}
