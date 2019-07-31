// Nathan Lewis
// MSc Project

/* 
 * Class represents a single group when points 
 * are split into log groups.
 */

import java.awt.Color;
import java.util.ArrayList;

public class Group {
	
	private double weightLimit;                      // Weight limit of group
	private ArrayList<Point> groupPoints = new ArrayList<Point>();
	public int id;
	private ArrayList<Edge> mst;                     // MST of group
	private int totalWeight;                         // Weight of MST for group
	private Color groupColor;

	public Group (double weightLimit) {
		
		this.weightLimit = weightLimit;
	}
		
	public double getWeightLimit() {
		
		return weightLimit;
	}
	
	public void setColor (Color groupColor) {
		this.groupColor = groupColor;
	}
	
	public Color getColor() {
		return groupColor;
	}
	
	// Adds point to group
	public void addPoint (Point point) {
		groupPoints.add(point);
	}
	// Sets MST weight
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	public int getTotalWeight() {
		return totalWeight;
	}
	
	public void setGroupID(int id) {
		this.id = id;
	}
	
	public int getGroupID() {
		return id;
	}
	
	public void printGroup() {
		
		System.out.println("------------------------------------------");
		System.out.println("Group ID = " + id);
		System.out.println("Group Weight Limit " + weightLimit);
		System.out.println("Number of Points = " + groupPoints.size());
		
		for (Point point : groupPoints) {
			System.out.println(point);
		}
		System.out.println("------------------------------------------");
	}
	
	// Returns group points
	public ArrayList<Point> getGroupPoints() {
		
		return groupPoints;
	}
	// Sets MST for group
	public void setMST(ArrayList<Edge> mst) {
		this.mst = mst;
	}
	
	public ArrayList<Edge> getMST() {
		return mst;
	}
	
	public void setPointID() {
		
		int i = 0;
		
		for (Point point : groupPoints) {
			point.setId(i);
			i++;
		}
	}
}
