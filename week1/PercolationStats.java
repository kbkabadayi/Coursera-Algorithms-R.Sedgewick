/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private double[] thresholds;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Jeez Rick");
        }
        this.N = N;
        this.T = T;
        thresholds = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(this.N);

            int openSite = 0;

            while (!p.percolates()) {
                openRandom(p);
                openSite++;
            }
            thresholds[i] = (double) openSite / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(T));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(T));
    }

    private void openRandom(Percolation p) {
        boolean isOpen = true;
        int row = 0;
        int col = 0;
        while (isOpen) {
            row = StdRandom.uniform(1, N + 1);
            col = StdRandom.uniform(1, N + 1);
            isOpen = p.isOpen(row, col);
        }
        p.open(row, col);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats s = new PercolationStats(N, T);

        System.out.println("mean:\t\t\t\t = " + s.mean());
        System.out.println("stddev:\t\t\t\t = " + s.stddev());
        System.out.println(
                "95% confidence interval:\t = " + s.confidenceLo() + ", " + s.confidenceHi());
    }
}
