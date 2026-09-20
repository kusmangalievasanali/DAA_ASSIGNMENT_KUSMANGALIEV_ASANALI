package algorithms;

import metrics.Metrics;

public class MedianOfMediansSelect {

    private static final int GROUP_SIZE = 5;

    public static int select(int[] array, int k, Metrics metrics) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k is out of range: " + k);
        }

        int[] copy = array.clone();
        return selectHelper(copy, 0, copy.length - 1, k, metrics);
    }

    private static int selectHelper(int[] array, int left, int right, int k, Metrics metrics) {

        metrics.enterRecursion();

        int size = right - left + 1;

        if (size <= GROUP_SIZE) {
            InsertionSort.sort(array, left, right, metrics);
            metrics.exitRecursion();
            return array[k];
        }

        int pivotValue = findMedianOfMedians(array, left, right, metrics);

        int[] bounds = partitionByValue(array, left, right, pivotValue, metrics);
        int lessEnd = bounds[0];
        int greaterStart = bounds[1];

        int result;

        if (k <= lessEnd) {
            result = selectHelper(array, left, lessEnd, k, metrics);
        } else if (k >= greaterStart) {
            result = selectHelper(array, greaterStart, right, k, metrics);
        } else {
            result = pivotValue;
        }

        metrics.exitRecursion();
        return result;
    }

    private static int findMedianOfMedians(int[] array, int left, int right, Metrics metrics) {

        int size = right - left + 1;
        int numberOfGroups = (size + GROUP_SIZE - 1) / GROUP_SIZE;
        int[] medians = new int[numberOfGroups];

        for (int i = 0; i < numberOfGroups; i++) {
            int groupLeft = left + i * GROUP_SIZE;
            int groupRight = Math.min(groupLeft + GROUP_SIZE - 1, right);

            InsertionSort.sort(array, groupLeft, groupRight, metrics);

            int medianIndex = groupLeft + (groupRight - groupLeft) / 2;
            medians[i] = array[medianIndex];
        }

        if (medians.length == 1) {
            return medians[0];
        }

        return selectHelper(medians, 0, medians.length - 1, medians.length / 2, metrics);
    }

    private static int[] partitionByValue(int[] array, int left, int right, int pivotValue, Metrics metrics) {

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