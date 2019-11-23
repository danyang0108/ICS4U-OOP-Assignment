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

public class Logarithm extends Function implements Calculations, Drawable {

	// variables for the equation y = a * ln(x - x1) + b
	protected double a;
	protected double b;
	protected double x1;

	// Constructor method for the logarithm function
	public Logarithm(double a, double b, double x1) {
		super(-100, 100); // default domain, changeable using setStartDomain and setEndDomain
		this.a = a;
		this.b = b;
		this.x1 = x1;
	}

	// Example of Static Polymorphism
	// Default Constructor method for the logarithm function
	public Logarithm() {
		super(-100, 100); // default domain, changeable using setStartDomain and setEndDomain
		this.a = 1;
		this.b = 0;
		this.x1 = 0;
	}

	// All of the methods below are an example of Dynamic Polymorphism

	// Returns a String that contains the equation of the actual function
	@Override
	public String toString() {

		String equation = "";

		// Handles cases for a
		// Doesn't print 0 and 1 as coefficient for the final equation
		if (a == 1.0)
			equation += "ln(x";
		else if (a == -1.0)
			equation += "-ln(x";
		else if (a == 0) {
			equation += b;
			return equation;
		} else
			equation += a + "*ln(x";

		// Handles cases for x1
		if (x1 > 0)
			equation += "-" + x1 + ")";
		else if (x1 == 0)
			equation += ")";
		else if (x1 < 0)
			equation += "+" + -x1 + ")";

		// Handles cases for b
		if (a != 0) {
			if (b > 0)
				equation += "+" + b;
			else if (b < 0)
				equation += "-" + -b;
		}

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
		return a * Math.log(x - x1) + b;
	}

	// Takes the x-coordinate as parameter, and checks if x is defined at that point
	@Override
	public boolean undefined(double x) {

		// undefined if the x value exceeds the domain of the function
		// or if you are using ln() for a non-positive number
		if (super.getStartDomain() <= x && super.getEndDomain() >= x && (x - x1) > 0) {
			return false;
		} else {
			return true;
		}
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