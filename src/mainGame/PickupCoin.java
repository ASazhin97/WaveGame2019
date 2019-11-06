/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The PickupCoin class allows the player to pick up coins.
 */
package mainGame;

// Imports 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

// Start of class 
// Is a subclass of PickUp 
public class PickupCoin extends Pickup {
	public PickupCoin(double x, double y, ID id, String path, Handler handler, Game game ) {
		// Takes variables from the superclass 
		super(x, y, id, path);
		this.img = getImage("images/PickupCoin.png");
	}

	// Methods
	// Used to define time in the game 
	public void tick() {
	}

	// Used to create coin graphics 
	public void render(Graphics g) {
		g.drawImage(this.img, (int)super.getX(), (int)super.getY(), 50, 50, null);
	}

	// Used to set the bounds of the image 
	public Rectangle getBounds() {
		return new Rectangle((int)super.getX(), (int)super.getY(), 50,  50);
	}
	
	// Used to get the image 
	public Image getImage(String path) {
		Image image = null;
		// Try to get the image 
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		// If image is not found, throw an exception 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	// Used to get the value of x 
	public double getX() {
		return x;
	}
	
	// Used to get the value of y 
	public double getY() {
		return y;
	}
}