package helpers;

import java.util.ArrayList;

public class Image {
    private int amount;
    private ArrayList<KeyPoint> points;

    public Image(ArrayList<KeyPoint> points, int amount) {
        this.points = points;
        this.amount = amount;
    }

    public ArrayList<KeyPoint> getPoints() {
        return points;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Image{" +
                "amount=" + amount +
                ", points=" + points +
                '}';
    }
}
