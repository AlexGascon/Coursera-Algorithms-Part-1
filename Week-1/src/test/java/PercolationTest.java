import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PercolationTest {

    @Test
    void testIsOpened() {
        Percolation percolation = new Percolation(3);
        assertFalse(percolation.isOpen(1, 2));
    }

    @Test
    void testOpen() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 2);
        assertTrue(percolation.isOpen(1, 2));
    }

    @Test
    void testNumberOfOpenSites() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 2);
        percolation.open(2, 1);
        assertEquals(2, percolation.numberOfOpenSites());
    }

    @Test
    void testOpenAnOpenedSite() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 2);
        percolation.open(1, 2);
        assertEquals(1, percolation.numberOfOpenSites());
    }

    @Test
    void testIsFull() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        assertTrue(percolation.isFull(1, 1));
    }

    @Test
    void testPercolates() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        assertTrue(percolation.percolates());
    }
}
