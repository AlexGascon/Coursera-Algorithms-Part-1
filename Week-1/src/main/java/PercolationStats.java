import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double mean, stddev;

    /**
     * Instantiates several Percolation objects and, for each of them, opens sites randomly
     * until the system percolates. When it does, it stores the result in order to enable the
     * obtention of stats from it
     *
     * @param n Size of the Percolation objects
     * @param trials Number of times that we'll repeat the experiment
     * @throws IllegalArgumentException If either n or trials are lower than 1
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("ERROR");

        this.trials = trials;

        double[] proportionOfSitesOpenedPerTrial = new double[trials];

        double totalSites = n * n;

        for (int trial = 0; trial < trials; trial++) {
            Percolation percolation = new Percolation(n);

            while (true) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                percolation.open(randomRow, randomCol);

                if (percolation.percolates()) break;
            }

            proportionOfSitesOpenedPerTrial[trial] = percolation.numberOfOpenSites() / totalSites;
        }

        this.mean = StdStats.mean(proportionOfSitesOpenedPerTrial);
        this.stddev = StdStats.stddev(proportionOfSitesOpenedPerTrial);
    }

    /**
     * Sample mean among the trials of the percolation threshold
     *
     * @return Sample mean among the trials of the percolation threshold
     */
    public double mean() {
        // This can be more explicit with memoization
        return this.mean;
    }

    /**
     * Sample standard deviation of percolation threshold
     *
     * @return Sample standard deviation of percolation threshold
     */
    public double stddev() {
        // This can be more explicit with memoization
        return this.stddev;
    }

    /**
     * Low endpoint of 95% confidence interval
     *
     * @return Low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - CONFIDENCE_95*stddev()/Math.sqrt(trials);
    }

    /**
     * High endpoint of 95% confidence interval
     *
     * @return High endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + CONFIDENCE_95*stddev()/Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(StdIn.readString());
        int trials = Integer.parseInt(StdIn.readString());

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.print("mean = " + stats.mean());
        StdOut.print("stddev = " + stats.stddev());
        StdOut.print("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}