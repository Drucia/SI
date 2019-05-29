package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IO {
    public static final String path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\Images_Similarity\\photos\\";
    public static final String extension = ".png.haraff.sift";

    public static ArrayList<Image> readImagesData(String image1, String image2)
    {
        ArrayList<Image> score = new ArrayList<>();
        score.add(readImagesData(image1));
        score.add(readImagesData(image2));

        return score;
    }

    private static Image readImagesData(String image)
    {
        Image image_scr = null;

        File file = new File(path + image + extension);
        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);
            int amount_of_features = Integer.parseInt(reader.readLine());

            ArrayList<KeyPoint> points = new ArrayList<>();
            int amount_of_points = Integer.parseInt(reader.readLine());

            for (int i=0; i<amount_of_points; i++)
            {
                String line[] = reader.readLine().split(" ");

                double x = Double.parseDouble(line[0]) / 1000;
                double y = Double.parseDouble(line[1]) / 1000;

                ArrayList<Integer> feat_of_point = new ArrayList<>();

                for (int j=5; j<amount_of_features+5; j++)
                    feat_of_point.add(Integer.parseInt(line[j]));

                KeyPoint kp = new KeyPoint(i,x,y,feat_of_point);
                points.add(kp);
            }

            image_scr = new Image(points, amount_of_points);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return image_scr;
    }
}
