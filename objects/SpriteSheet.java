package objects;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import util.FileParser;

public class SpriteSheet implements Comparable<SpriteSheet> {
	public static HashMap<String, ArrayList<Dimension>> TERRAIN_INFO = new HashMap<String, ArrayList<Dimension>>();

	public String name;
	public ImageIcon base;
	public ImageIcon[] iconlist;
	public ImageIcon[][] iconmap;
	public boolean[][] walkable;
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
		walkable = new boolean[h][w];
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
		ArrayList<Dimension> context = TERRAIN_INFO.get(name);
		if (context == null)
			return;
		for (Dimension d : context)
			walkable[d.height][d.width] = true;
	}

	public int compareTo(SpriteSheet o) {
		return name.compareTo(o.name);
	}

	public static void add(String key, Dimension d) {
		if (TERRAIN_INFO.keySet().contains(key))
			TERRAIN_INFO.get(key).add(d);
		else {
			TERRAIN_INFO.put(key, new ArrayList<Dimension>());
			TERRAIN_INFO.get(key).add(d);
		}
	}

	public static void save() {
		ArrayList<String> data = new ArrayList<String>();
		for (String n : TERRAIN_INFO.keySet())
			for (Dimension d : TERRAIN_INFO.get(n))
				data.add(d.width + ":" + d.height + ":" + n);
		FileParser.saveFile(data, "src/data/terrain info.txt");
	}

	public static void init() {
		ArrayList<String> data = FileParser.parseFile("src/data/terrain info.txt");
		for (String str : data) {
			String[] line = str.split(":");
			Dimension d = new Dimension(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
			add(line[2], d);
		}
	}
}
