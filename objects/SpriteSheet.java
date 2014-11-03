package objects;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class SpriteSheet implements Comparable<SpriteSheet> {

	public String name;
	public ImageIcon base;
	public ImageIcon[] iconlist;
	public ImageIcon[][] iconmap;
	public Dimension cut;
	public int width, height;

	public SpriteSheet(String filename, Dimension d, String n) {
		name = n;
		cut = d;
		base = new ImageIcon(filename);
		cutIcons();
	}

	public void cutIcons() {
		width = base.getIconWidth();
		height = base.getIconHeight();
		int w = width / cut.width;
		int h = height / cut.height;
		iconmap = new ImageIcon[h][w];
		iconlist = new ImageIcon[w * h];
		BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		canvas.getGraphics().drawImage(base.getImage(), 0, 0, null);
		int count = 0;
		for (int i = 0; i < h; ++i) {
			for (int k = 0; k < w; ++k) {
				iconlist[count] = new ImageIcon(canvas.getSubimage(k * cut.width, i * cut.height, cut.width, cut.height));
				iconmap[i][k] = iconlist[count++ ];
			}
		}
	}

	public int compareTo(SpriteSheet o) {
		return name.compareTo(o.name);
	}
}
