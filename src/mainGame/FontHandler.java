package mainGame;

import java.awt.Font;

/**
 * This java class will consolidate all Font references throughout the application.
 * If you want to change the fonts, change them here!
 * @author Richie
 *
 */
public class FontHandler {
	public static Font HEADER_FONT = new Font("Rockwell", 1, 30);
	public static Font BODY_FONT = new Font("Avenir", 1, 20);
	private static final int DEFAULT_HEADER_SIZE = 30;
	private static final int DEFAULT_BODY_SIZE = 20;
	
	
	public static void setFontSize(int newSize) {
		HEADER_FONT = new Font(HEADER_FONT.getFamily(), HEADER_FONT.getStyle(), newSize);
		BODY_FONT = new Font(BODY_FONT.getFamily(), BODY_FONT.getStyle(), newSize);
	}
	
	public static void resetFontSize() {
		HEADER_FONT = new Font(HEADER_FONT.getFamily(), HEADER_FONT.getStyle(), DEFAULT_HEADER_SIZE);
		BODY_FONT = new Font(BODY_FONT.getFamily(), BODY_FONT.getStyle(), DEFAULT_BODY_SIZE);
	}
	
	/**
	 * Returns a new font with the specified size
	 * @param font The font to return 
	 * @param size The size of the font to return
	 * @return a Font object with the specified size
	 */
	public static Font setSize(Font font, int size) {
		return new Font(font.getFamily(), font.getStyle(), size);
	}
}
