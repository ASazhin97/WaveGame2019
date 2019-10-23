package mainGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import mainGame.Game.STATE;

/**
 * Handles all mouse input
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class MouseListener extends MouseAdapter {

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

	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() / Game.scaleFactor);
		int my = (int) (e.getY() / Game.scaleFactor);
		
		//game.socket.emit("getBoard");

		if (game.gameState == STATE.GameOver) { //geting out of the game when game is over
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
			
		} else if (game.gameState == STATE.GameWon || game.gameState == STATE.GameWonEasy){ //is the game is won, or its the easy mode
			System.out.println("ass and titties");
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
		else if (game.gameState == STATE.Game) {
		}
		else if (game.gameState == STATE.GameEasy) {
		}
		else if (game.gameState == STATE.Upgrade) {
			if (mouseOver(mx, my, 100, 300, 1721, 174)) {
				upgradeText = upgradeScreen.getPath(1);
				upgrades.activateUpgrade(upgradeText);
				upgradeScreen.removeUpgradeOption(1);
				if (isEasy)
					game.gameState = STATE.GameEasy;
				else
					game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(2);
				upgrades.activateUpgrade(upgradeText);
				upgradeScreen.removeUpgradeOption(2);
				if (isEasy)
					game.gameState = STATE.GameEasy;
				else
					game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + 2 * (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(3);
				upgrades.activateUpgrade(upgradeText);
				upgradeScreen.removeUpgradeOption(3);
				if (isEasy)
					game.gameState = STATE.GameEasy;
				else
					game.gameState = STATE.Game;
			}
		}
		else if (game.gameState == STATE.Menu) {
			// Waves Button
			if (mouseOver(mx, my, 1050, 300, 350, 400)) {
				handler.object.clear();
				game.gameState = STATE.Game;
				
				
				if(game.getIsGameSaved()){
					game.setGameStats();
				}

				handler.addObject(player);
				// handler.addPickup(new PickupHealth(100, 100, ID.PickupHealth,
				// "images/PickupHealth.png", handler));
			}
			else if (mouseOver(mx, my, 1450, 300, 350, 400)) {
				handler.object.clear();
				game.gameState = STATE.GameEasy;

				handler.addObject(player);
			}
			// Help Button
			else if (mouseOver(mx, my, 80, 135, 850, 250)) {
				game.gameState = STATE.Help;
			}
			else if (mouseOver(mx,my,1050, 735, 750, 250)){
				game.gameState = STATE.Leaderboard;
			}
			
			// Credits
			else if (mouseOver(mx, my, 80, 435, 850, 250)) {
				JOptionPane.showMessageDialog(game,
						"Made by Brandon Loehle for his "
								+ "final project in AP Computer Science senior year, 2015 - 2016."
								+ "\n\nThis game is grossly unfinished with minor bugs. However,"
								+ " it is 100% playable, enjoy!");
			}
			// Quit Button
			else if (mouseOver(mx, my, 80, 735, 850, 250)) {
				System.exit(1);
			}
		}
		// Back Button for Help screen
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Menu;
				return;
			}
			
			if (mouseOver(mx, my, 1600, 870, 200, 65)){
				game.gameState = STATE.Help2;
			}
		}
		
		//back button for Second help screen to first help screen
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
		
		//Buttons for the third screen
		else if (game.gameState == STATE.Help3){
			if(mouseOver(mx, my, 100, 870, 200, 64)){
				game.gameState = STATE.Help2;
			}
			
			if(mouseOver(mx, my, 850, 870, 200, 64)){
				game.gameState = STATE.Menu;
			}
			
			
			
		}
		
		//for game in paused mode
		else if(game.gameState == STATE.Pause){
			
			
				if(mouseOver(mx, my, 550, 100, 900, 200)){
					handler.clearEnemies();
					handler.clearPlayer();
					spawner.resetTempCounter();
					spawner2.resetTempCounter();

					
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

				if(mouseOver(mx, my, 550, 400, 900, 200)){
					game.gameState = STATE.PauseH1;
				}
				
				if (this.mouseOver(mx, my, 550, 850, 900, 200)) {
				game.gameState = STATE.PauseShop;
				pause.setDescription("Click on an ability to see its description!");
				}

				if(mouseOver(mx, my, 550, 700, 900, 200)){
					pause.setGameSaved(true);
					pause.saveGame();
					pause.reset();
					
				}
				

		//buttons for the help pause menu
		}
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

			//Loadout Descriptions
			
			//Health Increase
			if(mouseOver(mx, my, 120, (0 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, rowWidth, rowHeight)){
				pause.setDescription("Increases player's maximum HP and heals the player to full.");
			}
			//Speed Boost
			if(mouseOver(mx, my, 120, (1 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, rowWidth, rowHeight)){
				pause.setDescription("Increases player's speed.");
			}
			//Damage Resistance
			if(mouseOver(mx, my, 120, (2 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, rowWidth, rowHeight)){
				pause.setDescription("Lowers damage taken by 25%.");
			}
			//Shrink
			if(mouseOver(mx, my, 120, (3 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, rowWidth, rowHeight)){
				pause.setDescription("Shrinks the player.");
			}
			//Health Regen
			if(mouseOver(mx, my, 120, (4 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, rowWidth, rowHeight)){
				pause.setDescription("Regenerates player's health at 0.25 HP per tick per purchase.");
			}
			//Extra Life
			if(mouseOver(mx, my, 120, (5 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20, rowWidth, rowHeight)){
				pause.setDescription("Gives the player an extra life.");
			}
			

			//Clear Screen
			if(mouseOver(mx,my, (1895 / 2), (0 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20 , rowWidth, rowHeight)){
				pause.setDescription("Clears all enemies off of the screen. Comes with 3 uses.");
			}
			//Freeze Time
			if(mouseOver(mx,my, (1895 / 2), (1 * (rowHeight + spaceBetweenRows)) + storeYOffset + 20 , rowWidth, rowHeight)){
				pause.setDescription("Freezes all enemies in place for a short period. Comes with 5 uses.");
			}
			//Back Button
			if(mouseOver(mx,my,1755,950,100,60)){
				game.gameState = STATE.Pause;
			}
		}
		else if(game.gameState == STATE.PauseH1){
			
			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Pause;
				
			}
			
			if(mouseOver(mx, my, 1600, 870, 200, 65)){
				game.gameState = STATE.PauseH2;
			}
			
		} else if(game.gameState == STATE.PauseH2){
			
			if(mouseOver(mx, my, 100, 870, 200, 64)){
				game.gameState = STATE.PauseH1;
			}
		
			if(mouseOver(mx, my, 1600, 870, 200, 65)){
				game.gameState = STATE.PauseH3;
			}
			
			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Pause;
				return;
			}
			
		} else if (game.gameState == STATE.PauseH3) {
			
			if(mouseOver(mx, my, 100, 870, 200, 64)){
				game.gameState = STATE.PauseH2;
			}
			
			if (mouseOver(mx, my, 850, 870, 200, 64)) {
				game.gameState = STATE.Pause;
			}
		} else if (game.gameState == STATE.Leaderboard){
			game.gameState = STATE.Menu;
		}
		
	}

	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Helper method to detect is the mouse is over a "button" drawn via Graphics
	 * 
	 * @param mx
	 *            mouse x position
	 * @param my
	 *            mouse y position
	 * @param x
	 *            button x position
	 * @param y
	 *            button y position
	 * @param width
	 *            button width
	 * @param height
	 *            button height
	 * @return boolean, true if the mouse is contained within the button
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}
	
	public static void setEasy(boolean x) {
		isEasy = x;
	}
}