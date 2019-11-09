/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The KeyInput class is used to translate from key presses on the keyboard to actions
 *in the game such as player movement and pausing the game. 
 */
package mainGame;

// Imports 
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import mainGame.Game.STATE;

// Start of class
// Uses the KeyAdapter interface 
public class KeyInput extends KeyAdapter {

	// Instance variables 
	private Handler handler;
	private boolean[] keyDown = new boolean[5];
	private int speed;
	private int diagonalSpeed;
	private Game game;
	private Upgrades upgrades;
	private String ability;
	private Pause pause;

	// Main constructor 
	public KeyInput(Pause pause, Handler handler, Game game, HUD hud, Player player, Spawn1to10 spawner, Upgrades upgrades) {
		this.handler = handler;
		this.speed = Player.playerSpeed;
		this.diagonalSpeed = Player.diagonalPlayerSpeed;
		this.game = game;
		this.upgrades = upgrades;
		this.pause = pause;
		// Checks if a certain key is pressed 
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		keyDown[4] = false;
	}

	// Methods 
	// Used for when a key is pressed, move the player in that direction 
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		this.speed = Player.playerSpeed;
		
		// If the player wants to pause the game, freeze the screen 
		if(key == 80){
			if(game.gameState == STATE.Game || game.gameState == STATE.GameEasy){
				pause.setGameSaved(false);
				game.gameState = STATE.Pause;
				
			// Else if the game is already paused, unfreeze the game 
			} else if (game.gameState == STATE.Pause || game.gameState == STATE.PauseH1 || game.gameState == STATE.PauseH2 || game.gameState == STATE.PauseH3 || game.gameState ==STATE.PauseShop){
				game.gameState = STATE.Game;
			}
		}
		// If the 'shift' key is pressed, activate the level skip ability 
		if (key == 88 && e.isShiftDown()) {
			upgrades.levelSkipAbility();
		}
		
		// If the player is on the menu screen, go to the leaderboard 
		if (key == 76){
			if (game.gameState == STATE.Menu){
				game.gameState = STATE.Leaderboard;
			// Else if the player is on the leaderboard, return to the menu screen 
			} else if (game.gameState == STATE.Leaderboard){
				game.gameState = STATE.Menu;
			}
		}
		
		// Finds what key is pressed by the player 
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			// If multiple keys are pressed, move in both directions 
			if (tempObject.getId() == ID.Player) {

				// Up key movement 
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
					tempObject.setVelY(-(this.speed));
					keyDown[0] = true;
				}
		
				// Left key movement 
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
					tempObject.setVelX(-(this.speed));
					keyDown[1] = true;
				}
				
				// Down key movement 
				if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
					tempObject.setVelY(this.speed);
					keyDown[2] = true;
				}
				
				// Right key movement 
				if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
					tempObject.setVelX(this.speed);
					keyDown[3] = true;
				}
				
				// Diagonal up and left key movement 
				if (keyDown[0] == true && keyDown[1] == true) {
					tempObject.setVelY(-(this.diagonalSpeed));
					tempObject.setVelX(-(this.diagonalSpeed));
				}
				// Diagonal up and right key movement 
				if (keyDown[0] == true && keyDown[3] == true) {
					tempObject.setVelY(-(this.diagonalSpeed));
					tempObject.setVelX(this.diagonalSpeed);
				}
				
				// Diagonal down and left key movement 
				if (keyDown[2] == true && keyDown[1] == true) {
					tempObject.setVelY(this.diagonalSpeed);
					tempObject.setVelX(-(this.diagonalSpeed));
				}
				
				// Diagonal down and right key movement 
				if (keyDown[2] == true && keyDown[3] == true) {
					tempObject.setVelY(this.diagonalSpeed);
					tempObject.setVelX(this.diagonalSpeed);
				}

				// If the space bar is pressed, activate the level skip ability 
				if (key == KeyEvent.VK_SPACE) {
				}
				
				// If the enter key is pressed, activate whatever ability the user has 
				if (key == KeyEvent.VK_ENTER) {
					ability = upgrades.getAbility();
					if (ability.equals("clearScreen")) {
						upgrades.clearScreenAbility();
					} else if (ability.equals("levelSkip")) {
						upgrades.levelSkipAbility();
					} else if (ability.equals("freezeTime")) {
						upgrades.freezeTimeAbility();
					}
				}
			}
		}
	}

	// Used to keep track of if the key is released 
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// For each key code, use the appropriate response 
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			// Focus on player 
			if (tempObject.getId() == ID.Player) {
				// Stop moving up when key is released 
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
					keyDown[0] = false;
				// Stop moving left when key is released
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
					keyDown[1] = false;
				// Stop moving down when key is released
				if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
					keyDown[2] = false;
				// Stop moving right when key is released
				if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
					keyDown[3] = false;
					keyDown[4] = false;
				}
				// Controls vertical movement 
				if (!keyDown[0] && !keyDown[2])
					tempObject.setVelY(0);
				// Controls horizontal movement 
				if (!keyDown[1] && !keyDown[3])
					tempObject.setVelX(0);
				
				// If up and down key are pressed, cancel out movement 
				if ((key == KeyEvent.VK_W || key == KeyEvent.VK_UP) && keyDown[2] == true){
					tempObject.setVelY(this.speed);
				}
				// If left and right key are pressed, cancel out movement 
				if ((key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) && keyDown[3] == true){
					tempObject.setVelX(this.speed);
				}
				// If down and up key are pressed, cancel out movement 
				if ((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) && keyDown[0] == true){
					tempObject.setVelY(-(this.speed));
				}
				// If right and left key are pressed, cancel out movement 
				if ((key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) && keyDown[1] == true){
					tempObject.setVelX(-(this.speed));
				}
			}
		}
	}
}