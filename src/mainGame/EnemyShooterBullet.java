/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemyShooterBullet class defines the bullet of the Shooter enemy (yellow enemy) in
 *the WaveGame. 
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// Start of class
// Is a subclass of GameObject 
public class EnemyShooterBullet extends GameObject {

	// Instance variables 
	private Handler handler;

	// Main constructor 
	public EnemyShooterBullet(double x, double y, double velX, double velY, ID id, Handler handler) {
		// Takes variables from superclass GameObject 
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
	}

	// Methods 
	// Used to define time in the game
	public void tick() {
		// Set the location of the bullet 
		this.x += velX;
		this.y += velY;
		// Add a trail to the bullet 
		if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -.5;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -.5;
		handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, 4, 4, 0.025, this.handler));
		removeBullets();
	}

	// Used to remove the bullet from the screen 
	public void removeBullets() {
		// Finds how many bullets are currently on the screen 
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			// If the bullet's timer is up, remove the bullet from the screen 
			if (tempObject.getId() == ID.EnemyShooterBullet) {
				if (tempObject.getX() >= Game.WIDTH || tempObject.getY() >= Game.HEIGHT) {
					handler.removeObject(tempObject);
				}
			}
		}
	}

	// Used to create the enemy 
	public void render(Graphics g) {
		// Sets the color
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, 4,4);
	}

	// Used to define the size of the enemy 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}
}