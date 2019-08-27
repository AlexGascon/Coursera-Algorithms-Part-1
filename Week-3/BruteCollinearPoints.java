import java.util.Arrays;

public class BruteCollinearPoints {

    private int numSegments;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        validateInput(points);

        numSegments = 0;
        lineSegments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++)
            for (int j = i+1; j < points.length; j++)
                for (int k = j+1; k < points.length; k++) {

                    // Checking if the first 3 points are collinear
                    Point p = points[i];
                    Point q = points[j];
                    Point r = points[k];

                    if (p.slopeTo(q) == p.slopeTo(r))

                        // If they are collinear, we look for a forth one
                        for (int t = k+1; t < points.length; t++) {
                            Point s = points[t];
                            if (p.slopeTo(s) == p.slopeTo(q)) {
                                addSegment(p, q, r, s);
                            }
                        }

                }

    }
    public int numberOfSegments() { return numSegments; }
    public LineSegment[] segments() {
        return Arrays.copyOfRange(lineSegments, 0, numberOfSegments());
    }

    private void addSegment(Point p, Point q, Point r, Point s) {

        // Finding the most separated set of points
        Point[] collinearPoints = {p, q, r, s};
        Arrays.sort(collinearPoints);
        Point minPoint = collinearPoints[0];
        Point maxPoint = collinearPoints[3];

        lineSegments[numSegments++] = new LineSegment(minPoint, maxPoint);
    }

    public static void main(String[] args) {
        // Empty method
    }

    private void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException("The input argument is null");
        for (Point point : points) if (point == null) throw new IllegalArgumentException("The input contains null points");

        for (int i = 0; i < points.length; i++)
            for (int j = i+1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("The input contains repeated points");

    }
}