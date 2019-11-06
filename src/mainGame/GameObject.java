/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The GameObject class keeps track of all objects in the game and provides the basic 
 *outline for methods used in its subclasses. 
 */
package mainGame;

// Imports 
import java.awt.Graphics;
import java.awt.Rectangle;
import org.json.JSONException;

// Start of class 
public abstract class GameObject {

	// Instance variables 
	protected double x, y;
	protected ID id;
	protected double velX, velY;
	protected boolean isMoving;
	protected int health;

	// Main constructor 
	public GameObject(double x, double y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	// Methods
	// Uses abstract methods as a basic outline for subclasses 
	public abstract void tick() throws JSONException;
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

	// Used to get x position of an object 
	public double getX() {
		return x;
	}

	// Used to set x position of an object 
	public void setX(int x) {
		this.x = x;
	}

	// Used to get y position of an object 
	public double getY() {
		return y;
	}

	// Used to set y position of an object
	public void setY(int y) {
		this.y = y;
	}

	// Used to get ID of an object 
	public ID getId() {
		return id;
	}

	// Used to set ID of an object 
	public void setId(ID id) {
		this.id = id;
	}

	// Used to get the horizontal velocity of an object 
	public double getVelX() {
		return velX;
	}

	// Used to set the horizontal velocity of an object 
	public void setVelX(int velX) {
		this.velX = velX;
	}

	// Used to get the vertical velocity of an object 
	public double getVelY() {
		return velY;
	}

	// Used to set the vertical velocity of an object 
	public void setVelY(int velY) {
		this.velY = velY;
	}

	// Used to get the health of the player 
	public int getHealth() {
		return this.health;
	}
}