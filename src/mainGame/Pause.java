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
		Font bigFont = FontHandler.setSize(FontHandler.HEADER_FONT, 95);
		Font smallFont = FontHandler.setSize(FontHandler.BODY_FONT, 50);
		// Draw menu button 
		if(game.gameState == STATE.Pause){	
			g.drawImage(buttonImg, 550, 100, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(bigFont);
			g.drawString("MAIN", 850, 230);
			// Draw help button 
			g.drawImage(buttonImg, 550, 350, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(bigFont);
			g.drawString("HELP", 850, 480);
			// Draw save button 
			g.drawImage(buttonImg, 550, 600, 900, 200, null);
			g.setColor(Color.WHITE);
			// If the game is not saved, print out 'save' and if it is saved, print out 'saved' 
			g.setFont(bigFont);
			if (!this.gameSaved) {
				g.drawString("SAVE", 850, 730);
			} else {
				g.drawString("SAVED", 850, 730);
			}
			// Draw shop button 
			g.drawImage(buttonImg, 550, 850, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(bigFont);
			g.drawString("SHOP", 850, 980);
		}

		//SHOP STATE
		bigFont = FontHandler.setSize(FontHandler.HEADER_FONT, 50);
		smallFont = FontHandler.setSize(FontHandler.HEADER_FONT, 20);
		Font mediumFont = FontHandler.setSize(FontHandler.BODY_FONT, 30);
		if (this.game.gameState == STATE.PauseShop) {

			int rectW = 1895;
			int rectH = 1020;
			int rowHeight = 70;
			int rowWidth = 600;
			int spaceBetweenRows = 4;			
			int storeYOffset = 200;
			//boolean canAfford, canAffordActive = false;
			boolean canAfford = false;

			g.setColor(new Color(200, 150, 100));
			g.fillRect(0,0,1920,1280);
			g.setFont(bigFont);
			g.setColor(Color.white);
			g.drawString("The Shop", 800, 70);

			g.drawString("Passive Abilities", 120, storeYOffset);
			g.drawString("Active Abilities", (rectW / 2) + 120, storeYOffset);
			g.drawString("Character Customization", (rectW / 2) + 30, storeYOffset + 250);

			//Back Button
			g.setColor(Color.white);
			g.drawString("Back",1755, 1000);
			// Current coins
			g.drawImage(coin,8,8,40,40,null);
			g.drawString("X" + hud.getScore(),33,48);
			//Left side of the Shop - Passive items
			for( int i = 0; i < 6; i++) {
				//Set font to rockwell
				g.setFont(mediumFont);

				String upgradeName = "#ERR in Pause.java:285";
				int quantity = 0;
				canAfford = hud.getScore()>=hud.getCost(); // User has enough money
				//canAffordActive = hud.getScore()>=hud.getActiveCost(); // User has enough money
				switch(i) {
				case 0: 
					upgradeName = "Increase Health";
					quantity = hud.getNumHealth();
					if (hud.getNumHealth() >= 7) canAfford = false;
					break;
				case 1: 
					upgradeName = "Increase Speed";
					quantity = hud.getNumSpeed();
					if (hud.getNumHealth() >= 8) canAfford = false;
					break;
				case 2: 
					upgradeName = "Increase Armor";
					quantity = hud.getNumArmor();
					break;
				case 3: 
					upgradeName = "Decrease Size";
					quantity = hud.getNumShrink();
					if (hud.getNumHealth() >= 8) canAfford = false;
					break;
				case 4: 
					upgradeName = "Health Regeneration";
					quantity = hud.getNumRegen();
					break;
				case 5:
					upgradeName = "Extra Life";
					quantity = hud.getExtraLives();
					break;
				}
				
				int price = (int) (hud.getCost() * ((double) (quantity + 1) / 2));
				if(hud.getScore() >= price) canAfford = true;
				else canAfford = false;

				//Create the row for the item name
				g.setColor(new Color(160, 110, 60));
				g.fillRect(120, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 20 , rowWidth, rowHeight);	//+20 is to offset passive label

				//Create the row for the price + buy button
				//default color
				g.setColor(new Color(60, 60, 60));	//grey
				//If we have enough money, make the box green
				if(canAfford) g.setColor(new Color(60, 160, 110)); //green
				//Make the box itself
				g.fillRect(120 + rowWidth, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight);	//+20 is to offset passive label

				//Create the price label
				//default color
				g.setColor(new Color(230, 100, 100));	//red
				//IF can afford, make text white
				if(canAfford) g.setColor(Color.white); 	//white
				//draw the price
				g.setFont(smallFont);
				g.drawString("BUY", 132 + rowWidth, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 50);
				g.drawString("(" + price + ")", 125 + rowWidth, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 72);
				g.setFont(mediumFont);

				//Create the quantity label
				//Set font to ROCKWELL :)
				g.setColor(Color.white);
				g.drawString(upgradeName, 130, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 65);	//65 centers the text vertically

				//Create the quant label
				g.drawString("x" + quantity, 65 + rowWidth, (i * (rowHeight + spaceBetweenRows) + storeYOffset + 65)); //65 aligns right && 65 centers the text vertically
			}


			//Right side of the Shop - Active items
			for( int i = 0; i < 2; i++) {
				//Set font to rockwell
				g.setFont(mediumFont);

				String upgradeName = "#ERR in Pause.java:285";
				int quantity = 0;

				switch(i) {
				case 0: 
					upgradeName = "Clear Screen";
					quantity = hud.getNumClear();
					break;
				case 1: 
					upgradeName = "Freeze Time";
					quantity = hud.getNumFreeze();
					break;
				}
				
				int price = (int) (hud.getActiveCost() * ((double) (quantity + 1) / 2));
				if(hud.getScore() >= price) canAfford = true;
				else canAfford = false;

				//Create the row for the item name
				g.setColor(new Color(160, 110, 60));
				g.fillRect((rectW / 2), (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 20 , rowWidth, rowHeight);	//+20 is to offset passive label

				//Create the row for the price + buy button
				//default color
				g.setColor(new Color(60, 60, 60));	//grey
				//If we have enough money, make the box green
				if(canAfford) g.setColor(new Color(60, 160, 110)); //green
				//Make the box itself
				g.fillRect((rectW / 2) + rowWidth, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight);	//+20 is to offset passive label

				//Create the price label
				//default color
				g.setColor(new Color(230, 100, 100));	//red
				//IF can afford, make text white
				if(canAfford) g.setColor(Color.white); 	//white
				//draw the price
				g.setFont(smallFont);
				g.drawString("BUY", (rectW / 2) + rowWidth + 12, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 50);
				g.drawString("(" + price + ")", (rectW / 2) + rowWidth + 5, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 72);
				g.setFont(mediumFont);

				//Create the quantity label
				//Set font to ROCKWELL :)
				g.setColor(Color.white);
				g.drawString(upgradeName, (rectW / 2) + 10, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 65);	//65 centers the text vertically

				//Create the quant label
				g.drawString("x" + quantity, (rectW / 2)  + rowWidth - 60, (i * (rowHeight + spaceBetweenRows) + storeYOffset + 65)); //65 aligns right && 65 centers the text vertically
			}
			for( int i = 0; i < 5; i++) {
				//Set font to rockwell
				g.setFont(mediumFont);

				String customizeColor = "#ERR in Pause.java:285";
				int color = 0;

				switch(i) {
				case 0: 
					customizeColor = "Red";
					color = 1;
					break;
				case 1: 
					customizeColor = "Blue";
					color = 2;
					break;
				case 2:
					customizeColor = "Pink";
					color = 3;
					break;
				case 3:
					customizeColor = "Green";
					color = 4;
					break;
				case 4:
					customizeColor = "White";
					color = 5;
					break;
				}
				//Create the row for the item name
				g.setColor(new Color(160, 110, 60));
				g.fillRect((rectW / 2), (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 320 , rowWidth, rowHeight);	//+20 is to offset passive label

				//Create the row for the price + buy button
				//default color
				g.setColor(new Color(60, 60, 60));	//grey
				g.setColor(new Color(60, 160, 110)); //green
				//Make the box itself
				g.fillRect((rectW / 2) + rowWidth, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 320, 75, rowHeight);	//+20 is to offset passive label

				//Create the price label
				//default color
				g.setColor(new Color(230, 100, 100));	//red
				g.setColor(Color.white); 	//white
				//draw the price
				g.setFont(smallFont);
				g.drawString("USE", (rectW / 2) + rowWidth + 12, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 350);
				//g.drawString("", (rectW / 2) + rowWidth + 5, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 375);
				g.setFont(mediumFont);

				//Create the quantity label
				//Set font to ROCKWELL :)
				g.setColor(Color.white);
				g.drawString(customizeColor, (rectW / 2) + 10, (i * (rowHeight + spaceBetweenRows)) + storeYOffset + 360);	//65 centers the text vertically

				//Create the quant label
				//g.drawString("x" + color, (rectW / 2)  + rowWidth - 60, (i * (rowHeight + spaceBetweenRows) + storeYOffset + 360)); //65 aligns right && 65 centers the text vertically
			}
				

			
			
			//Description
			g.drawString(this.getDescription(),120,950);

			// If the game is on the first page of the Help menu 
		} else if (this.game.gameState == STATE.PauseH1) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(bigFont);
			g.setColor(Color.white);
			g.drawString("Help", 900, 70);
			// Displays text in the Help menu 
			g.setFont(mediumFont);
			g.drawString("Waves: Simply use Arrow keys or WASD to move and avoid enemies.", 40, 300);
			g.drawString("One you avoid them long enough, a new batch will spawn in! Defeat each boss to win!", 40, 340);
			g.drawString("Press P to pause and un-pause", 40, 400);
			g.drawString("Press Enter to use abilities when they have been equipped", 40, 440);
			g.drawString("Click Next to see Enemy and Boss Summaries", 40, 800);

			// Next button 
			g.setFont(mediumFont);
			g.setColor(Color.white);
			g.drawRect(1600, 870, 200, 65);
			g.drawString("Next", 1650, 910);
			// Main menu button 
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);

			// If the game is on the second page of the Help menu 
		} else if (game.gameState == STATE.PauseH2){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(bigFont);
			g.setColor(Color.white);

			// Displays text in the Help menu 
			g.drawString("Different Enemies", 800, 70);
			g.setFont(mediumFont);
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
			g.setFont(mediumFont);
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

			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(bigFont);
			g.setColor(Color.white);

			// Displays text in the Help menu 
			g.drawString("The Bosses", 830, 70);
			g.setFont(mediumFont);
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
			g.setFont(mediumFont);
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