package algorithm;

import helpers.Image;
import helpers.KeyPoint;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ImageProcessor
{
    private static List<Integer> getNearestKeyPoints(int how_much, Image img, int idx)
    {
        List<Integer> score;
        ArrayList<KeyPoint> image = img.getPoints();

            KeyPoint curr = image.get(idx); // point for which we search neighbourhood
            Collections.sort(image, (o1, o2) -> {
                    double dis = o1.distance(curr) - o2.distance(curr);

                    if (dis == 0)
                        return 0;

                    return dis < 0 ? -1 : 1;
                });

            List<KeyPoint> nearestPoints = image.stream().limit(how_much+1).collect(Collectors.toList());

            score = nearestPoints
                    .stream()
                    .map(KeyPoint::getId)
                    .collect(Collectors.toList());

            score = score.subList(1, how_much+1);

        return score;
    }

    public static ArrayList<Pair<Integer, Integer>> getConsistentPairs(int amount, int limes, ArrayList<Pair<Integer, Integer>> pairs, Image a, Image b)
    {
        double consistent_limes = ((double)limes)/100 * amount;
        ArrayList<Pair<Integer, Integer>> filter_pairs = new ArrayList<>();

        for (Pair<Integer, Integer> p : pairs)
        {
            int idx_a = p.getKey();
            int idx_b = p.getValue();

            List<Integer> neigh_a = getNearestKeyPoints(amount, a, idx_a);
            List<Integer> neigh_b = getNearestKeyPoints(amount, b, idx_b);

            // choose pairs which are in neighbourhood of pkt a and pkt b
            ArrayList<Pair<Integer, Integer>> tmp = (ArrayList<Pair<Integer, Integer>>) pairs.stream()
                    .filter(pa -> neigh_a.contains(pa.getKey()) && neigh_b.contains(pa.getValue()))
                    .collect(Collectors.toList());

            if ((double)tmp.size() >= consistent_limes)
                filter_pairs.add(p);

        }

        return filter_pairs;
    }

    public static ArrayList<Pair<Integer, Integer>> getListOfPairKeyPoints(Image imgA, Image imgB) //first from A, second from B
    {
        ArrayList<Pair<Integer, Integer>> score = new ArrayList<>();
        ArrayList<KeyPoint> pointsA = imgA.getPoints();
        ArrayList<KeyPoint> pointsB = imgB.getPoints();
        ArrayList<Integer> neighA = new ArrayList<>();
        ArrayList<Integer> neighB = new ArrayList<>();

        // for points from A

        for (int i=0; i<imgA.getAmount(); i++)
        {
            // get most similar from B
            KeyPoint curr = pointsA.get(i);
            KeyPoint best =  Collections.min(pointsB, Comparator.comparing(s -> s.getSimilarity(curr)));
            neighA.add(best.getId());
        }

        for (int i=0; i<imgB.getAmount(); i++)
        {
            // get most similar from A
            KeyPoint curr = pointsB.get(i);
            KeyPoint best =  Collections.min(pointsA, Comparator.comparing(s -> s.getSimilarity(curr)));
            neighB.add(best.getId());
        }

        int how_much = imgA.getAmount() < imgB.getAmount() ? imgA.getAmount() : imgB.getAmount();

        for (int i=0; i<how_much; i++)
        {
            if (i == neighB.get(neighA.get(i)))
                score.add(new Pair<>(i, neighA.get(i)));
        }

        return score;
    }
}
