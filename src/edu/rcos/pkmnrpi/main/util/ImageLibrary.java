package edu.rcos.pkmnrpi.main.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.rcos.pkmnrpi.main.game.GameState;
import edu.rcos.pkmnrpi.main.objects.SpriteSheet;

/**
 * This is a database class designed specifically to hold the static sprite
 * data. However this class also helps with some of the text organization.
 */
public class ImageLibrary extends Library {
	public static ImageIcon[] back_sprites;
	public static int[] pixel_width = { 16, 16, 16, 16, 56 };
	public static String[] image_sheet_names = { "data/tilesets/misc_tiles.png", "data/tilesets/day_roofs.png", "data/tilesets/day_buildings.png", "data/tilesets/day_landscape.png",
			"data/tilesets/back_sprites.png" };
	public static BufferedImage[] player;
	public static List<SpriteSheet> sheets;

	// This method simply creates a solid color background sprite.
	public static BufferedImage bufferSolidColor(Color c, int w, int h) {
		BufferedImage base = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = base.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, w, h);
		return base;
	}

	public static SpriteSheet getSheet(String name) {
		for (SpriteSheet s : sheets)
			if (s.name.equals(name))
				return s;
		return null;
	}

	public static ImageIcon getSolidColor(Color c, int w, int h) {
		return new ImageIcon(bufferSolidColor(c, w, h));
	}

	public static ImageIcon getScaledIcon(ImageIcon i, int w, int h) {
		return new ImageIcon(i.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public static ImageIcon getIcon(String name, int x, int y) {
		for (SpriteSheet s : sheets) {
			if (s.name.equalsIgnoreCase(name))
				return s.iconmap[y][x];
		}
		return null;
	}

	public static ImageIcon getIcon(Pair<String, Integer, Integer> p) {
		return getIcon(p.a, p.b, p.c);
	}

	public static boolean canWalk(String name, int x, int y) {
		for (Dimension d : GameState.TERRAIN.get(name))
			if (d.width == x && d.height == y)
				return true;
		return false;
	}

	public static boolean canWalk(Pair<String, Integer, Integer> p) {
		return canWalk(p.a, p.b, p.c);
	}

	// This method is used to ensure transparency for sprites and animations.
	public static BufferedImage removeBackground(BufferedImage base, int background_RGB) {
		int w = base.getWidth();
		int h = base.getHeight();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < w; ++i)
			for (int j = 0; j < h; ++j) {
				if (background_RGB == base.getRGB(i, j)) {
					image.setRGB(i, j, 0);
				} else
					image.setRGB(i, j, base.getRGB(i, j));
			}
		return image;
	}

	public static void init() {
		sheets = new ArrayList<SpriteSheet>();
		sheets.add(new SpriteSheet(image_sheet_names[0], new Dimension(pixel_width[0], pixel_width[0]), "Misc"));
		sheets.add(new SpriteSheet(image_sheet_names[1], new Dimension(pixel_width[0], pixel_width[0]), "Roof"));
		sheets.add(new SpriteSheet(image_sheet_names[2], new Dimension(pixel_width[0], pixel_width[0]), "Building"));
		sheets.add(new SpriteSheet(image_sheet_names[3], new Dimension(pixel_width[0], pixel_width[0]), "Land"));
		sheets.add(new SpriteSheet(image_sheet_names[4], new Dimension(pixel_width[4], pixel_width[4]), "Backs", true));

		// Create back sprites, an array of image icons
		back_sprites = sheets.get(4).iconlist;

		// Load player sprites
		Image im = (new ImageIcon("data/tilesets/player_motion.png")).getImage();
		int w = im.getWidth(null);
		int h = im.getHeight(null);
		BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		buf.getGraphics().drawImage(im, 0, 0, null);
		buf = removeBackground(buf, bitwise_background_color);
		player = new BufferedImage[20];
		int temp = 0;
		for (BufferedImage b : Tileizer.cutter(buf, w, h, 16, 16, 20))
			player[temp++] = b;
	}
}
