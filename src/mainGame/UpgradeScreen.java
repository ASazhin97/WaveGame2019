/**
 *Original author: Brandon Loehle
 *Modifiers: Timothy Carta, Victoria Gorski, Richard Petrosino, James Salgado, and Julia Wilkinson 
 *Description: The UpgradeScreen class displays the upgrade screen when the player beats a boss
 *and is given the option to select one ability or power - up.   
 */
package mainGame;

// Imports 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

// Start of class 
public class UpgradeScreen {

	// Instance variables 
	private String text;
	private String[] paths = { "images/clearscreenability.png", "images/decreaseplayersize.png", "images/extralife.png",
			"images/healthincrease.png", "images/healthregeneration.png", "images/improveddamageresistance.png",
			"images/levelskipability.png", "images/freezetimeability.png", "images/speedboost.png" };
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private Random r = new Random();
	private int index1, index2, index3;

	// Main constructor 
	public UpgradeScreen(Game game, Handler handler, HUD hud) {
		// Adds paths to the images of the abilities and power - ups 
		addPaths();
		setIndex();
		text = "";
	}

	// Methods
	// Used to define time in the game 
	public void tick() {
	}

	// Used to create graphics 
	public void render(Graphics g) {
		// Sets font 
		Font font = new Font("Amoebic", 1, 175);
		text = "Select an Upgrade!";
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, 200);
		// Sets the size of the image 
		g.drawImage(getImage(imagePaths.get(index1)), 100, 300, 1721, 174, null);
		g.drawImage(getImage(imagePaths.get(index2)), 100, 300 + (60 + Game.HEIGHT / 6), 1721, 174, null);
		g.drawImage(getImage(imagePaths.get(index3)), 100, 300 + 2 * (60 + Game.HEIGHT / 6), 1721, 174, null);
	}

	// Used to define the paths to each image 
	public void resetPaths() {
		paths[0] = "images/clearscreenability.png";
		paths[1] = "images/decreaseplayersize.png";
		paths[2] = "images/extralife.png";
		paths[3] = "images/healthincrease.png";
		paths[4] = "images/healthregeneration.png";
		paths[5] = "images/improveddamageresistance.png";
		paths[6] = "images/levelskipability.png";
		paths[7] = "images/freezetimeability.png";
		paths[8] = "images/speedboost.png";
	}

	// Used to add paths to each image 
	public void addPaths() {
		for (int i = 0; i < 9; i++) {
			imagePaths.add(paths[i]);
		}
	}

	// Used to get the index of each image in the ArrayList 
	public int getIndex(int maxIndex) {
		int index = r.nextInt(maxIndex);
		return index;
	}

	// Used to set the index of three images 
	public void setIndex() {
		index1 = getIndex(9);
		index2 = getIndex(9);
		// Make sure all three images are different 
		if (index2 == index1) {
			index2++;
			if (index2 > 8) {
				index2 = 1;
			}
		}
		index3 = getIndex(9);
		if (index3 == index1) {
			index3--;
		}
		if (index3 == index2) {
			index3--;
		}
	}

	// Used to get the image 
	public Image getImage(String path) {
		Image image = null;
		// Try to get the image 
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		// If image cannot be found, throw an exception 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return image;
	}

	// Used to get the pixel width of the text 
	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

	// Used to get the path of the image 
	public String getPath(int x) {
		if (x == 1) {
			return paths[index1];
		} else if (x == 2) {
			return paths[index2];
		} else {
			return paths[index3];
		}
	}

	// Used to remove the image from possible upgrades if the player picks it 
	public void removeUpgradeOption(int x) {
		// Nullifies the image path
		if (x == 1) {
			paths[index1] = null;
		} else if (x == 2) {
			paths[index2] = null;
		} else {
			paths[index3] = null;
		}
	}

	// Used to check when the mouse is pressed 
	public void mousePressed(MouseEvent e) {
	}
	
	// Used to check when the mouse is released 
	public void mouseReleased(MouseEvent e) {
	}
}