import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    final int n, trials, totalSites;
    double[] proportionOfSitesOpenedPerTrial;

    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        this.totalSites = n * n;
    }

    // Sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(proportionOfSitesOpenedPerTrial);
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(proportionOfSitesOpenedPerTrial);
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (double) 0.0;
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }

    // test client
    public static void main(String[] args) {
    }
}