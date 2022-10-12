import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private final int n;

    public Board(int[][] givenTiles) {
        n = givenTiles.length;
        this.tiles = copy(givenTiles);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int currentNum = tiles[i][j];
                if (currentNum == 0) {
                    continue;
                }
                int targetRow = (currentNum - 1) / n;
                int targetCol = (currentNum - 1) % n;
                if (i != targetRow || j != targetCol) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int currentNum = tiles[i][j];
                if (currentNum == 0) {
                    continue;
                }
                int targetRow = (currentNum - 1) / n;
                int targetCol = (currentNum - 1) % n;
                manhattan += Math.abs(targetRow - i) + Math.abs(targetCol - j);
            }
        }
        return manhattan;
    }

    public int dimension() {
        return n;
    }

    @Override
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (dimension() != other.dimension()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> stackOfBoards = new Stack<>();
        int emptyNodeRow = 0, emptyNodeCol = 0;

        // find empty
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    emptyNodeRow = i;
                    emptyNodeCol = j;
                }
            }
        }
        if (isOperationValid(emptyNodeRow - 1, emptyNodeCol)) {
            stackOfBoards.push(new Board(
                    swap(emptyNodeRow, emptyNodeCol, emptyNodeRow - 1, emptyNodeCol)));
        }
        if (isOperationValid(emptyNodeRow + 1, emptyNodeCol)) {
            stackOfBoards.push(new Board(
                    swap(emptyNodeRow, emptyNodeCol, emptyNodeRow + 1, emptyNodeCol)));
        }
        if (isOperationValid(emptyNodeRow, emptyNodeCol - 1)) {
            stackOfBoards.push(new Board(
                    swap(emptyNodeRow, emptyNodeCol, emptyNodeRow, emptyNodeCol - 1)));
        }
        if (isOperationValid(emptyNodeRow, emptyNodeCol + 1)) {
            stackOfBoards.push(new Board(
                    swap(emptyNodeRow, emptyNodeCol, emptyNodeRow, emptyNodeCol + 1)));
        }
        return stackOfBoards;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        if (tiles[0][0] == 0) {
            return new Board(swap(0, 1, 1, 0));
        }

        int emptyNodeRow = 0;
        // find empty
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    emptyNodeRow = i;
                }
            }
        }
        int targetRow = 0;
        int targetCol = 1;

        if (targetRow == emptyNodeRow) {
            targetRow++;
        }

        return new Board(swap(0, 0, targetRow, targetCol));

    }

    private int[][] copy(int[][] givenTiles) {
        int[][] copyTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyTiles[i][j] = givenTiles[i][j];
            }
        }
        return copyTiles;
    }

    private boolean isOperationValid(int row, int col) {
        return 0 <= row && n - 1 >= row && 0 <= col && n - 1 >= col;
    }

    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] neighbor = copy(tiles);
        int swap = neighbor[row1][col1];
        neighbor[row1][col1] = neighbor[row2][col2];
        neighbor[row2][col2] = swap;
        return neighbor;
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

        StdOut.println(initial.twin());
    }
}
