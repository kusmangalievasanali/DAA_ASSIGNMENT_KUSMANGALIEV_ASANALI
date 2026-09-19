package algorithms;

import metrics.Metrics;

public class QuickSelect {

    public static int select(int[] array, int k, Metrics metrics) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k is out of range: " + k);
        }

        int left = 0;
        int right = array.length - 1;

        while (true) {

            metrics.enterRecursion();

            if (left == right) {
                metrics.exitRecursion();
                return array[left];
            }

            int[] bounds = QuickSort.partition(array, left, right, metrics);
            int lessEnd = bounds[0];
            int greaterStart = bounds[1];

            if (k <= lessEnd) {
                right = lessEnd;
            } else if (k >= greaterStart) {
                left = greaterStart;
            } else {
                metrics.exitRecursion();
                return array[k];
            }

            metrics.exitRecursion();
        }
    }
}