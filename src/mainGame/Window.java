/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Window class creates the game window and provides the proper dimensions. 
 */
package mainGame;

// Imports 
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

// Start of class
// Is a subclass of Canvas 
public class Window extends Canvas{

	// Instance variables 
	private static final long serialVersionUID = 1L;
	
	// Main constructor 
	public Window(int width, int height, String title, Game game){
		// Sets a title for the window 
		JFrame frame = new JFrame(title);
		// Sets the size of the screen 
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		// Creates the exit button on the window 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		// Start the game when the game opens 
		game.start();	
	}
}