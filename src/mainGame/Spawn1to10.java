/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Spawn1to10 class creates the levels 1 through 10 in the Wave mode of the game 
 *and defines the amount of time of each level, the enemies, and text that appears in each level. 
 */
package mainGame;

import java.util.ArrayList;
import java.util.Random;
import mainGame.Game.STATE;

// Start of class
public class Spawn1to10 {

	// Instance variables 
	public static int LEVEL_SET = 1;
	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random(1000);
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
	private LevelText welcomePit;
	private LevelText welcome1;
	private int randnumber;

	// Main constructor 
	public Spawn1to10(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		// Time to spawn enemy 
		spawnTimer = 1;
		// How long each level lasts
		levelTimer = 50;
		// Amount of levels in set 
		levelsRemaining = 10;
		hud.setLevel(1);
		tempCounter = 0;
		addLevels();
		index = r.nextInt(levelsRemaining);
		levelNumber = 0;	
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
		randnumber = getRandomInteger(100, 1);
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
				 welcome1 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Let's start off easy...",
						ID.Levels1to10Text, handler);
					handler.addObject(welcome1);
				LevelText controls = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Controls",
						ID.Levels1to10Text, handler);
						handler.addObject(controls);
				tempCounter++;
				
			}
			// If not playing any of the levels, clear the screen 
			if (levelTimer <= 0) {
				handler.removeObject(welcome1);
				handler.clearCoins();
				tempCounter = 0;
				index = r.nextInt(levelsRemaining - 5);
				levelNumber = levels.get(index);
				game.setRandomBg();
			}

		}
		
		// For this set of levels, generate coins
		if (levelNumber <= 20 && randnumber == 10) {
			// Generate coins 
			handler.addPickup(new PickupCoin(getRandomInteger(2000, 1),
			getRandomInteger(1000, 1), ID.PickupCoin, "images/PickupCoin.PNG", handler, game ));
		}
		// Level 1
		else if (levelNumber == 1) {
			
			if (spawning) {
				// Spawn the enemies 
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				handler.clearCoins();
				handler.addObject(welcome1 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler));
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another 
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 9, 9, ID.EnemyBasic, handler));
				spawnTimer = 50;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				handler.clearCoins();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 40;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					// If 5 levels are remaining, print out this message 
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					// Else print out this message 
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}

				game.setRandomBg();
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
				handler.clearCoins();
				LevelText welcome2 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome2);
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// Spawn enemy every 30 milliseconds 
			if (spawnTimer == 30) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 2, ID.EnemySweep, handler));
			// Spawn enemy every 20 milliseconds 
			} else if (spawnTimer == 20) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -2, ID.EnemySweep, handler));
			// Spawn enemy every 10 milliseconds 
			} else if (spawnTimer == 10) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 4, ID.EnemySweep, handler));
			// If one enemy is removed from screen, spawn another 
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -4, ID.EnemySweep, handler));
				spawnTimer = 80; 
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					// If 5 levels or less are remaining, print out this message 
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					// Else print out this message 
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}

				game.setRandomBg();
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
				handler.clearCoins();
				LevelText welcome3 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
					ID.Levels1to10Text, handler);
				handler.addObject(welcome3);
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another 
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -5, ID.EnemySmart, handler));
				spawnTimer = 100; //100
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					// If 5 or less levels are remaining, print out this message 
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					// Else print out this message 
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}

				game.setRandomBg();
			}
			// Level 4
		} else if (levelNumber == 4) {
			// Spawn the enemies 
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				handler.clearCoins();
				LevelText welcome4 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome4);
				levelTimer = 1000;
				tempCounter++;
				// If one enemy is removed from screen, spawn another 
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 100, 100,
						-20, ID.EnemyShooter, this.handler));
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					// If 5 or less levels are remaining, print out this message 
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					// Else print out this message 
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}

				game.setRandomBg();
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
				handler.clearCoins();
				LevelText welcome5 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
					ID.Levels1to10Text,handler);
				handler.addObject(welcome5);
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another 
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 100; //180
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				// Change level 
				if (levelsRemaining == 1) {
					levelNumber = 101;
				// And remove level from handler 
				} else {
					levels.remove(index);
					levelsRemaining--;
					// If 5 or less levels are remaining, print out this message 
					if (levelsRemaining > 5) {
						System.out.println("Level Number was: " + levelNumber + " And is staying low!");
						index = r.nextInt(levelsRemaining - 5);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber + "   " + index);
					}
					// Else print out this message
					else {
						System.out.println("Level Number was: " + levelNumber + " And is going up!");
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
						System.out.println("And is changing to: " + levelNumber);
					}
				}

				game.setRandomBg();
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
				handler.clearCoins();
				// Display level text
				if (levelsRemaining == 5) {
					LevelText welcome6 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome6);
					}
					else {
					LevelText welcome6 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome6);
					}
				
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another 
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 7, 7, ID.EnemyBasic, handler));
				spawnTimer = 50;
			} 
			// Spawn enemy every 35 milliseconds 
			if (spawnTimer == 35) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, 2, ID.EnemySweep, handler));
			// Spawn enemy every 25 milliseconds
			} else if (spawnTimer == 25) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, -2, ID.EnemySweep, handler));
			// Spawn enemy every 15 milliseconds 
			} else if (spawnTimer == 15) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, 4, ID.EnemySweep, handler));
			// If one enemy is removed from screen, spawn another
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, -4, ID.EnemySweep, handler));
				spawnTimer = 50;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
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

				game.setRandomBg();
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
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome7 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome7);
					}
					else {
					LevelText welcome7 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome7);
					}
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another 
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -8, ID.EnemySmart, handler));
				spawnTimer = 100;
			}
			// Spawn enemy every 35 milliseconds 
			if (spawnTimer == 35) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, 2, ID.EnemySweep, handler));
			// Spawn enemy every 25 milliseconds 
			} else if (spawnTimer == 25) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, -2, ID.EnemySweep, handler));
			// Spawn enemy every 15 milliseconds 
			} else if (spawnTimer == 15) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, 4, ID.EnemySweep, handler));
			// If one enemy is removed from screen, spawn another 
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 25, -4, ID.EnemySweep, handler));
				spawnTimer = 100;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
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

				game.setRandomBg();
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
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome8 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome8);
					}
					else {
					LevelText welcome8 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome8);
					}
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another 
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -8, ID.EnemySmart, handler));
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 50;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
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

				game.setRandomBg();
			}
		// Level 9
		} else if (levelNumber == 9) {
			// Spawn the enemies
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {	
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome9 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome9);
					}
					else {
					LevelText welcome9 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome9);
					}
				levelTimer = 1000;
				tempCounter++;
				// If one enemy is removed from screen, spawn another 
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 200, 200,
						-15, ID.EnemyShooter, this.handler));
				levelTimer = 2500;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another 
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 7, 7, ID.EnemyBasic, handler));
				spawnTimer = 50;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
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

				game.setRandomBg();
			}
		// Level 10
		} else if (levelNumber == 10) {
			// Spawn the enemies
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				handler.clearCoins();
				if (levelsRemaining == 5) {
					LevelText welcome10 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Let's step it up with Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome10);
					}
					else {
					LevelText welcome10 = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + hud.getLevel()),
						ID.Levels1to10Text,handler);
					handler.addObject(welcome10);
					}
				levelTimer = 1000;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 900){
				handler.clearLevelText();
			}
			// If one enemy is removed from screen, spawn another
			if (spawnTimer == 0) {
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 50;
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
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

				game.setRandomBg();
			}
		}

		// Boss level 
		else if (levelNumber == 101) {
			game.gameState = STATE.Boss;
			// Display level text
			if (tempCounter < 1) {
				handler.clearCoins();
				LevelText welcomePit = new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Welcome to the Pit"),
						ID.Levels1to10Text,handler);
				handler.addObject(welcomePit);
				handler.addObject(new EnemyBoss(ID.EnemyBoss, handler));
				tempCounter++;
			// Start the boss battle 
			} else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.EnemyBoss) {
						if (tempObject.getHealth() <= 0 && LEVEL_SET != 3) {
							handler.removeObject(tempObject);
							LEVEL_SET++;
							game.gameState = STATE.Upgrade;
							game.setRandomBg();
						}
						// Gets health of enemy 
						else if (tempObject.getHealth() <= 900){
							handler.clearLevelText();
						}
						// Player wins level 
						else if (tempObject.getHealth() <= 0 && LEVEL_SET == 3) {
							handler.removeObject(tempObject);
							game.gameState = STATE.GameWonEasy;
						}
						// Remove the text when enemy's health reaches 900 
						else if (tempObject.getHealth() == 900){
							handler.removeObject(welcomePit);
						}
					}
				}
			}
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
			tempCounter = 0;
			if (levelsRemaining > 5) {
				index = r.nextInt(levelsRemaining - 5);
				levelNumber = levels.get(index);
			}
			else {
				index = r.nextInt(levelsRemaining);
				levelNumber = levels.get(index);
			}
		}

		game.setRandomBg();
	}
	
// Used to create a random integer 
public static int getRandomInteger(int maximum, int minimum){
		
		return ((int) (Math.random()*(maximum - minimum))) + minimum;
		
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
		levels.clear();
		addLevels();
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