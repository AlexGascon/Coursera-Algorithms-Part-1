import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PercolationTest {

    @Test
    void testIsOpened() {
        Percolation percolation = new Percolation(3);
        assertFalse(percolation.isOpen(1, 2));
    }
}
