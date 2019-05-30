package app;

import algorithm.ImageProcessor;
import helpers.IO;
import helpers.KeyPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    @FXML
    private AnchorPane anchor;

    @FXML
    private VBox box;

    @FXML
    private ImageView image_a;

    @FXML
    private ImageView image_b;

    @FXML
    private ChoiceBox<String> choice_a;

    @FXML
    private ChoiceBox<String> choice_b;

    private ArrayList<String> photos_a = new ArrayList<>(Arrays.asList("pwr_mouse_1","mousee_1","box_1"));
    private ArrayList<String> photos_b = new ArrayList<>(Arrays.asList("pwr_mouse_2","mousee_2","box_2"));

    @FXML
    public void initialize()
    {
        ObservableList<String> olist = FXCollections.observableList(photos_a);
        choice_a.setItems(olist);
        choice_a.getSelectionModel().selectFirst();

        choice_a.setOnAction(event -> {image_a.setImage(new Image("/photos/" + choice_a.getValue() + ".png"));});

        olist = FXCollections.observableList(photos_b);
        choice_b.setItems(olist);
        choice_b.getSelectionModel().selectFirst();

        choice_b.setOnAction(event -> {image_b.setImage(new Image("/photos/" + choice_b.getValue() + ".png"));});
    }

    public void onStartClicked() {
        String img_1 = choice_a.getValue();
        String img_2 = choice_b.getValue();
        ArrayList<helpers.Image> data = IO.readImagesData(img_1, img_2);

        helpers.Image a = data.get(0);
        helpers.Image b = data.get(1);

        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints(a,b);

        // filtered

        s = ImageProcessor.getConsistentPairs(50, 0, s, a, b);

        WinController.photo_a = img_1;
        WinController.photo_b = img_2;
        WinController.a_p = a.getPoints();
        WinController.b_p = b.getPoints();
        WinController.pairs = s;

        WinController.initial();
    }
}
