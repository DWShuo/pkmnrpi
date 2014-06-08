package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ImageLibrary {
	public static ImageIcon[] icons, front_sprites, back_sprites, small_sprites;
	public static BufferedImage[] image_sheets;
	public static final String[] image_sheet_names = { "src/tilesets/misc_tiles.png", "src/tilesets/day_roofs.png", "src/tilesets/day_buildings.png",
			"src/tilesets/day_landscape.png", "src/tilesets/front_sprites.png", "src/tilesets/back_sprites.png" };
	public static final int[] pixel_width = { 16, 16, 16, 16, 56, 56 };
	public static int[] icon_counts = { 699, 437, 167, 290, 250, 250 };
	// The starting index for misc, roofs, buildings, landscapes,
	public static final int[] start_counts = {0, 700, 1137, 1304};
	public static final ImageIcon blank = new ImageIcon("src/blank.png");
	public static final int DEFAULT_ICON = icon_counts[0];

	public static void init() {
		// Initilize the image sheet array
		image_sheets = new BufferedImage[image_sheet_names.length];
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
		// Create small sprites, an array of image icons
	}
}
