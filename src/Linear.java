/*Author: Danyang Wang
 * Class: ICS4U
 * Instructor: Mr Radulovich
 * Date: October 18th, 2019
 * Assignment name: OOP Assignment
 * Description: This class extends Quadratic class, and it inherits all the methods from the parent 
 * class
 */
public class Linear extends Quadratic implements Calculations, Drawable {

	// variables for the equation y = m(x - x1) + b
	protected double m;
	protected double x1;
	protected double b;

	// Constructor method for the linear function
	public Linear(double m, double b, double x1) {
		super(0, m, b, x1); // linear function is a quadratic function with a = 0
		this.m = m;
		this.b = b;
		this.x1 = x1;
	}

	// Example of Static Polymorphism
	// Default Constructor method for the linear function
	public Linear() {
		super(0, 1, 0, 0); 
		this.m = 1;
		this.b = 0;
		this.x1 = 0;
	}

}
