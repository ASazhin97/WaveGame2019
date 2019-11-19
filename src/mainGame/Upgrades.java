/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Upgrades class is used to identify what upgrades the player can have after each
 *boss is beat and provides images for clarification.  
 */
package mainGame;

// Start of class 
public class Upgrades {

	// Instance varaiables
	private Handler handler;
	private HUD hud;
	private Player player;
	private SpawnEasy spawnerE;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private UpgradeScreen upgradeScreen;
	private String ability = "";

	// Main constructor 
	public Upgrades(Game game, Handler handler, HUD hud, UpgradeScreen upgradeScreen, Player player, SpawnEasy spawnerE, Spawn1to10 spawner,
			Spawn10to20 spawner2) {
		this.handler = handler;
		this.hud = hud;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.spawnerE = spawnerE;
		this.spawner = spawner;
		this.spawner2 = spawner2;

		handler.setUpgrades(this);
	}

	// Methods 
	// Used to define the Clear screen ability 
	public void clearScreenAbility() {
		// Clear all enemies from screen
		handler.clearEnemiesAbility();
		// Reduce the amount of power - ups left 
		hud.setAbilityUses(-1);
		// If no power - ups are available, do not use power - up 
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}
	}

	// Used to define the Shrink player ability 
	public void decreasePlayerSize() {
		// Sets the player size 
		player.setPlayerSize(player.getPlayerSize()/1.5);
	}

	// Used to define when the player gets an extra life 
	public void extraLife() {
		// Add extra life to the handler 
		hud.setExtraLives(hud.getExtraLives() + 1);
	}

	// Used to define the Increase health power - up 
	public void healthIncrease() {
		hud.healthIncrease();
	}

	// Used to define the Regenerate health power - up 
	public void healthRegeneration() {
		hud.setRegen();
	}

	// Used to define the Damage resistance power - up 
	public void improvedDamageResistance() {
		// Resets the amount of damage taken from an enemy's attack 
		player.setDamage(player.getDamage()/1.5);
	}

	// Used to define the Level skip ability 
	public void levelSkipAbility() {
		// Clears enemies on the screen 
		handler.clearEnemies();
		hud.setLevel(hud.getLevel() + 1);
		// If in Spawn1to10 set, skip level 
		if (Spawn1to10.LEVEL_SET == 1 || Spawn1to10.LEVEL_SET == 3) {
			spawner.skipLevel();
			// If in SpawnEasy class, skip level 
		} else if (SpawnEasy.LEVEL_SET == 1) {
			spawnerE.skipLevel();
			// If in Spawn1to10 set, skip level 
		} else if (Spawn1to10.LEVEL_SET == 2) {
			spawner2.skipLevel();
		}
		// After using the ability, subtract the number of abilities available 
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}

	}

	// Used to define the Freeze time ability 
	public void freezeTimeAbility() {
		// Stop the enemies in the game 
		handler.pause();
		Spawn1to10.setSpawn(false);
		Spawn10to20.setSpawn(false);
		// Subtract from the ability availability 
		hud.setAbilityUses(-1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}
	}

	// Used to define the Speed boost power - up 
	public void speedBoost() {
		// Changes player's speed 
		Player.playerSpeed *= 1.2;
		Player.diagonalPlayerSpeed *= 1.2;
	}

	// Used to get a certain ability 
	public String getAbility() {
		return ability;
	}

	// Used to define the images of each ability and power - up 
	public void activateUpgrade(String path) {
		// CLear screen ability 
		if (path.equals("images/clearscreenability.png")) {
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
			// Shrink player power - up 
		} else if (path.equals("images/decreaseplayersize.png")) {
			ability = "decreasePlayerSize";
			decreasePlayerSize();
			// Extra life power - up 
		} else if (path.equals("images/extralife.png")) {
			ability = "extraLife";
			extraLife();
			// Increase health power - up 
		} else if (path.equals("images/healthincrease.png")) {
			ability = "HealthIncrease";
			healthIncrease();
			// Regenerate health ability 
		} else if (path.equals("images/healthregeneration.png")) {
			ability = "HealthRegen";
			healthRegeneration();
			// Reduce amount of damage taken ability 
		} else if (path.equals("images/improveddamageresistance.png")) {
			ability = "ImprovedDamageRistance";
			improvedDamageResistance();
			// Level skip power - up 
		} else if (path.equals("images/levelskipability.png")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
			// Freeze time ability 
		} else if (path.equals("images/freezetimeability.png")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
			// Speed boost power - up 
		} else if (path.equals("images/speedboost.png")) {
			ability = "SpeedBoost";
			speedBoost();
		}	
		System.out.println(ability);
	}

	// Used to set the name of each ability 
	public void setAbility(String theAbility){
		// Clear screen ability 
		if(theAbility.equals("clearScreen")){
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
			// Shrink player power - up 
		} else if (theAbility.equals("decreasePlayerSize")) {
			ability = "decreasePlayerSize";
			decreasePlayerSize();
			// Extra life power - up 
		} else if (theAbility.equals("extraLife")) {
			ability = "extraLife";
			extraLife();
			// Increase health power - up 
		} else if (theAbility.equals("HealthIncrease")) {
			ability = "HealthIncrease";
			healthIncrease();
			// Regenerate health ability 
		} else if (theAbility.equals("HealthRegen")) {
			ability = "HealthRegen";
			healthRegeneration();
			// Reduce amount of damage taken ability 
		} else if (theAbility.equals("ImprovedDamageRistance")) {
			ability = "ImprovedDamageRistance";
			improvedDamageResistance();
			// Level skip power - up 
		} else if (theAbility.equals("levelSkip")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
			// Freeze time ability 
		} else if (theAbility.equals("freezeTime")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
			// Speed boost power - up 
		} else if (theAbility.equals("SpeedBoost")) {
			ability = "SpeedBoost";
			speedBoost();
		}
	}

	// Used to reset the upgrades at the start of a new game
	public void resetUpgrades() {
		Player.playerSpeed = 10;
		hud.resetHealth();
		hud.resetRegen();
		hud.setExtraLives(0);
		hud.reset();
		player.setPlayerSize(32);
		upgradeScreen.resetPaths();
	}
}