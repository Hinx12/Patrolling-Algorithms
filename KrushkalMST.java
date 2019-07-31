// Nathan Lewis
// MSc Project


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * Class represents the implementation of 
 * Krushkal's algorithm
 */

public class KrushkalMST {
        
    private int vertices;                 // total number of vertices 
    ArrayList<Edge> allEdges = new ArrayList<>();          // list of edges

    KrushkalMST(int vertices) {
            
    	this.vertices = vertices;
    }

    public void addEgde(int source, int destination, int weight) {
            
    	Edge edge = new Edge (source, destination, weight);                // creates edge instance
    	allEdges.add(edge); //add to total edges
    }
        
    public ArrayList<Edge> kruskalMST(){
    	
        // Stores all edges of complete graph in priority queue and sorts by weight    
    	PriorityQueue<Edge> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingDouble(o -> o.getWeight()));

    	//add all the edges to priority queue, //sort the edges on weights
    	for (int i = 0; i < allEdges.size() ; i++) {
                
    		pq.add(allEdges.get(i));
    	}

    	//create a parent []
    	int [] parent = new int[vertices];

    	//makeset
    	makeSet(parent);

    	ArrayList<Edge> mst = new ArrayList<>();

    	//process vertices - 1 edges
    	int index = 0;
    	while(index < vertices - 1){
                
    		Edge edge = pq.remove();
    		//check if adding this edge creates a cycle
    		int x_set = find (parent, edge.getSource());
    		int y_set = find (parent, edge.getDestination());

    		if (x_set==y_set){
    			//ignore, will create cycle
    		} else {
    			//add it to our final result
    			mst.add (edge);
    			index++;
    			union (parent,x_set,y_set);
    		}
    	}
            
    	return mst;
    }

    public void makeSet(int [] parent){
    	
    	// Make set- creating a new element with a parent pointer to itself.
    	for (int i = 0; i < vertices ; i++) {
                
    		parent[i] = i;
    	}
    }

    public int find(int [] parent, int vertex){
            
    	// chain of parent pointers from x upwards through the tree
    	// until an element is reached whose parent is itself
    	if(parent[vertex]!=vertex)
                
    		return find(parent, parent[vertex]);;
    		return vertex;
    }

    public void union(int [] parent, int x, int y){
            
    	int x_set_parent = find(parent, x);
    	int y_set_parent = find(parent, y);
    	//make x as parent of y
    	parent[y_set_parent] = x_set_parent;
    }
}