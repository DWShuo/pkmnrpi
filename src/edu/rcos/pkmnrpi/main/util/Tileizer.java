/**
 * @author Theodore Tenedorio
 * @created_on 5/20/2014
 */
package edu.rcos.pkmnrpi.main.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

// This class holds all of the methods to split up a tiled image into its separate tiles.
public class Tileizer {
	public static final int WIDTH = 16;
	private static final int s_w = 16;

	// This method just takes a filename and the number of tokens to return.
	// It uses the Tile.width value to determine default tile size.
	public static List<BufferedImage> tile_maker(String filename, int n) {
		Image i = (new ImageIcon(filename)).getImage();
		int w = i.getWidth(null), h = i.getHeight(null);

		BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		b.getGraphics().drawImage(i, 0, 0, w, h, 0, 0, w, h, null);

		return cutter(b, w, h, s_w, s_w, n);
	}

	// This is a more generic version of tile_maker that will cut out icons with
	// the given dimensions.
	public static List<BufferedImage> icon_maker(String filename, int icon_width, int icon_height, int n) {
		Image i = (new ImageIcon(filename)).getImage();
		int w = i.getWidth(null), h = i.getHeight(null);

		BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		b.getGraphics().drawImage(i, 0, 0, w, h, 0, 0, w, h, null);

		return cutter(b, w, h, icon_width, icon_height, n);
	}

	// This method does the actual slicing. It takes the source image & a set of
	// dimensions.
	public static List<BufferedImage> cutter(BufferedImage source, int total_w, int total_h, int w, int h, int n) {
		// First see how many columns and rows there are.
		int nx = total_w / w, ny = total_h / h;
		List<BufferedImage> lst = new ArrayList<BufferedImage>();

		// Iterate through the image and pull each sub image out.
		for (int j = 0; j < ny; ++j)
			for (int i = 0; i < nx; ++i)
				if (lst.size() < n)
					lst.add(source.getSubimage(i * w, j * h, w, h));
				else
					return lst;
		return lst;
	}
}
