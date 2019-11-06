/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Spawn10to20 class creates the levels 11 through 20 in the Wave mode of the game 
 *and defines the amount of time of each level, the enemies, and text that appears in each level. 
 */
package mainGame;

// Imports 
import java.util.ArrayList;
import java.util.Random;
import mainGame.Game.STATE;

// Start of class 
public class Spawn10to20 {

	// Instance variables 
	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random(1000);
	private int randx, randy;
	private int timer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>();
	private int index;
	private int randomMax;
	private int levelNumber = 0;
	private int tempCounter = 0;
	public static int LEVEL_SET_2_RESET = 0;
	public static boolean spawning = true;
	private int randnumber;
	private Random r1 = new Random();



	// Main constructor 
	public Spawn10to20(Handler handler, HUD hud, Spawn1to10 spawner, Game game) {
		restart();
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		hud.restoreHealth();
		// Amount of time per level 
		timer = 10;
		levelTimer = 150;
		randomMax = 10;
		hud.setLevel(1);
		tempCounter = 0;
		addLevels();
		index = r.nextInt(randomMax);
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
		// Adds coins to handler 
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
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Same levels...",
						ID.Levels1to10Text,handler));
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2, "...but a little harder now",
						ID.Levels1to10Text,handler));
				tempCounter++;
			}

			// If the level ended, clear the screen 
			if (levelTimer <= 0) {
				handler.clearEnemies();
				tempCounter = 0;
				levelNumber = levels.get(index);
			}
		}
		if (levelNumber <= 20 && randnumber == 10) {
			handler.addPickup(new PickupCoin(getRandomInteger(2000, 1),
					getRandomInteger(1000, 1), ID.PickupCoin, "images/PickupCoin.PNG", handler, game ));
		}

		// Level 11 
		else if (levelNumber == 1) {
			// Spawn the enemies 
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Hotshots"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			// If one enemy is removed from screen, spawn another 
			if (timer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 13, 13, ID.EnemyBasic, handler));
				timer = 80;
			}

			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 40;
				tempCounter = 0;
				// Change level 
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 12 
		} else if (levelNumber == 2) {
			// Spawn the enemies 
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Sweep"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			// Spawn enemy every 30 milliseconds 
			if (timer == 30) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 2, ID.EnemySweep, handler));
				// Spawn enemy every 20 milliseconds 
			} else if (timer == 20) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -2, ID.EnemySweep, handler));
				// Spawn enemy every 10 milliseconds 
			} else if (timer == 10) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 4, ID.EnemySweep, handler));
				// Spawn enemy when another enemy is removes 
			} else if (timer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -4, ID.EnemySweep, handler));
				timer = 45;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				// Change level 
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 13 
		} else if (levelNumber == 3) {
			// Spawn the enemies 
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Tail Chase"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			// If one enemy is removed from screen, spawn another 
			if (timer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -7, ID.EnemySmart, handler));
				timer = 60;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				// Change level 
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 14 
		} else if (levelNumber == 4) {
			// Spawn the enemies
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Target"),
						ID.Levels1to10Text,handler));
				// If one enemy is removed from screen, spawn another 
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 100, 100,
						-30, ID.EnemyShooter, this.handler));
				levelTimer = 1300;
				tempCounter++;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1120) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				// Change level 
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 15 
		} else if (levelNumber == 5) {
			// Spawn the enemies
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Burst"),
						ID.Levels1to10Text,handler));
				levelTimer = 1400;
				tempCounter++;
			}
			// If one enemy is removed from screen, spawn another 
			if (timer <= 0) {
				handler.addObject(new EnemyBurst(-250, 250, 75, 75, 250, side[r.nextInt(4)], ID.EnemyBurst, handler));
				timer = 120;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1220) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				// Change level 
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 16 
		} else if (levelNumber == 6) {
			// Spawn the enemies
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Seeing Red"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			// If one enemy is removed from screen, spawn another 
			if (timer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 15, 15, ID.EnemyBasic, handler));
				timer = 50;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen 
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 40;
				tempCounter = 0;
				// Change level 
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 17 
		} else if (levelNumber == 7) {
			// Spawn the enemies
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Big Sweep"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			// Spawn enemy every 35 milliseconds 
			if (timer == 35) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, 2, ID.EnemySweep, handler));
				// Spawn enemy every 25 milliseconds 
			} else if (timer == 25) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, -2, ID.EnemySweep, handler));
				// Spawn enemy every 15 milliseconds 
			} else if (timer == 15) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, 4, ID.EnemySweep, handler));
				// If one enemy is removed from screen, spawn another
			} else if (timer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, -4, ID.EnemySweep, handler));
				timer = 30;
			}
			// When level timer reaches a certain amount, clear the level text 
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			// When the level timer reaches 0, clear the screen
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				// Change level
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					// And remove level from handler 
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 18 
		} else if (levelNumber == 8) {
			// Spawn the enemies
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Run"),
						ID.Levels1to10Text,handler));
				levelTimer = 1000;
				tempCounter++;
			}
			// If one enemy is removed from screen, spawn another 
			if (timer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -9, ID.EnemySmart, handler));
				timer = 50;
			}
			// When the level timer reaches 0, clear the screen
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				// Change level
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 19 
		} else if (levelNumber == 9) {
			// Spawn the enemies
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Point Blank"),
						ID.Levels1to10Text,handler));
				// If one enemy is removed from screen, spawn another 
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 200, 200,
						-40, ID.EnemyShooter, this.handler));
				levelTimer = 2500;
				tempCounter++;
			}
			// When the level timer reaches 0, clear the screen
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				// Change level
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
			// Level 20 
		} else if (levelNumber == 10) {
			// Spawn the enemies
			if (spawning) {
				timer--;
			}
			levelTimer--;
			// Display level text 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Crazy Burst"),
						ID.Levels1to10Text,handler));
				levelTimer = 1400;
				tempCounter++;
			}
			// If one enemy is removed from screen, spawn another 
			if (timer <= 0) {
				handler.addObject(new EnemyBurst(-300, 300, 60, 60, 300, side[r.nextInt(4)], ID.EnemyBurst, handler));
				timer = 60;
			}

			// When the level timer reaches 0, clear the screen
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				// Change level
				if (randomMax == 1) {
					levelNumber = 101;
					// And remove level from handler 
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		}

		// Boss level 
		else if (levelNumber == 101) {
			game.gameState = STATE.Boss;
			// Start the boss battle 
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Can you win the gauntlet?"),
						ID.Levels1to10Text,handler));
				// Adds each assisting enemy for the boss
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 1));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 2));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 + 50, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 3));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 4));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 5));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 + 50, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 6));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 7));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 50, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 8));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 + 50, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 9));
				handler.clearLevelText();
				tempCounter++;
			}
			// Clear level text after certain amount of timer 
			else if (tempCounter == 1) {
				handler.clearLevelText();
			}
		}
	}

	// Used to use the skip level power - up 
	public void skipLevel() {
		if (randomMax == 1) {
			tempCounter = 0;
			levelNumber = 101;
		} else if (randomMax > 1) {
			levels.remove(index);
			randomMax--;
			tempCounter = 0;
			index = r.nextInt(randomMax);
			levelNumber = levels.get(index);
		}
	}

	public static int getRandomInteger(int maximum, int minimum){

		return ((int) (Math.random()*(maximum - minimum))) + minimum;

	}
	// Used to spawn various enemies 
	public static void setSpawn(boolean x) {
		spawning = x;
	}

	// Used to restart the game 
	public void restart() {
		levelNumber = -10;
		tempCounter = 0;
		levelTimer = 150;
		randomMax = 10;
		index = r.nextInt(randomMax);
	}

	// Used to get the level number 
	public int getLevelNumber(){
		return levelNumber;
	}

	// Used to set the level number 
	public void setLevelNumber(int l){
		levelNumber = l;
	}

	// Used to get the amount of levels remaining 
	public int getLevelsRemaining(){
		return randomMax;
	}

	// Used to set the amount of levels remaining 
	public void setLevelsRemaining(int levelRem){
	}

	// Used to set the RandomMax variable 
	public void setRandomMax(int n){
		randomMax = n;
	}

	// Used to reset the temporary counter
	public void resetTempCounter() {
		tempCounter = 0;
	}
}