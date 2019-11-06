/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Pause class defines the entire pause menu including leading to the main menu, 
 *the help menu, the shop menu, and saving the game.  
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import mainGame.Game.STATE;

// Start of class 
public class Pause {

	// Instance variables 
	private Game game;
	private Handler handler;
	private Image buttonImg;
	private HUD hud;
	private Image enemy1Img;
	private Image enemy2Img;
	private Image enemy3Img;
	private Image enemy4Img;
	private Image enemy5Img;
	private Image speedBoostIcon;
	private Image healthRegenIcon;
	private Image healthIncreaseIcon;
	private Image shrinkIcon;
	private Image freezeTimeIcon;
	private Image clearScreenIcon;
	private Image extraLifeIcon;
	private Image damageResistanceIcon;
	private Image coin;
	private Image boss1Img;
	private Image boss2Img;
	private Boolean gameSaved;
	private FileWriter savedGameFile;
	private Spawn1to10 spawner1;
	private Spawn10to20 spawner2;
	private SpawnEasy spawnerE;
	private Upgrades upgrade;
	private String description;

	// Main constructor 
	public Pause (HUD hud, Game game, Handler handler, Boolean gameSaved, Spawn1to10 sp1, Spawn10to20 sp2, SpawnEasy sp3, Upgrades upgrade){
		this.hud = hud;
		this.game = game;
		this.handler = handler;
		this.spawner1 = sp1;
		this.spawner2 = sp2;
		this.spawnerE = sp3;
		this.upgrade = upgrade;
		
		// Sets button images 
		buttonImg = getImage("/images/MenuButton.png");
		// Sets enemy images in the Help menu from the pause menu 
		enemy1Img = getImage("/images/gameImgEnemy1.PNG");
		enemy2Img = getImage("/images/gameImgEnemy2.PNG");
		enemy3Img = getImage("/images/gameImgEnemy3.PNG");
		enemy4Img = getImage("/images/gameImgEnemy4.PNG");
		enemy5Img = getImage("/images/gameImgEnemy5.PNG");
		// Gets the images of the shop menu from the pause menu 
		speedBoostIcon = getImage("/images/SpeedBoostAbility.PNG");
		healthRegenIcon = getImage("/images/Health Regen Ability.png");
		healthIncreaseIcon = getImage("/images/Health Increase Ability.png");
		shrinkIcon = getImage("/images/Shrink Ability.png");
		freezeTimeIcon = getImage("/images/Freeze Time Ability.png");
		clearScreenIcon = getImage("/images/Clear Screen Ability.png");
		extraLifeIcon = getImage("/images/Extra Life Ability.png");
		clearScreenIcon = getImage("/images/Clear Screen Ability.png");
		damageResistanceIcon = getImage("/images/Damage Resistance Ability.png");
		coin = getImage("/images/PickupCoin.png");
		// Gets the boss images 
		boss1Img = getImage("/images/EnemyBoss.png");
		boss2Img = getImage("/images/bosseye.png");
		// Saves the game 
		this.gameSaved = gameSaved;
	}
	
	// Methods
	// Used for descriptions in game 
	public void setDescription(String s){
		description = s;
	}

	// Used to get descriptions in game 
	public String getDescription(){
		return description;
	}

