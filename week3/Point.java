package assignment3;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y)
                return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) return 0.0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y == that.y)
            return Integer.compare(this.x, that.x);
        return Integer.compare(this.y, that.y);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Double.compare(slopeTo(p1), slopeTo(p2));
            }
        };
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}