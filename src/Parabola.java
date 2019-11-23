/*Author: Danyang Wang
 * Class: ICS4U
 * Instructor: Mr Radulovich
 * Date: October 18th, 2019
 * Assignment name: OOP Assignment
 * Description: This class extends Quadratic class, and it inherits all the methods from the parent 
 * class
 */
public class Parabola extends Quadratic implements Calculations, Drawable {

	// variables for the equation y = a(x - x1)^2 + b
	protected double a;
	protected double b;
	protected double x1;

	// Constructor method for the parabola function
	public Parabola(double a, double b, double x1) {
		super(a, 0, b, x1);
		this.a = a;
		this.b = b;
		this.x1 = x1;
	}

	// Example of Static Polymorphism
	// Default Constructor method for the parabola function
	public Parabola() {
		super(1, 0, 0, 0);
		this.a = 1;
		this.b = 0;
		this.x1 = 0;
	}

}
