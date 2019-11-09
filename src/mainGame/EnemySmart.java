/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemySmart class defines the Smart enemy (green enemy) in the WaveGame and
 *includes setting the size, direction, and speed of the enemy.
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// Start of class
// Is a subclass of GameObject 
public class EnemySmart extends GameObject {

	// Instance variables 
	private Handler handler;
	private GameObject player;
	private int speed;

	// Main constructor 
	public EnemySmart(double x, double y, int speed, ID id, Handler handler) {
		// Takes variables from superclass GameObject
		super(x, y, id);
		this.handler = handler;
		this.speed = speed;
		// Keeps track of the amount of enemies on - screen 
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
	}

	// Methods 
	// Used to define time in the game
	public void tick() {
		// Set the location of the enemy 
		this.x += velX;
		this.y += velY;
		// Gets the location of the player 
		double diffX = this.x - player.getX() - 16;
		double diffY = this.y - player.getY() - 16;
		// Keeps track of distance between the enemy and the player 
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		// Sets the speed of the enemy
		velX = ((this.speed / distance) * diffX);
		velY = ((this.speed / distance) * diffY);
		// Adds a trail to the enemy 
		handler.addObject(new Trail(x, y, ID.Trail, Color.green, 16, 16, 0.025, this.handler));
	}

	// Used to create the enemy 
	public void render(Graphics g) {
		// Sets the color 
		g.setColor(Color.green);
		g.fillRect((int) x, (int) y, 16, 16);
	}

	// Used to define the size of the enemy 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}
}