import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int top;
    private int gridLength;
    private int bottom;
    private int openCounter;
    private WeightedQuickUnionUF uf;
    private boolean[][] openMatrix;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Oh fuq");
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        top = n * n;
        bottom = n * n + 1;
        openMatrix = new boolean[n][n];
        gridLength = n;
        openCounter = 0;
    }

    public void open(int row, int col) {
        // check boundaries and throw if need be
        checkBounds(row, col);

        // already open
        if (isOpen(row, col)) {
            return;
        }

        // first row
        if (row == 1) {
            // top left corner
            if (col == 1) {
                if (isOpen(row + 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row + 1, col));
                }
                if (isOpen(row, col + 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col + 1));
                }
            }
            // top right corner
            else if (col == gridLength) {
                if (isOpen(row + 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row + 1, col));
                }
                if (isOpen(row, col - 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col - 1));
                }
            }
            // general case for first row
            else {
                if (isOpen(row + 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row + 1, col));
                }
                if (isOpen(row, col - 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col - 1));
                }
                if (isOpen(row, col + 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col + 1));
                }
            }
            // unify current with top
            if (uf.find(top) != uf.find(matrixToSingle(row, col))) {
                uf.union(top, matrixToSingle(row, col));
            }
        }
        // last row
        else if (row == gridLength) {
            // bottom left corner
            if (col == 1) {
                if (isOpen(row - 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row - 1, col));
                }
                if (isOpen(row, col + 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col + 1));
                }
            }
            // bottom right corner
            else if (col == gridLength) {
                if (isOpen(row - 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row - 1, col));
                }
                if (isOpen(row, col - 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col - 1));
                }
            }
            // general case for last row
            else {
                if (isOpen(row - 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row - 1, col));
                }
                if (isOpen(row, col - 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col - 1));
                }
                if (isOpen(row, col + 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col + 1));
                }
            }
            // unify current with bottom
            if (uf.find(bottom) != uf.find(matrixToSingle(row, col))) {
                uf.union(bottom, matrixToSingle(row, col));
            }
        }
        // general case
        else {
            if (col == 1) {
                if (isOpen(row - 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row - 1, col));
                }
                if (isOpen(row + 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row + 1, col));
                }
                if (isOpen(row, col + 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col + 1));
                }
            }
            else if (col == gridLength) {
                if (isOpen(row - 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row - 1, col));
                }
                if (isOpen(row + 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row + 1, col));
                }
                if (isOpen(row, col - 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col - 1));
                }
            }
            else {
                if (isOpen(row - 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row - 1, col));
                }
                if (isOpen(row + 1, col)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row + 1, col));
                }
                if (isOpen(row, col - 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col - 1));
                }
                if (isOpen(row, col + 1)) {
                    uf.union(matrixToSingle(row, col), matrixToSingle(row, col + 1));
                }
            }

        }

        openMatrix[row - 1][col - 1] = true;
        openCounter++;
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || col < 1 || row > gridLength || col > gridLength) {
            throw new IllegalArgumentException("You fucked up");
        }
    }

    private int matrixToSingle(int row, int col) {
        return (col - 1) * gridLength + (row - 1);
    }

    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return openMatrix[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        return uf.find(top) == uf.find(matrixToSingle(row, col));
    }

    public int numberOfOpenSites() {
        return openCounter;
    }

    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

}
