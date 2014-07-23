package pokedex;

import java.awt.Dimension;

import javax.swing.ImageIcon;

public interface PokedexUI {
	public static final String valid_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,.:$ ";
	public static final String bad_chars = "/";
	public static final ImageIcon portrait_icon = new ImageIcon("src/tilesets/pokedex_background.png");
	public static Dimension TSIZE = new Dimension(7, 14);
}
