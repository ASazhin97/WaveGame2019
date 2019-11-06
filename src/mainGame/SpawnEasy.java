/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The SpawnEasy class creates the levels 1 through 10 in the Easy mode of the game 
 *and defines the amount of time of each level, the enemies, and text that appears in each level. 
 */
package mainGame;

import java.util.ArrayList;
import java.util.Random;
import mainGame.Game.STATE;

// Start of class 
public class SpawnEasy {

	// Instance variables 
	public static int LEVEL_SET = 1;
	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random();
	private int randx, randy;
	private int spawnTimer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>(); 
	private int index;
	private int levelsRemaining;
	private int levelNumber = 0;
	private int tempCounter = 0;
	public static boolean spawning = true;
	private int levelCounter = 1;
	
	// Main constructor 
	public SpawnEasy(Handler handler, HUD hud, Spawn1to10 spawner, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		// Time to spawn enemy
		spawnTimer = 10;
		// How long each level lasts
		levelTimer = 150;
		// Amount of levels in set 
		levelsRemaining = 10;
		hud.setLevel(1);
		tempCounter = 0;
		addLevels();
		index = r.nextInt(levelsRemaining);
		levelNumber = 0;
		levelCounter = 1;
	}

	// Methods 
	// Used to add levels to the ArrayList to keep track of levels 
	public void addLevels() {
		for (int i = 1; i <= 10; i++) {
			levels.add(i);
		}
	}

