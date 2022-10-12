/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean isSolvable;
    private final int minMoves;
    private final Stack<Board> winningMoves;


    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        winningMoves = new Stack<>();
        MinPQ<SearchNode> realQ = new MinPQ<>();
        MinPQ<SearchNode> twinQ = new MinPQ<>();

        realQ.insert(new SearchNode(initial, null, 0));
        twinQ.insert(new SearchNode(initial.twin(), null, 0));

        SearchNode thisNode = realQ.delMin();
        SearchNode thisTwinNode = twinQ.delMin();

        int counter = 0;
        while (!thisNode.board.isGoal() && !thisTwinNode.board.isGoal()) {
            SearchNode prev = thisNode.prev;
            SearchNode twinPrev = thisTwinNode.prev;

            for (Board board : thisTwinNode.board.neighbors()) {
                if (twinPrev == null || !board.equals(twinPrev.board)) {
                    twinQ.insert(new SearchNode(board, thisTwinNode, thisTwinNode.moves + 1));
                }
            }
            for (Board board : thisNode.board.neighbors()) {
                if (prev == null || !board.equals(prev.board)) {
                    realQ.insert(new SearchNode(board, thisNode, thisNode.moves + 1));
                }
            }

            thisNode = realQ.delMin();
            thisTwinNode = twinQ.delMin();
        }
        if (thisNode.board.isGoal()) {
            isSolvable = true;
            while (thisNode != null) {
                winningMoves.push(thisNode.board);
                counter++;
                thisNode = thisNode.prev;
            }
        }
        else {
            isSolvable = false;
        }
        minMoves = counter - 1;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        if (isSolvable()) {
            return minMoves;
        }
        return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return winningMoves;
        }
        return null;
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

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int priority;
        private final int moves;

        public SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;

            priority = moves + board.manhattan();
        }


        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }
}
