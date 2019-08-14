import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int n, trials;
    private final double totalSites;
    private final double mean, stddev;
    private final double[] proportionOfSitesOpenedPerTrial;

    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("ERROR");

        this.n = n;
        this.trials = trials;
        this.totalSites = (double) n * n;
        this.proportionOfSitesOpenedPerTrial = new double[trials];

        for (int trial = 0; trial < trials; trial++) {
            Percolation percolation = new Percolation(n);

            while (true) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                percolation.open(randomRow, randomCol);

                if (percolation.percolates()) break;
            }

            this.proportionOfSitesOpenedPerTrial[trial] = percolation.numberOfOpenSites() / totalSites;
        }

        this.mean = StdStats.mean(proportionOfSitesOpenedPerTrial);
        this.stddev = StdStats.stddev(proportionOfSitesOpenedPerTrial);
    }

    // Sample mean of percolation threshold
    public double mean() {
        // This can be more explicit with memoization
        return this.mean;
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        // This can be more explicit with memoization
        return this.stddev;
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.print("mean = " + stats.mean());
        StdOut.print("stddev = " + stats.stddev());
        StdOut.print("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}