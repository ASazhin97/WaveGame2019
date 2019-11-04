package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * The game over screen
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class GameOver {

	private Game game;
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;
	private int ticks = 0;
	private Leaderboard leaderboard;

	public GameOver(Game game, Handler handler, HUD hud, Leaderboard leaderboard) throws MalformedURLException {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.leaderboard = leaderboard;
		timer = 90;
		this.retryColor = Color.white;
	}
	
	public void sendScore() {
		
		String username = JOptionPane.showInputDialog("Enter a username to submit your score!");
		
		//©TameBeets
		try {
			//First we create a file and writing objects
			FileWriter fw = new FileWriter("leaderboard.csv", true);
			BufferedWriter writer = new BufferedWriter(fw);
			
			writer.write(username + "," + hud.getScore());
			writer.newLine();
			writer.close();
			
			System.out.println("successfully sentScore(): " + username + ": " + hud.getScore());
			
			//Retrieve new data
			//leaderboard.retrieveData();
			hud.sortLeaderboard();
			
		} catch(IOException e) {
			//Filewriting failed
			System.out.println("sendScore() failed in GameOver.java: " + e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}

	public void tick(){
		handler.clearPlayer();
		handler.clearCoins();
		flash();
	}

	public void render(Graphics g) {
		Font font = FontHandler.setSize(FontHandler.BODY_FONT, 95);
		Font font2 = FontHandler.setSize(FontHandler.BODY_FONT, 50);
		g.setFont(font);
		text = "Game Over";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(font2);
		text = "Level: " + hud.getLevel();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 50);
		text = "Score: " + hud.getScore();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 50);
		g.setColor(this.retryColor);
		g.setFont(font2);
		text = "Click anywhere to play again";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 150);

	}

	public void flash() {
		timer--;
		if (timer == 45) {
			this.retryColor = Color.black;
		} else if (timer == 0) {
			this.retryColor = Color.white;
			timer = 90;
		}
	}

	/**
	 * Function for getting the pixel width of text
	 * 
	 * @param font
	 *            the Font of the test
	 * @param text
	 *            the String of text
	 * @return width in pixels of text
	 */
	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

}
