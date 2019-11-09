/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The PickupHealth class is used to add health to the player when the user collects 
 *the health. 
 */
package mainGame;

// Imports 
import java.awt.Graphics;
import java.awt.Rectangle;

// Start of class
// Is a subclass of Pickup 
public class PickupHealth extends Pickup{
	
	// Main constructor 
	public PickupHealth(double x, double y, ID id, String path, Handler handler) {
		// Takes variables from the superclass 
		super(x, y, id, path);
	}

	// Methods
	// Used to define time in the game 
	public void tick() {
	}

	// Used to create coin graphics 
	public void render(Graphics g) {
		g.drawImage(this.img, (int)this.x, (int)this.y, 16, 16, null);
	}

	// Used to set the bounds of the image 
	public Rectangle getBounds() {
		return null;
	}
}