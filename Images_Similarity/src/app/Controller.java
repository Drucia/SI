package app;

import algorithm.ImageProcessor;
import helpers.IO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private ImageView image_a;

    @FXML
    private ImageView image_b;

    @FXML
    private TextField neigh_size;

    @FXML
    private TextField neigh_lim;

    private static final String start_path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\Images_Similarity\\src\\photos";

    private File img_1;
    private File img_2;

    @FXML
    public void initialize() {
        img_1 = new File(start_path + "\\pwr_mouse_1.png");
        img_2 = new File(start_path + "\\pwr_mouse_2.png");
    }

    public void onStartClickedWithoutSelection() {
        ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

        // set ImageProcessor data

        ImageProcessor.imgA = data.get(0);
        ImageProcessor.imgB = data.get(1);

        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

        // choose best model

        //s = ImageProcessor.goRunsac(s);

        WinController.photo_a = img_1.toURI().toString();
        WinController.photo_b = img_2.toURI().toString();
        WinController.a_p = ImageProcessor.imgA.getPoints();
        WinController.b_p = ImageProcessor.imgB.getPoints();
        WinController.pairs = s;

        WinController.initial();
    }

    public void onStartClickedWithSelection() {
        // prepare data for image
        ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

        // set ImageProcessor data

        ImageProcessor.imgA = data.get(0);
        ImageProcessor.imgB = data.get(1);

        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

        // filtered

        s = ImageProcessor.getConsistentPairs(Integer.parseInt(neigh_size.getText()), Integer.parseInt(neigh_lim.getText()), s);

        WinController.photo_a = img_1.toURI().toString();
        WinController.photo_b = img_2.toURI().toString();
        WinController.a_p = ImageProcessor.imgA.getPoints();
        WinController.b_p = ImageProcessor.imgB.getPoints();
        WinController.pairs = s;

        WinController.initial();
    }

    public void onBrowseAClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        fileChooser.setInitialDirectory(new File(start_path));
        File tmp = fileChooser.showOpenDialog(new Stage());

        if (tmp != null) {
            img_1 = tmp;
            image_a.setImage(new Image(img_1.toURI().toString()));
        }
    }

    public void onBrowseBClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        fileChooser.setInitialDirectory(new File(start_path));
        File tmp = fileChooser.showOpenDialog(new Stage());

        if (tmp != null) {
            img_2 = tmp;
            image_b.setImage(new Image(img_2.toURI().toString()));
        }
    }
}
