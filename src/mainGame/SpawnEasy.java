/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The SpawnEasy class creates the levels 1 through 10 in the Easy mode of the game 
 *and defines the amount of time of each level, the enemies, and text that appears in each level. 
 */
package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.net.URL;
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
	private String text;
	private int timer;
	private int y; 
	private LevelText welcome1;
	private int randnumber;
	
	// Main constructor 
	public SpawnEasy(Handler handler, HUD hud, SpawnEasy spawnerE, Game game) {
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
		levelsRemaining = 5;
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
				index = r.nextInt(levelsRemaining - 1);
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
			// Spawn the enemies 
			if (spawning) {
				spawnTimer--;
			}
			levelTimer--;
			// Display level text
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Level " + levelCounter),
						ID.LevelsSpawnEasy,handler));
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
					ID.LevelsSpawnEasy,handler);
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
					ID.LevelsSpawnEasy, handler);
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
					ID.LevelsSpawnEasy,handler);
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
					ID.LevelsSpawnEasy,handler);
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
		
		}

		// Boss level 
		else if (levelNumber == 101) {
			System.out.println("101");
			LEVEL_SET++;
			MouseListener.setEasy(true);
			Game.levelSetEasy();
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
		levelsRemaining = 5;
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
	
	// Used to get the controls image 
	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	

	// Used to create graphics 
	public void render(Graphics g) {
		// Time the text appears for 
		timer--;

		// Refers to the control text of the game 
		if (text == "Controls") {
			// Sets text
			Font font = FontHandler.setSize(FontHandler.BODY_FONT, 25);
			g.setFont(font);
			g.setColor(Color.CYAN);
			g.fillRect(1586, 10, 300, 300);
			g.setColor(Color.BLACK);
			g.drawString(this.text, 1675, (int) 29);
			// Sets the image 
			g.drawImage(this.getImage("/images/arrowkeys.png"), 1680, 100, 100, 100, null);
			// Text over the image 
			g.drawString("Left", 1600, 155);
			g.drawString("right", 1810, 155);
			g.drawString("up", 1710, 90);
			g.drawString("Down", 1695, 225);
			Font font2 = FontHandler.setSize(FontHandler.BODY_FONT, 18);
			g.setFont(font2);
			g.drawString("Pause Menu: PRESS P", 1588, 265);
			g.drawString("Activate Power-up: PRESS ENTER", 1588, 285);
		}
		// Else set the font again 
		else {
			Font font = FontHandler.setSize(FontHandler.HEADER_FONT, 125);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString(this.text, Game.WIDTH / 2 - getTextWidth(font, this.text) / 2, (int) this.y);
			// Removes text from screen when timer is done 
			if (timer == 0) {
				r.nextInt(9);
				timer = 15;
			} 
		}	
	}
	
	// Used to create a random integer 
	public static int getRandomInteger(int maximum, int minimum){
			
			return ((int) (Math.random()*(maximum - minimum))) + minimum;
			
			}
	
	// Used to get the width of the text 
	public int getTextWidth(Font font, String text) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}
}