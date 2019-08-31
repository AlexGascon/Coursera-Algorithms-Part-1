import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private int segmentCount;
    private LineSegment[] collinearSegments;
    private Segment[] collinearSegmentsInternal;

    public FastCollinearPoints(Point[] points) {
        validateInput(points);
        segmentCount = 0;
        collinearSegments = new LineSegment[points.length];
        collinearSegmentsInternal = new Segment[points.length];

        for (Point p : points) {
            // Sort the points according to the slope they make with p
            Point[] slopesToP = Arrays.copyOf(points, points.length);
            Arrays.sort(slopesToP, p.slopeOrder());

            for (int j = 1; j < slopesToP.length - 2; j++) {
                Stack<Point> collinearPoints = new Stack<Point>();
                collinearPoints.push(p);
                collinearPoints.push(slopesToP[j]);
                int end = j + 1;

                while (end < slopesToP.length && areCollinear(p, collinearPoints.peek(), slopesToP[end]))
                    collinearPoints.push(slopesToP[end++]);

                if (collinearPoints.size() >= 4) {
                    //debugSegment(collinearPoints);
                    addSegment(collinearPoints);
                    break;
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        return Arrays.copyOfRange(collinearSegments, 0, numberOfSegments());
    }

    private boolean isIncluded(Segment s) {
        for (int i = 0; i < segmentCount; i++)
            if (s.equals(collinearSegmentsInternal[i]))
                return true;

        return false;
    }

    private void printArray(Object[] arr) {
        for (Object x : arr)
            StdOut.println(x);
    }

    private void addSegment(Stack<Point> collinearPoints) {
        Point[] ps = collinearPoints.toArray(new Point[0]);
        Arrays.sort(ps);
        Segment s = new Segment(ps[0], ps[ps.length - 1]);

        if (!isIncluded(s)) {
            collinearSegmentsInternal[segmentCount] = s;
            collinearSegments[segmentCount] = new LineSegment(s.minPoint, s.maxPoint);
            segmentCount++;
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }

    private void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException("The input cannot be null");

        for (Point point : points) if (point == null) throw new IllegalArgumentException("The input cannot contain null points");

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("The input cannot contain repeated points");
    }

    private boolean areCollinear(Point p1, Point p2, Point p3) {
        return (Double.compare(p1.slopeTo(p2), p1.slopeTo(p3)) == 0);
    }

    private class Segment {
        public Point minPoint, maxPoint;

        public Segment(Point minPoint, Point maxPoint) {
            this.minPoint = minPoint;
            this.maxPoint = maxPoint;
        }

        public boolean equals(Segment other) {
            return ((minPoint.compareTo(other.minPoint) == 0) && (maxPoint.compareTo(other.maxPoint) == 0));
        }
    }

}
