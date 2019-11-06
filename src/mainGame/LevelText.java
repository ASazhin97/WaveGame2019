/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The LevelText class is used for all instances of text in the game. 
 */
package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.Random;

// Start of class 
// Is a subclass of GameObject 
public class LevelText extends GameObject {

	// Instance variables
	private String text;
	private int timer;
	private Random r = new Random();

	// Main constructor 
	public LevelText(double x, double y, String text, ID id, Handler _handler) {
		super(x, y, id);
		this.text = text;
		timer = 180;
	}

	// Methods 
	// Used to define time in the game 
	@Override
	public void tick() {
	}

	// Used to create graphics 
	@Override
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

	// Used to get the width of the text 
	public int getTextWidth(Font font, String text) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
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

	// Used to create size of image 
	@Override
	public Rectangle getBounds() {
		return null;
	}
}