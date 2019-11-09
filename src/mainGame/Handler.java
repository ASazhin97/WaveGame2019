/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Handler class handles all progress the player makes throughout the game, 
 *including coins picked up, enemies created and removed, and abilities the player has and uses. 
 */
package mainGame;

// Imports 
import java.awt.Graphics;
import java.util.ArrayList;
import org.json.JSONException;

// Start of class 
public class Handler {

	// Instance variables 
	ArrayList<GameObject> object = new ArrayList<GameObject>();
	ArrayList<Pickup> pickups = new ArrayList<Pickup>();
	ArrayList<PickupCoin> coinPickups = new ArrayList<PickupCoin>();
	private static int timer = 0;
	private static boolean freeze;
	private Pickup pickupObject;
	private Upgrades upgrades;

	// Methods 
	// Used to define time in the game 
	// If object cannot be defined, throw an exception 
	public void tick() throws JSONException {
		// Store objects in an ArrayList 
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			// If the temporary object is from the EnemyBurstWarning class, do not use the Screen 
			// Freeze ability 
			if (tempObject.getId() == ID.Player || tempObject.getId() == ID.Trail
					|| tempObject.getId() == ID.EnemyBurstWarning) {
				// Constantly updates states of all objects in the array 
				tempObject.tick();
			} else {
				// If the timer of an object reaches 0, do not allow player to freeze the screen 
				if (timer == 0) {
					tempObject.tick();
					Spawn1to10.setSpawn(true);
					Spawn10to20.setSpawn(true);
					freeze = false;
				} 
				// If object's time limit is below 0, do not allow player to freeze screen 
				else if (timer < 0) {
					tempObject.tick();
				}
				// Else let player freeze screen 
				else
					freeze = true;
			}
		}

		// Creates an array that holds all pick - ups 
		for (int i = 0; i < pickups.size(); i++) {
			Pickup pickupObject = pickups.get(i);
			// Constantly updates states of all objects in the array 
			pickupObject.tick();
		}
		// Creates an array that holds all coin pick = ups specifically 
		for (int i = 0; i < coinPickups.size(); i++) {
			PickupCoin coinObject = coinPickups.get(i);
			// Constantly updates states of all objects in the array 
			coinObject.tick();
		}
		// If the Freeze ability is used, start a timer for when the ability ends 
		if (freeze)
			timer --;
	}

	// Used to create graphics of each object 
	public void render(Graphics g) {
		// For each object, draw it when the object is called 
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject != null) {
				tempObject.render(g);
			}
		}
		// For each pick - up, draw it when the object is called 
		for (int i = 0; i < pickups.size(); i++) {
			Pickup tempObject = pickups.get(i);
			if (tempObject != null) {
				tempObject.render(g);
			}
		}
		// For each coin pick - up, draw it when the object is called 
		for (int i = 0; i < coinPickups.size(); i++) {
			Pickup tempObject = coinPickups.get(i);
			if (tempObject != null) {
				tempObject.render(g);
			}
		}
	}

	// Used to call the Pause ability 
	public void pause() {
		// Starts timer for how long ability can be used for 
		timer = 300;
	}

	// Used to add game objects that can be picked - up 
	public void addObject(GameObject pickupCoin) {
		this.object.add(pickupCoin);
	}

	//  Used to remove objects from the game 
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	// Used to add pick - ups to the game 
	public void addPickup(Pickup object) {
		this.pickups.add(object);
	}

	// Used to remove pick - ups from the game 
	public void removePickup(Pickup object) {
		this.pickups.remove(object);
	}
	// Used to add pick - up coins to the game 
	public void addPickupCoin(PickupCoin object) {
		this.pickups.add(object);
	}

	// Used to remove pick - up coins to the game 
	public void removePickupCoin(PickupCoin object) {
		this.coinPickups.remove(object);
	}

	// Used to get the horizontal position of the pick - up
	public double getpickupX() {
		return pickupObject.getX();
	}

	// Used to get the vertical position of the pick - up
	public double getpickupY() {
		return pickupObject.getY();
	}

	//Create a reference to upgrades
	public void setUpgrades(Upgrades upgrades) {
		this.upgrades = upgrades;
	}
	/**
	 * Clears all entities that have an ID of some sort of enemy
	 */
	//Clears only enemies not bosses or players.
	public void clearEnemiesAbility() {
		for(int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			if (tempObject.getId() != ID.Player && tempObject.getId() != ID.BossEye && tempObject.getId() != ID.EnemyBoss) {
				this.removeObject(tempObject);
				i--;
			}
		}
	}
	public void clearEnemies() {
		for (int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			// If the enemy cannot attack the player during its spawn time, remove the enemy from
			// the array
			if (tempObject.getId() != ID.Player) {
				this.removeObject(tempObject);
				i--;
			}
		}
	}

	// Used to clear the coins from the screen 
	public void clearCoins() {
		for (int i = 0; i < pickups.size(); i++) {
			Pickup tempObject = pickups.get(i);
			// If the coin cannot be picked - up by the player during its spawn time, remove the
			// coin from the array 
			if (tempObject.getId() != ID.Player) {
				this.removePickup(tempObject);
				i--;
			}
		}
	}
	// Used to clear the level text from the screen 
	public void clearLevelText() {
		for (int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			// If the timer of the level text is 0, remove text from the screen 
			if (tempObject.getId() == ID.Levels1to10Text){
				this.removeObject(tempObject);
			}
		}
	}

	// Used to clear the player from the screen 
	public void clearUpgrades() {
		upgrades.resetUpgrades();
	}


	/**
	 * Clears all entities that have an ID of player
	 */
	public void clearPlayer() {
		for (int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			// If the player is not needed on the screen, remove the player from the screen 
			if (tempObject.getId() == ID.Player) {
				this.removeObject(tempObject);
				i--; // Removing shrinks the array by 1, causing the loop to skip a player (should
				// there be more than one)
			}
		}
	}

	// Used to get the value of the timer 
	public static int getTimer() {
		return timer;
	}

	// Used to check if the player can use the Freeze ability 
	public static boolean getFreeze() {
		return freeze;
	}

	// Used to get the bounds of objects 
	public void getBounds() {
	}
}