package algorithms;

import metrics.Metrics;
import java.util.Random;

public class QuickSort {

    private static final Random random = new Random();

    public static void sort(int[] array, Metrics metrics) {
        if (array == null) {
            return;
        }
        if (array.length <= 1) {
            return;
        }

        int left = 0;
        int right = array.length - 1;

        sortHelper(array, left, right, metrics);
    }

    private static void sortHelper(int[] array, int left, int right, Metrics metrics) {

        while (left < right) {

            metrics.enterRecursion();

            int[] bounds = partition(array, left, right, metrics);
            int lessEnd = bounds[0];
            int greaterStart = bounds[1];

            int leftPartSize = lessEnd - left + 1;
            int rightPartSize = right - greaterStart + 1;

            if (leftPartSize < rightPartSize) {
                sortHelper(array, left, lessEnd, metrics);
                left = greaterStart;
            } else {
                sortHelper(array, greaterStart, right, metrics);
                right = lessEnd;
            }

            metrics.exitRecursion();
        }
    }

    public static int[] partition(int[] array, int left, int right, Metrics metrics) {

        int randomIndex = left + random.nextInt(right - left + 1);
        int pivotValue = array[randomIndex];

        int lessThanPointer = left;
        int currentPointer = left;
        int greaterThanPointer = right;

        while (currentPointer <= greaterThanPointer) {

            metrics.incrementComparisons();

            if (array[currentPointer] < pivotValue) {
                swap(array, lessThanPointer, currentPointer);
                lessThanPointer = lessThanPointer + 1;
                currentPointer = currentPointer + 1;

            } else if (array[currentPointer] > pivotValue) {
                swap(array, currentPointer, greaterThanPointer);
                greaterThanPointer = greaterThanPointer - 1;

            } else {
                currentPointer = currentPointer + 1;
            }
        }

        int[] result = new int[2];
        result[0] = lessThanPointer - 1;
        result[1] = greaterThanPointer + 1;
        return result;
    }

    private static void swap(int[] array, int indexA, int indexB) {
        int temp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = temp;
    }
}