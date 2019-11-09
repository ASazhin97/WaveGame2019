/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemySweep class defines the Sweep enemy (cyan enemy) in the WaveGame and
 *includes setting the size, direction, and speed of the enemy.
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// Start of class
// Is a subclass of GameObject 
public class EnemySweep extends GameObject {

	// Instance variable
	private Handler handler;

	// Main constructor 
	public EnemySweep(double x, double y, double velX, double velY, ID id, Handler handler) {
		// Takes variables from superclass GameObject
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
	}

	// Methods 
	// Used to define time in the game
	public void tick() {
		// Set the location of the enemy 
		this.x += velX;
		this.y += velY;
		// Makes sure the enemy does not go through the game's bounds
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;
		// Adds a trail to the enemy 
		handler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, 0.025, this.handler));
	}

	// Used to create the enemy 
	public void render(Graphics g) {
		// Sets the color 
		g.setColor(Color.cyan);
		g.fillRect((int) x, (int) y, 16, 16);
	}

	// Used to define the size of the enemy 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}
}