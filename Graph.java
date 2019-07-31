// Nathan Lewis
// MSc Project

/* Class visualises MST of points
 * Computes and Visualises LowerBounds.
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 */
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.awt.Color; 

public class Graph {
	// Instance Variables
	private int totalWeight; 
	private double LB1; 
	private double LB2; 
	private double Xmin;
	private double Xmax;
	private double Ymin;
	private double Ymax;
	private double alterX;
	private double alterY;
	private double stats;
	private ArrayList<Edge> mst;
	private List<Double> LBs = new ArrayList<>();
	private boolean isPressed = false;
	private Point maxPoint;
	private Point minPoint;
	private int furthestDist;
	
	// Graph Fonts.
	Font weightFont = new Font("Verdana", Font.BOLD, 10);
	Font statFont = new Font ("Verdana", Font.BOLD, 11);
	
	public ArrayList<Edge> getMST() {
		return mst;
	}
	
	public double getAlterY() {
		return alterY;
	}
		
	public int getTotalWeight() {
		return totalWeight;
	}
	
	public int getFurthestDist() {
		return furthestDist;
	}
	
	public void computeMST(List<Point> InputPoints) {
		
		// Number of Vertices in Graph 
		KrushkalMST graph = new KrushkalMST(InputPoints.size());
		
		// Add Complete Graph Between All Vertices
		for (Point point1 : InputPoints) {
			for(Point point2 : InputPoints) {
				if (point1 != point2) {	
					int dist = distBetweenPoints(point1, point2);
					graph.addEgde(point1.getId(), point2.getId(), dist);
				}
			}
		}
		mst = graph.kruskalMST();
	}
	
	// Method prints graph (not called) in program
	public void printGraph() {
        
		System.out.println("Minimum Spanning Tree: ");
    	
    	for (int i = 0; i < mst.size() ; i++) {
           
    		Edge edge = mst.get(i);
            System.out.println("[edge " + i + "] " + "source: " + edge.getSource() +
                    " destination: " + edge.getDestination() +
                    " weight: " + edge.getWeight());
        }
    } 
	
	// Method computes MST total weight
	public void computeMSTWeight() {
		
		totalWeight = 0;
		
		for (int i = 0; i < mst.size() ; i++) {
			
			Edge edge = mst.get(i);
			totalWeight = totalWeight + edge.getWeight();
		}
		
	}
	
