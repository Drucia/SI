import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Loader
{
    public static String problem_name;
    public static String data_type;
    public static int dimension;
    public static int nb_of_item;
    public static int capacity;
    public static double min_speed;
    public static double max_speed;
    public static double renting_ratio;
    public static ArrayList<Town> towns;
    public static ArrayList<Item> items;


    public static void readData(String name)
    {
        towns = new ArrayList<>();
        items = new ArrayList<>();

        File file = new File(name);
        try
        {
            FileReader freader = new FileReader(file);
            BufferedReader reader = new BufferedReader(freader);
            String line = reader.readLine();
            String args[] = line.split(":");
            problem_name = args[1];

            line = reader.readLine();
            args = line.split(":");

            data_type = args[1];
            line = reader.readLine();
            args = line.split(":");

            dimension = Integer.parseInt(args[1].replaceAll("\\s",""));
            line = reader.readLine();
            args = line.split(":");

            nb_of_item = Integer.parseInt(args[1].replaceAll("\\s",""));
            line = reader.readLine();
            args = line.split(":");

            capacity = Integer.parseInt(args[1].replaceAll("\\s",""));
            line = reader.readLine();
            args = line.split(":");

            min_speed = Double.parseDouble(args[1].replaceAll("\\s",""));
            line = reader.readLine();
            args = line.split(":");

            max_speed = Double.parseDouble(args[1].replaceAll("\\s",""));
            line = reader.readLine();
            args = line.split(":");

            renting_ratio = Double.parseDouble(args[1].replaceAll("\\s",""));

            reader.readLine();
            reader.readLine();

            while((line = reader.readLine()) != null && (args = line.split("\\s")).length == 3)
            {
                towns.add(new Town(Integer.parseInt(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
            }

            while((line = reader.readLine()) != null)
            {
                args = line.split("\\s");
                items.add(new Item(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
            }
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

}
