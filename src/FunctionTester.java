/*Author: Danyang Wang
 * Class: ICS4U
 * Instructor: Mr Radulovich
 * Date: October 18th, 2019
 * Assignment name: OOP Assignment
 * Description: This class tests the six functions by drawing the functions on screen with javafx
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class FunctionTester extends Application {

	public static void main(String[] args) {
		launch(args);

	}
	private double screenX = 600;
	private double screenY = 600;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Drawing Functions Test");
		Group root = new Group();
		Canvas canvas = new Canvas(screenX, screenY);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		Quadratic q = new Quadratic(3, 7, 4, 0);
		//System.out.println(q.getSlope(10));
		//System.out.println(q.getArea(-7, 6));
		Parabola p = new Parabola(4,2,7);
		//System.out.println(p.getSlope(8));
		//System.out.println(p.getArea(3, 10));
		Cubic c = new Cubic (1,0,0,0,0);
		//System.out.println(c.getSlope(1));
		Linear linear = new Linear(1,0,0);
		//System.out.println(linear.getSlope(7));
		//System.out.println(linear.getArea(-5, 5));
		c.setColour(Color.BLUE);
		c.draw(canvas);
		c.setDomain(-100, 100);
		//c.draw(canvas);
		//q.setDomain(-10,10);
		Arc a = new Arc(10,0,0);
		//a.setDomain(2, 4);
		//System.out.println(a.getArea(-13,-12));
		a.setDomain(-100,50);
		//a.setColour(Color.RED);
		//a.draw(canvas);
		
		Logarithm l = new Logarithm(4,2,3);
		l.setDomain(-4,23);
		//System.out.println(l.getSlope(7));
		//System.out.println(l.getArea(4,7));
		l.setColour(Color.RED);
		//l.draw(canvas);
		
		
		
		//q.draw(canvas);
		//drawShapes(gc);
		
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

	}

}