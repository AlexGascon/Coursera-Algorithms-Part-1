public class FastCollinearPoints {

    public FastCollinearPoints(Point[] points) {
        validateInput(points);
    }

    public int numberOfSegments() {
        return 0;
    }

    public LineSegment[] segments() {
        return new LineSegment[0];
    }

    private void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException("The input cannot be null");

        for (Point point : points) if (point == null) throw new IllegalArgumentException("The input cannot contain null points");

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i] == points[j]) throw new IllegalArgumentException("The input cannot contain repeated points");
    }
}
