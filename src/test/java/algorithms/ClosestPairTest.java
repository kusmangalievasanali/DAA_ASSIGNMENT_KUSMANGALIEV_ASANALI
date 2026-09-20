package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosestPairTest {

    @Test
    public void testAgainstBruteForce() {
        Random random = new Random();

        for (int trial = 0; trial < 50; trial++) {
            int size = random.nextInt(500) + 2;

            Point[] points = new Point[size];
            for (int i = 0; i < size; i++) {
                double x = random.nextDouble() * 10000 - 5000;
                double y = random.nextDouble() * 10000 - 5000;
                points[i] = new Point(x, y);
            }

            ClosestPair.Result fastResult = ClosestPair.findClosestPair(points, new Metrics());
            ClosestPair.Result bruteResult = ClosestPair.bruteForce(points, 0, points.length - 1, new Metrics());

            assertEquals(bruteResult.distance, fastResult.distance, 1e-9);
        }
    }

    @Test
    public void testTwoPoints() {
        Point[] points = {new Point(0, 0), new Point(3, 4)};
        ClosestPair.Result result = ClosestPair.findClosestPair(points, new Metrics());
        assertEquals(5.0, result.distance, 1e-9);
    }

    @Test
    public void testThreePoints() {
        Point[] points = {new Point(0, 0), new Point(1, 1), new Point(10, 10)};
        ClosestPair.Result result = ClosestPair.findClosestPair(points, new Metrics());
        assertEquals(Math.sqrt(2), result.distance, 1e-9);
    }
}