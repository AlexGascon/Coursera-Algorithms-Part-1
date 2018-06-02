import java.util.Arrays;

public class BruteCollinearPoints {

	private int num_segments = 0;
	private LineSegment[] found_segments;

	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new java.lang.IllegalArgumentException("The input argument is null");
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new java.lang.IllegalArgumentException("The input contains null points");

			for (int j = i+1; j < points.length; j++) {
				if (points[j] == null) throw new java.lang.IllegalArgumentException("The input contains null points");
				if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException("The input contains repeated points");
			}
		}

		num_segments = 0;
		found_segments = new LineSegment[points.length];

		for (int i = 0; i < points.length; i++) 
			for (int j = i+1; j < points.length; j++)
				for (int k = j+1; k < points.length; k++) {
					
					// Checking if the first 3 points are collinear
					Point p = points[i];
					Point q = points[j];
					Point r = points[k];

					if (p.slopeTo(q) == p.slopeTo(r))

						// If they are collinear, we look for a forth one
						for (int l = k+1; l < points.length; l++) {
							Point s = points[l];
							if (p.slopeTo(s) == p.slopeTo(q)) {
								addSegment(p, q, r, s);
							} 
						}

				}

	}
	public int numberOfSegments() { return num_segments; }
	public LineSegment[] segments() {
		return Arrays.copyOfRange(found_segments, 0, numberOfSegments());
	}

	private void addSegment(Point p, Point q, Point r, Point s) {
		
		// Finding the most separated set of points
		Point[] collinear_points = {p, q, r, s};
		Arrays.sort(collinear_points);
		Point min_point = collinear_points[0];
		Point max_point = collinear_points[3];
		
		found_segments[num_segments++] = new LineSegment(min_point, max_point);
	}

	public static void main(String[] args) {
		// Empty method
	}


}