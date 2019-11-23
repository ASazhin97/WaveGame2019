package mainGame;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

// Start of class 
// Is a subclass of GameObject 
public class EasyEnemyBoss extends GameObject {

	// Instance variables
	private Handler handler;
	private int timer = 80;
	private int timer2 = 50;
	Random r = new Random();
	private Image img;
	private int spawn;

	// Main constructor 
	public EasyEnemyBoss(ID id, Handler handler) {
		// Takes variables from superclass GameObject 
		super(Game.WIDTH / 2 - 48, -120, id);
		this.handler = handler;
		// Sets the speed for the EnemyBoss
		velX = 0;
		velY = 2;
		// Sets the image of the EnemyBoss 
		img = getImage("images/EnemyBoss.png");
		// Sets the health for the boss
		this.health = 500;
	}
	
	// Methods
	// Used to define time in the game 
	public void tick() {
		// Using velocity to control how the enemy moves 
		this.x += velX;
		this.y += velY;

		// If the timer reaches 0, stop the game
		if (timer <= 0)
			velY = 0;
		else
			// Else continue to decrease the timer 
			timer--;
		// Calls the drawFirstBullet method to cause enemy to shoot 
		drawFirstBullet();
		// If timer reaches 0, reduce the health of the boss by 1 and start timer2
		if (timer <= 0)
			this.health -= 1;
			timer2--;
		// If timer2 reaches 0 and the horizontal velocity of the enemy is 0, increase velX to 8 
		if (timer2 <= 0) {
			if (velX == 0)
				velX = 8;
			// If enemy is still moving, keep spawning bullets 
			this.isMoving = true;
			spawn = r.nextInt(30);
			// If there are no enemy bullets on screen, spawn more enemy bullets 
			if (spawn == 0) {
				handler.addObject(
						new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler,5 + r.nextInt(25),100 + r.nextInt(500)));
			}
		}

		// Makes sure the enemy does not go through the game's bounds
		if (this.x <= 0 || this.x >= Game.WIDTH - 96)
			velX *= -1;
		}

	// Used to define the image of the EnemyBoss
	public Image getImage(String path) {
		Image image = null;
		// Tries to get image 
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		// If there is no image, print out an error message
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return image;
	}
	
	// Used to define the graphics for the background
	public void render(Graphics g) {
		// Creates the border between player and enemy 
		// Sets the color of the border
		g.setColor(Color.LIGHT_GRAY);
		// Draws a line 
		g.drawLine(0, 138, Game.WIDTH, 138);
		// Draws the boss image above the border 
		g.drawImage(img, (int) this.x, (int) this.y, 96, 96, null);

		// Creates the enemy's health bar
		// Sets the color of the health 
		g.setColor(Color.GRAY);
		// Sets the size of the health bar
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
		// Shows how much health is left
		g.setColor(Color.RED);
		// When the player attacks, decrease the health bar using the color white 
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, this.health, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
	}

	// Used to set image size of enemy 
	@Override 
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 96, 96);
	}

	// Used to create the enemy's attack 
	public void drawFirstBullet() {
		// If the last bullet is about to fade, spawn another enemy bullet 
		if (timer2 == 1)
			handler.addObject(new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler,10 + r.nextInt(25),100 + r.nextInt(500)));
	}
}
