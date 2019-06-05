package helpers;

import java.util.ArrayList;

public class KeyPoint {
    private int id;
    private double x;
    private double y;
    private ArrayList<Integer> features;

    public KeyPoint(int id, double x, double y, ArrayList<Integer> features) {
        this.x = x;
        this.y = y;
        this.features = features;
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        return "KeyPoint{" +
                "x=" + x +
                ", y=" + y +
                ", features=" + features +
                '}';
    }

    public double distance(KeyPoint other)
    {
        return Math.sqrt(Math.pow((x - other.x), 2) + Math.pow((y - other.y), 2));
    }

    public double getSimilarity(KeyPoint other)
    {
        double sum = 0;
        ArrayList<Integer> feat = other.features;

        for (int i=0; i<features.size(); i++)
              sum += Math.pow((features.get(i) - feat.get(i)), 2);
            //sum += Math.abs(features.get(i) - feat.get(i));

        return Math.sqrt(sum);
    }
}
