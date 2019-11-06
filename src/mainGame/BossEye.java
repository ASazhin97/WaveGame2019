/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The BossEye class defines the final boss of the WaveGame including design,
 *attack, and speed.
 */

// Package
package mainGame;

// Imports
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;
import org.json.JSONException;
import mainGame.Game.STATE;

// Start of class
// Is a subclass of GameObject 
public class BossEye extends GameObject {

	// Instance variables
	private Image img;
	private Random r = new Random();;
	private float alpha = 0;
	private double life = 0.005;
	private int tempCounter = 0;
	private int timer;
	private int spawnOrder = 1;
	private int placement;
	private double speed;
	private double[] speedTypes = { -5, -6, -7, -8, -9 };
	private GameObject player;
	private Handler handler;
	private int timeCounter = 0;
	private Game game;
	private int velX1 = 4;
	private int velY1 = 11;
	private int velX2 = 11;
	private int velY2 = 4;
	private int velY3 = 7;
	private int velX3 = 7;
	private int velX4 = (int)(Math.random() * 10);
	private int velY4 = (int)(Math.random() * 10);
	private int stopTime = 2360;
	
	// Main constructor 
	public BossEye(Game game, double x, double y, ID id, Handler handler, int placement) {
		// Takes variables from superclass GameObject 
		super(x, y, id);
		// Defines boss image 
		this.img = getImage("images/bosseye.png");
		this.velX = 0;
		this.velY = 0;
		// Defines the speed boss moves at 
		this.speed = speedTypes[r.nextInt(4)];
		this.handler = handler;
		this.placement = placement;
		// Defines how long the boss stays on screen 
		this.timer = 200;
		this.game = game;
	}

	// Methods
	// Used to define time in the game 
	public void tick() throws JSONException {
		// Keeps track of in - game time 
		timeCounter++;
		// If the time is greater or equal than the designated stop time, end the game
		if(timeCounter >= stopTime){
			// If the player lost, end the game
			if(game.gameState == STATE.GameOver){
			} else {
				// Else if the player won, end the time counter and record the player's score 
				game.gameState = STATE.GameWon;
				if(timeCounter == 0){
					game.getGameOver().sendScore();
				}
				// Clears the screen and resets the score 
				handler.clearPlayer();
				alpha *= 0;
			}
		}
		
		// If temporary timer reaches 0
		if (tempCounter == 0) {
			// If the eye health is less than value, add 0.001 to the health 
			if (alpha < 0.995) {
				alpha += life + 0.001;
			} else {
				// Else keep increasing the timer and keep attacking the player 
				tempCounter++;
				for (int i = 0; i < handler.object.size(); i++) {
					if (handler.object.get(i).getId() == ID.Player)
						this.player = handler.object.get(i);
				}
			}
		// Else change spawnOrder depending on the state on the temporary counter
		} else if (tempCounter == 1) {
			spawn();
			if (this.placement == 1 && this.spawnOrder >= 1) {
				attackPlayer();
			} else if (this.placement == 2 && this.spawnOrder >= 2) {
				bounceAtAngle();
			} else if (this.placement == 3 && this.spawnOrder >= 3) {
				bounceAtRandomAngle();
			} else if (this.placement == 4 && this.spawnOrder >= 4) {
				bounceAtAngle3();
			} else if (this.placement == 5 && this.spawnOrder >= 5) {
				bounceAtAngle();
			} else if (this.placement == 6 && this.spawnOrder >= 6) {
				bounceAtAngle2();
			} else if (this.placement == 7 && this.spawnOrder >= 7) {
				bounceAtRandomAngle();
			} else if (this.placement == 8 && this.spawnOrder >= 8) {
				bounceAtAngle3();
			} else if (this.placement == 9 && this.spawnOrder >= 9) {
				bounceAtRandomAngle();
			} else {
				// When the health reaches 0, enemy is defeated
				this.health = 0;
			}
		}
	}
	// Used to spawn enemies
	public void spawn() {
		// Decreases the amount of time the enemy is on screen 
		timer--;
		// If timer reaches 0, spawn another enemy that lasts for 200 milliseconds
		if (timer == 0) {
			this.spawnOrder++;
			timer = 200;
		}
	}

