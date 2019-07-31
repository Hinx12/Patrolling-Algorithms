// Nathan Lewis
// MSc Project


/*
 * Class represents the visualisation of the input instance.
 * Visualisation library StdDraw.
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 */

import java.util.List;
import java.awt.Font;


public class InputGraph {

	private final int CANVASSIZE  = 900;       // 900 x 900 image.
	
	private double scale[]; 		// X and Y Scales of Image.
	private double axis[]; 			// X and Y Axis Labels.
	private String title; 			// Title of graph
	private int inputSpeed; 
	
	// Fints used for graph.
	private Font graphFont = new Font("Verdana", Font.ITALIC, 10);
	private Font weightFont = new Font("Verdana", Font.BOLD, 10);
	private Font statFont = new Font ("Verdana", Font.BOLD, 16);
	
	// Constructor takes scales and axis values
	public InputGraph(double scale[], double axis[], int inputSpeed) {
		this.scale = scale;
		this.axis = axis;
		this.inputSpeed = inputSpeed;
	}
	
	// Method Builds graph.
	public void buildGraph(int dist, int weight) {
		
		StdDraw.clear();
		titleGraph(dist, weight);
		StdDraw.setCanvasSize(CANVASSIZE, CANVASSIZE);
		StdDraw.setXscale(scale[0], scale[1]);
		StdDraw.setYscale(scale[0], scale[1]);
	}
	
	//Method Labels Axis of Graph.
	public void labelAxis (double limit) {
		// Draw title of Graph.
		StdDraw.setFont(statFont);
		StdDraw.text(0, (1.15 * limit), title);
		
		StdDraw.line(axis[0], axis[1], axis[2], axis[3]);
		StdDraw.line(axis[4], axis[5], axis[6], axis[7]);
		
		// Centre (0,0) label.
		StdDraw.setFont(graphFont);
		StdDraw.text(0.00, 0.00, "0");
		
		double xLabel = -(limit);
		double yLabel = -(limit);
		// Labels X Axis (50 Intervals).
		while (xLabel <= limit) {
			String xText = String.valueOf(xLabel);
			StdDraw.text(xLabel ,0.00, xText, 90);
			if (xLabel == -50) {
				xLabel = xLabel + 100;
			} else
				xLabel = xLabel + 50;
		}
		// Labels Y Axis (50 Intervals).
		while (yLabel <= limit) {
				
			String yText = String.valueOf(yLabel);
			StdDraw.text(0.00, yLabel, yText, 0);
			
			if (yLabel == -50) {
				yLabel = yLabel + 100;
			} else
				yLabel = yLabel + 50;
		}
		// Draws Border of Graph.
		StdDraw.setPenRadius(.003);
		StdDraw.rectangle(0, 0, (1.05 * limit), (1.05 * limit));
		StdDraw.setPenRadius();
	}
	
	// Method to plot points (bamboos).
	public void plotPoints (List<Point> InputPoints, int size, int option) {
		
		// Initialises point counter.
		int i = 1;
		StdDraw.setFont(statFont);
		StdDraw.text((1.06 * size), -(1.1 * size), "Point Counter");
		for (Point point : InputPoints) {
			StdDraw.setPenRadius(.06);
			StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
			StdDraw.point((1.06 * size), -(1.18 * size));
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text((1.06 * size), -(1.18 * size), String.valueOf(i));
			
			// Draws points (bamboos).
			double x = (double) point.getX();
			double y = (double) point.getY();
			StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
			StdDraw.point(x, y);
			StdDraw.setPenRadius();
			// Initialises distance lines from (0,0).
			if (option == 3 || option == 4 || option == 5) {
				StdDraw.line(0,0,point.getX(), point.getY());
			}
			// Draw weight onto point.
			StdDraw.setFont(weightFont);
			String weight = String.valueOf(point.getWeight());
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(x, y, weight);
			
			// Pause animation between plotting each point.
			if (option == 2) {
				i++;
				continue;
			
			} else {
				StdDraw.pause(inputSpeed);   // 100
				i++;
			}
		}
		StdDraw.setFont(statFont);
		StdDraw.text((0), -(1.15 * size), "Please Press Enter to Continue Through each Step....");
	}
	
	// Method to title graph.
	public void titleGraph(int dist, int weight) {
		
		
		switch (dist) {
		case 1:
			title = "Random Distribution";
			break;
		case 2:
			title = "Equidistant Distribution ";
			break;
		case 3:
			title = "Gaussian Distribution ";
			break;
		case 4:
			title = "Gaussian No Centre ";
			break;
		case 5:
			title = "Probability Spectrum ";
			break;
		default:
		}
		
		switch (weight) {
		case 1:
			title = title + " and Random Weight";
			break;
		case 2:
			title = title + " and Power Law";
			break;
		case 3: 
			title = title + " and Inverse Weight";
		default:
		}
	}
}
	
