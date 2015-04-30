package edu.rcos.pkmnrpi.main.util;

import java.awt.Font;

public class FontLibrary {

	private static FontLibrary instance;
	
	private final Font largeFont;
	private final Font mediumFont;
	private final Font smallFont;
	
	private FontLibrary() {
		// Load fonts
		largeFont = new Font("Pokemon GB", Font.TRUETYPE_FONT, 24);
		mediumFont = new Font("Pokemon GB", Font.TRUETYPE_FONT, 22);
		smallFont = new Font("Pokemon GB", Font.TRUETYPE_FONT, 16);
	}
	
	public static FontLibrary getInstance() {
		if (instance == null) {
			instance = new FontLibrary();
		}
		return instance;
	}
	
	public Font getLargeFont() {
		return largeFont;
	}
	
	public Font getMediumFont() {
		return mediumFont;
	}
	
	public Font getSmallFont() {
		return smallFont;
	}
	
}
