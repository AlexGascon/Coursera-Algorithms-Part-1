import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStatsTest {
    @Test
    void testMean() {
        int gridSide = 10;
        int trials = 3;
        PercolationStats stats = new PercolationStats(gridSide, trials);
        stats.proportionOfSitesOpenedPerTrial = new double[] {1, 0.5, 0.75};

        assertEquals(stats.mean(), 0.75);
    }

    @Test
    void testStdDev() {
        int gridSide = 10;
        int trials = 3;
        PercolationStats stats = new PercolationStats(gridSide, trials);
        stats.proportionOfSitesOpenedPerTrial = new double[] {0.5, 0, -0.5};

        assertEquals(stats.stddev(), 0.5);
    }
}