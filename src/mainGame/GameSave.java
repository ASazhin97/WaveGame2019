/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The GameSave class holds the saved data of the player and can be retrieved when 
 *prompted by the player 
 */
package mainGame;

// Start of class
public class GameSave {
	
	// Instance variables
	private String name;
	private int score;
	private double health;
	private int level;
	private int enemy;
	private int levelsRemaining;
	private String ability;
	private int abilityUses;
	
	// Main constructor 
	public GameSave(String n, int sc, double hp, int lvl, int en, int lvlRem, String ab, int abilUses){
		name = n;
		score = sc;
		health = hp;
		level = lvl;
		enemy = en;
		levelsRemaining = lvlRem;
		ability = ab;
		abilityUses = abilUses;
	}
	
	// Methods 
	// Used to get the name of the player 
	public String getName(){
		return name;
	}
	
	// Used to get the score of the player 
	public int getScore(){
		return score;
	}
	
	// Used to get the health of the player 
	public double getHealth(){
		return health;
	}
	
	// Used to get the level of the player 
	public int getLevel(){
		return level;
	}
	
	// Used to get the enemy of the current saved level 
	public int getEnemy(){
		return enemy;
	}
	
	// Used to get how many levels are remaining 
	public int getLevelsRem(){
		return levelsRemaining;
	}
	
	// Used to get the abilities the player has 
	public String getAbility(){
		return ability;
	}
	
	// Used to get how many abilities were used by the player 
	public int getAbilityUses(){
		return abilityUses;
	}
}