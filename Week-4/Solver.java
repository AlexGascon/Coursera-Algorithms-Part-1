import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private BoardNode solutionBoard;
    private boolean solvable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("The initial board can't be null");

        // Check if the initial board is solvable
        MinPQ<BoardNode> tree = new MinPQ<BoardNode>();
        BoardNode initialNode = new BoardNode(initial, null, 0);
        tree.insert(initialNode);

        MinPQ<BoardNode> twinTree = new MinPQ<BoardNode>();
        BoardNode initialTwinNode = new BoardNode(initial.twin(), null, 0);
        twinTree.insert(initialTwinNode);

        while (solutionBoard == null && isSolvable()) {
            BoardNode currentNode = tree.delMin();
            if (currentNode.board.isGoal()) {
                solutionBoard = currentNode;
                return;
            }

            for (Board neighbor : currentNode.board.neighbors()) {
                if (currentNode.previous != null && neighbor.equals(currentNode.previous.board))
                    continue;
                else
                    tree.insert(new BoardNode(neighbor, currentNode, currentNode.moves + 1));
            }

            BoardNode twinNode = twinTree.delMin();
            if (twinNode.board.isGoal()) {
                solvable = false;
                return;
            }
            for (Board twinNeighbor : twinNode.board.neighbors())
                if (twinNode.previous != null && twinNeighbor.equals(twinNode.previous.board))
                    continue;
                else
                    twinTree.insert(new BoardNode(twinNeighbor, twinNode, twinNode.moves + 1));
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve the initial board
    public int moves() {
        if (!isSolvable()) return -1;

        return solutionBoard.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Board[] pathToSolution = new Board[moves() + 1];
        int i = moves();
        BoardNode currentNode = solutionBoard;

        while (i >= 0) {
            pathToSolution[i--] = currentNode.board;
            currentNode = currentNode.previous;
        }

        return new ArrayList<>(Arrays.asList(pathToSolution));
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

class BoardNode implements Comparable<BoardNode> {
    public Board board;
    public BoardNode previous;
    public int moves;
    public Integer cachedPriority;

    public BoardNode(Board board, BoardNode previous, int moves) {
        this.board = board;
        this.previous = previous;
        this.moves = moves;
    }

    public int manhattanPriority() {
        if (cachedPriority == null) cachedPriority = moves + board.manhattan();

        return cachedPriority;
    }

    public int compareTo(BoardNode other) {
        return this.manhattanPriority() - other.manhattanPriority();
    }
}