package algorithms;

import metrics.Metrics;

public class MergeSort {

    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, metrics);
    }

    private static void sort(int[] a, int[] buffer, int left, int right, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (right - left + 1 <= CUTOFF) {
                InsertionSort.sort(a, left, right, metrics);
                return;
            }

            int mid = left + (right - left) / 2;
            sort(a, buffer, left, mid, metrics);
            sort(a, buffer, mid + 1, right, metrics);
            merge(a, buffer, left, mid, right, metrics);
        } finally {
            metrics.exitRecursion();
        }
    }

    private static void merge(int[] a, int[] buffer, int left, int mid, int right, Metrics metrics) {
        for (int k = left; k <= right; k++) {
            buffer[k] = a[k];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.incrementComparisons();
            if (buffer[i] <= buffer[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = buffer[j++];
            }
        }
        while (i <= mid) {
            a[k++] = buffer[i++];
        }

        while (j <= right) {
            a[k++] = buffer[j++];
        }
    }
}