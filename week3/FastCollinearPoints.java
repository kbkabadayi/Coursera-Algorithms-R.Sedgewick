package assignment3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> currentSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pointsOrig) {
        if (pointsOrig == null) throw new IllegalArgumentException();
        int len = pointsOrig.length;
        Point[] points = new Point[len];
        System.arraycopy(pointsOrig, 0, points, 0, len);

        for (Point p : points)
            if (p == null)
                throw new IllegalArgumentException();
        Arrays.sort(points);
        for (int i = 1; i < len; i++)
            if (points[i].equals(points[i-1]))
                throw new IllegalArgumentException();

        Point[] sorted = new Point[len];
        System.arraycopy(points, 0, sorted, 0, len);
        List<List<Double>> storedPoints = new ArrayList<>(len);
        for (int i = 0; i < len; i++)
            storedPoints.add(new ArrayList<>());
        currentSegments = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            System.arraycopy(sorted, 0, points, 0, len);
            Point p = points[i];
            Arrays.sort(points, i, len, p.slopeOrder());

            int j = i, k = i;
            double slopeJ = p.slopeTo(points[j]);
            while (true) {
                if (k == len || Double.compare(slopeJ, p.slopeTo(points[k])) != 0) {
                    Point q = points[k-1];
                    int index = Arrays.binarySearch(sorted, q);
                    if (k - j >= 3 && isValid(slopeJ, storedPoints.get(index))) {
                        currentSegments.add(new LineSegment(p, q));
                        storedPoints.get(index).add(slopeJ);
                    }
                    j = k;
                    if (k == len) break;
                    slopeJ = p.slopeTo(points[j]);
                } else k++;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return currentSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[numberOfSegments()];
        for (int i = 0; i < segments.length; i++)
            segments[i] = currentSegments.get(i);
        return segments;
    }

    private boolean isValid(double otherSlope, List<Double> slopes) {
        for (double slope : slopes) {
            if (Double.compare(slope, otherSlope) == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("assignment3/collinear/input10.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
