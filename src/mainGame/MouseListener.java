/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The MouseListener class keeps track of all mouse movements in the game including
 *anything that is clicked on the screen. 
 */
package mainGame;

// Imports 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import mainGame.Game.STATE;

// Start of class
// Is a subclass of MouseAdapter
public class MouseListener extends MouseAdapter {

	// Instance variables 
	private Game game;
	private Handler handler;
	private HUD hud;
	private SpawnEasy spawnerE;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private UpgradeScreen upgradeScreen;
	private Upgrades upgrades;
	private Player player;
	private String upgradeText;
	private Pause pause;
	public static boolean isEasy;

	// Main constructor 
	public MouseListener(Game game, Handler handler, HUD hud, SpawnEasy spawnerE, Spawn1to10 spawner, Spawn10to20 spawner2,
			UpgradeScreen upgradeScreen, Player player, Upgrades upgrades, Pause pause) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawnerE = spawnerE;
		this.spawner = spawner;
		this.spawner2 = spawner2;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.upgrades = upgrades;
		this.pause = pause;
	}

	// Methods
	// Used if the mouse is pressed 
	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() / Game.scaleFactor);
		int my = (int) (e.getY() / Game.scaleFactor);

		// If the game is over, click on the screen to get back to the menu 
		// Reset all variables 
		if (game.gameState == STATE.GameOver) {
			handler.object.clear();
			upgrades.resetUpgrades();
			hud.health = 100;
			hud.setScore(0);
			hud.setLevel(1);
			spawner.restart();
			spawnerE.restart();
			spawnerE.addLevels();

			spawner.addLevels();
			spawner2.restart();
			spawner2.addLevels();
			Spawn1to10.LEVEL_SET = 1;
			SpawnEasy.LEVEL_SET = 1;
			game.gameState = STATE.Menu;
			pause.unSaveGame();
			game.setIsGameSaved(false);
			MouseListener.setEasy(false);
			// Else if the player won the game, reset all the variables 
		} else if (game.gameState == STATE.GameWon || game.gameState == STATE.GameWonEasy){
			handler.object.clear();
			upgrades.resetUpgrades();
			hud.health = 100;
			hud.setScore(0);
			hud.setLevel(1);
			spawnerE.restart();
			spawnerE.addLevels();
			spawner.restart();
			spawner.addLevels();
			spawner2.restart();
			spawner2.addLevels();
			Spawn1to10.LEVEL_SET = 1;
			SpawnEasy.LEVEL_SET = 1;
			game.gameState = STATE.Menu;
			pause.unSaveGame();
			game.setIsGameSaved(false);
			MouseListener.setEasy(false);
		}
		// Else if the game is playing, do not do anything 
		else if (game.gameState == STATE.Game) {
		}
		// Else if the game is in easy mode, do not do anything 
		else if (game.gameState == STATE.GameEasy) {
		}
		// Else if the game is on the upgrade screen, follow where the player clicks 
		else if (game.gameState == STATE.Upgrade) {
			if (mouseOver(mx, my, 100, 300, 1721, 174)) {
				upgradeText = upgradeScreen.getPath(1);
				// Displays description of the upgrade
				upgrades.activateUpgrade(upgradeText);
				upgradeScreen.removeUpgradeOption(1);
				// If in Easy mode, bring the player back to Easy mode
				if (isEasy)
					game.gameState = STATE.GameEasy;
				// If in Wave mode, bring the player back to Wave mode
				else
					game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(2);
				// Displays description of the upgrade
				upgrades.activateUpgrade(upgradeText);
				upgradeScreen.removeUpgradeOption(2);
				// If in Easy mode, bring the player back to Easy mode
				if (isEasy)
					game.gameState = STATE.GameEasy;
				// If in Wave mode, bring the player back to Wave mode
				else
					game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + 2 * (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(3);
				// Displays description of the upgrade
				upgrades.activateUpgrade(upgradeText);
				upgradeScreen.removeUpgradeOption(3);
				// If in Easy mode, bring the player back to Easy mode
				if (isEasy)
					game.gameState = STATE.GameEasy;
				// If in Wave mode, bring the player back to Wave mode
				else
					game.gameState = STATE.Game;
			}
		}
		// If the player is on the game menu 
		else if (game.gameState == STATE.Menu) {
			// Waves Button
			if (mouseOver(mx, my, 1050, 300, 350, 400)) {
				handler.object.clear();
				game.gameState = STATE.Game;

				// If the game has a save file, get its stats
				if(game.getIsGameSaved()){
					game.setGameStats();
				}
				handler.addObject(player);
			}

			// If the player wants to go to Easy mode 
			else if (mouseOver(mx, my, 1450, 300, 350, 400)) {
				handler.object.clear();
				game.gameState = STATE.GameEasy;
				handler.addObject(player);
			}

			// If the player wants to go to the Help menu 
			else if (mouseOver(mx, my, 80, 135, 850, 250)) {
				game.gameState = STATE.Help;
			}

			// If the player wants to go to the Leaderboard 
			else if (mouseOver(mx,my,1050, 735, 750, 250)){
				game.gameState = STATE.Leaderboard;
			}

			// If the player wants to go to the credits 
			else if (mouseOver(mx, my, 80, 435, 850, 250)) {
				JOptionPane.showMessageDialog(game,
						"Made by Brandon Loehle for his "
								+ "final project in AP Computer Science senior year, 2015 - 2016."
								+ "\n\nThis game is grossly unfinished with minor bugs. However,"
								+ " it is 100% playable, enjoy!");
			}

			// If the player wants to exit the game 
			else if (mouseOver(mx, my, 80, 735, 850, 250)) {
				System.exit(1);
			}
		}

		// Back button for Help screen
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Menu;
				return;
			}
			if (mouseOver(mx, my, 1600, 870, 200, 65)){
				game.gameState = STATE.Help2;
			}
		}

		// Back button for second Help screen 
		else if (game.gameState == STATE.Help2){

			if(mouseOver(mx, my, 100, 870, 200, 64)){
				game.gameState = STATE.Help;
			}

			if(mouseOver(mx, my, 850, 870, 200, 64)){
				game.gameState = STATE.Menu;
			}

			if(mouseOver(mx, my, 1600, 870, 200, 65)){
				game.gameState = STATE.Help3;
			}

		}

		// Back button for third help screen 
		else if (game.gameState == STATE.Help3){
			if(mouseOver(mx, my, 100, 870, 200, 64)){
				game.gameState = STATE.Help2;
			}
			if(mouseOver(mx, my, 850, 870, 200, 64)){
				game.gameState = STATE.Menu;
			}
		}

		// If the game is paused, clear the screen 
		else if(game.gameState == STATE.Pause){
			if(mouseOver(mx, my, 550, 100, 900, 200)){
				handler.clearEnemies();
				handler.clearPlayer();
				spawner.resetTempCounter();
				spawner2.resetTempCounter();

				// When exiting the pause menu, restart the level 
				if(!pause.getGameSave()){
					handler.object.clear();
					upgrades.resetUpgrades();
					hud.health = 100;
					hud.setScore(0);
					hud.setLevel(1);
					spawner.restart();
					spawner.addLevels();
					spawner2.restart();
					spawner2.addLevels();
					Spawn1to10.LEVEL_SET = 1;
					pause.unSaveGame();
					game.setIsGameSaved(false);
				} 
				game.gameState = STATE.Menu;
			}

			// Help menu from the pause menu 
			if(mouseOver(mx, my, 550, 400, 900, 200)){
				game.gameState = STATE.PauseH1;
			}

			// Shop menu from the pause menu 
			if (this.mouseOver(mx, my, 550, 850, 900, 200)) {
				game.gameState = STATE.PauseShop;
				pause.setDescription("Click on an ability to see its description!");
			}

			// Save the game from the pause menu 
			if(mouseOver(mx, my, 550, 700, 900, 200)){
				pause.setGameSaved(true);
				pause.saveGame();
				pause.reset();
			}
		}
		// Shop menu buttons 
		else if(game.gameState == STATE.PauseShop){
			int rowHeight = 70;
			int rowWidth = 600;
			int spaceBetweenRows = 4;			
			int storeYOffset = 200;


			//Health Increase
			if (mouseOver(mx, my, 120 + rowWidth, (0 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight)) {
				if(hud.getScore()>=hud.getCost()) {
					hud.setScore(-(int)hud.getCost());
					hud.setCost(hud.getCost()*hud.getCostMultipier());
					hud.healthIncrease();
					hud.setNumHealth();
				}
			}
			//Speed Boost
			if (mouseOver(mx, my, 120 + rowWidth, (1 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight)) {
				if(hud.getScore()>=hud.getCost()) {
					hud.setScore(-(int)hud.getCost());
					hud.setCost(hud.getCost()*hud.getCostMultipier());
					upgrades.speedBoost();
					hud.setNumSpeed();
				}	
			}
			//Damage Resistance
			if (mouseOver(mx, my, 120 + rowWidth, (2 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight)) {
				if(hud.getScore()>=hud.getCost()) {
					hud.setScore(-(int)hud.getCost());
					hud.setCost(hud.getCost()*hud.getCostMultipier());
					upgrades.improvedDamageResistance();
					hud.setNumArmor();
				}
			}
			//Shrink
			if (mouseOver(mx, my, 120 + rowWidth, (3 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight)) {
				if(hud.getScore()>=hud.getCost()) {
					hud.setScore(-(int)hud.getCost());
					hud.setCost(hud.getCost()*hud.getCostMultipier());
					upgrades.decreasePlayerSize();
					hud.setNumShrink();
				}
			}
			//Health Regen
			if (mouseOver(mx, my, 120, (4 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, rowWidth, rowHeight)) {
				if(hud.getScore()>=hud.getCost()) {
					hud.setScore(-(int)hud.getCost());
					hud.setCost(hud.getCost()*hud.getCostMultipier());
					hud.setRegen();
					hud.setNumRegen();
					hud.setregenValue();
				}

			}
			//Extra Life
			if (mouseOver(mx, my, 120 + rowWidth, (5 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight)) {
				if(hud.getScore()>=hud.getCost()){
					hud.setScore(-(int)hud.getCost());
					hud.setCost(hud.getCost()*hud.getCostMultipier());
					hud.setExtraLives(hud.getExtraLives() + 1);
				}
			}

			//Clear Screen
			if (mouseOver(mx, my, (1895 / 2) + rowWidth, (0 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight)) {
				System.out.println("Clicked clear");
				if(upgrades.getAbility().equals("")) {
					if (hud.getScore() >= hud.getActiveCost()) {
						hud.setScore(-(int) hud.getActiveCost());
						hud.setActiveCost(hud.getActiveCost() * 2);
						upgrades.setAbility("clearScreen");
						hud.setNumClear();
					}
				}

				// Three uses for the Clear Screen power - up 
				else if(upgrades.getAbility().equals("clearScreen")){
					if (hud.getScore() >= hud.getActiveCost()) {
						hud.setScore(-(int) hud.getActiveCost());
						hud.setActiveCost(hud.getActiveCost() * 2);
						hud.setAbilityUses(3);
						hud.setNumClear();
					}
				}
			}
			//Freeze Time
			if (mouseOver(mx, my, (1895 / 2) + rowWidth, (1 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, 75, rowHeight)) {
				System.out.println("Clicked freez");
				if(upgrades.getAbility().equals("")){
					if (hud.getScore() >= hud.getActiveCost()) {
						hud.setScore(-(int) hud.getActiveCost());
						hud.setActiveCost(hud.getActiveCost() * 2);
						upgrades.setAbility("freezeTime");
						hud.setNumFreeze();
					}
				}
				else if(upgrades.getAbility().equals("freezeTime")){
					if (hud.getScore() >= hud.getActiveCost()) {
						hud.setScore(-(int) hud.getActiveCost());
						hud.setActiveCost(hud.getActiveCost() * 2);
						hud.setAbilityUses(5);
						hud.setNumFreeze();
					}
				}
			}

			// Ability descriptions 
			// Health regeneration ability 
			if(mouseOver(mx,my,1050,125,125,125)){
				pause.setDescription("Regenerates player's health at 0.25 HP per tick per purchase.");
			}
			// Health increase ability 
			if(mouseOver(mx,my,1250,125,125,125)){
				pause.setDescription("Increases player's maximum HP and heals the player to full.");
			}
			// Shrink ability 
			if(mouseOver(mx,my,1450,125,125,125)){
				pause.setDescription("Shrinks the player.");
			}
			// Damage resistance ability 
			if(mouseOver(mx,my,1650,125,125,125)){
				pause.setDescription("Lowers damage taken by 25%.");
			}
			// Speed boost ability 
			if(mouseOver(mx,my,1050,325,125,125)){
				pause.setDescription("Increases player's speed.");
			}
			// Extra life power - up 
			if(mouseOver(mx,my,1250,325,125,125)){
				pause.setDescription("Gives the player an extra life.");
			}
			// Freeze Time power - up 
			if(mouseOver(mx,my,1450,325,125,125)){
				pause.setDescription("Freezes all enemies in place for a short period. Comes with 5 uses.");
			}
			// Clear Screen power - up 
			if(mouseOver(mx,my,1650,325,125,125)){
				pause.setDescription("Clears all enemies off of the screen. Comes with 3 uses.");
			}
			// Back button 
			if(mouseOver(mx,my,1795,950,80,50)){
				game.gameState = STATE.Pause;
			}
		}
		// Pause the game if looking at the first page of the Help menu 
		else if(game.gameState == STATE.PauseH1){
			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Pause;
			}
			// Pause the game if looking at the second page of the Help menu 
			if(mouseOver(mx, my, 1600, 870, 200, 65)){
				game.gameState = STATE.PauseH2;
			}
			// Go from the second page of the Help menu to the first 
		} else if(game.gameState == STATE.PauseH2){

			if(mouseOver(mx, my, 100, 870, 200, 64)){
				game.gameState = STATE.PauseH1;
			}
			// Pause the game if looking at the third page of the Help menu 
			if(mouseOver(mx, my, 1600, 870, 200, 65)){
				game.gameState = STATE.PauseH3;
			}

			// Un - pause the game 
			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Pause;
				return;
			}
			// Go from the third page of the Help menu to the second 
		} else if (game.gameState == STATE.PauseH3) {

			if(mouseOver(mx, my, 100, 870, 200, 64)){
				game.gameState = STATE.PauseH2;
			}

			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Pause;
			}
			// Go to the leaderboard to the menu 
		} else if (game.gameState == STATE.Leaderboard){
			game.gameState = STATE.Menu;
		}
	}

	// Used to check when the mouse is released
	public void mouseReleased(MouseEvent e) {
	}

	// Used to check where the mouse is clicking 
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	// Used to set the game to Easy mode
	public static void setEasy(boolean x) {
		isEasy = x;
	}
}