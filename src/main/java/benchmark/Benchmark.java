package benchmark;

import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.QuickSelect;
import metrics.Metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "duplicates"};
    private static final int REPEATS = 5;

    public static void main(String[] args) throws IOException {

        FileWriter writer = new FileWriter("results.csv");
        writer.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

        printHeader();

        for (int size : SIZES) {
            for (String inputType : INPUT_TYPES) {

                int[] baseArray = generateArray(size, inputType);

                runAndWrite(writer, "MergeSort", inputType, size, baseArray);
                runAndWrite(writer, "QuickSort", inputType, size, baseArray);
                runAndWrite(writer, "QuickSelect", inputType, size, baseArray);
            }
        }

        writer.close();
        System.out.println();
        System.out.println("Done. Results saved to results.csv");
    }

    private static void printHeader() {
        System.out.println();
        System.out.printf("%-15s %-12s %-10s %-12s %-14s %-10s%n",
                "algorithm", "input", "n", "time_ms", "comparisons", "max_depth");
        System.out.println("-".repeat(75));
    }

    private static void runAndWrite(FileWriter writer, String algorithmName, String inputType, int size, int[] baseArray) throws IOException {

        double[] times = new double[REPEATS];
        long[] comparisonsArray = new long[REPEATS];
        int[] maxDepthArray = new int[REPEATS];

        for (int i = 0; i < REPEATS; i++) {

            int[] arrayCopy = baseArray.clone();
            Metrics metrics = new Metrics();

            metrics.startTimer();

            if (algorithmName.equals("MergeSort")) {
                MergeSort.sort(arrayCopy, metrics);

            } else if (algorithmName.equals("QuickSort")) {
                QuickSort.sort(arrayCopy, metrics);

            } else if (algorithmName.equals("QuickSelect")) {
                int k = arrayCopy.length / 2;
                QuickSelect.select(arrayCopy, k, metrics);
            }

            metrics.stopTimer();

            times[i] = metrics.getElapsedMillis();
            comparisonsArray[i] = metrics.getComparisons();
            maxDepthArray[i] = metrics.getMaxDepth();
        }

        int medianIndex = findMedianIndex(times);

        double medianTime = times[medianIndex];
        long medianComparisons = comparisonsArray[medianIndex];
        int medianMaxDepth = maxDepthArray[medianIndex];

        String csvLine = algorithmName + "," + inputType + "," + size + "," + medianTime + "," + medianComparisons + "," + medianMaxDepth + "\n";
        writer.write(csvLine);

        System.out.printf("%-15s %-12s %-10d %-12.4f %-14d %-10d%n",
                algorithmName, inputType, size, medianTime, medianComparisons, medianMaxDepth);
    }

    private static int findMedianIndex(double[] times) {

        double[] sortedTimes = times.clone();
        Arrays.sort(sortedTimes);
        double medianValue = sortedTimes[sortedTimes.length / 2];

        for (int i = 0; i < times.length; i++) {
            if (times[i] == medianValue) {
                return i;
            }
        }

        return 0;
    }

    private static int[] generateArray(int size, String inputType) {

        Random random = new Random();
        int[] array = new int[size];

        if (inputType.equals("random")) {
            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(2000001) - 1000000;
            }

        } else if (inputType.equals("sorted")) {
            for (int i = 0; i < size; i++) {
                array[i] = i;
            }

        } else if (inputType.equals("duplicates")) {
            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(10);
            }
        }

        return array;
    }
}