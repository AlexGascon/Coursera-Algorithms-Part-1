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
            Point[] slopesToP = getSlopesTo(p, points);

            for (int j = 0; j < slopesToP.length - 2; j++) {
                Point q = slopesToP[j];
                Point[] candidatePoints = Arrays.copyOfRange(slopesToP, j+1, slopesToP.length);
                Point[] collinearSequence = findCollinearSequence(p, q, candidatePoints);

                if (collinearSequence.length >= 4) {
                    addSegment(collinearSequence);
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

    private Point[] findCollinearSequence(Point p, Point q, Point[] candidatePoints) {
        Stack<Point> collinearPoints = new Stack<Point>();
        collinearPoints.push(p);
        collinearPoints.push(q);

        for (Point r : candidatePoints)
            if (areCollinear(p, collinearPoints.peek(), r))
                collinearPoints.push(r);
            else
                break;

        return collinearPoints.toArray(new Point[0]);
    }

    private void addSegment(Stack<Point> collinearPoints) {
        addSegment(collinearPoints.toArray(new Point[0]));
    }

    private void addSegment(Point[] ps) {
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

    private Point[] getSlopesTo(Point p, Point[] points) {
        // Returns an array of Points sorted by its slope to a point P
        Point[] slopesToP = Arrays.copyOf(points, points.length);
        Arrays.sort(slopesToP, p.slopeOrder());

        // The point with the lowest slow to p is P itself (-INF), so we exclude it
        return Arrays.copyOfRange(slopesToP, 1, slopesToP.length);
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
