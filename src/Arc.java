/*Author: Danyang Wang
 * Class: ICS4U
 * Instructor: Mr Radulovich
 * Date: October 18th, 2019
 * Assignment name: OOP Assignment
 * Description: This class extends Function class, and it includes methods for 
 * calculation and drawable. 
 */
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Arc extends Function implements Calculations, Drawable {

	// variables for the equation y = sqrt(r^2 - (x - x1)^2) + y1
	protected double r;
	protected double xcenter;
	protected double ycenter;

	// Constructor method for the arc function
	public Arc(double r, double xcenter, double ycenter) {
		super(-100, 100); // default domain, changeable using setStartDomain and setEndDomain
		this.r = r;
		this.xcenter = xcenter;
		this.ycenter = ycenter;
	}

	// Example of Static Polymorphism
	// Default Constructor method for the arc function
	public Arc() {
		super(-100, 100); // default domain, changeable using setStartDomain and setEndDomain
		this.r = 1;
		this.xcenter = 0;
		this.x1 = 0;
	}

	// All of the methods below are an example of Dynamic Polymorphism

	// Returns a String that contains the equation of the actual function
	@Override
	public String toString() {

		String equation = "";

		equation += "sqrt(";

		// Handles cases for r
		// Doesn't print 0 as coefficient for the final equation
		if (r == 0)
			equation += "-";
		else
			equation += "(" + r + ")^2-";

		// Handles cases for xcenter
		if (xcenter == 0)
			equation += "(x)^2)";
		else if (xcenter > 0)
			equation += "(x-" + xcenter + ")^2)";
		else
			equation += "(x+" + -xcenter + ")^2)";

		// Handles cases for ycenter
		if (ycenter == 0)
			return equation;
		else if (ycenter > 0)
			equation += "+" + ycenter;
		else
			equation += "-" + -ycenter;

		return equation;

	}

	// Takes the x-coordinate as parameter, and returns the y value for the
	// corresponding x value
	@Override
	public double val(double x) {

		// returns NaN (not a number) if the x value is undefined
		if (undefined(x)) {
			System.out.println(x + "is not defined, therefore cannot calculate value");
			return Double.NaN;
		}

		// calculates the y value using the equation of the function
		double y = Math.sqrt(Math.pow(r, 2) - Math.pow(x - xcenter, 2)) + ycenter;
		return y;
	}

	// Takes the x-coordinate as parameter, and checks if x is defined at that point
	@Override
	public boolean undefined(double x) {

		// undefined if the x value exceeds the domain of the function
		// also if you are using sqrt() for a negative number
		if (super.getStartDomain() <= x && super.getEndDomain() >= x
				&& Math.pow(r, 2) - Math.pow(x - xcenter, 2) >= 0) {
			return false;
		} else {
			return true;
		}
	}

	// Takes an interval as parameter, and calculates the area of the graph for that
	// interval
	@Override
	public double getArea(double x_start, double x_end) {
		int i = 0;

		// splits the graph into this many thin rectangles
		// area becomes more accurate as you increase the number of rectangles
		int rectangles = 10000;
		double deltaX = (x_end - x_start) / rectangles;
		double Area = 0;
		for (double x = x_start; x <= x_end; x = x_start + i * deltaX) {
			// if x is undefined, the program will print a message
			// it will simply not include the parts that are undefined when calculating the
			// area
			if (undefined(x)) {
				System.out.println(x + "is not defined, therefore not included in area calculation");
			}

			// if x is defined, the program adds the area of the rectangle to the Area
			// variable
			else {
				Area += val(x) * deltaX;
			}
			i++;
		}
		return Area;
	}

	// Takes the x-coordinate as parameter, and calculates the slope at that point
	@Override
	public double getSlope(double x) {
		double slope;

		// The smaller deltaX is, the more accurate the slope will be
		double deltaX = 1e-9;
		// Checks if x + deltaX and x - deltaX are defined, otherwise returns NaN (not a
		// number)
		if (undefined(x + deltaX)) {
			System.out.println("x + deltaX is not defined");
			return Double.NaN;
		} else if (undefined(x - deltaX)) {
			System.out.println("x - deltaX is not defined");
			return Double.NaN;
		}
		// calculating the slope
		else {
			slope = (val(x + deltaX) - val(x - deltaX)) / (2.0 * deltaX);
		}
		return slope;
	}

	// Takes the canvas as parameter, and draws the function on the canvas
	@Override
	public void draw(Canvas canvas) {

		double xStart = super.getStartDomain(); // starting x value
		double xEnd = super.getEndDomain(); // ending x value
		double deltaX = 0.01; // draws 100 points for every 1.0 interval of x
		double windowHeight = canvas.getHeight(); // y-value
		double windowWidth = canvas.getWidth(); // x-value
		int i = 0;
		double curX = xStart + i * deltaX, curY; // current x and y values
		double prevX;
		double minY = 1e9, maxY = -1e9; // minimum and maximum y value
		int lShift = 15, dShift = 30; // shifts to make all the labels fit on screen

		// storing the coordinates
		ArrayList<Double> xCoord = new ArrayList<Double>();
		ArrayList<Double> yCoord = new ArrayList<Double>();

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// axis
		gc.strokeLine(0, windowHeight / 2.0, windowWidth, windowHeight / 2);
		gc.strokeLine(windowWidth / 2, 0, windowWidth / 2, windowHeight);

		// draws function with the selected colour
		gc.setStroke(super.getColour());

		// this loop calculates the x and y coordinates of the function

		while (curX <= xEnd) {
			prevX = curX;

			// Doesn't draw the point if curX is undefined
			if (undefined(curX)) {
				System.out.println("X is undefined at " + curX);
				curX = xStart + i * deltaX;
				i++;
				continue;
			}

			// updates current x and y coordinates
			curX = xStart + i * deltaX;
			curY = -val(prevX) + windowHeight / 2.0;

			// stores coordinates
			xCoord.add(prevX - xStart);
			yCoord.add(curY - windowHeight / 2.0);

			// finds maximum and minimum y value
			if (curY - windowHeight / 2.0 < minY)
				minY = curY - windowHeight / 2.0;
			if (curY - windowHeight / 2.0 > maxY)
				maxY = curY - windowHeight / 2.0;

			i++;
		}

		// calculates scale factors
		double xScaleFactor = windowWidth / (xEnd - xStart);
		double yScaleFactor = windowHeight / (maxY - minY);

		// draws the function on the canvas
		for (int j = 1; j < xCoord.size(); j++) {
			gc.strokeLine(xCoord.get(j - 1) * xScaleFactor, (yCoord.get(j - 1) - minY) * yScaleFactor,
					xCoord.get(j) * xScaleFactor, (yCoord.get(j) - minY) * yScaleFactor);

		}

		int numOfLabels = 5; // number of labels that display the coordinates
		double xIncrement = (xEnd - xStart) / (numOfLabels - 1);
		double yIncrement = (maxY - minY) / (numOfLabels - 1);

		gc.setStroke(Color.BLACK);

		// this for loop draws the labels that display the coordinates
		for (int j = 0; j < numOfLabels; j++) {
			String xLabel = Double.toString(xStart + j * xIncrement);
			String yLabel = Double.toString(-(minY + j * yIncrement));
			if (yLabel.equals("-0.0")) yLabel = "0.0";
			if (j == 0) {
				gc.strokeText(xLabel, j * xIncrement * xScaleFactor,
						windowHeight / 2.0 + lShift);
				gc.strokeText(yLabel, windowWidth / 2.0 - dShift,
						j * yIncrement * yScaleFactor + lShift);
			} else if (j == numOfLabels - 1) {
				gc.strokeText(xLabel, j * xIncrement * xScaleFactor 
						- dShift, windowHeight / 2.0 + lShift);
				gc.strokeText(yLabel, windowWidth / 2.0 - dShift,
						j * yIncrement * yScaleFactor);
			} else {
				gc.strokeText(xLabel, j * xIncrement * xScaleFactor,
						windowHeight / 2.0 + lShift);
				gc.strokeText(yLabel, windowWidth / 2.0 - dShift,
						j * yIncrement * yScaleFactor);

			}
		}
	}
}
