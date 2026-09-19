package algorithms;

import metrics.Metrics;

public class MergeSort {

    private static final int CUTOFF = 15;

    public static void sort(int[] array, Metrics metrics) {
        if (array == null) {
            return;
        }
        if (array.length <= 1) {
            return;
        }

        int[] buffer = new int[array.length];

        int left = 0;
        int right = array.length - 1;

        sortHelper(array, buffer, left, right, metrics);
    }

    private static void sortHelper(int[] array, int[] buffer, int left, int right, Metrics metrics) {

        metrics.enterRecursion();

        int size = right - left + 1;

        if (size <= CUTOFF) {
            InsertionSort.sort(array, left, right, metrics);
            metrics.exitRecursion();
            return;
        }

        int middle = (left + right) / 2;

        sortHelper(array, buffer, left, middle, metrics);
        sortHelper(array, buffer, middle + 1, right, metrics);
        merge(array, buffer, left, middle, right, metrics);

        metrics.exitRecursion();
    }

    private static void merge(int[] array, int[] buffer, int left, int middle, int right, Metrics metrics) {

        for (int i = left; i <= right; i++) {
            buffer[i] = array[i];
        }

        int leftPointer = left;
        int rightPointer = middle + 1;
        int currentPosition = left;

        while (leftPointer <= middle && rightPointer <= right) {

            metrics.incrementComparisons();

            if (buffer[leftPointer] <= buffer[rightPointer]) {
                array[currentPosition] = buffer[leftPointer];
                leftPointer = leftPointer + 1;
            } else {
                array[currentPosition] = buffer[rightPointer];
                rightPointer = rightPointer + 1;
            }

            currentPosition = currentPosition + 1;
        }

        while (leftPointer <= middle) {
            array[currentPosition] = buffer[leftPointer];
            leftPointer = leftPointer + 1;
            currentPosition = currentPosition + 1;
        }

        while (rightPointer <= right) {
            array[currentPosition] = buffer[rightPointer];
            rightPointer = rightPointer + 1;
            currentPosition = currentPosition + 1;
        }
    }
}