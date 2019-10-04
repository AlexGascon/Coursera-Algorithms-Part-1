import java.util.Arrays;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int n;
    private final int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;

        board = copyBoardContent(tiles);
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                s.append(String.format("%2d ", board[i][j]));

            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (solution(row, col) != 0 && solution(row, col) != board[row][col])
                    distance++;

        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                distance += distanceToCorrectPosition(board[row][col], row, col);
        
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (board[row][col] != solution(row, col))
                    return false;

        return true;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;

        Board otherBoard = (Board) other;

        return Arrays.deepEquals(this.board, otherBoard.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int blankRow = 0, blankCol = 0;
        boolean blankFound = false;
        ArrayList<Board> neighbors = new ArrayList<Board>();

        for (blankRow = 0; blankRow < n; blankRow++) {
            for (blankCol = 0; blankCol < n; blankCol++)
                if (board[blankRow][blankCol] == 0) {
                    blankFound = true;
                    break;
                }

            if (blankFound) break;
        }

        // Top neighbor
        if (blankRow > 0)
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow - 1, blankCol));

        // Left neighbor
        if (blankCol > 0)
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow, blankCol - 1));

        // Right neighbor
        if (blankCol < (n - 1))
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow, blankCol + 1));

        // Bottom neighbor
        if (blankRow < (n - 1))
            neighbors.add(copyFromOriginalAndSwapTiles(blankRow, blankCol, blankRow + 1, blankCol));

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int swappingRow, swappingCol, twinRow, twinCol;
        swappingRow = swappingCol = 0;
        twinCol = twinRow = n - 1;

        if (board[swappingRow][swappingCol] == 0)
            swappingCol++;

        if (board[twinRow][twinCol] == 0)
            twinCol--;

        return copyFromOriginalAndSwapTiles(swappingRow, swappingCol, twinRow, twinCol);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        System.out.print(initial.toString());
    }

    private int distanceToCorrectPosition(int value, int currentRow, int currentCol) {
        if (value == 0) return 0;

        int correctRow = (value - 1) / n;
        int correctCol = (value - 1) % n;

        int dist = Math.abs(currentRow - correctRow) + Math.abs(currentCol - correctCol);
        return dist;
    }

    private int solution(int row, int col) {
        if (row == (n - 1) && col == (n - 1)) return 0;
        return row * n + col + 1;
    }

    private int[][] copyBoardContent(int[][] original) {
        int[][] destination = new int[n][n];

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                destination[row][col] = original[row][col];

        return destination;
    }

    private Board copyFromOriginalAndSwapTiles(int originalRow, int originalCol, int newRow, int newCol) {
        int[][] boardCopy = copyBoardContent(board);

        boardCopy[originalRow][originalCol] = board[newRow][newCol];
        boardCopy[newRow][newCol] = board[originalRow][originalCol];

        return new Board(boardCopy);
    }
}
