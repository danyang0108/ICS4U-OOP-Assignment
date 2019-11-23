/*Author: Danyang Wang
 * Class: ICS4U
 * Instructor: Mr Radulovich
 * Date: October 18th, 2019
 * Assignment name: OOP Assignment
 * Description: This is the drawable interface for the six functions
 */
import javafx.scene.canvas.Canvas;

public interface Drawable 
{
	/**
	 * Draws the given function to the JavaFX graphics context (screen)
	 * @param gc -  the JavaFX Graphics Context to be drawn into
	 * @param f - function to be drawn
	 */
	public void draw(Canvas gc);

}