	// Method calculates the distance between two points
	public int distBetweenPoints(Point p1, Point p2) {

		int dist = (int) Math.round(Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2.0) + Math.pow(p1.getY() - p2.getY(), 2.0)));
		
		return dist;
	}
	
	// MEthod computes LB1 (D * furthestpoint * 2)
	public double computeLB1(List<Point>InputPoints) {
		
		maxPoint = Collections.max(InputPoints, Comparator.comparing(c -> c.getWeight())); // finds max points
		double maxWeight = maxPoint.getWeight();
		
		List<Integer> distances = new ArrayList<Integer>(); // stopres all distances from this points
	
		for(Point point : InputPoints) {
			
			int dist = distBetweenPoints(maxPoint, point);    // calculates distance from maxPoint to other points in set V
			distances.add(dist);
			
		}
		furthestDist = Collections.max(distances); // finds the max from all these points 
		LB1 = (double) Math.round((maxWeight * furthestDist) * 2 * (100)) / 100; // Finally calculates LB1
		
		return LB1;
	}
	
	// Method visualises LB1 (StdDraw lib)
	public void visLB1 (List<Point>InputPoints) {
		
		StdDraw.enableDoubleBuffering();
		for (Point point : InputPoints) {
			if (point != maxPoint) {
				int dist = distBetweenPoints(maxPoint, point);
				if (dist == furthestDist) {
					StdDraw.setPenRadius();
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.line(maxPoint.getX(), maxPoint.getY(), point.getX(), point.getY());
					StdDraw.setPenColor(StdDraw.BOOK_RED);
					StdDraw.setPenRadius(.06);
					StdDraw.point(maxPoint.getX(), maxPoint.getY());
					StdDraw.point(point.getX(), point.getY());
					StdDraw.setFont(weightFont);
					StdDraw.setPenRadius();
					StdDraw.setPenColor(StdDraw.WHITE);
					String maxWeight = String.valueOf(maxPoint.getWeight());
					StdDraw.text(maxPoint.getX(), maxPoint.getY(), maxWeight);
					String furthPointWeight = String.valueOf(point.getWeight());
					StdDraw.text(point.getX(), point.getY(), furthPointWeight);
					StdDraw.setPenRadius();
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.setFont(statFont); 
					StdDraw.text(Xmax + (-stats / 2), 0 ,"Max Weight = " + maxPoint.getWeight());
					StdDraw.text(Xmax + (-stats / 2), 0 + (alterY), "Distance = " + furthestDist);
					StdDraw.text(Xmax + (-stats / 2), 0 + (2 * alterY), "(LB1) (D*2) * h1 = " + LB1);
					System.out.println("The First Lower Bound (D*2) * h1 = " + LB1);
				}
			}
		}
		StdDraw.pause(500);
		StdDraw.show();
	}
	
	// Method computes LB2
	public double computeLB2(List<Point>InputPoints, int lb2Speed) {
		
		List<Point>points = new ArrayList<>();
		
		for (Point point : InputPoints) {
			points.add(point);
		}
		
		/*
		 * while loop compute MSTs for points
		 * smallest point is removed 
		 * and the MST is recalculated 
		 * the MAX LB is then found 
		 */
		while (points.size() > 1) {
			StdDraw.enableDoubleBuffering();
			setPointIDs(points);
			computeMST(points);    // computes MST 
			computeMSTWeight();
			minPoint = Collections.min(points, Comparator.comparing(c -> c.getWeight()));  // Finds MinPoint
			double minWeight = minPoint.getWeight();
			LB2 = (double) Math.round((minWeight * totalWeight) * (100)) / 100;      // computes LB
			LBs.add(LB2);                                                            // Adds to list of all calculated LBs
			computeScales(InputPoints, 1);
			visEdges(points, StdDraw.BLACK);
			visNodes(points, StdDraw.BOOK_LIGHT_BLUE, 0.06);
			
			StdDraw.setFont(statFont); 
			StdDraw.text(Xmax + (-stats / 2), 0 ,"MST = " + totalWeight);
			StdDraw.text(Xmax + (-stats / 2), 0 + (alterY), "Min Weight = " + minPoint.getWeight());
			StdDraw.text(Xmax + (-stats / 2), 0 + (2 * alterY), "MST * hn = " + LB2);
			points.remove(minPoint);                                          // removes min point for recalculation
			StdDraw.show();
			StdDraw.pause(lb2Speed);
		}
		StdDraw.enableDoubleBuffering();
		LB2 = Collections.max(LBs);                           // finds max LB
		StdDraw.text(Xmax + (-stats / 2), 0 + (4 * alterY), "(LB2) Max LB = " + LB2);
		System.out.println("The Second Lower Bound MST * hn = " + LB2);
		StdDraw.pause(500);
		StdDraw.show();
		return LB2;
	}
	
	// Method computes visualisation scales for each instance
	public void computeScales (List<Point>InputPoints, int type) {
	
		Point XpointMin = Collections.min(InputPoints, Comparator.comparing(s -> s.getX())); 
		Point XpointMax = Collections.max(InputPoints, Comparator.comparing(s -> s.getX())); 
		Point YpointMin = Collections.min(InputPoints, Comparator.comparing(s -> s.getY())); 
		Point YpointMax = Collections.max(InputPoints, Comparator.comparing(s -> s.getY())); 
		
		Xmin = (double) XpointMin.getX();
		Xmax = (double) XpointMax.getX();
		Ymin = (double) YpointMin.getY();
		Ymax = (double) YpointMax.getY();
		
		alterX = (Xmin / 10);
		alterY = (Ymin / 10);
		stats = (Xmin / 1.2);
		
		if (type == 0) {
			StdDraw.clear();		
			StdDraw.setXscale(Xmin - (-alterX), Xmax + (-alterX));
			StdDraw.setYscale(Ymin - (-alterY), Ymax + (-alterY));
		} else {
			StdDraw.clear();		
			StdDraw.setXscale(Xmin - (-alterX), Xmax + (-stats));
			StdDraw.setYscale(Ymin - (-alterY), Ymax + (-alterY));
		}
	}
	
	// Method visualises nodes
	public void visNodes(List<Point>InputPoints, Color fill, double size) {
		
		StdDraw.enableDoubleBuffering();
		for (Point point : InputPoints) {
			double x = (double) point.getX();
			double y = (double) point.getY();
			StdDraw.setPenColor(fill);
			StdDraw.setPenRadius(size);
			StdDraw.point(x, y); 
			StdDraw.setFont(weightFont);
			String weight = String.valueOf(point.getWeight());
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(x, y, weight);
		}
		StdDraw.show();
	}
	
	// Method visualises edges
	public void visEdges(List<Point>InputPoints, Color fill) {
		
		StdDraw.setPenRadius();
		for (Point point1 : InputPoints) {
			for(Point point2 : InputPoints) {
				for (Edge edge : mst) {
					if (point1.getId() == edge.getSource()) {
							if (point2.getId() == edge.getDestination()) {
								double x1 = (double) point1.getX();
								double y1 = (double) point1.getY();
								double x2 = (double) point2.getX();
								double y2 = (double) point2.getY();
								StdDraw.setPenColor(fill);
								StdDraw.line(x1, y1, x2, y2);
						}
					}
				}
			}
		}
	}
			
	
	public void setPointIDs(List<Point>InputPoints) {
		
		int x = 0;
		
		for (Point point : InputPoints) {
			point.setId(x);
			x++;
		}
	}
	
	// Method shows group stats of log groups 
	public void showGroupStat(Group group, double space) {
		
		StdDraw.setPenColor(group.getColor());
		StdDraw.point(Xmax + (-stats / 2), 0 + space);
		String groupID = String.valueOf("G" + group.getGroupID());
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setFont(weightFont);
		StdDraw.text(Xmax + (-stats / 2), 0 + space, groupID);
		String numOfPoints = String.valueOf("       " + group.getGroupPoints().size());
		StdDraw.textLeft(Xmax + (-stats / 2), 0 + space, numOfPoints);
	}
	
	// Method finally shows the compiute solution
	// UB, LB1, LB2 and Ratio
	public void showBounds (double LB1, double LB2, double UB, double space) {
		
		StdDraw.setFont(statFont);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(Xmax + (-stats / 2), 0 + space, "LB1 = " + LB1);
		StdDraw.text(Xmax + (-stats / 2), 0 + space + alterY, "LB2 = " + LB2);
		StdDraw.text(Xmax + (-stats / 2), 0 + space + (2 * alterY), "UB = " + UB);
		
		System.out.println("Lower Bound 1 = " + LB1);
		System.out.println("Lower Bound 2 = " + LB2);
		System.out.println("Upper Bound = " + UB);
		
		double maxLB = Math.max(LB1, LB2);                          // Find MAX(LB1, LB2)
		double ratio = Math.round((UB / maxLB) * 100) / 100D;        // Computes ratio
		StdDraw.text(Xmax + (-stats / 2), 0 + space + (3 * alterY), "Ratio = " + ratio);
		System.out.println("Ratio = " + ratio);
		
	}
	
	// if ENTER is pressed LB2 is computed
	public double checkKey(List<Point>InputPoints, int lb2Speed) { 
	    
	
		while (isPressed == false) {
			
			if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
				
				computeLB2(InputPoints, lb2Speed);
				isPressed = true;
			}
		}
		isPressed = false;
		return LB2;
	}
}