	// Used to define time in the game 
	public void tick() {
		// Keeps track of player's horizontal position 
		if (game.getPlayerXInt() > (Game.WIDTH - Game.WIDTH/(6 + (2/3)) - 5)) {
			randx = r.nextInt((Game.WIDTH - (Game.WIDTH - game.getPlayerXInt())) - Game.WIDTH/(6 + (2/3)));
		} else if (game.getPlayerXInt() < Game.WIDTH/(6 + (2/3)) + 5) {
			randx = r.nextInt(Game.WIDTH - game.getPlayerXInt() - Game.WIDTH/(6 + (2/3))) + game.getPlayerXInt() + Game.WIDTH/(6 + (2/3));
		} else {
			if (r.nextInt(2) == 0) {
				randx = r.nextInt((Game.WIDTH - (Game.WIDTH - game.getPlayerXInt())) - Game.WIDTH/(6 + (2/3)));
			} else {
				randx = r.nextInt(Game.WIDTH - game.getPlayerXInt() - Game.WIDTH/(6 + (2/3))) + game.getPlayerXInt() + Game.WIDTH/(6 + (2/3));
			}
		}
		// Keeps track of player's vertical position 
		if (game.getPlayerYInt() > (Game.HEIGHT - Game.HEIGHT/(6 + (2/3))) - 5) {
			randy = r.nextInt((Game.HEIGHT - (Game.HEIGHT - game.getPlayerYInt())) - Game.HEIGHT/(6 + (2/3)));
		} else if (game.getPlayerYInt() < Game.HEIGHT/(6 + (2/3)) + 5) {
			randy = r.nextInt(Game.HEIGHT - game.getPlayerYInt() - Game.HEIGHT/(6 + (2/3))) + game.getPlayerYInt() + Game.HEIGHT/(6 + (2/3));
		} else {
			if (r.nextInt(2) == 0) {
				randy = r.nextInt(Game.HEIGHT - game.getPlayerYInt() - Game.HEIGHT/(6 + (2/3))) + game.getPlayerYInt() + Game.HEIGHT/(6 + (2/3));
			} else {
				randy = r.nextInt((Game.HEIGHT - (Game.HEIGHT - game.getPlayerYInt())) - Game.HEIGHT/(6 + (2/3)));
			}
		}
		
		// Before starting the set of levels, display this message 
		if (levelNumber <= 0) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "EASY MODE",
						ID.Levels1to10Text, handler));
				tempCounter++;
			}
			// If not playing any of the levels, clear the screen 
			if (levelTimer <= 0) {
				handler.clearEnemies();
				tempCounter = 0;
				levelCounter = 1;
				levelNumber = levels.get(index);
			}

		}
		
		// Level 1
		else if (levelNumber == 1) {
			// Spawn the enemies 
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
						ID.Levels1to10Text,handler));
				levelTimer = 2000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1900) {
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 7, 7, ID.EnemyBasic, handler));
				spawnTimer = 150;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 40;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 2
		} else if (levelNumber == 2) {
			// Spawn the enemies 
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				LevelText welcome2 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome2);
				levelTimer = 2000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (tempCounter	> 1900) {
				handler.clearLevelText();
			}
			// Spawn enemy every 60 milliseconds 
			if (spawnTimer == 60) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, 2, ID.EnemySweep, handler));
			// Spawn enemy every 40 milliseconds 
			} else if (spawnTimer == 40) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, -2, ID.EnemySweep, handler));
			// Spawn enemy every 20 milliseconds 
			} else if (spawnTimer == 20) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, 4, ID.EnemySweep, handler));
			// If one enemy is removed from screen, spawn another
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 13, -4, ID.EnemySweep, handler));
				spawnTimer = 120;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 3
		} else if (levelNumber == 3) {
			// Spawn the enemies 
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				LevelText welcome3 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text, handler);
				handler.addObject(welcome3);
				levelTimer = 1500;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1400) {
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -5, ID.EnemySmart, handler));
				spawnTimer = 150;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 4
		} else if (levelNumber == 4) {
			// Spawn the enemies 
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				LevelText welcome4 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome4);
				levelTimer = 1300;
				tempCounter++;
				// If one enemy is removed from screen, spawn another
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 100, 100,
						-10, ID.EnemyShooter, this.handler));
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1200) {
				
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 5 
		} else if (levelNumber == 5) {
			// Spawn the enemies 
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				LevelText welcome5 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome5);
				levelTimer = 1400;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text
			if (levelTimer == 1300) {
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 30, 30, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 180;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 6 
		} else if (levelNumber == 6) {
			// Spawn the enemies
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				LevelText welcome6 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome6);
				levelTimer = 1500;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text
			if (levelTimer == 1400) {
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 5, 5, ID.EnemyBasic, handler));
				spawnTimer = 50;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 40;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 7 
		} else if (levelNumber == 7) {
			// Spawn the enemies
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				LevelText welcome7= new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome7);
				levelTimer = 1200;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text
			if (levelCounter == 1100) {
				handler.clearLevelText();
			}
			// Spawn enemy every 200 milliseconds
			
			if (spawnTimer == 200) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 2, ID.EnemySweep, handler));
			// Spawn enemy every 100 milliseconds
			} else if (spawnTimer == 100) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -2, ID.EnemySweep, handler));
			// Spawn enemy every 50 milliseconds
			} else if (spawnTimer == 50) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 4, ID.EnemySweep, handler));
			// If one enemy is removed from screen, spawn another
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -4, ID.EnemySweep, handler));
				spawnTimer = 400;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 8 
		} else if (levelNumber == 8) {
			// Spawn the enemies
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				LevelText welcome8 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome8);
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text
			if (levelTimer == 900) {
				handler.clearLevelText();
				
			}
			// If one enemy is removed from screen, spawn another
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -3, ID.EnemySmart, handler));
				spawnTimer = 80;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 9 
		} else if (levelNumber == 9) {
			// Display level text
			LevelText welcome9 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
						ID.Levels1to10Text,handler);
			handler.addObject(welcome9);
			levelTimer--;
			// Spawn the enemies
			// If one enemy is removed from screen, spawn another
			if (tempCounter < 1) {	
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 200, 200,
						-10, ID.EnemyShooter, this.handler));
				levelTimer = 2000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text
			if (levelTimer == 2400) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		// Level 10 
		} else if (levelNumber == 10) {
			// Spawn the enemies
			if (spawning) {
				spawnTimer--;
			}
			// Display level text
			LevelText welcome10 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
						ID.Levels1to10Text,handler);
			handler.addObject(welcome10);
			levelTimer--;
			if (tempCounter < 1) {			
				levelTimer = 1400;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text
			if (levelCounter == 1300) {
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 25, 25, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 100;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		}

		// Boss level 
		else if (levelNumber == 101) {
			System.out.println("101");
			LEVEL_SET++;
			MouseListener.setEasy(true);
			Game.levelSet3();
			levelCounter = 1;
			game.gameState = STATE.Upgrade;
		}
	}

	// Used to use the skip level power - up
	public void skipLevel() {
		if (levelsRemaining == 1) {
			tempCounter = 0;
			levelNumber = 101;
		} else if (levelsRemaining > 1) {
			levels.remove(index);
			levelsRemaining--;
			System.out.println(levelsRemaining);
			levelCounter++;
			tempCounter = 0;
			index = r.nextInt(levelsRemaining);
			levelNumber = levels.get(index);
		}
	}
	
	// Used to spawn enemies
	public static void setSpawn(boolean x) {
		spawning = x;
	}

	// Used to restart the game 
	public void restart() {
		levelNumber = -10;
		tempCounter = 0;
		levelTimer = 150;
		levelsRemaining = 10;
		index = r.nextInt(levelsRemaining);
	}
	
	// Used to get the level number
	public int getLevelNumber(){
		return levelNumber;
	}
	
	// Used to get the amount of levels remaining 
	public int getLevelsRemaining(){
		return levelsRemaining;
	}
	
	// Used to set the amount of levels remaining 
	public void setLevelsRemaining(int levelRem){
		levelsRemaining = levelRem;
	}
	
	// Used to set the level number
	public void setLevelNumber(int l){
		levelNumber = l;
	}
	
	// Used to reset the temporary counter
	public void resetTempCounter(){
		tempCounter = 0;
	}
}