	// Used for the eye that follows the player 
	public void attackPlayer() {	
		// If player is still on - screen, get the position of player and follow  
		if (player != null) {
			double diffY = (this.y - player.getY());
			double diffX = (this.x - player.getX());
			// Calculated distance between player and enemy 
			double distance = Math.sqrt(((this.x - this.player.getX()) * (this.x - this.player.getX()))
					+ ((this.y - this.player.getY()) * (this.y - this.player.getY())));
			// Controls the speed of the enemy 
			this.velX = (this.speed / distance) * diffX;
			this.velY = (this.speed / distance) * diffY;
			// If the time counter is greater than the stop timer, reduce enemy's speed to 0 
			if(timeCounter >= stopTime){
				velX *= 0;
				velY *= 0;
			}
			
			// Controls the position and speed of the enemy 
			this.x += this.velX;
			this.y += this.velY;
		}
	}
	
	// Used to control the horizontal bounce of the enemy 
	public void bounceAtAngle() {
		// If the time counter is greater than or equal to the stop timer, enemy's bounce is 0
		if(timeCounter >= stopTime){
			velX1 *= 0;
			velY1 *= 0;
		}
		
		// Controls the bounce and bounce speed of the enemy
		this.x += velX1;
		this.y += velY1;
		
		// Makes sure the enemy does not go through the game's bounds
		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			velY1 *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			velX1 *= -1;
		}
	}
	
	// Used to control the vertical bounce angle of the enemy 
	public void bounceAtAngle2 (){
		// If the time counter is greater than or equal to the stop timer, enemy's bounce is 0
		if(timeCounter >= stopTime){
			velX2 *= 0;
			velY2 *= 0;
		}
		
		// Controls the bounce and bounce speed of the enemy
		this.x += velX2;
		this.y += velY2;
		
		// Makes sure the enemy does not go through the game's bounds
		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			velY2 *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			velX2 *= -1;
		}
	}
	
	// Used to control the neutral bounce of the enemy 
	public void bounceAtAngle3(){
		// If the time counter is greater than or equal to the stop timer, enemy's bounce is 0
		if(timeCounter >= stopTime){
			velX3 *= 0;
			velY3 *= 0;
		}
		
		// Controls the bounce and bounce speed of the enemy
		this.x += velX3;
		this.y += velY3;

		// Makes sure the enemy does not go through the game's bounds
		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			velY3 *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			velX3 *= -1;
		}
	}
	
	// Changes the direction of the enemy's bounce 
	public void bounceAtRandomAngle(){
		// If the time counter is greater than or equal to the stop timer, enemy's bounce is 0
		if(timeCounter >= stopTime){
			velX4 *= 0;
			velY4 *= 0;
		}
		
		// Controls the bounce and bounce speed of the enemy
		this.x += velX4;
		this.y += velY4;
		
		// As the timer counter reaches a multiple of 10, multiply the speed of the enemy by a
		// random integer
		if (timeCounter % 60 == 0){
			
			double negator1 = Math.random();
			double negator2 = Math.random();
			
			velX4 = (int)(Math.random() * 10);
			// If the random integer for the negator is less than 0.5, set the enemy's bounce to -1
			if (negator1 < .5){
				velX4 *= -1;
			}
			
			velY4 = (int)(Math.random() * 10);
			if (negator2 < .5) {
				velY4 *= -1;
			}
			
		}
		
		// Makes sure the enemy does not go through the game's bounds
		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			
			velY4  *= -1;
		}
		
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			
			velX4  *= -1;
		}
	}
	
	// Used to define the graphics of the screen 
	public void render(Graphics g) {
		// If the color of the background is black, change the color of the screen to green so it
		// doesn't conflict with the "Game Over" text
		if (g.getColor() == Color.BLACK) {
			g.setColor(Color.GREEN);
		}
		// Set the boss image to transparent to clear the screen when the player wins 
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.drawImage(img, (int) this.x, (int) this.y, null);
		g2d.setComposite(makeTransparent(1));
	}
	
	// Used to clear the screen 
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	// Used to set the size of the boss image 
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, (int) this.img.getWidth(null), (int) this.img.getHeight(null));
	}

	// Sets the boss image 
	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			// If the image doesn't exist, print out the error
			e.printStackTrace();
		}
		return image;
	}
}
