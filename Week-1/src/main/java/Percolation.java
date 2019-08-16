import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSitesCount;

    private final boolean[][] openSites;
    private final WeightedQuickUnionUF grid;
    private final int n, top, bottom;

    /**
     * Creates an n-by-n grid, with all sites initially blocked
     *
     * @param n Size of the side of the grid
     * @throws IllegalAgumentException if n <= 0
     */
    public Percolation(int n) throws IllegalArgumentException {
        if (n <= 0) throw new IllegalArgumentException("ERROR");

        this.n = n;
        this.grid = new WeightedQuickUnionUF(n * n + 2);
        this.openSites = new boolean[n][n];
        this.openSitesCount = 0;

        top = 0;
        bottom = n * n + 1;

        connectFirstRowToTop();
        connectLastRowToBottom();
    }

    /**
     * Opens the site (row, col) if it's not already open
     *
     * @param row Row of the site that we want to open. Must be between 1 and n (both included)
     * @param col Column of the site that we want to open. Must be between 1 and n (both included)
     * @throws IllegalArgumentException if either row or column are not in the [1, n] range
     */
    public void open(int row, int col) throws IllegalArgumentException {
        validateCoordinate(row);
        validateCoordinate(col);

        if (isOpen(row, col)) return;

        openSite(row, col);
        connectToNeighbors(row, col);
    }

    /**
     * Indicates if a specific site is open or not
     *
     * @param row Row of the site that we want to open. Must be between 1 and n (both included)
     * @param col Column of the site that we want to open. Must be between 1 and n (both included)
     * @throws IllegalArgumentException if either row or column are not in the [1, n] range
     * @return boolean indicating if the site (row, col) is open
     */
    public boolean isOpen(int row, int col) throws IllegalArgumentException {
        validateCoordinate(row);
        validateCoordinate(col);

        return openSites[row - 1][col - 1];
    }

    /**
     * Indicates if the specified site is connected to any site in the top row of the grid
     *
     * @param row Row of the site that we want to open. Must be between 1 and n (both included)
     * @param col Column of the site that we want to open. Must be between 1 and n (both included)
     * @throws IllegalArgumentException if either row or column are not in the [1, n] range
     * @return boolean indicating if the site connects with any site in the top
     */
    public boolean isFull(int row, int col) throws IllegalArgumentException {
        validateCoordinate(row);
        validateCoordinate(col);

        boolean connectedToTop = grid.connected(top, convertToGrid(row, col));
        return isOpen(row, col) && connectedToTop;
    }

    /**
     * Indicates how many sites are open in the grid
     *
     * @return Number of open sites in the grid
     */
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    /**
     * Indicates if the grid percolates (i.e. if any site at the top row is connected to any site
     * at the bottom row)
     *
     * @return boolean indicating if the grid percolates
     */
    public boolean percolates() {
        if ((n == 1) && !isOpen(1, 1)) return false;

        return grid.connected(top, bottom);
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

    private void validateCoordinate(int coordinate) {
        if (coordinate < 1 || coordinate > n)
            throw new IllegalArgumentException("ERROR");
    }
}