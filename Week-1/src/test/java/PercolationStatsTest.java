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
        stats.sitesOpenedPerTrial = new int[] {100, 50, 75};

        assertEquals(stats.mean(), 0.75);
    }
}