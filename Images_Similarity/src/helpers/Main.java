package helpers;

import algorithm.ImageProcessor;
import javafx.util.Pair;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        String img_1 = "cup_1";
        String img_2 = "cup_1";
        ArrayList<Image> data = IO.readImagesData(img_1, img_2);

        //ArrayList<Pair<Integer, Integer>> s = ImageProcessor.getListOfPairKeyPoints(data.get(0), data.get(1));

        // filtered

        //s = ImageProcessor.getConsistentPairs(300, 50, s, data.get(0), data.get(1));

        System.out.println("KONIEC");
    }
}
