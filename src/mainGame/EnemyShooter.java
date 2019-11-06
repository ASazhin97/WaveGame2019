/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemyShooter class defines the Shooter enemy (yellow enemy) in the WaveGame and
 *includes setting the size, direction, and speed of the enemy.
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// Start of class
// Is a subclass of GameObject 
public class EnemyShooter extends GameObject {

	// Instance variables 
	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int timer;
	private GameObject player;
	private double bulletVelX;
	private double bulletVelY;
	private int bulletSpeed;

	// Main constructor 
	public EnemyShooter(double x, double y, int sizeX, int sizeY, int bulletSpeed, ID id, Handler handler) {
		// Takes variables from superclass GameObject 
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		// Sets how long the enemy Shooter is on screen 
		this.timer = 30;
		this.bulletSpeed = bulletSpeed;
		// Track the player's position to shoot at
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
		// Makes sure the enemy does not go through the game's bounds
		if (this.y <= 0 || this.y >= Game.HEIGHT - 40)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;
		// Adds a trail to the enemy
		handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, this.sizeX, this.sizeY, 0.025, this.handler));
		timer--;
		// When the timer reaches 0, fire another bullet and update amount of bullets on - screen 
		if (timer <= 0) {
			shoot();
			updateEnemy();
			// Reset the amount of time until next bullet 
			timer = 10;
		}
	}

	// Used to shoot bullets at the player 
	public void shoot() {
		// Track location of player 
		double diffX = this.x - player.getX() - 16;
		double diffY = this.y - player.getY() - 16;
		// Distance between bullet and player 
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		// Sets the speed of the bullet 
		bulletVelX = ((this.bulletSpeed / distance) * diffX);
		bulletVelY = ((this.bulletSpeed / distance) * diffY);
		// Creates a new bullet when method is called 
		handler.addObject(
				new EnemyShooterBullet(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler));
	}

	// Used to update how many bullets are on - screen 
	public void updateEnemy() {
		this.sizeX--;
		this.sizeY--;
		// When the bullet's time reaches 0, remove it from the enemy's array
		if (sizeX <= 0) {
			handler.removeObject(this);
		}
	}

	// Used to create the enemy 
	public void render(Graphics g) {
		// Sets the color 
		g.setColor(Color.yellow);
		g.fillRect((int) x, (int) y, this.sizeX, this.sizeY);
	}

	// Used to define the size of the enemy 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}
}