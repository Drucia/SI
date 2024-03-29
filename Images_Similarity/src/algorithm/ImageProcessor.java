package algorithm;

import helpers.Image;
import helpers.KeyPoint;
import javafx.util.Pair;
import org.ejml.simple.SimpleMatrix;

import java.util.*;
import java.util.stream.Collectors;

public class ImageProcessor
{
    public static Image imgA;
    public static Image imgB;
    private static ArrayList<KeyPoint> a_p;
    private static ArrayList<KeyPoint> b_p;

    public static void initialize(Image ia, Image ib)
    {
        imgA = ia;
        imgB = ib;
        a_p = ia.getPoints();
        b_p = ib.getPoints();
    }

    private static List<Integer> getNearestKeyPoints(int how_much, Image img, int idx)
    {
        List<Integer> score;
        ArrayList<KeyPoint> image = new ArrayList<>(img.getPoints());

            KeyPoint curr = image.get(idx); // point for which we search neighbourhood
            Collections.sort(image, (o1, o2) -> {
                    double dis = o1.distance(curr) - o2.distance(curr);

                    if (dis == 0)
                        return 0;

                    return dis < 0 ? -1 : 1;
                });

            // get only different than 0

            image.removeIf(p -> p.distance(curr) == 0);

            List<KeyPoint> nearestPoints = image.stream().limit(how_much).collect(Collectors.toList());

            score = nearestPoints
                    .stream()
                    .map(KeyPoint::getId)
                    .collect(Collectors.toList());

        return score;
    }

    public static ArrayList<Pair<Integer, Integer>> getConsistentPairs(int amount, int limes, ArrayList<Pair<Integer, Integer>> pairs)
    {
        double consistent_limes = ((double)limes)/100 * amount;
        ArrayList<Pair<Integer, Integer>> filter_pairs = new ArrayList<>();

        for (Pair<Integer, Integer> p : pairs)
        {
            int idx_a = p.getKey();
            int idx_b = p.getValue();

            List<Integer> neigh_a = getNearestKeyPoints(amount, imgA, idx_a);
            List<Integer> neigh_b = getNearestKeyPoints(amount, imgB, idx_b);

            ArrayList<Pair<Integer, Integer>> tmp_pairs = new ArrayList<>(pairs);

            // choose pairs which are in neighbourhood of pkt a and pkt b
            ArrayList<Pair<Integer, Integer>> tmp = (ArrayList<Pair<Integer, Integer>>) tmp_pairs.stream()
                    .filter(pa -> neigh_a.contains(pa.getKey()) && neigh_b.contains(pa.getValue()))
                    .collect(Collectors.toList());

            if ((double)tmp.size() >= consistent_limes)
                filter_pairs.add(p);

        }

        return filter_pairs;
    }

