/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Game class compiles all the classes in the WaveGame for the complete game
 *product.
 */
package mainGame;

// Imports 
import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.net.MalformedURLException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import org.json.JSONException;
import io.socket.SocketIO;

// Start of class
// Is a subclass of Canvas and implements methods from the Runnable interface
public class Game extends Canvas implements Runnable {
	
	// Instance variables 
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1920, HEIGHT = 1080;
	public static final int drawWidth = 1280, drawHeight = 720;
	public static double scaleFactor;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private HUD hud;
	private SpawnEasy spawnerE;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private Menu menu;
	private GameOver gameOver;
	private GameWon gameWon;
	private UpgradeScreen upgradeScreen;
	private MouseListener mouseListener;
	private Upgrades upgrades;
	private Player player;
	private Pause pause;
	private Leaderboard leaderboard;
	public STATE gameState = STATE.Menu;
	public static int TEMP_COUNTER;
	private Midi menuMIDIPlayer;
	private String menuMIDIMusic = "m64file_synth.mid";
	private Midi gameMIDIPlayer;
	private String gameMIDIMusic = "Danza_Kuduro_synth.mid";
	private Midi bossMIDIPlayer;
	private String bossMIDIMusic = "sm1castl_synth.mid";
	private String ShopMIDIMusic = "Fresh-Prince-Belair.mid";
	private Midi ShopMIDIPlayer;
	float originalTempoGAME;
	private Game game;
	private Color bgColor = Color.black;
	private Color[] bgColorArr = new Color[11];
	private File _inFile;
	private Scanner _fileInput;
	private Boolean isGameSaved;
	private GameSave savedGame;	
	public SocketIO socket;
	private Midi upgradeMidiPlayer;
	private String upgradeMIDIMusic = "Bandit_Radio_synth.midi";
	// Creates different menus the player can access 
	public enum STATE {
		Menu, Help, Help2, Help3, Game, GameOver, GameWon, Upgrade, Boss, Pause, PauseH1, PauseH2, PauseH3, PauseShop, Leaderboard, GameWonEasy, GameEasy
	};

	// Main constructor 
	// Throws exception if reference material is not found 
	public Game() throws MalformedURLException {

		// Sets the screen 
		scaleFactor = (double) drawWidth / (double) WIDTH;
		// Holds saved files for the game 
		this.readFromSavedGameFile("gameSavesFile.txt");
		// Creates new instances of each class to be referenced 
		handler = new Handler();
		hud = new HUD();
		spawnerE = new SpawnEasy(this.handler, this.hud, this.spawnerE, this);
		spawner = new Spawn1to10(this.handler, this.hud, this);
		spawner2 = new Spawn10to20(this.handler, this.hud, this.spawner, this);
		menu = new Menu(this, this.handler, this.hud, this.spawner);
		upgradeScreen = new UpgradeScreen(this, this.handler, this.hud);
		// Creates new player and defines its width and height 
		player = new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, this.hud, this);
		upgrades = new Upgrades(this, this.handler, this.hud, this.upgradeScreen, this.player, this.spawnerE, this.spawner,
				this.spawner2);
		gameOver = new GameOver(this, this.handler, this.hud, this.leaderboard);
		gameWon = new GameWon(this, this.handler, this.hud);
		pause = new Pause(this.hud, this, this.handler, false, this.spawner, this.spawner2, spawnerE, upgrades);
		leaderboard = new Leaderboard(this,this.handler,this.hud);
		mouseListener = new MouseListener(this, this.handler, this.hud, this.spawnerE, this.spawner, this.spawner2, this.upgradeScreen,
				this.player, this.upgrades, pause);
		// Adds keyListener to track what keys are pressed on the keyboard 
		this.addKeyListener(new KeyInput(this.pause, this.handler, this, this.hud, this.player, this.spawner, this.upgrades));
		// Adds a mouseListener to track what the player clicks and where the player moves 
		this.addMouseListener(mouseListener);
		// Creates new music files 
		gameMIDIPlayer = new Midi();
		menuMIDIPlayer = new Midi();
		bossMIDIPlayer = new Midi();
		upgradeMidiPlayer = new Midi();
		ShopMIDIPlayer = new Midi();
		// Creates screen the game is played on 
		new Window((int) drawWidth, (int) drawHeight, "Wave Game ", this);

