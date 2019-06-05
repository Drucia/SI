package app;

import helpers.KeyPoint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

public class WinController
{
    public static String photo_a;
    public static String photo_b;
    public static ArrayList<Pair<Integer, Integer>> pairs;
    public static ArrayList<KeyPoint> a_p;
    public static ArrayList<KeyPoint> b_p;

    public static void initial()
    {
        int height = 300;
        int width = 400;
        Stage primaryStage = new Stage();
        ImageView imgA = new ImageView();
        imgA.setFitWidth(width);
        imgA.setFitHeight(height);
        imgA.setX(0);
        imgA.setY(0);
        imgA.setImage(new Image(photo_a));
        ImageView imgB = new ImageView();
        imgB.setFitWidth(width);
        imgB.setFitHeight(height);
        imgB.setX(0);
        imgB.setY(height);
        imgB.setImage(new Image(photo_b));
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().addAll(imgA, imgB);

        for (Pair<Integer, Integer> p : pairs) {
            Line line = new Line();
            line.setStroke(Color.LIGHTGRAY);
            KeyPoint a_cor = a_p.get(p.getKey());
            KeyPoint b_cor = b_p.get(p.getValue());
            line.setStartX(a_cor.getX()/2);
            line.setStartY(a_cor.getY()/2);
            line.setEndX(b_cor.getX()/2);
            line.setEndY(height+b_cor.getY()/2);
            root.getChildren().add(line);
        }

        primaryStage.setTitle("Images Similarity");
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("KONIEC");
    }
}
