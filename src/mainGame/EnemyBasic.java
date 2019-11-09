/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemyBasic class defines the basic outline for each of the enemies in the game
 *including color, bounds, and graphics.
 */
package mainGame;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// Start of class
// Is a subclass of GameObject 
public class EnemyBasic extends GameObject {

	// Instance variable
	private Handler handler;

	// Main constructor 
	public EnemyBasic(double x, double y, int velX, int velY, ID id, Handler handler) {
		// Takes variables from superclass GameObject 
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
	}

	// Methods
	// Used to define time in the game 
	public void tick() {
		
		// Controls the speed of the enemy 
		this.x += velX;
		this.y += velY;

		// Makes sure the enemy does not go through the game's bounds
		if (this.y <= 0 || this.y >= Game.HEIGHT - 40)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;

		// Create a trail for the enemy that is red, 16 pixels long, 16 pixels high
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 16, 16, 0.025, this.handler));
	}

	// Used to define the graphics of the screen 
	public void render(Graphics g) {
		// Set the color of the enemy to red
		g.setColor(Color.red);
		// Set the shape of the enemy to a 16 px by 16 px rectangle 
		g.fillRect((int) x, (int) y, 16, 16);
	}

	// Used to set the size of the image 
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}
}