package metrics;
//comparison part
public class Metrics {
    private long comparisons = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;
    private long startTime;
    private long elapsedNanos;

    public void incrementComparisons() {
        comparisons++;
    }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        elapsedNanos = System.nanoTime() - startTime;
    }

    public long getComparisons() { return comparisons; }
    public int getMaxDepth() { return maxDepth; }
    public long getElapsedNanos() { return elapsedNanos; }
    public double getElapsedMillis() { return elapsedNanos / 1_000_000.0; }

    public void reset() {
        comparisons = 0;
        currentDepth = 0;
        maxDepth = 0;
        elapsedNanos = 0;
    }
}