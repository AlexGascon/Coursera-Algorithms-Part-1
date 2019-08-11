import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int n, openSitesCount;
    WeightedQuickUnionUF grid;
    boolean[][] openSites;

    // Creates an n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        this.grid = new WeightedQuickUnionUF(n * n);
        this.openSites = new boolean[n][n];
        this.openSitesCount = 0;
    }

    // Opens the site (row, col) if it's not open
    public void open(int row, int col) {
        openSitesCount++;
        openSites[row][col] = true;
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openSites[row][col];
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return false;
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // Does the system percolate?
    public boolean percolates() {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}