/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The GameOver class is used to define the game over screen of the game when the 
 *player loses.
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

// Start of class
public class GameOver {

	// Instance variables 
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;
	
	// Main constructor 
	public GameOver(Game game, Handler handler, HUD hud, Leaderboard leaderboard) throws MalformedURLException {
		this.handler = handler;
		this.hud = hud;
		// Sets how long the game over screen appears 
		timer = 90;
		this.retryColor = Color.white;
	}
	
	// Methods 
	// Used to send score to the leaderboard 
	public void sendScore() {
		// Asks player to submit a score 
		String username = JOptionPane.showInputDialog("Enter a username to submit your score!");
		// Try to create a new filewriter to record the name 
		try {
			FileWriter fw = new FileWriter("leaderboard.csv", true);
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(username + "," + hud.getScore());
			writer.newLine();
			writer.close();
			// Prints out if score was successfully recorded
			System.out.println("successfully sentScore(): " + username + ": " + hud.getScore());
			// HUD class sorts players if multiple scores are added 
			hud.sortLeaderboard();
		// If the player's score did not get recorded, throw an exception 
		} catch(IOException e) {
			System.out.println("sendScore() failed in GameOver.java: " + e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}
	
	// Used to define time in the game 
	public void tick(){
		// Clear screen when game is over 
		handler.clearPlayer();
		handler.clearCoins();
		handler.clearEnemies();
		handler.clearUpgrades();
		flash();
	}

	// Used to create graphics of game over screen 
	public void render(Graphics g) {
		// Defines the text
		Font font = FontHandler.setSize(FontHandler.BODY_FONT, 95);
		Font font2 = FontHandler.setSize(FontHandler.BODY_FONT, 50);
		g.setFont(font);
		text = "Game Over";
		// Draws text in designated position 
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(font2);
		// Records current level 
		text = "Level: " + hud.getLevel();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 50);
		// Records current score 
		text = "Score: " + hud.getScore();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 50);
		g.setColor(this.retryColor);
		g.setFont(font2);
		// Allows user to continue if screen is clicked 
		text = "Click anywhere to play again";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 150);

	}

	// Used to display the retry button 
	public void flash() {
		// Decrease the time 
		timer--;
		// When timer reaches 45 milliseconds, set text to black 
		if (timer == 45) {
			this.retryColor = Color.black;
		// When timer reaches 0 milliseconds, set text to white 
		} else if (timer == 0) {
			this.retryColor = Color.white;
			// Restart timer 
			timer = 90;
		}
	}

	// Used to get pixel width of the text 
	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}
}