    public static ArrayList<Pair<Integer, Integer>> getListOfPairKeyPoints()
    {
        ArrayList<Pair<Integer, Integer>> score = new ArrayList<>();
        ArrayList<KeyPoint> pointsA = new ArrayList<>(imgA.getPoints());
        ArrayList<KeyPoint> pointsB = new ArrayList<>(imgB.getPoints());
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

    public static Pair<SimpleMatrix, ArrayList<Pair<Integer, Integer>>> goRansac(ArrayList<Pair<Integer, Integer>> data, int iter, int samples, boolean isAffine, double max_error, Double size_of_image, boolean is_distr_heu, Integer iterations){
        SimpleMatrix best_model = null;
        int best_score = 0;
        ArrayList<Pair<Integer, Integer>> consensus = new ArrayList<>();

        if (data.size() >= samples) {
            ArrayList<Pair<Integer, Integer>> source = new ArrayList<>(data);

            if (iterations != null)
                iter = iterations;

            for (int i = 0; i < iter; i++) {
                SimpleMatrix curr_model = null;
                List<Pair<Integer, Integer>> s = null;

                while (curr_model == null) {
                    Collections.shuffle(source);
                    s = size_of_image == null ? getPairs(source, samples) : getPairsWithImageSize(source, size_of_image, samples);
                    curr_model = calculateModel(s, isAffine);
                }

                int score = 0;
                ArrayList<Pair<Integer, Integer>> curr_consensus = new ArrayList<>();

                for (Pair<Integer, Integer> pair : data) {
                    double error = modelError(curr_model, pair, isAffine);
                    if (error < max_error) {
                        score++;
                        curr_consensus.add(pair);
                    }
                }

                if (score > best_score) {
                    best_score = score;
                    best_model = curr_model;
                    consensus = curr_consensus;

                    if (is_distr_heu)
                        source.addAll(s);
                }
            }
        }

        return new Pair<>(best_model, consensus);
    }

    private static List<Pair<Integer, Integer>> getPairsWithImageSize(ArrayList<Pair<Integer, Integer>> data, Double image_size, int samples)
    {
        Random random = new Random();
                double r = image_size / 100;
                double R = image_size * 0.5;

        ArrayList<Pair<Integer, Integer>> s = new ArrayList<>();
                    s.add(data.get(random.nextInt(data.size())));

                    while (s.size() < samples)
                    {
                        Pair<Integer, Integer> next_pair = data.get(random.nextInt(data.size()));
                        boolean isBad = false;

                        for (int p=0; p<s.size() && !isBad; p++)
                        {
                            int id_a = s.get(p).getKey();
                            int id_b = s.get(p).getValue();
                            KeyPoint last_a = a_p.get(id_a);
                            KeyPoint last_b = b_p.get(id_b);

                            int id_next_a = next_pair.getKey();
                            int id_next_b = next_pair.getValue();
                            KeyPoint next_a = a_p.get(id_next_a);
                            KeyPoint next_b = b_p.get(id_next_b);

                            double first = (Math.pow(last_a.getX() - next_a.getX(), 2) + Math.pow(last_a.getY() - next_a.getY(), 2));
                            double second = (Math.pow(last_b.getX() - next_b.getX(), 2) + Math.pow(last_b.getY() - next_b.getY(), 2));

                            if ((first > r*r && first < R*R) && (second > r*r && second < R*R))
                                s.add(next_pair);
                            else
                                isBad = true;
                        }
                    }
                return s;
    }

    private static List<Pair<Integer, Integer>> getPairs(ArrayList<Pair<Integer, Integer>> data, int samples)
    {
        return data.subList(0, samples);
    }

    private static double modelError(SimpleMatrix curr_model, Pair<Integer, Integer> pairs, boolean isAffine) {
        if (isAffine)
            return affineModelError(curr_model, pairs);
        else
            return outlookModelError(curr_model, pairs);
    }

    private static double outlookModelError(SimpleMatrix curr_model, Pair<Integer, Integer> pair) {
        double a = curr_model.get(0, 0);
        double b = curr_model.get(0, 1);
        double c = curr_model.get(0, 2);
        double d = curr_model.get(1, 0);
        double e = curr_model.get(1, 1);
        double f = curr_model.get(1, 2);
        double g = curr_model.get(2, 0);
        double h = curr_model.get(2, 1);
        double x = a_p.get(pair.getKey()).getX();
        double y = a_p.get(pair.getKey()).getY();
        double u = b_p.get(pair.getValue()).getX();
        double v = b_p.get(pair.getValue()).getY();

        double trans_x = a*x + b*y + c;
        double trans_y = d*x + e*y + f;
        double t = g*x + h*y + 1;
        trans_x /= t;
        trans_y /= t;

        return Math.sqrt(Math.pow((trans_x - u), 2) + Math.pow((trans_y - v), 2));
    }

    private static double affineModelError(SimpleMatrix curr_model, Pair<Integer, Integer> pair)
    {
        double a = curr_model.get(0, 0);
        double b = curr_model.get(0, 1);
        double c = curr_model.get(0, 2);
        double d = curr_model.get(1, 0);
        double e = curr_model.get(1, 1);
        double f = curr_model.get(1, 2);
        double x = a_p.get(pair.getKey()).getX();
        double y = a_p.get(pair.getKey()).getY();
        double u = b_p.get(pair.getValue()).getX();
        double v = b_p.get(pair.getValue()).getY();

        double trans_x = a*x + b*y + c;
        double trans_y = d*x + e*y + f;

        return Math.sqrt(Math.pow((trans_x - u), 2) + Math.pow((trans_y - v), 2));
    }

    private static SimpleMatrix calculateModel(List<Pair<Integer, Integer>> s, boolean isAffine) {
        if (isAffine)
            return affineTransform(s);
        else
            return outlookTransform(s);
    }

    private static SimpleMatrix affineTransform(List<Pair<Integer, Integer>> pairs)
    {
        // prepare data for matrix

        KeyPoint a1 = a_p.get(pairs.get(0).getKey());
        KeyPoint a2 = a_p.get(pairs.get(1).getKey());
        KeyPoint a3 = a_p.get(pairs.get(2).getKey());
        KeyPoint b1 = b_p.get(pairs.get(0).getValue());
        KeyPoint b2 = b_p.get(pairs.get(1).getValue());
        KeyPoint b3 = b_p.get(pairs.get(2).getValue());

        double[][] data_to_matrix_left = {
                {a1.getX(), a1.getY(), 1, 0, 0, 0},
                {a2.getX(), a2.getY(), 1, 0, 0, 0},
                {a3.getX(), a3.getY(), 1, 0, 0, 0},
                {0, 0, 0, a1.getX(), a1.getY(), 1},
                {0, 0, 0, a2.getX(), a2.getY(), 1},
                {0, 0, 0, a3.getX(), a3.getY(), 1}
        };

        SimpleMatrix m_left = new SimpleMatrix(data_to_matrix_left);

        double[][] data_to_matrix_right = {
                {b1.getX()},
                {b2.getX()},
                {b3.getX()},
                {b1.getY()},
                {b2.getY()},
                {b3.getY()}
        };

        SimpleMatrix m_right = new SimpleMatrix(data_to_matrix_right);
        SimpleMatrix score;

        try {
            score = m_left.invert().mult(m_right);
            double data_to_matrix_a[][] = {
                    {score.get(0), score.get(1), score.get(2)},
                    {score.get(3), score.get(4), score.get(5)},
                    {0, 0, 1}
            };

            SimpleMatrix A = new SimpleMatrix(data_to_matrix_a);

            return A;
        } catch (Exception e)
        {
            return null;
        }
    }

    private static SimpleMatrix outlookTransform(List<Pair<Integer, Integer>> pairs)
    {
        // prepare data for matrix

        KeyPoint a1 = a_p.get(pairs.get(0).getKey());
        KeyPoint a2 = a_p.get(pairs.get(1).getKey());
        KeyPoint a3 = a_p.get(pairs.get(2).getKey());
        KeyPoint a4 = a_p.get(pairs.get(3).getKey());
        KeyPoint b1 = b_p.get(pairs.get(0).getValue());
        KeyPoint b2 = b_p.get(pairs.get(1).getValue());
        KeyPoint b3 = b_p.get(pairs.get(2).getValue());
        KeyPoint b4 = b_p.get(pairs.get(3).getValue());

        double[][] data_to_matrix_left = {
                {a1.getX(), a1.getY(), 1, 0, 0, 0, -b1.getX()*a1.getX(), -b1.getX()*a1.getY()},
                {a2.getX(), a2.getY(), 1, 0, 0, 0, -b2.getX()*a2.getX(), -b2.getX()*a2.getY()},
                {a3.getX(), a3.getY(), 1, 0, 0, 0, -b3.getX()*a3.getX(), -b3.getX()*a3.getY()},
                {a4.getX(), a4.getY(), 1, 0, 0, 0, -b4.getX()*a4.getX(), -b4.getX()*a4.getY()},
                {0, 0, 0, a1.getX(), a1.getY(), 1, -b1.getY()*a1.getX(), -b1.getY()*a1.getY()},
                {0, 0, 0, a2.getX(), a2.getY(), 1, -b2.getY()*a2.getX(), -b2.getY()*a2.getY()},
                {0, 0, 0, a3.getX(), a3.getY(), 1, -b3.getY()*a3.getX(), -b3.getY()*a3.getY()},
                {0, 0, 0, a4.getX(), a3.getY(), 1, -b4.getY()*a4.getX(), -b4.getY()*a4.getY()}
        };

        SimpleMatrix m_left = new SimpleMatrix(data_to_matrix_left);

        double[][] data_to_matrix_right = {
                {b1.getX()},
                {b2.getX()},
                {b3.getX()},
                {b4.getX()},
                {b1.getY()},
                {b2.getY()},
                {b3.getY()},
                {b4.getY()}
        };

        SimpleMatrix m_right = new SimpleMatrix(data_to_matrix_right);

        SimpleMatrix score;
        try {
            score = m_left.invert().mult(m_right);
            double data_to_matrix_h[][] = {
                    {score.get(0), score.get(1), score.get(2)},
                    {score.get(3), score.get(4), score.get(5)},
                    {score.get(6), score.get(7), 1}
            };

            SimpleMatrix H = new SimpleMatrix(data_to_matrix_h);

            return H;
        } catch (Exception e)
        {
            return null;
        }
    }
}
