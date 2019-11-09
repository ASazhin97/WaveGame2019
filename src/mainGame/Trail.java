/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Trail path creates the trail that follow the player. 
 */

package mainGame;

// Imports 
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

// Start of class
// Is a subclass of GameObject 
public class Trail extends GameObject{
	
	// Instance variables 
	private float alpha = 1;
	private Handler handler;
	private Color color;
	private int width, height;
	private double life;	
	
	// Main constructor 
	public Trail(double x, double y, ID id, Color color, int width, int height, double life, Handler handler) {
		// Takes variables from the superclass 
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
	}

	// Methods
	// Used to define the time in the game 
	public void tick() {
		// If the player gets attacked, decrease the trial by 1 pixel 
		if (alpha > life){
			alpha -= life - 0.001;
		}
		else handler.removeObject(this);
	}

	// Used to create the graphics
	public void render(Graphics g) {
		// Sets the color of the trail and its size 
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(makeTransparent(alpha));
		g.setColor(color);
		g.fillRect((int)this.x, (int)this.y, this.width, this.height);
		// Allows rectangles to look like they are fading 
		g2d.setComposite(makeTransparent(1));
	}
	
	// Used to make the rectangles of the trail fade away 
	private AlphaComposite makeTransparent(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	// Used to get bounds of the trail 
	public Rectangle getBounds() {
		return null;
	}
}