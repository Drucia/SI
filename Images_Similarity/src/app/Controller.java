package app;

import algorithm.ImageProcessor;
import helpers.IO;
import helpers.KeyPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.ejml.simple.SimpleMatrix;
import java.io.File;
import java.util.ArrayList;

public class Controller {
    private static final int SAMPLES_FOR_AFFINE = 3;
    private static final int SAMPLES_FOR_OUTLOOK = 4;

    @FXML
    private Button circle;
    @FXML
    private ImageView image_a;

    @FXML
    private ImageView image_b;

    @FXML
    private TextField neigh_size;

    @FXML
    private TextField neigh_lim;

    @FXML
    private TextField runsac_iter;

    @FXML
    private TextField runsac_error;

    @FXML
    private AnchorPane anchor;

    @FXML
    private CheckBox heu_dis;
    @FXML
    private CheckBox heu_distr;
    @FXML
    private CheckBox heu_iter;

    private ArrayList<Circle> circles;

    private static final String start_path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\Images_Similarity\\src\\photos";

    private File img_1;
    private File img_2;

    @FXML
    public void initialize() {
        img_1 = new File(start_path + "\\natalia_1.png");
        img_2 = new File(start_path + "\\natalia_2.png");
        circles = new ArrayList<>();
    }

    public void onStartClickedWithoutSelection() {
        ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

        // set ImageProcessor data

        ImageProcessor.imgA = data.get(0);
        ImageProcessor.imgB = data.get(1);

        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

        WinController.initial(s, ImageProcessor.imgA.getPoints(), ImageProcessor.imgB.getPoints(), img_1.toURI().toString(), img_2.toURI().toString(), "BEZ SELEKCJI");
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

        WinController.initial(s, ImageProcessor.imgA.getPoints(), ImageProcessor.imgB.getPoints(), img_1.toURI().toString(), img_2.toURI().toString(), "Z SELEKCJĄ");
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

    public void onAffineStartClicked(ActionEvent actionEvent) {
        // prepare data for image
        ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

        // set ImageProcessor data

        ImageProcessor.imgA = data.get(0);
        ImageProcessor.imgB = data.get(1);

        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

        // filter

        s = ImageProcessor.getConsistentPairs(Integer.parseInt(neigh_size.getText()), Integer.parseInt(neigh_lim.getText()), s);

        Double size_of_image = null; // distance heuristic

        // go ransac with affine

        if (heu_dis.isSelected())
            size_of_image = new Double(WinController.HEIGHT + WinController.WIDTH);

        //if (heu_distr.isSelected())
        //if (heu_iter.isSelected())

        Pair<SimpleMatrix, ArrayList<Pair<Integer, Integer>>> best_model = ImageProcessor.goRansac(s, Integer.parseInt(runsac_iter.getText()), SAMPLES_FOR_AFFINE, true, Double.parseDouble(runsac_error.getText()), size_of_image);

        WinController.initial(best_model.getValue(), ImageProcessor.imgA.getPoints(), ImageProcessor.imgB.getPoints(), img_1.toURI().toString(), img_2.toURI().toString(), "AFINICZNA");
    }

    public void onOutlookStartClicked(ActionEvent actionEvent) {
        // prepare data for image
        ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

        // set ImageProcessor data

        ImageProcessor.imgA = data.get(0);
        ImageProcessor.imgB = data.get(1);

        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

        // filter

        s = ImageProcessor.getConsistentPairs(Integer.parseInt(neigh_size.getText()), Integer.parseInt(neigh_lim.getText()), s);

        Double size_of_image = null; // distance heuristic

        // go ransac with outlook

        if (heu_dis.isSelected())
            size_of_image = new Double(WinController.HEIGHT + WinController.WIDTH);

        //if (heu_distr.isSelected())
        //if (heu_iter.isSelected())  

        Pair<SimpleMatrix, ArrayList<Pair<Integer, Integer>>> best_model = ImageProcessor.goRansac(s, Integer.parseInt(runsac_iter.getText()), SAMPLES_FOR_OUTLOOK, false, Double.parseDouble(runsac_error.getText()), size_of_image);

        WinController.initial(best_model.getValue(), ImageProcessor.imgA.getPoints(), ImageProcessor.imgB.getPoints(), img_1.toURI().toString(), img_2.toURI().toString(), "PERSPEKTYWICZNA");
    }

    public void onPointsClicked(ActionEvent actionEvent) {
        if (circle.getText().equals("PUNKTY KLUCZOWE")) {

            // prepare data for image
            ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

            // draw points

            ArrayList<KeyPoint> p_a = data.get(0).getPoints();
            ArrayList<KeyPoint> p_b = data.get(1).getPoints();

            for (KeyPoint p : p_a) {
                Circle c = new Circle(6 + p.getX() / 2, 3 + p.getY() / 2, 2, Color.YELLOW);
                circles.add(c);
                anchor.getChildren().add(c);
            }

            for (KeyPoint p : p_b) {
                Circle c = new Circle(6 + p.getX() / 2, 306 + p.getY() / 2, 2, Color.YELLOW);
                circles.add(c);
                anchor.getChildren().add(c);
            }

            circle.setText("USUŃ PUNKTY");
        } else
        {
            for (Circle c : circles)
                anchor.getChildren().remove(c);

            circles = new ArrayList<>();
            circle.setText("PUNKTY KLUCZOWE");
        }
    }
}
