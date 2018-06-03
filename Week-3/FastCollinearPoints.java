import java.util.Arrays;

public class FastCollinearPoints {

	private int numSegments = 0;
	private LineSegment[] foundSegments;
	private Point[] points, currentPoints, minPoints, maxPoints;

	public FastCollinearPoints(Point[] points) {
		// Empty method
	}
	public int numberOfSegments() { 
		return 0;
	}
	public int numberOfSegments() { return numSegments;	}

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
