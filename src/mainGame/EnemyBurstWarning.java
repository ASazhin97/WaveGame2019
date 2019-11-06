/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemyBurstWarning class defines the warning of the Burst Enemy (orange enemy) in 
 *the WaveGame. 
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// Start of class
// Is a subclass of GameObject 
public class EnemyBurstWarning extends GameObject {

	// Instance variables
	private Handler handler;
	private int width;
	private int height;
	private int timer;
	private Color color;
	private int hasFlashed;

	// Main constructor 
	public EnemyBurstWarning(double x, double y, int width, int height, ID id, Handler handler) {
		// Takes variables from superclass GameObject 
		super(x, y, id);
		this.handler = handler;
		this.width = width;
		this.height = height;
		// How long each burst lasts for 
		timer = 10;
		// Sets the color of the burst warning 
		this.color = Color.orange;
		// Initial value of the number of flashes 
		this.hasFlashed = 0;
	}

	// Methods 
	// Used to define time in the game 
	public void tick() {
		// Give a warning and check the amount of warnings given after each millisecond 
		flash();
		checkFlash();
	}

	// Used to flash a warning to the player 
	public void flash() {
		timer--;
		// When the timer reaches 5 milliseconds, do not flash more warnings
		if (timer == 5) {
			this.color = Color.black;
		// Else if the timer is 0, flash the warning and add to the hasFlashed variable
		} else if (timer == 0) {
			this.color = Color.orange;
			this.hasFlashed++;
			// Reset the timer after each warning 
			timer = 10;
		}
	}

	// Used to keep track of the number of flashes 
	public void checkFlash() {
		// If the warning has flashed five times, stop the burst warning 
		if (this.hasFlashed == 5) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getId() == ID.EnemyBurstWarning) {
					handler.removeObject(tempObject);
					i--;
				}
			}
		}
	}

	// Used to create the burst enemy warning 
	public void render(Graphics g) {
		// Sets the color
		g.setColor(this.color);
		g.fillRect((int) x, (int) y, this.width, this.height);
	}

	// Used to define the size of the warning 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}
}