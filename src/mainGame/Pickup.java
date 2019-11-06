/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Pickup class allows the user to pick up abilities and power - ups during 
 *gameplay and is a basic outline for the PickupCoin and PickupHealth classes.
 */
package mainGame;

// Imports 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

// Start of class 
public abstract class Pickup {

	// Instance variables 
	protected double x;
	protected double y;
	protected ID id;
	protected String path;
	protected Image img;

	// Main constructor 
	public Pickup(double x, double y, ID id, String path) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.path = path;
		this.img = setImg(this.path);

	}

	// Methods
	// Used to get images 
	public Image setImg(String path) {
		Image img = null;
		// Try to find the image 
		try {
			URL imageURL = Game.class.getResource(path);
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		// If the image cannot be found, throw an exception 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	// Used to define time in the game 
	public abstract void tick();
	// Used to create graphics 
	public abstract void render(Graphics g);
	// Used to set bounds for images 
	public abstract Rectangle getBounds();
	// Used to get the value of x
	public double getX() {
		return x;
	}
	// Used to set the value of x 
	public void setX(double x) {
		this.x = x;
	}
	// Used to get the value of y 
	public double getY() {
		return y;
	}
	// Used to set the value of y 
	public void setY(double y) {
		this.y = y;
	}
	// Used to get the ID of an object 
	public ID getId() {
		return id;
	}
	// Used to set the ID of an object 
	public void setId(ID id) {
		this.id = id;
	}
}