/*Author: Danyang Wang
 * Class: ICS4U
 * Instructor: Mr Radulovich
 * Date: October 18th, 2019
 * Assignment name: OOP Assignment
 * Description: This class extends Cubic class, and it inherits all the methods from the parent 
 * class
 */

public class Quadratic extends Cubic implements Calculations, Drawable {
	
	// variables for the equation y = a(x - x1)^2 + b(x - x1) + c
	protected double a;
	protected double b;
	protected double c;
	protected double x1;
	
	// Constructor method for the quadratic function
	public Quadratic (double a, double b, double c, double x1) {
		super(0, a, b, c, x1);
		this.a = a;
		this.b = b;
		this.c = c;
		this.x1 = x1;
	}
	
	// Example of Static Polymorphism
	// Default Constructor method for the quadratic function
	public Quadratic() {
		super(0, 1, 0, 0, 0); 
		this.a = 1;
		this.b = 0;
		this.c = 0;
		this.d = 0;
	}

}
