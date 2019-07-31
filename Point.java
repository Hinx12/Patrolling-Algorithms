// Nathan Lewis
// MSc Project

// Class represents a single point.

public class Point {
	
	// Instance Variables 
	private int x;
	private int y;
	private int distance;         // Distance thrown from (0,0).
	private double weight;        // Urgency Factor.
	private int id;
	private boolean added = false;
	
	// Constructor when creating each point (Xcoord, Ycoord, Distance Thrown).
	public Point(int x, int y, int distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDistance() {
		return distance;
	}

	// Returns String representation of each point 
	public String toString() {
		return "ID " + id + " (" + x + ", " + y + "), Distance Thrown = " + distance + ", Weight = " + weight;
	}
	
	public void setWeight(double someWeight) {
		this.weight = someWeight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	/* Methods to help check whether the point has
	 * already been assigned a group.
	 */
	public void isAdded(Point point) {
		added = true;
	}
	
	public boolean checkAdded() {
		return added;
	}
}
	
	
	
	