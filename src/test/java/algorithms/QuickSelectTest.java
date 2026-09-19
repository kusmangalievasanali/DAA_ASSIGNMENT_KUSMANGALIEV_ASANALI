package algorithms;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuickSelectTest {

    @Test
    public void testRandomArrays() {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int size = random.nextInt(500) + 1;
            int[] array = new int[size];

            for (int j = 0; j < size; j++) {
                array[j] = random.nextInt(1000) - 500;
            }

            int[] sorted = array.clone();
            Arrays.sort(sorted);

            int k = random.nextInt(size);

            int[] copy = array.clone();
            Metrics metrics = new Metrics();
            int result = QuickSelect.select(copy, k, metrics);

            assertEquals(sorted[k], result);
        }
    }

    @Test
    public void testEmptyArrayThrows() {
        int[] array = new int[0];
        Metrics metrics = new Metrics();

        assertThrows(IllegalArgumentException.class, () -> {
            QuickSelect.select(array, 0, metrics);
        });
    }

    @Test
    public void testKOutOfRangeThrows() {
        int[] array = {1, 2, 3};
        Metrics metrics = new Metrics();

        assertThrows(IllegalArgumentException.class, () -> {
            QuickSelect.select(array, 5, metrics);
        });
    }

    @Test
    public void testNegativeKThrows() {
        int[] array = {1, 2, 3};
        Metrics metrics = new Metrics();

        assertThrows(IllegalArgumentException.class, () -> {
            QuickSelect.select(array, -1, metrics);
        });
    }
}