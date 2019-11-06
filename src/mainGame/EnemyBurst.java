/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemyBurst class defines the Burst enemy (orange enemy) in the WaveGame and
 *includes setting the size, direction, and speed of the enemy.
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

// Start of class
// Is a subclass of GameObject 
public class EnemyBurst extends GameObject {

	// Instance variables 
	private Handler handler;
	private int timer;
	private int size;
	private String side;
	private Random r = new Random();

	// Main constructor 
	public EnemyBurst(double x, double y, double velX, double velY, int size, String side, ID id, Handler handler) {
		// Takes variables from superclass GameObject 
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		// Sets how many milliseconds the enemy stays on screen 
		this.timer = 60; 
		this.side = side;
		this.size = size;
		// If the enemy is coming from the left, send a burst warning to the left of the screen 
		if (this.side.equals("left")) {
			handler.object.add(new EnemyBurstWarning(0, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		// If the enemy is coming from the right, send a burst warning to the right of the screen 
		} else if (this.side.equals("right")) {
			handler.object.add(new EnemyBurstWarning(Game.WIDTH - 45, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		// If the enemy is coming from the top, send a burst warning to the top of the screen 
		} else if (this.side.equals("top")) {
			handler.object.add(new EnemyBurstWarning(0, 0, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		// If the enemy is coming from the bottom, send a burst warning to the bottom of the screen 
		} else if (this.side.equals("bottom")) {
			handler.object
					.add(new EnemyBurstWarning(0, Game.HEIGHT - 85, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		}
	}

	// Methods 
	// Used to define time in the game 
	public void tick() {
		// Sets the trail of the enemy 
		handler.addObject(new Trail(x, y, ID.Trail, Color.orange, this.size, this.size, 0.025, this.handler));
		timer--;
		// When the timer of the enemy reaches 0, set the location of the next enemy 
		if (timer <= 0) {
			// Move the enemy based on its speed 
			this.x += velX;
			this.y += velY;
		}
	}

	// Used to set the position of the enemy 
	public void setPos() {
		// If enemy is coming from the left, randomly set the location on the left of the screen 
		if (this.side.equals("left")) {
			this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;
		// If enemy is coming from the right, randomly set the location on the right of the screen 
		} else if (this.side.equals("right")) {
			this.x = Game.WIDTH + 200;
			this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;
		// If enemy is coming from the top, randomly set the location on the top of the screen 
		} else if (this.side.equals("top")) {
			this.y = -(size);
			this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;
		// If enemy is coming from the bottom, randomly set the location on the bottom of the screen 
		} else if (this.side.equals("bottom")) {
			this.y = Game.HEIGHT + 200;
			this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;
		}
	}

	// Used to set the speed of the enemy
	public void setVel() {
		// If the enemy is coming from the left, move the enemy from left to right
		if (this.side.equals("left")) {
			this.velY = 0;
			// If the enemy is coming from the right, move the enemy from right to left
		} else if (this.side.equals("right")) {
			this.velX = -(this.velX);
			this.velY = 0;
		// If the enemy is coming from the top, move the enemy from top to bottom
		} else if (this.side.equals("top")) {
			this.velX = 0;
		// If the enemy is coming from the bottom, move the enemy from bottom to top
		} else if (this.side.equals("bottom")) {
			this.velX = 0;
			this.velY = -(this.velY);
		}
	}
	
	// Used to define the enemy 
	public void render(Graphics g) {
		// Set the color of the enemy 
		g.setColor(Color.orange);
		g.fillRect((int) x, (int) y, this.size, this.size);
	}

	// Used to define the size of the enemy 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 200, 200);
	}
}
