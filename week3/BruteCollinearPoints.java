package assignment3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> currentSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointsOrig) {
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

        currentSegments = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                for (int k = j+1; k < len; k++) {
                    for (int m = k+1; m < len; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];
                        if (isSlopeEqual(p, q, r) && isSlopeEqual(p, r, s))
                            currentSegments.add(new LineSegment(p, s));
                    }
                }
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

    private boolean isSlopeEqual(Point p, Point q, Point r) {
        double pq = p.slopeTo(q);
        double pr = p.slopeTo(r);
        return Double.compare(pq, pr) == 0;
    }
}
