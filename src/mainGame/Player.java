/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Player class defines every aspect of the player including its movement, damage 
 *in - take, and abilities. 
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import org.json.JSONException;
import mainGame.Game.STATE;

// Start of class
// Is a subclass of GameObject 
public class Player extends GameObject {
	
	// Instance variables 
	Random r = new Random();
	Handler handler;
	private HUD hud;
	private Game game;
	private double damage;
	private int playerWidth, playerHeight;
	private int tempInvincible = 0;
	public static int playerSpeed = 10;
	public static int diagonalPlayerSpeed = 8;
	private SimpleMidi hitsoundMIDIPlayer;
	private String hitsoundMIDIMusic = "HitsoundPart2.mid";
	private String pickupcoinMIDIMusic = "pickupcoin.mid";
	private boolean wasHit;
	
	// Main constructor 
	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.damage = 7;
		// Defines the pixel size of the player 
		playerWidth = 32;
		playerHeight = 32;
		// Sound when player gets hit 
		hitsoundMIDIPlayer = new SimpleMidi();
	}

	// Methods 
	// Used to define time in the game 
	@Override
	public void tick() throws JSONException {
		// Control speed of player 
		this.x += velX;
		this.y += velY;
		x = Game.clamp(x, 0, Game.WIDTH - 38);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);
		// Adds trail to player 
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, playerWidth, playerHeight, 0.05, this.handler));
		collision();
		// If the player is invincible, start decreasing the time of the power - up 
		if (tempInvincible > 0) {
			tempInvincible--;
		}
		
		// If the player was hit by the enemy, play the Midi file 
		if (wasHit == true) {
			// Try to play the Midi file 
			try {
				hitsoundMIDIPlayer.PlayMidi(hitsoundMIDIMusic);
			}
			// If file cannot be played, throw an exception 
			catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
			wasHit = false;
		}
		// Check to see if player's health is 0 
		checkIfDead();
	}

	// Used to check if player is dead 
	public void checkIfDead() throws JSONException {
		// If health is 0 and the player has no extra lives 
		if (hud.health <= 0) {
			if (hud.getExtraLives() == 0) {
				game.gameState = STATE.GameOver;
				// Send score to leaderboard 
				game.getGameOver().sendScore();
			}
			// Else if the player has an extra life, keep playing the game 
			else if (hud.getExtraLives() > 0) {
				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.setHealth(100);
			}
		}
	}

	// Used to check if player collides with any enemies 
	public void collision() {
		hud.updateScoreColor(Color.white);
		// Keep adding pick ups to the Handler class 
		if (!handler.pickups.isEmpty()) {
		for (int j = 0; j < handler.pickups.size(); j++) {
			 Pickup pickupObject = handler.pickups.get(j);
			// Picks up coins 
			if (pickupObject.getId() == ID.PickupCoin) {
				// If player picks up a coin, add 100 points to the score 
				if (this.getBounds().intersects(pickupObject.getBounds())) {
					hud.setScore(100);
					handler.removePickup(pickupObject);
					// Try to play the pick up coin sound 
					try {
						hitsoundMIDIPlayer.PlayMidi(pickupcoinMIDIMusic);
					}
					// If sound cannot be played, throw an exception 
					catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
		// If the enemy or enemy attack hits the player, player's health will take damage 
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.EnemyBasic || tempObject.getId() == ID.EnemyFast
					|| tempObject.getId() == ID.EnemySmart || tempObject.getId() == ID.EnemyBossBullet
					|| tempObject.getId() == ID.EnemySweep || tempObject.getId() == ID.EnemyShooterBullet
					|| tempObject.getId() == ID.EnemyBurst || tempObject.getId() == ID.EnemyShooter
					|| tempObject.getId() == ID.BossEye) {
				// Subtract damage from health 
				if (getBounds().intersects(tempObject.getBounds()) && tempInvincible == 0) {
					hud.health -= damage;
					hud.updateScoreColor(Color.red);
					wasHit = true;
					tempInvincible = 15;
				}
			}
			// When player is against the boss 
			if (tempObject.getId() == ID.EnemyBoss) {
				// If the player is hurt by boss, subtract damage from health 
				if (this.y <= 138 && tempObject.isMoving) {
					hud.health -= 2;
					hud.updateScoreColor(Color.red);
					wasHit = true;
				}
			}
		}
	}

	// Used to create player 
	@Override
	public void render(Graphics g) {
		// Set the color and shape 
		g.setColor(Color.white);
		g.fillRect((int) x, (int) y, playerWidth, playerHeight);
	}

	// Used to set size 
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 32, 32);
	}

	// Used to set the amount of damage taken 
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	// Used to get the amount of damage taken 
	public double getDamage(){
		return this.damage;
	}

	// Used to set the size of the player when the shrink ability is used 
	public void setPlayerSize(double size) {
		this.playerWidth = (int)size;
		this.playerHeight = (int)size;
	}
	
	// Used to get the size of the player 
	public double getPlayerSize(){
		return this.playerWidth;
	}
}