/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The EnemyBossBullet class creates the bullets used by the first boss in the WaveGame.
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

// Start of class
// Is a subclass of GameObject 
public class EnemyBossBullet extends GameObject {

	// Instance variables
	private Handler handler;
	Random r = new Random();
	private int max = 45;
	private int min = -45;
	private int sizeX = 16;
	private int sizeY = 16;
	private int ticksToExplode;
	private int explosionSize;
	private boolean isGrowing = true;
	
	// Main constructor 
	public EnemyBossBullet(double x, double y, ID id, Handler handler, int _ticksToExplode, int _explosionSize) {
		// Takes variables from superclass GameObject 
		super(x, y, id);
		this.handler = handler;
		// Sets the velocity and angle of the bullet randomly
		velX = (r.nextInt((max - min) + 1) + min);
		velY = 30;
		ticksToExplode = _ticksToExplode;
		explosionSize = _explosionSize;
	}

	// Methods
	// Used to define time in the game 
	public void tick() {
		// Set the location of the bullet 
		this.x += velX;
		this.y += velY;
		// Time until bullet explodes
		ticksToExplode --;
		// When bullet timer reaches 0, cause explosion 
		if (ticksToExplode <= 0){
			explode();
		}
		// If the bullet goes above the height of the window, remove it from the game  
		if (this.y >= Game.HEIGHT)
			handler.removeObject(this);
	}

	// Used to create the bullet graphics 
	public void render(Graphics g) {
		// Set the color of the bullet 
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, this.sizeX, this.sizeY);
	}

	// Used to define the size of bullet 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}
	
	// Used to make the bullet explode 
	public void explode(){
		this.velX = 0;
		this.velY = 0;
		
		// If the bullet already exploded, keep decreasing the size of the explosion  
		if (isGrowing){
			sizeX += (int)(explosionSize/100);
			x -= (int)(explosionSize/200);
			sizeY += (int)(explosionSize/100);
			y -= (int)(explosionSize/200);
		// Else increase the size of the explosion 
		} else {
			sizeX -= (int)(explosionSize/100);
			x += (int)(explosionSize/200);
			sizeY -= (int)(explosionSize/100);
			y += (int)(explosionSize/200);
		}
		
		// If the explosion is done, set isGrowing to false 
		if (this.sizeX >= explosionSize){
			isGrowing = false;
		}
		
		// When the explosion is done, remove the bullet from the game 
		if (this.sizeX <= 0){
			handler.removeObject(this);
		}
	}
}