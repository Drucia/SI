package app;

import algorithm.ImageProcessor;
import helpers.IO;
import helpers.KeyPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    public static final int SAMPLES_FOR_AFFINE = 3;
    public static final int SAMPLES_FOR_OUTLOOK = 4;

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

    @FXML
    private TextField p;

    @FXML
    private Label p_label;

    private ArrayList<Circle> circles;

    private static final String start_path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\Images_Similarity\\src\\photos";

    private File img_1;
    private File img_2;

    @FXML
    public void initialize() {
        img_1 = new File(start_path + "\\natalia_1.png");
        img_2 = new File(start_path + "\\natalia_2.png");
        circles = new ArrayList<>();
        heu_iter.setOnAction(event -> setDisabled(!heu_iter.isSelected()));
    }

    private void setDisabled(boolean selected) {
        p.setDisable(selected);
        p_label.setDisable(selected);
    }

    public void onStartClickedWithoutSelection() {
        goMergeInPairs(false);
    }

    public void onStartClickedWithSelection() {
        goMergeInPairs(true);
    }

    private void goMergeInPairs(boolean isSelection) {
        ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

        // set ImageProcessor data

        ImageProcessor.initialize(data.get(0), data.get(1));

        long startTime, endTime, totalTime;

        startTime = System.nanoTime();

        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

        int amount_before = s.size();

        if (isSelection) // filtered
            s = ImageProcessor.getConsistentPairs(Integer.parseInt(neigh_size.getText()), Integer.parseInt(neigh_lim.getText()), s);

        int amount_after = s.size();

        endTime = System.nanoTime();
        totalTime = endTime - startTime;

        System.out.println("\nWYBRANA METODA: " + (isSelection ? "Z SELEKCJĄ" : "BEZ SELEKCJI"));
        System.out.println("Czas realizacji zadania [s]: " + (double)totalTime / 1_000_000_000.0);
        System.out.println("Liczba par przed selekcją: " + amount_before);
        System.out.println("Liczba par po użyciu metody: " + amount_after);

        WinController.initial(s, ImageProcessor.imgA.getPoints(), ImageProcessor.imgB.getPoints(), img_1.toURI().toString(), img_2.toURI().toString(), isSelection ? "Z SELEKCJĄ" : "BEZ SELEKCJI");
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
        goRansac(true);
    }

    public void onOutlookStartClicked(ActionEvent actionEvent) {
        goRansac(false);
    }

    private void goRansac(boolean isAffine) {
        // prepare data for image
        ArrayList<helpers.Image> data = IO.readImagesData(img_1.getName(), img_2.getName());

        // set ImageProcessor data

        ImageProcessor.initialize(data.get(0), data.get(1));

        long startTime, endTime, totalTime;

        startTime = System.nanoTime();
        ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints();

        int amount_before = s.size();

        // filter

        s = ImageProcessor.getConsistentPairs(Integer.parseInt(neigh_size.getText()), Integer.parseInt(neigh_lim.getText()), s);

        int amount_after = s.size();

        Double size_of_image = null; // distance heuristic
        boolean is_distr_heu = false; // distr heuristic
        Integer iterations = null; // iter heuristic

        // go ransac

        if (heu_dis.isSelected())
            size_of_image = (double) (WinController.HEIGHT + WinController.WIDTH);

        if (heu_distr.isSelected())
            is_distr_heu = true;

        if (heu_iter.isSelected())
            iterations = calculateProbability(amount_after, amount_before, Integer.parseInt(p.getText()), isAffine ? SAMPLES_FOR_AFFINE : SAMPLES_FOR_OUTLOOK);

        Pair<SimpleMatrix, ArrayList<Pair<Integer, Integer>>> best_model = ImageProcessor.goRansac(s, Integer.parseInt(runsac_iter.getText()), isAffine ? SAMPLES_FOR_AFFINE : SAMPLES_FOR_OUTLOOK, isAffine, Double.parseDouble(runsac_error.getText()), size_of_image, is_distr_heu, iterations);

        endTime = System.nanoTime();
        totalTime = endTime - startTime;

        System.out.println("\nWYBRANA METODA: " + (isAffine ? "AFINICZNA" : "PERSPEKTYWICZNA"));
        System.out.println("Czas realizacji zadania [s]: " + (double)totalTime / 1_000_000_000.0);
        System.out.println("Liczba par przed selekcją: " + amount_before);
        System.out.println("Liczba par po użyciu metody: " + best_model.getValue().size());

        WinController.initial(best_model.getValue(), ImageProcessor.imgA.getPoints(), ImageProcessor.imgB.getPoints(), img_1.toURI().toString(), img_2.toURI().toString(), isAffine ? "AFINICZNA" : "PERSPEKTYWICZNA");
    }

    public static int calculateProbability(int amount_after, int amount_before, int p, int samples) {
        return (int) (Math.log(1-((double)p)/100)/Math.log(1-Math.pow((double) amount_after/amount_before, samples)));
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
