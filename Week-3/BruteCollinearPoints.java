public class BruteCollinearPoints {

	private final Point[4] points;
	private final int segments;

	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new java.lang.IllegalArgumentException("The input argument is null");
		for (int i = 0; i < points.length; i++)
			if (points[i] == null) throw new java.lang.IllegalArgumentException("The points can't be null");
	}
	public int numberOfSegments()
	public LineSegment[] segments()
}