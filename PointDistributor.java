// Nathan Lewis
// MSc Project

/* Class is responsible for creating all input instances.
 * Also handles all input from the user
 * Driver Class (Main Method)
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.Color; 

public class PointDistributor {
	
	// Instance Variables
	private int stdDev;  
	private double bound;                     // Log Bound
	private int distOption; 
	private int numOfPoints; 
	private int weightOption;
	private int radius;                       // Radius for Probability range
	private double probability;               // Probability for Probability range
	private int constant;                     // constant for weight distribution
	private double power;                     // exponent for weight distribution
	private ArrayList<Point> coordinates = new ArrayList<Point>(); 
	private Scanner in = new Scanner(System.in); 
	private Random r = new Random();
	private boolean isPressed = false; 
	private int lb2Speed;                     // determines speed of run
	private int inputSpeed;
	private Color dft = new Color(0, 204, 0);
	
	public static void main(String[] args) {

		
		PointDistributor distributor = new PointDistributor();
		
		distributor.readDistroInput();
		
		distributor.startDistribution();
		
		distributor.readWeightInput();
		
		distributor.setParameters();
		
		distributor.askBound();
		
		distributor.startWeight();
		
		distributor.askSpeed();
		
		distributor.printPoints();

		distributor.graphOption();
		
		distributor.checkKey();
		
		System.out.println("GoodBye");
		
		
	}
	  
	// Method handles speed of run
	public void askSpeed() {
		
		int speed;
		
		while (true) {
			try {

				System.out.println("Choose the Distribution of Points");
				System.out.println("----------------------------------");
				System.out.println("1.Normal Run");                 
				System.out.println("2.Fast Run (for the purposes of experimentation)");                 
				speed = in.nextInt();										 
				break;

			} catch (InputMismatchException ime) {

				System.out
						.println("Please enter a number from the following options");
				in.nextLine();
			}
		}
		
		if (speed == 1) {
			inputSpeed = 100;
			lb2Speed = 200;
		} else {
			inputSpeed = 10;
			lb2Speed = 10;
		}
	}

	// Method promts user for specific point distribution.
	public void readDistroInput() {

		while (true) {
			try {

				System.out.println("Choose the Distribution of Points");
				System.out.println("----------------------------------");
				System.out.println("1.Random Distribution");                 
				System.out.println("2.Equidistant Distribution");       
				System.out.println("3.Gaussian Distribution (Normal Distribution)");            
				System.out.println("4.Gaussian (Normal Distribution) - Centre Removed"); 
				System.out.println("5.Probability Range - Bernoulli");                  
				distOption = in.nextInt();										 
				break;

			} catch (InputMismatchException ime) {

				System.out
						.println("Please enter a number from the following options");
				in.nextLine();
			}
		}
	}
	
	// Starts distribution depending on inputted point distribution.
	public void startDistribution() {
		
		switch (distOption) {
		case 1:
			randomDistribution();
			break;
		case 2:
			evenDistribution();
			break;
		case 3:
			randomGaussian();
			break;
		case 4: 
			randomGaussianNoCentre();
			break;
		case 5:
			probabiltySpectrum();
			break;
		default:
			System.out
					.print("Please enter a number from the following options");
		}
	}

	// Method generates random XY coordinates(-500 to 500)
	public void randomDistribution() {
		
		// Reads number of points 
		readPointInput();
		System.out.println("Generating " + numOfPoints + " random co-ordinates between the range of -500 to 500.");
		
		int points = 0;
		
		while (points != numOfPoints) {
			int Xcoord = r.nextInt(500 + 1 + 500) - 500;
			int Ycoord = r.nextInt(500 + 1 + 500) - 500;
			int distThrown = (int) Math.round(Math.sqrt((Xcoord * Xcoord) + (Ycoord * Ycoord))); // Pythagoras to compute distance.
			if (distThrown < 50) {
				continue;
			} else {
				coordinates.add(new Point(Xcoord, Ycoord, distThrown));
				points++;
			}
		}
	}
	
	// Method generates 100 equidistant points.
	public void evenDistribution() {
		
		// Default number of points for this option.
		numOfPoints = 100;
		System.out.println("Generating 100 Equadistant co-ordinates");
		
		// Distance between points is 100.
		// 10 Points each row.
		int phiX = -450;
		int phiY = -450;
		
		while (phiY <= 450) {
			
			if (phiX == 450) {
				int Xcoord = phiX;
				int Ycoord = phiY;
				int distThrown = (int) Math.round(Math.sqrt((Xcoord * Xcoord) + (Ycoord * Ycoord))); // Pythagoras to compute distance.
				coordinates.add(new Point(Xcoord, Ycoord, distThrown));
				phiX = -450;  // Resets X coord to start on next row
				phiY = phiY + 100; // Moves Y coord up by 100.
			} else {
				int Xcoord = phiX;
				int Ycoord = phiY;
				int distThrown = (int) Math.round(Math.sqrt((Xcoord * Xcoord) + (Ycoord * Ycoord)));
				coordinates.add(new Point(Xcoord, Ycoord, distThrown));
				phiX = phiX + 100;
			}
		}
	}
	
	// Method generates normal distribution (Mean 0) and inputted StdDev.
	public void randomGaussian() {
		
		// Read stdDev from user
		readStdDevInput();
		// Read number of points
		readPointInput();
		System.out.println("Generating " + numOfPoints + " co-ordinates within the range of -500 to 500 (Mean 0 and SD " + stdDev +")");

		int points = 0;
		
		while (points != numOfPoints) {
			
			// Generates random gaussian value * stdDev.
			int Gaussian = (int) Math.round(r.nextGaussian() * stdDev);
			int distThrown = (int) Math.sqrt(Gaussian * Gaussian); // distance must be positive integer.
			
			if (distThrown < 10) {
				continue;
			} else {
				int angle = r.nextInt(360) + 1; // Generate random angle.
				int Ycoord = (int) Math.round(Math.sin(angle) * Gaussian); // sin(angle).
				int Xcoord = (int) Math.round(Math.cos(angle) * Gaussian); // cos(angle).
				coordinates.add(new Point(Xcoord, Ycoord, distThrown));
				points++;
			}
		}
	}

	// Gaussian Distribution - With the Centre removed with Chosen SD (Mean Always 0)
	public void randomGaussianNoCentre () {
		
		// Read StdDev
		readStdDevInput();
		// Read numOfPoints
		readPointInput();
		System.out.println("Generating " + numOfPoints + " co-ordinates within the range of -500 to 500. (Not Inluding Points in SD " + stdDev + ")");
		
		int pointCounter = 0;
		
		while (pointCounter != numOfPoints) {
				
			int Guassian = (int) Math.round(r.nextGaussian() * stdDev);
			int distThrown = (int) Math.sqrt(Guassian * Guassian);
				
			// Skips iteration if distance in in StdDev.
			if (distThrown <= stdDev) {
				continue;
			// Else adds point and increments
			} else {
				int angle = r.nextInt(360) + 1;
				int Ycoord = (int) Math.round(Math.sin(angle) * Guassian); // sin(angle).
				int Xcoord = (int) Math.round(Math.cos(angle) * Guassian); // cos(angle).
				coordinates.add(new Point(Xcoord, Ycoord, distThrown));
				pointCounter++;
				}
			}
	}
	
	/* User Enters a Bernoulli Probability (0 <= p <= 1.0)
	 * of landing in a chosen radius.
	 * (1 - p) of landing outside radius.
	 */
	public void probabiltySpectrum() {
		
		// Read radius input.
		readRadInput();
		
		// Read prob input.
		readProbInput();
		
		// Read numOfPoints.
		readPointInput();
		System.out.println("Generating " + numOfPoints + " random co-ordinates inside the radius " + radius + " with probability " + probability);
		
		int points = 0;
		
		while (points != numOfPoints) {
			
			//Generating bernoulli value (true / false).
			boolean bernoulli = StdRandom.bernoulli(probability);
		
				//Distributes point randomly with radius.
				if (bernoulli == true) {
					double randValue = r.nextDouble() * 2 - 1;
					int angle = r.nextInt(360) + 1;
					int dist = (int) Math.round(randValue * radius);
					int distThrown = (int) Math.sqrt(dist * dist);
					if (distThrown < 10) {
						continue;
					}
					int Ycoord = (int) Math.round(Math.sin(angle) * dist);
					int Xcoord = (int) Math.round(Math.cos(angle) * dist);
					coordinates.add(new Point(Xcoord, Ycoord, distThrown));
					points++;
			
				//Distributes point randomly outside radius.
				} else {
					int Xcoord = r.nextInt(500 + 1 + 500) - 500;
					int Ycoord = r.nextInt(500 + 1 + 500) - 500;
					int distThrown = (int) Math.round(Math.sqrt((Xcoord * Xcoord) + (Ycoord * Ycoord)));
			
						if (distThrown <= radius) {
							continue;
						} else {
							coordinates.add(new Point(Xcoord, Ycoord, distThrown));
							points++;
						}
				}	
			}
	}
	
	// Method promts user for specific weight distribution.	
	public void readWeightInput() {

		while (true) {
			try {
				System.out.println("Choose the Weight of Points");
				System.out.println("---------------------------");
				System.out.println("1.Random Weight");
				System.out.println("2.Power Law Weight");
				System.out.println("3.Inverse Weight");
				weightOption = in.nextInt();
				break;

			} catch (InputMismatchException ime) {

				System.out
						.println("Please enter a number from the following options");
				in.nextLine();
			}
		}
	}
	
	// Calls distribution depending on inputted weight distribution.
	public void startWeight() {
		
		switch (weightOption) {
		case 1:
			randomWeight();
			break;
		case 2:
			powerLaw();
			break;
		case 3:
			inverseWeight();
			break;
		default:
			System.out.print("Please enter a number from the following options");
		}
	}
	
	// Sets suitable parameters for (C and x) for powerLaw and InverseWeight.
	public void setParameters() {
		
		if (distOption == 3 && weightOption == 2) {
			constant = 1000;
			power = 1.02;
		} else if (distOption == 3 && weightOption == 3) {
			constant = 10;
			power = 1.3;
		} else if (distOption == 5 && weightOption == 2) {
			constant = 1000;
			power = 1.02;
		} else if (distOption == 5 && weightOption == 3) {
			constant = 10;
			power = 1.1;
		} else if (weightOption == 2) {
			power = 1.7;
			constant = 100000;
		} else if (weightOption == 3) {
			power = 1.5;
			constant = 100;
		} 
	}
	
	// Assigns random weight between 1 - 100 for each point.
	public void randomWeight() {

		for (Point point : coordinates) {
			double weight = (double) Math.round(1.00 + (100 - 1.00) * r.nextDouble() * 100) / 100;
			point.setWeight(weight);
		}
	}
	
	// Assigns powerLaw weight for each point.
	public void powerLaw() {

		for (Point point : coordinates) {
			int dist = point.getDistance();
			double weight = (double) Math.round(constant / Math.pow(dist, power) * 100) / 100;       
			point.setWeight(weight);
		}
	}

	// Assigns inverse weight for each point.
	public void inverseWeight() {

		for (Point point : coordinates) {
			int dist = point.getDistance();
			double weight = (double) Math.round((Math.pow(dist, power) / constant) * 100) / 100;    
			point.setWeight(weight);
		}
	}
	
	
	public void graphOption () {
		
		if (distOption == 1 || distOption == 2 || distOption == 5) {
			visGraphRan();
		} else {
			visGraphGaus();
		}	
	}
	
	// Visualises Rand/Equidistant/Bernouli Graphs
	public void visGraphRan() {
		
		double[] scale = {-625.00, 625.00}; 
		double[] axis = {-500, 0, 500, 0, 0, 500, 0, -500};
		
		InputGraph ran = new InputGraph(scale, axis, inputSpeed);
		ran.buildGraph(distOption, weightOption);
		ran.labelAxis(500);
		
		if (distOption == 5) {
			StdDraw.circle(0, 0, radius);
		}
		ran.plotPoints(coordinates, 500, distOption);
	}
	 
	// Visualises Gaus/GausNoCentre Graphs.
	public void visGraphGaus() {
		
		int scales = 3 * stdDev;
		int alter = scales / 4;
		
		double[] scale = {-(scales) - (alter), (scales) + (alter)};
		double[] axis = {-(scales), 0, (scales), 0, 0, (scales), 0, -(scales)};
		
		InputGraph guas = new InputGraph(scale, axis, inputSpeed);
		guas.buildGraph(distOption, weightOption);
		guas.labelAxis(scales);
		StdDraw.circle(0, 0, stdDev);
		guas.plotPoints(coordinates, stdDev * 3, distOption);
	}
	
	
	// Method asks the user for the number of points they wish to generate.
	public void readPointInput() {

		while (true) {
			try {

				System.out
						.println("How many points would you like to generate?");
				numOfPoints = in.nextInt();
				break;

			} catch (InputMismatchException ime) {

				System.out.println("Please enter an integer");
				in.nextLine();
			}
		}
	}
	
	
	// Method reads stdDev from user (50 - 150).
	public void readStdDevInput() {
		
		do {
			while (true) {
				try {

					System.out
						.println("What Standard Deviation would you like to Use? (50 - 150)");
					stdDev = in.nextInt();
					break;
				
				} catch (InputMismatchException ime) {

					System.out.println("Please Enter an Integer Between 50 and 150");
					in.nextLine();
				}
			}
		} while (stdDev < 50 || stdDev > 150);
		
		
	}
	
	// Method reads radius from user (for Probability distribution).
	public void readRadInput() {
		
		while (true) {
			try {
				System.out.println("What Radius Would you like to Choose?");
				radius = in.nextInt();
				break;
			
			} catch (InputMismatchException ime) { 
				
				System.out.println("Please Enter an Integer");
				in.nextLine();
			}
		}
	}
	
	// Method reads probability from user (for Probability distribution).
	public void readProbInput() {
	
		do {
			while (true) {
				try {

					System.out
						.println("What Probability to land in this radius (0 <= p <= 1.0)");
					probability = in.nextDouble();
					break;
				
				} catch (InputMismatchException ime) {

					System.out.println("Please Enter a value 0 <= p <= 1.0");
					in.nextLine();
				}
			}
		} while (probability < 0 || probability > 1);
	}
	
	// Method prints set of points.
	public void printPoints() {

		for (Point point : coordinates) {
			System.out.println(point);
		}
	}
	
	// Asks user for bound
	public void askBound() {
		
		while (true) {
			try {
				System.out.println("----------------------------------");
				System.out.println("Choose Logirithmic Bound");
				System.out.println("----------------------------------");
				System.out.println("Option 1. (Log 2)");                 
				System.out.println("Option 2. (Log 4)");   
				System.out.println("Option 3. (Log 1.5)");
				bound = in.nextInt();										 
				break;

			} catch (InputMismatchException ime) {

				System.out
						.println("Please enter a number from the following options");
				in.nextLine();
			}
		}
		
		if (bound == 1) {
			bound = 2;
		} else if (bound == 2) {
			bound = 4;
		} else {
			bound = 1.5;
		}
	}
	
	// Executes if ENTER is pressed after InputGraph.
	public void checkKey() { 
	    
		while (isPressed == false) {
		
			if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
				// Computes and Visualises LBs.
				Graph graph = new Graph();
				graph.computeScales(coordinates, 1);
				graph.visNodes(coordinates, dft, 0.06);
				double LB1 = graph.computeLB1(coordinates);
				graph.visLB1(coordinates);
				double LB2 = graph.checkKey(coordinates, lb2Speed);
				int diameter = graph.getFurthestDist() * 2;   // (2 * furthestDist) = diameter.
				// Starts dividing groups.
				GroupManager groups = new GroupManager(coordinates, numOfPoints, LB1, LB2, diameter, bound);
				groups.beginGroups();
				isPressed = true;
			}
		}
		isPressed = false;
	 }
}