		bgColorArr[0] = new Color(175, 120, 120);
		bgColorArr[1] = new Color(125, 175, 120);
		bgColorArr[2] = new Color(165, 120, 175);
		bgColorArr[3] = new Color(110, 120, 170);
		bgColorArr[4] = new Color(170, 110, 150);
		bgColorArr[5] = new Color(95, 170, 170);
		bgColorArr[6] = new Color(95, 170, 135);
		bgColorArr[7] = new Color(170, 140, 95);
		bgColorArr[8] = new Color(50, 50, 50);
		bgColorArr[9] = new Color(140, 95, 170);
		bgColorArr[10] = new Color(95, 170, 150);
		
		// Retrieves any data previously stored on the leaderboard 
		leaderboard.retrieveData();
	}

	// Methods
	// Sets level, health, and score of player in Wave mode 
	public void setGameStats(){
		hud.setLevel(savedGame.getLevel());
		hud.setHealth(savedGame.getHealth());
		hud.setScore(savedGame.getScore());
		
		// If the player's saved game is greater or equal to level 10, set LevelsRemaining and 
		// LevelNumber variables and restart the counter 
		if(savedGame.getLevel() <= 10){
			spawner.setLevelsRemaining(savedGame.getLevelsRem());
			spawner.setLevelNumber(savedGame.getEnemy());
			spawner.resetTempCounter();
		// Else spawn enemies from Spawn1to10 class 
		} else {
			Spawn1to10.LEVEL_SET = 2;
			spawner2.setLevelNumber(savedGame.getEnemy());
			spawner2.setRandomMax(savedGame.getLevelsRem());
			spawner2.resetTempCounter();
			// Get any previously saved ability and abilities used data
			upgrades.setAbility(savedGame.getAbility());
			hud.setAbilityUses(savedGame.getAbilityUses());
		}
	}
	
	// Used to check if there is a saved game 
	public void setIsGameSaved(Boolean b){
		isGameSaved = b;
	}
	
	// Used to return the state of the game 
	public Boolean getIsGameSaved(){	
		return isGameSaved;
	}
	
	// Used to check if the game is running 
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	// Checks to see if there are any saved games 
	public void readFromSavedGameFile(String inputFile){
		// Checks system if there is a saved game 
		try {
			_inFile =  new File(inputFile); 
			_fileInput = new Scanner(_inFile);
			// If there is no saved game, return that there is no saved game 
			if(_fileInput.nextInt() == 0){
				isGameSaved = false;
			// Else if saved game is found, return all data used in the game 
			} else {
				isGameSaved = true;
				do{
					// Returns all data from the saved file 
					_fileInput.nextLine();
					String name = _fileInput.next();
					int score = _fileInput.nextInt();
					double health = _fileInput.nextDouble();
					int level = _fileInput.nextInt();
					int enemy = _fileInput.nextInt();
					int lvlRem = _fileInput.nextInt();
					String ability = _fileInput.next();
					int abilityUses = _fileInput.nextInt();
					// Updates the saved game file 
					savedGame = new GameSave(name, score, health, level, enemy, lvlRem, ability, abilityUses);
					
					} while( _fileInput.hasNextLine());
				}
			// If file is not found, close the game 
			} catch (FileNotFoundException e){
			System.out.println(e);
			System.exit(1);
		}
	}
	
	// Used to check if the game is running 
	public synchronized void stop() {
		// Tests to see if the game runs 
		try {
			thread.join();
			running = false;
		// If the game does not run, throw an exception 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Used to keep track of FPS and refresh rate 
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		// Checks FPS rate
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		double maxRefreshRate = 0;
		// If multiple monitors are used to play the game 
		for (int i = 0; i < gs.length; i++) {
			DisplayMode dm = gs[i].getDisplayMode();
			int refreshRate = dm.getRefreshRate();
			// Default of 60 FPS given if system cannot supply its own FPS rate 
			if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
				System.out.println("Unknown refresh rate");
				// If FPS rate is larger than 60, display FPS rate as 60 
				if (maxRefreshRate < 60) {
					maxRefreshRate = 60;
				}
			// Defines refresh rate of system
			} else {
				System.out.println("Refresh Rate for Screen " + i + " : " + refreshRate);
				// If refreshRate is larger than maxRefreshRate, set number as new maxRefreshRate
				if (refreshRate >= maxRefreshRate) {
					maxRefreshRate = refreshRate;
				}
			}
		}
		System.out.println("Using refresh rate: " + maxRefreshRate);
		// Set refreshRate to maxRefreshRate
		double nsScreen = 1000000000 / maxRefreshRate;
		double deltaScreen = 0;
		// While the game is running, define the refreshRate 
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			deltaScreen += (now - lastTime) / nsScreen;
			lastTime = now;
			// While the game is running, try to run in - game time 
			while (delta >= 1) {
				try {
					tick();
				// If game cannot run properly 
				} catch (IOException e) {
					e.printStackTrace();
				// If objects cannot be defined 
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// Decrease the maxRefreshRate
				delta--;
			}
			// While the screen is refreshing, render needed objects and increase FPS 
			while (deltaScreen >= 1) {
				render();
				frames++;
				deltaScreen--;
			}
			// If the refreshRate falls under 1000 milliseconds, print out the FPS rate, the 
			// gameState, and level set 
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				System.out.println(gameState);
				System.out.println(Spawn1to10.LEVEL_SET);
				frames = 0;
			}
		}
		// Stop the refreshRate timer 
		stop();
	}

	// Used to define time in the game
	private void tick() throws IOException, JSONException {
		// If the game is paused in any way, stop the background music 
		if(gameState == STATE.Pause || gameState == STATE.PauseShop || gameState == STATE.PauseH1 || gameState == STATE.PauseH2 || gameState == STATE.PauseH3 || gameState == STATE.Leaderboard){
			bossMIDIPlayer.StopMidi();
			upgradeMidiPlayer.StopMidi();
			menuMIDIPlayer.StopMidi();
			gameMIDIPlayer.StopMidi();
			
			// Set shop music as background music 
			try {
				ShopMIDIPlayer.PlayMidi(ShopMIDIMusic);
			// If music is not found, throw an error 
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		} else {
		// Make sure Handler class is updating 
		handler.tick();
		// If the player is playing the game but is not playing levels 1 - 11, make sure the HUD class 
		// is updating 
		if (gameState == STATE.Game || gameState == STATE.Boss && Spawn1to10.LEVEL_SET != 3) {
			hud.tick();
			// If the player is playing levels 1 - 10, run through the Spawn1to10 class 
			if (Spawn1to10.LEVEL_SET == 1) {
				spawner.tick();
			// If the player is playing levels 11 - 20, run through the Spawn10to20 class 
			} else if (Spawn1to10.LEVEL_SET == 2) {
				spawner2.tick();
			}
			// If the player is playing easy mode or boss mode, make sure the HUD class is updating 
			} else if (gameState == STATE.GameEasy || gameState == STATE.Boss) {
			hud.tick();
			// If the player is playing easy mode, run through the SpawnEasy class
			if (SpawnEasy.LEVEL_SET == 1) {
				spawnerE.tick();
			// Else if game state cannot be identified, run the Spawn1to10 class
			} else {
				spawner.tick();
		}
			// If the player is on the menu or help screen, make sure the Menu class is updating 
			} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Help2 || gameState == STATE.Help3) {
			menu.tick();
			// If the player is on the upgrade screen, make sure the UpgradeScreen class is updating 
			} else if (gameState == STATE.Upgrade) {
			upgradeScreen.tick();
			// If the player is on the game over screen, make sure the GameOver class is updating 
			} else if (gameState == STATE.GameOver) {
			gameOver.tick();
			// If the player won the game, make sure the GameWon class is updating 
			} else if (gameState == STATE.GameWon){
			// Record player's score 
			gameWon.highscore = true;
			gameWon.tick();
			// If the player won easy mode of the game, make sure to update the GameWon class
			} else if (gameState == STATE.GameWonEasy){
			// Do not record player's score 
			gameWon.highscore = false;
			gameWon.tick();
			}
		// Keeps track of music files in game 
		// If player is starting the game, stop all background music 
		if (gameState == STATE.Game) {
			bossMIDIPlayer.StopMidi();
			upgradeMidiPlayer.StopMidi();
			menuMIDIPlayer.StopMidi();
			ShopMIDIPlayer.StopMidi();
			// Try to play game music 
			try {
				gameMIDIPlayer.PlayMidi(gameMIDIMusic);
				originalTempoGAME = gameMIDIPlayer.getTempo();
			// Throw exception is music file is not found 
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
			// If the player is on levels 11 - 20, make sure to play the correct music 
			if (Spawn1to10.LEVEL_SET == 2) {
				gameMIDIPlayer.setTempo(160);
			}
			// If the player is on levels 1 - 10, make sure to play the correct music 
			else if (Spawn1to10.LEVEL_SET == 1) {
				gameMIDIPlayer.setTempo(originalTempoGAME);
			}
		// If the player is on the menu or help screens, stop all background music 
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Help2 || gameState == STATE.Help3) {
			gameMIDIPlayer.StopMidi();
			bossMIDIPlayer.StopMidi();
			upgradeMidiPlayer.StopMidi();
			ShopMIDIPlayer.StopMidi();
			// Try to play the menu music 
			try {
				menuMIDIPlayer.PlayMidi(menuMIDIMusic);
			// If music does not play, throw exception 
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		// If the player is on the boss levels, stop all background music 
		}else if (gameState == STATE.Boss) {
			gameMIDIPlayer.StopMidi();
			menuMIDIPlayer.StopMidi();
			ShopMIDIPlayer.StopMidi();
			// Try to play the boss music 
			try {
				bossMIDIPlayer.PlayMidi(bossMIDIMusic);
			// If music does not play, throw exception 
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		// If the player is on the upgrade screen, stop all background music 
		}  else if (gameState == STATE.Upgrade) {
			bossMIDIPlayer.StopMidi();
			ShopMIDIPlayer.StopMidi();
			// Try to play the upgrade screen music 
			try {
				upgradeMidiPlayer.PlayMidi(upgradeMIDIMusic);
			// If music does not play, throw exception 
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		// If not on any of the game screens, stop all music 
		} else {
			gameMIDIPlayer.StopMidi();
			menuMIDIPlayer.StopMidi();
			bossMIDIPlayer.StopMidi();
			upgradeMidiPlayer.StopMidi();
			ShopMIDIPlayer.StopMidi();
		}	
		}
	}

	// Used to create objects in the game 
	private void render() {
		// Used to buffer game according to computer recommendations
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			// If computer does not recommend anything, create new buffer strategy 
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		// Draw graphics according to scale 
		g.scale(scaleFactor, scaleFactor);
		// Set the color of the brush 
		g.setColor(bgColor);
		g.fillRect(0, 0, (int) WIDTH, (int) HEIGHT);
		// Make sure Handler class is always updating 
		handler.render(g);
		// If game is paused at any point, draw HUD class or Pause class
		if (gameState == STATE.Pause || gameState == STATE.PauseH1 || gameState == STATE.PauseH2
				|| gameState == STATE.PauseH3 || gameState == STATE.PauseShop) {
			hud.render(g);
			pause.render(g);
		} else {
			// If game is being played at any point, draw respective levels 
			if (gameState == STATE.Game || gameState == STATE.Boss || gameState == STATE.GameEasy) {
				hud.render(g);
			// If game is on the general menu or the help menu, draw Menu class 									
			} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Help2
					|| gameState == STATE.Help3) {
				menu.render(g);
			// If game is on upgrade screen, draw UpgradeScreen class
			} else if (gameState == STATE.Upgrade) {
				upgradeScreen.render(g);
			// If game is over, draw GameOver class
			} else if (gameState == STATE.GameOver) {
				gameOver.render(g);
			// If game is won in Waves mode or Easy mode, draw GameWon class 
			} else if (gameState == STATE.GameWon || gameState == STATE.GameWonEasy) {
				gameWon.render(g);
			// If game is in leaderboard menu, draw Leaderboard class 
			} else if (gameState == STATE.Leaderboard){
				leaderboard.render(g);
			}
		}
		// Else do not display any graphics 
		g.dispose();
		bs.show();
	}

	// Used to check if player and enemies are on screen 
	public static double clamp(double var, double min, double max) {
		// If position is larger than max position, set position at max 
		if (var >= max)
			return var = max;
		// If position is smaller than min position, set position at min 
		else if (var <= min)
			return var = min;
		// Else do not change position 
		else
			return var;
	}

	// Used to get the horizontal position of player 
	public int getPlayerXInt() {
		return (int) player.getX();
	}

	// Used to get the vertical position of player 
	public int getPlayerYInt() {
		return (int) player.getY();
	}

	public static void levelSetEasy() {
		SpawnEasy.LEVEL_SET = 1;
	}
	// Used to spawn level set 3
	public static void levelSet3() {
		Spawn1to10.LEVEL_SET = 3;
	}
	
	// Used when the game is over 
	public GameOver getGameOver(){
		return gameOver;
	}
	


	public void setRandomBg() {
		int rand = new Random().nextInt(10);	//Random number between 0 and 10
		bgColor = bgColorArr[rand];
	}
	// Used to run the main game 
	public static void main(String[] args) throws MalformedURLException {
		new Game();
	}
}
