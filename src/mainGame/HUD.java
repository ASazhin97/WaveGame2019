/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The HUD class controls the display for the game and includes data regarding the 
 *player's health, the use of various abilities, and many other aspects of the game. 
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

// Start of class 
public class HUD {
	
	// Instance variables
	public double health = 100;
	private double healthMax = 100;
	private double greenValue = 255;
	private int score = 00000000000;
	private int level = 0;
	private boolean regen = false;
	private int timer = 10;
	private int healthBarWidth = 400;
	private double healthBarModifier = 2.5;
	private String ability = "";
	private int abilityUses = 0;
	private Color scoreColor = Color.white;
	private Color freezeColor = new Color(0, 255, 255, 25);
	private Color regenColor = new Color(120, 255, 120);
	private int extraLives = 0;
	private String highScoreString = "";
	private double costMultipier = 1.25;
	private double cost = 500;
	private double activeCost = 3000;
	private int numFreeze=0;
	private int numRegen=0;
	private int numHealth=0;
	private int numSpeed=0;
	private int numShrink=0;
	private int numArmor=0;
	private int numClear=0;
	private double regenValue = 0;
	private ArrayList<String> leaderboard;

	// Methods
	// Used to get amount of "clear all enemies off the screen" abilities
	public int getNumClear() {
		return numClear;
	}

	// Used to set the amount of "clear all enemies off the screen" abilities
	public void setNumClear() {
		this.numClear += 1;
	}
	
	// Used to get amount of "regenerate player's health" abilities 
	public double getregenValue() {
		return regenValue;
	}

	// Used to set the amount of "regenerate player's health" abilities 
	public void setregenValue() {
		this.regenValue += .25;
	}

	// Used to get the amount of "freezes all enemies in place" abilities 
	public int getNumFreeze() {
		return numFreeze;
	}

	// Used to set the amount of "freezes all enemies in place" abilities
	public void setNumFreeze() {
		this.numFreeze += 1;
	}

	// Used to get the amount of regeneration abilities of the player 
	public int getNumRegen() {
		return numRegen;
	}

	// Used to set the amount of regeneration abilities of the player  
	public void setNumRegen() {
		this.numRegen += 1;
	}

	// Used to get the amount of "increases player's maximum health" abilities 
	public int getNumHealth() {
		return numHealth;
	}

	// Used to set the amount of "increases player's maximum health" abilities 
	public void setNumHealth() {
		this.numHealth += 1;
	}

	// Used to get amount of "increases player's speed" abilities 
	public int getNumSpeed() {
		return numSpeed;
	}

	// Used to set amount of "increases player's speed" abilities 
	public void setNumSpeed() {
		this.numSpeed += 1;
	}

	// Used to get amount of "shrinks the player" abilities 
	public int getNumShrink() {
		return numShrink;
	}

	// Used to set amount of "shrinks the player" abilities 
	public void setNumShrink() {
		this.numShrink += 1;
	}

	// Used to get amount of "lower damage taken" abilities
	public int getNumArmor() {
		return numArmor;
	}

	// Used to set amount of "lower damage taken" abilities 
	public void setNumArmor() {
		this.numArmor += 1;
	}

	// Used to get the amount the cost at the store is multiplied by 
	public double getCostMultipier() {
		return costMultipier;
	}

	// Used to set the cost multiplier at the store 
	public void setCostMultipier(double costMultipier) {
		this.costMultipier = costMultipier;
	}

	// Used to get the cost of a power - up
	public double getCost() {
		return cost;
	}

	// Used to set the cost of a power - up
	public void setCost(double cost) {
		this.cost = cost;
	}

	// Used to get the activation cost of a power - up
	public double getActiveCost(){
		return activeCost;
	}
	
	// Used to set the activation cost of a power - up
	public void setActiveCost(double a){
		this.activeCost = a;
	}

	// Used to define time in the game
	public void tick() {
		// Determine the health of the player
		health = Game.clamp(health, 0, health);
		health = Game.clamp(health, 0, healthMax);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = health * healthBarModifier;
		
		// If regeneration ability is unlocked, regenerate health until timer reaches 0 
		if (regen) {
			timer--;
			if (timer == 0) {
				health += this.getregenValue();
				timer = 10;
			}
			
			// Set health when regeneration ability ends 
			health = Game.clamp(health, 0, healthMax);
		}
	}
	
	// Used to reset the health bar 
	public void reset(){
		health = 100;
		greenValue = 255;
		healthBarModifier = 2;
	}

