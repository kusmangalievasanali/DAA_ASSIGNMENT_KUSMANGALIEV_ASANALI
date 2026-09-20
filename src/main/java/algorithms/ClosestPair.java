package algorithms;

import metrics.Metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPair {

    public static class Result {
        public double distance;
        public Point pointA;
        public Point pointB;
    }

    public static Result findClosestPair(Point[] points, Metrics metrics) {

        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));

        return closestPairHelper(sortedByX, 0, sortedByX.length - 1, metrics);
    }

    private static Result closestPairHelper(Point[] pointsByX, int left, int right, Metrics metrics) {

        metrics.enterRecursion();

        int size = right - left + 1;

        if (size <= 3) {
            Result result = bruteForce(pointsByX, left, right, metrics);
            metrics.exitRecursion();
            return result;
        }

        int middle = (left + right) / 2;
        double middleX = pointsByX[middle].x;

        Result leftResult = closestPairHelper(pointsByX, left, middle, metrics);
        Result rightResult = closestPairHelper(pointsByX, middle + 1, right, metrics);

        Result best = leftResult.distance <= rightResult.distance ? leftResult : rightResult;

        List<Point> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(pointsByX[i].x - middleX) < best.distance) {
                strip.add(pointsByX[i]);
            }
        }

        strip.sort(Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && j < i + 8; j++) {

                metrics.incrementComparisons();

                double d = distance(strip.get(i), strip.get(j));
                if (d < best.distance) {
                    Result newBest = new Result();
                    newBest.distance = d;
                    newBest.pointA = strip.get(i);
                    newBest.pointB = strip.get(j);
                    best = newBest;
                }
            }
        }

        metrics.exitRecursion();
        return best;
    }

    public static Result bruteForce(Point[] points, int left, int right, Metrics metrics) {

        Result best = new Result();
        best.distance = Double.MAX_VALUE;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {

                metrics.incrementComparisons();

                double d = distance(points[i], points[j]);
                if (d < best.distance) {
                    best.distance = d;
                    best.pointA = points[i];
                    best.pointB = points[j];
                }
            }
        }

        return best;
    }

    private static double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}