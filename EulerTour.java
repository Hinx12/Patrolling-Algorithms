// Nathan Lewis
// MSc Project

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/*
 * Class represents Euler tour of graph
 * Precise Euler tour is not calculated
 * This is used to show how the tour of group MSTs work
 * Class could be altered for future work to implement a precise tour 
 */
public class EulerTour {

	static class Line {
		int node;
		boolean used;

		Line(int x) { node = x; }
	}

	static ArrayList<Line>[] adjList;
	static LinkedList<Integer> tour;

	static void eulerTour(ListIterator<Integer> itr, int u) {
		for(Line nxt: adjList[u])
			if(!nxt.used)
			{
				nxt.used = true;
				for(Line rev: adjList[nxt.node])
					if(rev.node == u && !rev.used)
					{
						rev.used = true;
						break;
					}
				itr.add(u);
				eulerTour(itr, nxt.node);
				itr.previous();
			}
	}

	static void addEdge(int u, int v) {
		adjList[u].add(new Line(v));
		adjList[v].add(new Line(u));
	}

	// Method computes tour
	public void computeTour(Group group) {		
		
		// Adds 
		adjList = new ArrayList[group.getGroupPoints().size()];
		for(int i = 0; i < group.getGroupPoints().size(); ++i)
			adjList[i] = new ArrayList<Line>();
		
		ArrayList<Edge> theMST = new ArrayList<>();
		theMST = group.getMST();
		
		// Adds edge from source to destination node and vise versa (so each edge is visited twice)
		for(Edge edge : theMST) {
			addEdge(edge.getSource(), edge.getDestination());
			addEdge(edge.getDestination(), edge.getSource());
		}
		
		tour = new LinkedList<Integer>();
		eulerTour(tour.listIterator(), theMST.get(theMST.size()- 1).getSource());		// starts from the first node listed in MST.
		tour.add(theMST.get(theMST.size() - 1).getSource());
		visTour(group);
	}
	
	// Method visualises tour of MSTs
	public void visTour(Group group) {
		
		Graph test = new Graph();
		test.computeMST(group.getGroupPoints());  // compute MST
		test.visEdges(group.getGroupPoints(), StdDraw.BLACK);    // vis edges 
		test.visNodes(group.getGroupPoints(), StdDraw.LIGHT_GRAY, 0.06);  // vis nodes
		 
		for (int i = 0; i < tour.size(); i++) {
			for (Point point : group.getGroupPoints()) {
				if(tour.get(i) == point.getId()) {
					StdDraw.enableDoubleBuffering();
					StdDraw.setPenRadius(0.06);
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.point(point.getX(), point.getY());
					StdDraw.show();
					StdDraw.enableDoubleBuffering();
					StdDraw.setPenColor(group.getColor());
					StdDraw.point(point.getX(), point.getY());
					if (point.getId() == tour.getFirst()) {
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.text(point.getX(), point.getY(),"Start");
					} else if (i == tour.size() - 1) {
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.text(point.getX(), point.getY(), "End");
					} else {
						String weight = String.valueOf(point.getWeight());
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.text(point.getX(), point.getY(), weight);
					}
					StdDraw.pause(150);
					StdDraw.show();
				}
			}
		}
	}
}
