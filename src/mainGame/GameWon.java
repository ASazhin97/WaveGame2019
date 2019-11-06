/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The GameWon class is used to display the screen for when the player wins the game.  
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

// Start of class 
public class GameWon {

	// Instance variables 
	private Game game;
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;
	private int ticks = 0;
	public boolean highscore = true;
	private SimpleMidi winMIDIPlayer;
	private String winMIDIMusic = "Super_Mario_Bros._-_Flag_synth.mid";

	// Main constructor 
	public GameWon(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		// Sets the time of how long the screen appears for 
		timer = 90;
		this.retryColor = Color.white;
		// Plays background music 
		winMIDIPlayer = new SimpleMidi();
	}

	// Methods 
	// Used to define time in the game 
	public void tick() throws MalformedURLException, JSONException {
		// Clears screen and calls the flash method
		handler.clearPlayer();
		flash();

		// If the timer is 0 and there is a new high score, prompt player for username 
		if (ticks == 0 && highscore == true){
			String username = JOptionPane.showInputDialog("Enter a username");
			// Adds a new username and score to the game's files 
			JSONObject jsonString = new JSONObject()
					.put("username", username)
					.put("score", hud.getScore());
			game.socket.emit("setScore", jsonString);
		}
		// Else if timer equals 1, try to play background music 
		else if (ticks == 1){
			try {
				winMIDIPlayer.PlayMidi(winMIDIMusic);
			// If background music does not work, throw exception 
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		}
		// Increase timer 
		ticks++;
	}

	// Used to create graphics on the screen 
	public void render(Graphics g) {
		// Sets font and text
		Font font = new Font("Amoebic", 1, 100);
		Font font2 = new Font("Amoebic", 1, 60);
		g.setFont(font);
		text = "CONGRATS you WON the Game!!!";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(font2);
		// Gets current level 
		text = "Level: " + hud.getLevel();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 50);
		// Gets current score
		text = "Score: " + hud.getScore();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 50);
		g.setColor(this.retryColor);
		g.setFont(font2);
		// Allows user to return to menu if screen is clicked 
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