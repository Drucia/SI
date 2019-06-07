package helpers;

import java.io.*;
import java.util.ArrayList;

public class IO {
    public static final String path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\Images_Similarity\\src\\photos\\";
    public static final String extension = ".haraff.sift";

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

        // use extract features to generate points file if is there not the file
        if (!file.exists()) {

            Runtime run = Runtime.getRuntime();
            String cyg_path = "cd /cygdrive/d/Users/oladr/Studia/Term_VI/Sztuczna/WorkSpace/Images_Similarity/src/photos";
            String command = "extract_features2.tar/extract_features/extract_features_32bit.exe -haraff -sift -i " + image + " -DE";

            try {
                String[] env = new String[]{"path=%PATH%;D:/cygwin/bin/"};
                Process proc = run.exec(new String[]{"bash.exe", "-c", cyg_path + " && " + command},
                        env);
                proc.waitFor();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        proc.getInputStream()));
                while (br.ready())
                    System.out.println(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // delete unused files
            File file_to_dele = new File(path + image + extension + ".png");
            file_to_dele.delete();
            file_to_dele = new File(path + image + extension + ".params");
            file_to_dele.delete();
        }

        try {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);
            int amount_of_features = Integer.parseInt(reader.readLine());

            ArrayList<KeyPoint> points = new ArrayList<>();
            int amount_of_points = Integer.parseInt(reader.readLine());

            for (int i=0; i<amount_of_points; i++)
            {
                String line[] = reader.readLine().split(" ");

                double x = Double.parseDouble(line[0]);
                double y = Double.parseDouble(line[1]);

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
