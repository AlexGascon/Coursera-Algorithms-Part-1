import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSitesCount;

    private final boolean[][] openSites;
    private final WeightedQuickUnionUF grid;
    private final int n, top, bottom;

    // Creates an n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        this.grid = new WeightedQuickUnionUF(n * n + 2);
        this.openSites = new boolean[n][n];
        this.openSitesCount = 0;

        top = 0;
        bottom = n * n + 1;

        connectFirstRowToTop();
        connectLastRowToBottom();
    }

    // Opens the site (row, col) if it's not open
    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        openSite(row, col);
        connectToNeighbors(row, col);
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openSites[row - 1][col - 1];
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean connectedToTop = grid.connected(top, convertToGrid(row, col));
        return isOpen(row, col) && connectedToTop;
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // Does the system percolate?
    public boolean percolates() {
        return grid.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }

    private int convertToGrid(int row, int col) {
        return (row - 1) * n + col;
    }

    private void openSite(int row, int col) {
        openSitesCount++;
        openSites[row - 1][col - 1] = true;
    }

    private void connectFirstRowToTop() {
        for (int col = 1; col <= n; col++)
            grid.union(top, convertToGrid(1, col));
    }

    private void connectLastRowToBottom() {
        for (int col = 1; col <= n; col++)
            grid.union(bottom, convertToGrid(n, col));
    }

    private void connectToNeighbors(int row, int col) {
        connect(row, col, row - 1, col); // Top
        connect(row, col, row + 1, col); // Bottom
        connect(row, col, row, col - 1); // Left
        connect(row, col, row, col + 1); // Right
    }

    private void connect(int row1, int col1, int row2, int col2) {
        // Avoid the connection if the neighbor is out of bounds
        if ((row2 < 1) || (row2 > n) || (col2 < 1) || (col2 > n)) return;

        // Avoid the connection if the neighbor is closed
        if (!isOpen(row2, col2)) return;

        int element1 = convertToGrid(row1, col1);
        int element2 = convertToGrid(row2, col2);
        grid.union(element1, element2);
    }
}