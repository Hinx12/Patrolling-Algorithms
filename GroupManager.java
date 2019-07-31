// Nathan Lewis
// MSc Project

import java.awt.event.KeyEvent;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Class represents the group manager for all groups
 * Class handles the splitting of groups
 * Adds point to appropriote group
 * Computes upper bound
 */

public class GroupManager {
	
	private int numOfPoints;                          // total number of points
	private ArrayList<Point> coordinates = new ArrayList<Point>(); 
	private ArrayList<Group> groups = new ArrayList<Group>();             // stores all groups in list
	private double maxWeight;                                 
	private boolean isPressed = false;
	private int maxGroups;                                 // Max number of groups depending on log bounds
	private double LB1;
	private double LB2; 
	private double UB;
	private int diameter;  // D   
	private double bound;                              // log bound

	public GroupManager (ArrayList<Point> coordinates, int numOfPoints, double LB1, double LB2, int diameter, double bound) {
		
		this.coordinates = coordinates;
		this.numOfPoints = numOfPoints;
		this.LB1 = LB1;
		this.LB2 = LB2;
		this.diameter = diameter;
		this.bound = bound;
	}

	public void startGroups() {
		
		createGroups();
		splitPoints();
		amendGroups();
		visGroups();
		checkKey();
		computeTour();
	}
	
	// Method computes tour of group MSTs
	// Not fully implemented
	// Tours each tree one after the other
	// no precise walk 
	public void computeTour() {
		EulerTour tour = new EulerTour();
		Graph test = new Graph();
		StdDraw.clear();
		test.computeScales(coordinates, 0);
		
		for (Group group : groups) {
			if (group.getGroupPoints().size() == 1) {
					StdDraw.pause(300);
					test.visNodes(group.getGroupPoints(), group.getColor(), 0.06);
			} else {
					tour.computeTour(group);
			}
		}
	}
	
	// Computes UB of an instance
	public void computeUB() {
		
		List<Double> UBs = new ArrayList<>();
		
		// For each group
		for (Group group : groups) {    
			
			Point maxPoint = Collections.max(group.getGroupPoints(), Comparator.comparing(c -> c.getWeight()));   // finds max weight in group points
			double maxWeight = maxPoint.getWeight();
			double UpperBound = (double) Math.round((group.getTotalWeight()) * (maxWeight) * 100) / 100;    // MAX point * MSt total weight
			UBs.add(UpperBound);   // adds to list of UBs for each group
		}
		UB = Math.round(groups.size() * (2 * Collections.max(UBs)) * 100) / 100D;  // finds max product and * by 2
	}
	
	// Visualises log groups
	public void visGroups() {
		
		Graph graph = new Graph();
		graph.computeScales(coordinates, 1);
		Colors colors = new Colors();

		int i = 0;
		double space = 0 - (3 * graph.getAlterY());
		
		for (Group group : groups) {
			
			group.setColor(colors.getColors().get(i));
			graph.showGroupStat(group, space);
			graph.visNodes(group.getGroupPoints(), group.getColor(), .06); 
			space = space + graph.getAlterY();
			i++;
			StdDraw.pause(1000);
		}
		
		checkKey();
		StdDraw.enableDoubleBuffering();
		
		for (Group group : groups) {
			
			if (group.getGroupPoints().size() == 1) {
				continue;
			} else {
				graph.computeMST(group.getGroupPoints()); // computes MST of gorup
				group.setMST(graph.getMST());
				graph.computeMSTWeight();             // Compute total weight of gorup
				group.setTotalWeight(graph.getTotalWeight());	
				graph.visEdges(group.getGroupPoints(), group.getColor());
			}
		}
		
		int iterator = 0;
		
		// sets group colour
		for (Group group : groups) {
			group.setColor(colors.getColors().get(iterator));
			graph.visNodes(group.getGroupPoints(), group.getColor(), .06); 
			iterator++;
		}
		
		computeUB();
		graph.showBounds(LB1, LB2, UB, -8 * graph.getAlterY()); // shows all bounds 
		StdDraw.show();
		StdDraw.pause(500);
	}

	// Method to create log groups
	public void createGroups() {
		
		Point maxPoint = Collections.max(coordinates, Comparator.comparing(c -> c.getWeight())); // finds max weight
		maxWeight = maxPoint.getWeight();
		double logGroups = Math.log(numOfPoints) / Math.log(bound); // computes number of log groups
		maxGroups = (int) Math.ceil(logGroups); // rounds value up for MAX number of groups
		
		// Assigns weight limit to groups
		for (int i = 1;  i <= maxGroups; i++) {
			Group group = new Group(maxWeight);
			maxWeight  = maxWeight / bound;   // maxweight / bound to assign to total weight of next group
			groups.add(group);
		}
	}
	
	// MEthod splits points into their appropriote group
	public void splitPoints() {
		
		// iterates through groups (starting from G1, which contains the heaviest points)
		for (Group group : groups) {
			for (Point point : coordinates) {
				// adds points to group
				// checkAdded is a flag so points are not added more than once to a group
				if (point.getWeight() == group.getWeightLimit() || point.getWeight() > group.getWeightLimit() / bound && point.checkAdded() == false) {
					group.addPoint(point); // adds point
					point.isAdded(point); // changes flag to true
				}
			}
		}
		
		// Finally adds those points that weren't added to the last group (smallest total limit group, Gn)
		for (Point point : coordinates) {
				if (point.checkAdded() == false) {
					groups.get(maxGroups - 1).addPoint(point);
					point.isAdded(point);
				}
		}
	}

	public void amendGroups() {
		
		List<Group> toRemove = new ArrayList<Group>();
	
		for (Group group : groups) {
		    
			if (group.getGroupPoints().isEmpty()) {
		        toRemove.add(group);
		    }
		}
		groups.removeAll(toRemove);
		
		
		int id = 1;
		// Sets group ID 
		for (Group group : groups) {
			group.setGroupID(id);
			id++;
		}
		// Sets point IDs for group
		for (Group group :groups) {
			 group.setPointID();
		}
		
		for (Group group : groups) {
			group.printGroup();
		}
		
	}
	// Calls startGroup when ENTER is hit
	public void beginGroups() { 
	    
		while (isPressed == false) {
		
			if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
				startGroups();
				isPressed = true;
			}
		}
		isPressed = false;
	 }
	
	public void checkKey() { 
	    
		while (isPressed == false) {
		
			if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
				isPressed = true;
			}
		}
		isPressed = false;
	 }
	
}