	// Used to write the save file 
	public void writeToSavedGameFile(String n, int score, double hp, int level, int enemy, int lvlRem, String ability, int abilityUses){
		
		// Try to write a new save file
		try{
		savedGameFile = new FileWriter("gameSavesFile.txt");
		PrintWriter printWriter = new PrintWriter(savedGameFile);
		printWriter.println("1");
		printWriter.print(n + " " + score + " "+ hp + " " + level + " " + enemy + " " + lvlRem + " " + ability + " " + abilityUses);
		printWriter.close();
		// If the file cannot be found, throw exception 
		} catch (FileNotFoundException e){
			System.out.println(e);
			System.exit(1);	
		// If data cannot be found, throw exception 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Used to overwrite the saved game file 
	public void clearSavedGameFile(){
		// Try to overwrite the saved file 
		try{
		savedGameFile = new FileWriter("gameSavesFile.txt");
		PrintWriter printWriter = new PrintWriter(savedGameFile);
		printWriter.println(0);
		printWriter.close();
		// If the file cannot be found, throw an exception 
		} catch (FileNotFoundException e){
			System.out.println(e);
			System.exit(1);
		// If the data cannot be found, throw an exception 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Used to save elements of the game into a save file 
	public void saveGame(){
		double health = hud.getHealth();
		int score = hud.getScore();
		int level = hud.getLevel();
		int enemy = 0;
		int levelsRem = spawner1.getLevelsRemaining();
		int abilityUses = 0;
		String ability = "none";
		if(level < 11) {
			enemy = spawner1.getLevelNumber();
			levelsRem = spawner1.getLevelsRemaining();
		} else {
			enemy = spawner2.getLevelNumber();
			ability = upgrade.getAbility();
			abilityUses = hud.getAbilityUses();
			levelsRem = spawner2.getLevelsRemaining();
		}
		writeToSavedGameFile("Alex", score, health, level, enemy, levelsRem, ability, abilityUses);
	}
	
	// Used to run a new game 
	public void unSaveGame(){
		hud.resetHealth();
		hud.setLevel(1);
		hud.setScore(0);
		spawner1.setLevelNumber(0);
		spawner1.setLevelsRemaining(10);
		this.clearSavedGameFile();
	}
	
	// Used to define time in the game 
	public void tick(){
	}
	
	// Used to check if the game is saved 
	public void setGameSaved(Boolean s){
		gameSaved = s;
	}
	
	// Used to get the saved game 
	public Boolean getGameSave(){
		return gameSaved;
	}
	
	// Used to reset the state of the game 
	public void reset(){
		new Pause(this.hud, this.game, this.handler, gameSaved, this.spawner1, this.spawner2, this.spawnerE, this.upgrade);
	}
	
	// Used to create graphics of the game 
	public void render(Graphics g){
		// Set font 
		Font font;
		if(game.gameState == STATE.Pause){
			font = new Font("Amoebic", 1, 100);
			// Draw menu button 
			g.drawImage(buttonImg, 550, 100, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("MAIN", 850, 230);
			// Draw help button 
			g.drawImage(buttonImg, 550, 350, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("HELP", 850, 480);
			// Draw save button 
			g.drawImage(buttonImg, 550, 600, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(font);
			// If the game is not saved, print out 'save' and if it is saved, print out 'saved' 
            if (!this.gameSaved) {
                g.drawString("SAVE", 850, 730);
			} else {
                g.drawString("SAVED", 850, 730);
			}
            // Draw shop button 
			g.drawImage(buttonImg, 550, 850, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(font);
            g.drawString("SHOP", 850, 980);
		}

		// Text for Shop menu in pause 
        Font font2;
        if (this.game.gameState == STATE.PauseShop) {
            font = new Font("impact", 1, 50);
            font2 = new Font("impact",1,30);
    g.setColor(Color.BLACK);
    g.fillRect(0,0,1920,1280);
    g.setFont(font);
    g.setColor(Color.white);
    g.drawString("Shop", 900, 70);
    int rectW = 1895;
    int rectH = 1020;
    int rectY = rectH - 80;
    // Used to draw the Shop menu 
    g.setColor(Color.white);
    g.drawRect(10, 80, (rectW / 2) - 15, (rectY / 2) - 15);
    g.drawRect((rectW / 2) + 5, 80, (rectW / 2) - 15, (rectY / 2) - 15); 
    g.drawRect(10, 80 + (rectY / 2) + 5, (rectW / 2) - 15, (rectY / 2) - 15); 
    g.drawRect((rectW / 2) + 5, 80 + (rectY / 2) + 5, (rectW / 2) - 15, (rectY / 2) - 15);
    // Abilities text 
    g.setFont(font2);
    g.drawString("Passive Abilities", 360, 110);
    g.drawString("Active Abilities", 360, 585);
    g.drawString("Passive Loadout", 1260, 110);
    g.drawString("Description", 1290, 585);
    // Back Button
    g.drawRect(1795,950,80,50);
    g.drawString("Back",1805,985);
    // Current amount of coins
    g.drawImage(coin,8,8,40,40,null);
    g.drawString("X" + hud.getScore(),33,48);

    // Left side of the Shop
    // Health regeneration ability 
    g.drawImage(healthRegenIcon, 100, 125, 125, 125, null);
    g.drawImage(coin,100,260,40,40,null);
    g.drawString("X" + (int)hud.getCost(),125,300);
    // Increase health ability 
    g.drawImage(healthIncreaseIcon, 300, 125, 125, 125, null);
    g.drawImage(coin,300,260,40,40,null);
    g.drawString("X" + (int)hud.getCost(),325,300);
    // Shrink player ability 
    g.drawImage(shrinkIcon, 500, 125, 125, 125, null);
    g.drawImage(coin,500,260,40,40,null);
    g.drawString("X" + (int)hud.getCost(),525,300);
    // Extra life power - up 
    g.drawImage(extraLifeIcon, 300, 325, 125, 125, null);
    g.drawImage(coin,300,460,40,40,null);
    g.drawString("X" + (int)hud.getCost(),325,500);
    // Freeze time power - up 
    g.drawImage(freezeTimeIcon, 100, 650, 125, 125, null);
    g.drawImage(coin,100,785,40,40,null);
    g.drawString("X" + (int)hud.getActiveCost(),125,825);
    // Speed boost power - up 
    g.drawImage(speedBoostIcon, 100, 325, 125, 125, null);
    g.drawImage(coin,100,460,40,40,null);
    g.drawString("X" + (int)hud.getCost(),125,500);
    // Damage resistance power - up 
    g.drawImage(damageResistanceIcon, 700, 125, 125, 125, null);
    g.drawImage(coin,700,260,40,40,null);
    g.drawString("X" + (int)hud.getCost(),725,300);
    // Clear Screen power - up 
    g.drawImage(clearScreenIcon, 500, 650, 125, 125, null);
    g.drawImage(coin,500,785,40,40,null);
    g.drawString("X" + (int)hud.getActiveCost(),525,825);

    // Loads all abilities and power - ups and how many of them are available 
    g.drawImage(healthRegenIcon, 1050, 125, 125, 125, null);
    g.drawString("X"+hud.getNumRegen(),1050,300);
    g.drawImage(damageResistanceIcon, 1650, 125, 125, 125, null);
    g.drawString("X"+hud.getNumArmor(),1650,300);
    g.drawImage(freezeTimeIcon, 1450, 325, 125, 125, null);
    g.drawString("X"+hud.getNumFreeze(),1450,500);
    g.drawImage(clearScreenIcon, 1650, 325, 125, 125, null);
    g.drawString("X"+hud.getNumClear(),1650,500);
    g.drawImage(speedBoostIcon, 1050, 325, 125, 125, null);
    g.drawString("X"+hud.getNumSpeed(),1050,500);
    g.drawImage(healthIncreaseIcon, 1250, 125, 125, 125, null);
    g.drawString("X"+hud.getNumHealth(),1250,300);
    g.drawImage(shrinkIcon, 1450, 125, 125, 125, null);
    g.drawString("X"+hud.getNumShrink(),1450,300);
    g.drawImage(extraLifeIcon, 1250, 325, 125, 125, null);
    g.drawString("X"+hud.getExtraLives(),1250,500);

    // Description of game 
    g.drawString(this.getDescription(),1000,650);
        }
        	// If the game is on the second page of the Help menu 
			else if(game.gameState == STATE.Help2){
			font = new Font("impact", 1, 50);
			font2 = new Font("impact", 1, 30);
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);
			
			// If the game is in the Shop menu 
			g.drawString("Shop", 900, 70);
			int rectW = 1895;
			int rectH = 1020;
			int rectY = rectH - 80;
			g.setColor(Color.white);
			// Draws Shop menu 
			g.drawRect(10, 80, (rectW / 2) - 15, (rectY / 2) - 15);
			g.drawRect((rectW / 2) + 5, 80, (rectW / 2) - 15, (rectY / 2) - 15); 
			g.drawRect(10, 80 + (rectY / 2) + 5, (rectW / 2) - 15, (rectY / 2) - 15);
			g.drawRect((rectW / 2) + 5, 80 + (rectY / 2) + 5, (rectW / 2) - 15, (rectY / 2) - 15);
			g.setFont(font2);
			// Draws abilities text 
			g.drawString("Passive Abilities", 360, 110);
			g.drawString("Active Abilities", 360, 585);
			g.drawString("Passive Loadout", 1260, 110);
			g.drawString("Description", 1290, 585);

			// Back button
			g.drawRect(1795,950,80,50);
			g.drawString("Back",1805,985);

			// Current coins
			g.drawImage(coin,8,8,40,40,null);
			g.drawString("X" + hud.getScore(),33,48);

			//Left side of the Shop
			// Health regeneration ability 
			g.drawImage(healthRegenIcon, 100, 125, 125, 125, null);
			g.drawImage(coin,100,260,40,40,null);
			g.drawString("X" + (int)hud.getCost(),125,300);
			// Increase health ability 
			g.drawImage(healthIncreaseIcon, 300, 125, 125, 125, null);
			g.drawImage(coin,300,260,40,40,null);
			g.drawString("X" + (int)hud.getCost(),325,300);
			// Shrink player ability 
			g.drawImage(shrinkIcon, 500, 125, 125, 125, null);
			g.drawImage(coin,500,260,40,40,null);
			g.drawString("X" + (int)hud.getCost(),525,300);
			// Extra life power - up 
			g.drawImage(extraLifeIcon, 300, 325, 125, 125, null);
			g.drawImage(coin,300,460,40,40,null);
			g.drawString("X" + (int)hud.getCost(),325,500);
			// Freeze time power - up 
			g.drawImage(freezeTimeIcon, 100, 650, 125, 125, null);
			g.drawImage(coin,100,785,40,40,null);
			g.drawString("X" + (int)hud.getActiveCost(),125,825);
			// Speed boost power - up 
			g.drawImage(speedBoostIcon, 100, 325, 125, 125, null);
			g.drawImage(coin,100,460,40,40,null);
			g.drawString("X" + (int)hud.getCost(),125,500);
			// Damage resistance power - up 
			g.drawImage(damageResistanceIcon, 700, 125, 125, 125, null);
			g.drawImage(coin,700,260,40,40,null);
			g.drawString("X" + (int)hud.getCost(),725,300);
			// Clear screen power - up 
			g.drawImage(clearScreenIcon, 500, 650, 125, 125, null);
			g.drawImage(coin,500,785,40,40,null);
			g.drawString("X" + (int)hud.getActiveCost(),525,825);

		    // Loads all abilities and power - ups and how many of them are available 
			g.drawImage(healthRegenIcon, 1050, 125, 125, 125, null);
			g.drawString("X"+hud.getNumRegen(),1050,300);
			g.drawImage(damageResistanceIcon, 1650, 125, 125, 125, null);
			g.drawString("X"+hud.getNumArmor(),1650,300);
			g.drawImage(freezeTimeIcon, 1450, 325, 125, 125, null);
			g.drawString("X"+hud.getNumFreeze(),1450,500);
			g.drawImage(clearScreenIcon, 1650, 325, 125, 125, null);
			g.drawString("X"+hud.getNumClear(),1650,500);
			g.drawImage(speedBoostIcon, 1050, 325, 125, 125, null);
			g.drawString("X"+hud.getNumSpeed(),1050,500);
			g.drawImage(healthIncreaseIcon, 1250, 125, 125, 125, null);
			g.drawString("X"+hud.getNumHealth(),1250,300);
			g.drawImage(shrinkIcon, 1450, 125, 125, 125, null);
			g.drawString("X"+hud.getNumShrink(),1450,300);
			g.drawImage(extraLifeIcon, 1250, 325, 125, 125, null);
			g.drawString("X"+hud.getExtraLives(),1250,500);

			// Description of game 
			g.drawString(this.getDescription(),1000,650);

		// If the game is on the first page of the Help menu 
        } else if (this.game.gameState == STATE.PauseH1) {
            font = new Font("impact", 1, 50);
            font2 = new Font("impact", 1, 30);
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 900, 70);

			// Displays text in the Help menu 
			g.setFont(font2);
			g.drawString("Waves: Simply use Arrow keys or WASD to move and avoid enemies.", 40, 300);
			g.drawString("One you avoid them long enough, a new batch will spawn in! Defeat each boss to win!", 40, 340);
			g.drawString("Press P to pause and un-pause", 40, 400);
			g.drawString("Press Enter to use abilities when they have been equipped", 40, 440);
			g.drawString("Click Next to see Enemy and Boss Summaries", 40, 800);

			// Next button 
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(1600, 870, 200, 65);
			g.drawString("Next", 1650, 910);
			// Main menu button 
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
			
		// If the game is on the second page of the Help menu 
		} else if (game.gameState == STATE.PauseH2){
			font = new Font("impact", 1, 50);
			font2 = new Font("impact", 1, 30);
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);
			
			// Displays text in the Help menu 
			g.drawString("Different Enemies", 800, 70);
			g.setFont(font2);
			g.drawString("1. Green. These will", 40, 300);
			g.drawString("follow you where ever", 40, 340);
			g.drawString("you are on screen.", 40, 380);
			g.drawString("2. Red. These bounce", 400, 300);
			g.drawString("of the walls at a", 400, 340);
			g.drawString("45 degree angle", 400, 380);
			g.drawString("3. Cyan. These also", 750, 300);
			g.drawString("bounce of walls but at", 750, 340);
			g.drawString("a shallow angle", 750, 380);
			g.drawString("4. Yellow. These squares", 1100, 300);
			g.drawString("shoot little bullets at", 1100, 340);
			g.drawString("you to dodge", 1100, 380);
			g.drawString("5. Burst. Warning flashes", 1500, 300);
			g.drawString("will appear from the side", 1500, 340);
			g.drawString("they will jump out from", 1500, 380);
			
			// Back button 
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(100, 870, 200, 64);
			g.drawString("Back", 150, 910);
			// Main menu button 
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
			// Next button 
			g.drawRect(1600, 870, 200, 65);
			g.drawString("Next", 1650, 910);
			// Images of the enemies 
			g.drawImage(enemy1Img, 100, 440, 250, 250, null);
			g.drawImage(enemy2Img, 400, 440, 250, 250, null);
			g.drawImage(enemy3Img, 750, 440, 250, 250, null);
			g.drawImage(enemy4Img, 1100, 440, 250, 250, null);
			g.drawImage(enemy5Img, 1500, 440, 300, 250, null);
			
		// If the game is on the third page of the Help menu 
		} else if (game.gameState == STATE.PauseH3){
			font = new Font("impact", 1, 50);
			font2 = new Font("impact", 1, 30);
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);
			
			// Displays text in the Help menu 
			g.drawString("The Bosses", 830, 70);
			g.setFont(font2);
			g.drawString("The Red Boss. Dodge the", 40, 300);
			g.drawString("explosive bullets that he", 40, 340);
			g.drawString("throws and stay below the line.", 40, 380);
			g.drawImage(boss1Img, 100, 440, 250, 250, null);
			g.drawString("The Green Eye Boss. Each", 600, 300);
			g.drawString("moves differently so keep", 600, 340);
			g.drawString("moving and stay alert!", 600, 380);
			// Boss image 
			g.drawImage(boss2Img, 600, 440, 250, 250, null);
			// Back button 
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(100, 870, 200, 64);
			g.drawString("Back", 150, 910);
			// Main menu button 
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
		}
	}
	
	// Used to get the image path 
	public Image getImage(String path) {
		Image image = null;
		// Try to find the image 
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		// If image is not found, throw exception 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
}