	// Used to create graphics
	public void render(Graphics g) {
		// Sets fonts used 
		Font font = new Font("Amoebic", 1, 30);
		g.setColor(Color.GRAY);
		// Sets health bar 
		g.fillRect(15, 15, healthBarWidth, 64);
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect((int) 15, (int) 15, (int) health * 4, 64);
		// If Regeneration ability is used and the health bar is not full, add health 
		if (regen && health < healthMax)
			g.setColor(regenColor);
		else
			g.setColor(scoreColor);
		g.drawRect(15, 15, healthBarWidth, 64);
		// If Freeze ability is used, freeze the game 
		if (Handler.getFreeze()) {
			g.setColor(Color.GRAY);
			g.fillRect(1560, 20, 300, 30);
			g.setColor(Color.CYAN);
			g.fillRect(1560, 20, Handler.getTimer(), 30);
			g.setColor(scoreColor);
			g.drawRect(1560, 20, 300, 30);
			g.setColor(freezeColor);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}

		// Displays extra lives on the screen 
		g.setFont(font);
		g.setColor(scoreColor);
		g.drawString("Score: " + score, 15, 115);
		g.drawString("Level: " + level, 15, 150);
		g.drawString("Extra Lives: " + extraLives, 15, 185);
		
		// Displays current high score 
		if (this.highScoreString != null){
			g.drawString("High Score:", 15, 950);
			g.drawString(this.highScoreString, 15, 1000);
		}

		// Using the Freeze ability, display this message 
		if (ability.equals("freezeTime")) {
			g.drawString("Time Freezes: " + abilityUses, Game.WIDTH - 300, 64);
		// Using the Clear Screen ability, display this message 
		} else if (ability.equals("clearScreen")) {
			g.drawString("Screen Clears: " + abilityUses, Game.WIDTH - 300, 64);
		// Using the Level Skip ability, display this message 
		} else if (ability.equals("levelSkip")) {
			g.drawString("Level Skips: " + abilityUses, Game.WIDTH - 300, 64);
		}
	}

	// Used to set the ability 
	public void setAbility(String ability) {
		this.ability = ability;
	}
	
	// Used to get the ability being used 
	public String getAbility(){
		return ability;
	}

	// Used to get the amount of times the ability has been used 
	public int getAbilityUses() {
		return this.abilityUses;
	}

	// Used to set the amount of times the ability has been used 
	public void setAbilityUses(int abilityUses) {
		this.abilityUses += abilityUses;
	}

	// Used to update the score 
	public void updateScoreColor(Color color) {
		this.scoreColor = color;
	}

	// Used to set the score of the player
	public void setScore(int score) {
		this.score += score;
	}
	
	// Used to get the player's health 
	public double getHealth(){
		return health;
	}

	// Used to get the score of the player
	public int getScore() {
		return score;
	}

	// Used to get the level of the player
	public int getLevel() {
		return level;
	}

	// Used to set the level of the player
	public void setLevel(int level) {
		this.level = level;
	}

	// Used to set the health of the player 
	public void setHealth(double health) {
		this.health = health;
	}

	// Used to activate the Regeneration ability 
	public void setRegen() {
		regen = true;
	}

	// Used to stop the Regeneration ability 
	public void resetRegen() {
		regen = false;
	}

	// Used to set the amount of extra lives of the player
	public void setExtraLives(int lives) {
		this.extraLives = lives;
	}

	// Used to get the amount of extra lives of the player
	public int getExtraLives() {
		return this.extraLives;
	}

	// Used to increase the health of the player
	public void healthIncrease() {
		healthMax = healthMax+50;
		this.health = healthMax;
		healthBarModifier = (250/healthMax);
		healthBarWidth = 4*(int)healthMax;
	}

	// Used to reset the health of the player 
	public void resetHealth() {
		healthMax = 100;
		this.health = healthMax;
		healthBarModifier = 2.5;
		healthBarWidth = 400;
	}

	// Used to restore the health of the player 
	public void restoreHealth() {
		this.health = healthMax;
	}

	// Used to set the high score of the player 
	public void setHighScore(String data) {
		// Creates the leaderboard 
		leaderboard = new ArrayList<String>();
		// Separate the data into lines 
		if(data != null) {
			String[] rows = data.split("\n");
			// Keep adding rows to the leaderboard for amount of scores added 
			for(int i = 0; i < rows.length; i++) {
				leaderboard.add(rows[i]);
			}
		// Else clear the leaderboard
		} else {
			leaderboard.clear();
		}
		
		// Gets the highest score of the leaderboard 
		this.highScoreString = leaderboard.get(0);
	}
	
	// Used to get the leaderboard 
	public ArrayList<String> getLeaderboard(){
		return leaderboard;
	}
	
	// Used to set the leaderboard 
	public void sortLeaderboard() {
		if(leaderboard.size() == 0) {
			return;
		} 
		
		// Separate name and score by using a comma 
		for(int i = 0; i < leaderboard.size(); i++) {
			int scoreI = Integer.parseInt(leaderboard.get(i).split(",")[1]);
			
			for(int j = 0; j < leaderboard.size(); j++) {
				int scoreJ = Integer.parseInt(leaderboard.get(j).split(",")[1]);
				
				// If there is a new highest score, update the leaderboard 
				if(scoreJ < scoreI) {
					String tmp = leaderboard.get(j);
					leaderboard.set(j, leaderboard.get(i));
					leaderboard.set(i, tmp);
				}
			}
		}
	}
}