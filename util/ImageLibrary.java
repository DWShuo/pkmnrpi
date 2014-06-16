package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ImageLibrary {
	public static ImageIcon[] icons, front_sprites, back_sprites, small_sprites;
	public static ImageIcon[][] text;
	public static final int GRAY = 0, GREEN = 1, BLACK = 2, VIOLET = 3, YELLOW = 4, BROWN = 5, RED = 6, LAVENDER = 7, PURPLE = 8, LIME = 9, GOLD = 10, ICE = 11, BEIGE = 12,
			MAGENTA = 13, PINK = 14, SAND = 15, WHITE = 16, SKY = 17, gray = GRAY, green = GREEN, black = BLACK, violet = VIOLET, yellow = YELLOW, brown = BROWN, red = RED,
			lavender = LAVENDER, purple = PURPLE, lime = LIME, gold = GOLD, ice = ICE, beige = BEIGE, magenta = MAGENTA, pink = PINK, sand = SAND, white = WHITE, sky = SKY;
	public static BufferedImage[] image_sheets, player;
	public static final String[] image_sheet_names = { "src/tilesets/misc_tiles.png", "src/tilesets/day_roofs.png", "src/tilesets/day_buildings.png",
			"src/tilesets/day_landscape.png", "src/tilesets/front_sprites.png", "src/tilesets/back_sprites.png" };
	public static final int[] pixel_width = { 16, 16, 16, 16, 56, 56 };
	public static int[] icon_counts = { 699, 437, 167, 290, 250, 250 };
	// The starting index for misc, roofs, buildings, landscapes,
	public static final int[] start_counts = { 0, 700, 1137, 1304 };
	public static final ImageIcon blank = new ImageIcon("src/blank.png");
	public static final int DEFAULT_ICON = icon_counts[0];

	public static BufferedImage bufferSolidColor(Color c, int w, int h) {
		BufferedImage base = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = base.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, w, h);
		return base;
	}

	public static ImageIcon getSolidColor(Color c, int w, int h) {
		return new ImageIcon(bufferSolidColor(c, w, h));
	}

	public static void init() {
		// Initilize the image sheet array
		image_sheets = new BufferedImage[image_sheet_names.length + 1];
		// Load each image sheet from the list of names
		ArrayList<ArrayList<BufferedImage>> lst = new ArrayList<ArrayList<BufferedImage>>();
		for (int i = 0; i < image_sheet_names.length; ++i) {
			// Load the image
			Image im = (new ImageIcon(image_sheet_names[i])).getImage();
			int w = im.getWidth(null), h = im.getHeight(null);
			// Create the new buffered image
			image_sheets[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			// Draw the image on to the buffered image
			image_sheets[i].getGraphics().drawImage(im, 0, 0, w, h, 0, 0, w, h, null);
			// Now the sheets are cut up into the icons
			lst.add(Tileizer.cutter(image_sheets[i], w, h, pixel_width[i], pixel_width[i], icon_counts[i]));
		}

		int total = 1;
		for (int i = 0; i < icon_counts.length; ++i) {
			// FOR DEBUGGING ONLY
			assert (icon_counts[i] == lst.get(i).size());
			total += icon_counts[i];
		}
		total -= 500;

		// Create icons, the array of image icon
		icons = new ImageIcon[total];
		int index = 0;
		for (BufferedImage im : lst.get(0))
			icons[index++ ] = new ImageIcon(im);
		icons[index++ ] = blank;
		icon_counts[0]++ ;
		for (BufferedImage im : lst.get(1))
			icons[index++ ] = new ImageIcon(im);
		for (BufferedImage im : lst.get(2))
			icons[index++ ] = new ImageIcon(im);
		for (BufferedImage im : lst.get(3))
			icons[index++ ] = new ImageIcon(im);

		// Create front sprites, an array of image icons
		front_sprites = new ImageIcon[icon_counts[4]]; // One for each pokemon
		for (int i = 0; i < icon_counts[4]; ++i)
			front_sprites[i] = new ImageIcon(lst.get(4).get(i));
		// Create back sprites, an array of image icons
		back_sprites = new ImageIcon[icon_counts[5]]; // One for each pokemon
		for (int i = 0; i < icon_counts[5]; ++i)
			back_sprites[i] = new ImageIcon(lst.get(5).get(i));

		// Create the text sprites
		Image im = (new ImageIcon("src/tilesets/typefont.png")).getImage();
		int w = im.getWidth(null), h = im.getHeight(null), idx = image_sheets.length - 1;
		image_sheets[idx] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		image_sheets[idx].getGraphics().drawImage(im, 0, 0, null);
		// Split the sheet up by color
		BufferedImage[] ls = new BufferedImage[18];
		int temp = 0;
		for (BufferedImage b : Tileizer.cutter(image_sheets[idx], w, h, 119, 48, 18))
			ls[temp++ ] = b;
		text = new ImageIcon[18][];
		for (int i = 0; i < text.length; ++i) {
			text[i] = new ImageIcon[68];
			// Cut up the sheets into font sprites
			BufferedImage[] font = new BufferedImage[4 * 17];
			temp = 0;
			for (BufferedImage b : Tileizer.cutter(ls[i], 119, 48, 7, 12, 4 * 17))
				font[temp++ ] = b;
			// Pull the letters in first
			int c = 0;
			for (int j = 0; j < 13; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
			for (int j = 17; j < 30; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
			for (int j = 34; j < 47; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
			for (int j = 51; j < 64; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
			for (int j = 13; j < 17; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
			for (int j = 30; j < 34; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
			for (int j = 47; j < 51; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
			for (int j = 64; j < 68; ++j) {
				text[i][c++ ] = new ImageIcon(font[j]);
			}
		}

		// Load player sprites
		im = (new ImageIcon("src/tilesets/player_motion.png")).getImage();
		w = im.getWidth(null);
		h = im.getHeight(null);
		BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		buf.getGraphics().drawImage(im, 0, 0, null);
		player = new BufferedImage[20];
		temp = 0;
		for (BufferedImage b : Tileizer.cutter(buf, w, h, 16, 16, 20))
			player[temp++ ] = b;
	}
}
