/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The MenuFireworks class is used to display the fireworks that occur after the timer
 *for the circles on the main menu reach 0. 
 */
package mainGame;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

// Start of class
// Is a subclass of GameObject 
public class MenuFireworks extends GameObject {

	// Instance variables 
	private Handler handler;
	private Random r;
	private double velX;
	private double x;
	private double y;
	private int sizeX;
	private int sizeY;
	private int max = 5;
	private int min = -5;
	private Color color;

	// Main constructor 
	public MenuFireworks(double x, double y, int sizeX, int sizeY, double velX, double velY, Color color, ID id,
			Handler handler) {
		// Takes variables from superclass GameObject
		super(x, y, id);
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		// Creates random colors 
		r = new Random();
		this.color = color;
	}

	// Methods 
	// Used to create the graphics 
	public void render(Graphics g) {
		// Set the oval as a random color 
		g.setColor(this.color);
		g.fillOval((int) this.x, (int) this.y, sizeX, sizeY);
	}

	// Used to define time in the game 
	public void tick() {
		// Speed for the fireworks
		this.x += velX;
		this.y += velY;

		// If circle reaches 100 pixels, create a firework 
		if (this.y <= 100) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.id == ID.Firework) {
					sparks(tempObject);
					// Remove fireworks after timer ends 
					handler.removeObject(tempObject);
				}
			}
		}
	}

	// Used to create the sparks of the firework 
	public void sparks(GameObject tempObject) {
		// Creates each individual spark 
		for (int ii = 0; ii < 3; ii++) {
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -5,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -4,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -3,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -2,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -1,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt(4) + 1), 0, this.color,
					ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, -(r.nextInt(4) + 1), 0, this.color,
					ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 1,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 2,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 3,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 4,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 5,
					this.color, ID.FireworkSpark, handler));
		}
	}

	// Used to get the bounds of the image 
	@Override
	public Rectangle getBounds() {
		return null;
	}
}