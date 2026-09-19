package algorithms;

import metrics.Metrics;

public class InsertionSort {

    public static void sort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= left) {
                metrics.incrementComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
    public static void sort(int[] a, Metrics metrics) {
        sort(a, 0, a.length - 1, metrics);
    }
}