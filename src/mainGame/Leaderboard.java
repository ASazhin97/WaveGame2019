/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The Leaderboard class keeps track of high scores made by various players and 
 *organizes them based on value.  
 */
package mainGame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

// Start of class 
public class Leaderboard {
	
	// Instance variables 
	private Handler handler;
	private HUD hud;
	private String text;

	// Main constructor 
	public Leaderboard(Game game, Handler handler, HUD hud) throws MalformedURLException {
		this.handler = handler;
		this.hud = hud;
	}
	
	// Methods 
	// Used to define time in the game 
	public void tick(){
		// Clear the player when the leaderboard is in use 
		handler.clearPlayer();
	}

	// Used to create the leaderboard graphics 
	public void render(Graphics g) {
		//Sets text 
		Font bigFont = FontHandler.setSize(FontHandler.HEADER_FONT, 96);
		Font smallFont = FontHandler.setSize(FontHandler.HEADER_FONT, 35);
		g.setFont(bigFont);
		text = "Leaderboard";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(bigFont, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(smallFont);
		// Sets the leaderboard as an ArrayList 
		ArrayList<String> leaderboard = hud.getLeaderboard();
		// If the leaderboard has at least one entry, keep adding to its size for more entries 
		if(leaderboard.size() > 0) {
			for (int i = 0; i < leaderboard.size(); i++){
				text = leaderboard.get(i);
				g.drawString(text,Game.WIDTH / 2 - getTextWidth(smallFont,text)/2, Game.HEIGHT/2 + (50*i));
			}
		// Else if there are no entries, display this message 
		} else {
			text = "No scores input yet!";
			g.drawString(text,Game.WIDTH / 2 - getTextWidth(smallFont,text)/2, Game.HEIGHT/2 + (50));
		}
	}
	
	// Used to retrieve the leaderboard data 
	public Boolean retrieveData() {

		// Try to create new leaderboard data 
	    try {
	    	File CSV = new File("leaderboard.csv");
	        
	    	// If data was successfully added, create new name and score 
	    	if(!CSV.createNewFile()) {
	    		BufferedReader reader = new BufferedReader(new FileReader(CSV));
	    		String scores = new String();
	    		String row = new String();
	        	// If the line is empty, add score to score string 
	           	while ((row = reader.readLine()) != null) {
	           		scores += row + "\n";
	           	}
	            reader.close();	
	            // If score is empty, do not set a high score
	            if(scores.isEmpty()) {
	            	hud.setHighScore(null);
	            // Else set a high score 
	            } else {
	            	hud.setHighScore(scores);
	            }
	        // Else if data could not be saved to leaderboard, display this message 
	    	} else {
	    		System.out.println("Leaderboard.csv does not exist and was created, blank.");
	    		hud.setHighScore(null);
	    	}
	    	return true;
	    // If leaderboard could not be updated, throw an exception 
	    } catch (IOException e) {
	    	System.out.println("IOException ERROR during initialization when checking for leaderboard.csv");
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getStackTrace());
	    	return false;
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