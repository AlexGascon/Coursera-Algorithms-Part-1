import java.util.Arrays;

public class FastCollinearPoints {

    private int numSegments = 0;
    private LineSegment[] foundSegments;
    private Point[] points, currentPoints, minPoints, maxPoints;

    public FastCollinearPoints(Point[] points) {
        
        // Preventing corner cases
        if (points == null) throw new java.lang.IllegalArgumentException("The input argument is null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException("The input contains null points");

            for (int j = i+1; j < points.length; j++) {
                if (points[j] == null) throw new java.lang.IllegalArgumentException("The input contains null points");
                if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException("The input contains repeated points");
            }
        }

        this.points = Arrays.copyOfRange(points, 0, points.length);
        minPoints = new Point[points.length];
        maxPoints = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] slopesToP = Arrays.copyOfRange(points, 0, points.length);
            Arrays.sort(slopesToP, p.slopeOrder());

            // We start the inner loop in 1 because the element with less slope 
            // will be the point itself (which has -Infinity)
            for (int j = 1; j < slopesToP.length - 2; j++) {
                if (p.slopeTo(slopesToP[j]) == p.slopeTo(slopesToP[j+1]))
                    // We avoid this comparison if the first two points aren't collinear
                    if (p.slopeTo(slopesToP[j]) == p.slopeTo(slopesToP[j+2]))
                        addToSegment(p, slopesToP[j], slopesToP[j+1], slopesToP[j+2]);
            }
        }

    }
    public int numberOfSegments() { return numSegments; }

    public LineSegment[] segments() {
        foundSegments = new LineSegment[numberOfSegments()];

        for (int i = 0; i < foundSegments.length; i++)
            foundSegments[i] = new LineSegment(minPoints[i], maxPoints[i]);

        return Arrays.copyOfRange(foundSegments, 0, numberOfSegments());
    }

    private void addToSegment(Point p, Point q, Point r, Point s) {
        // Finding the most separated set of points
        Point[] collinearPoints = {p, q, r, s};
        Arrays.sort(collinearPoints);
        Point minPoint = collinearPoints[0];
        Point maxPoint = collinearPoints[3];

        for (int i = 0; i < numSegments; i++) {
            if (minPoints[i].compareTo(minPoint) == 0 && maxPoints[i].compareTo(maxPoint) == 0)
                return;
        }

        if (numSegments == minPoints.length) {
            resizePoints(minPoints.length * 2);
        }

        minPoints[numSegments] = minPoint;
        maxPoints[numSegments] = maxPoint;
        numSegments++;
    }

    private void resizePoints(int capacity) {
        Point[] minCopy = new Point[capacity];
        Point[] maxCopy = new Point[capacity];

        for (int i = 0; i < minPoints.length; i++) {
            minCopy[i] = minPoints[i];
            maxCopy[i] = maxPoints[i];
        }

        this.minPoints = minCopy;
        this.maxPoints = maxCopy;
    }
}
