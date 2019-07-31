// Nathan Lewis
// MSc Project

/*
 * Class represent a single edge in the MST
 */

public class Edge {
        
		private int source;           // source point
		private int destination;      // other point
		private int weight;           // edge weight

        public Edge(int source, int destination, int weight) {
            
        	this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        
        public int getSource() {
        	return source;
        }
        
        public int getDestination() {
        	return destination;
        }
        
        public int getWeight() {
        	return weight;
        }

    }


