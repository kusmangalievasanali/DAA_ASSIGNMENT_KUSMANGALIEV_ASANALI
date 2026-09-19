package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {

    @Test
    public void testRandomArrays() {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int size = random.nextInt(500) + 1;
            int[] array = new int[size];

            for (int j = 0; j < size; j++) {
                array[j] = random.nextInt(1000) - 500;
            }

            int[] expected = array.clone();
            Arrays.sort(expected);

            Metrics metrics = new Metrics();
            QuickSort.sort(array, metrics);

            assertArrayEquals(expected, array);
        }
    }

    @Test
    public void testEmptyArray() {
        int[] array = new int[0];
        Metrics metrics = new Metrics();
        QuickSort.sort(array, metrics);
        assertArrayEquals(new int[0], array);
    }

    @Test
    public void testOneElement() {
        int[] array = {5};
        Metrics metrics = new Metrics();
        QuickSort.sort(array, metrics);
        assertArrayEquals(new int[]{5}, array);
    }

    @Test
    public void testAllEqual() {
        int[] array = {7, 7, 7, 7, 7};
        Metrics metrics = new Metrics();
        QuickSort.sort(array, metrics);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, array);
    }

    @Test
    public void testAlreadySorted() {
        int[] array = {1, 2, 3, 4, 5};
        Metrics metrics = new Metrics();
        QuickSort.sort(array, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    public void testDepthOnSortedArray() {
        int size = 100000;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        Metrics metrics = new Metrics();
        QuickSort.sort(array, metrics);

        double limit = 2 * (Math.log(size) / Math.log(2));

        assertTrue(metrics.getMaxDepth() <= limit);
    }
}