package objects;

import game.GameState;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import util.FileParser;
import util.ImageLibrary;
import util.Pair;

public class TileMap {
	public static Pair<String, Integer, Integer> fill = new Pair<String, Integer, Integer>("Land", 3, 15);

	public Pair<String, Integer, Integer>[][] mapdata;
	public int centerx, centery;
	public String name;

	public TileMap(int x, int y, String n) {
		name = n;
		mapdata = new Pair[y][x];
		clear_map();
		GameState.MAPS.put(name, this);
	}

	public TileMap(String filename) {
		load(new File(filename));
		GameState.MAPS.put(name, this);
	}

	public void fillMap(Pair<String, Integer, Integer> p) {
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				mapdata[i][k] = p;
	}

	public void fillMap(String sheet, int x, int y) {
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				mapdata[i][k] = new Pair<String, Integer, Integer>(sheet, x, y);
	}

	public static void fillMap(Pair<String, Integer, Integer>[][] data, Pair<String, Integer, Integer> p) {
		for (int i = 0; i < data.length; ++i)
			for (int k = 0; k < data[0].length; ++k)
				data[i][k] = p;
	}

	public void clear_map() {
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				mapdata[i][k] = fill;
	}

	public BufferedImage getSubMap(Rectangle r) {
		int unit = ImageLibrary.pixel_width[0];
		int x = r.x / unit;
		int y = r.y / unit;
		int w = r.width / unit + (r.width % unit == 0 ? 0 : 1);
		int h = r.height / unit + (r.height % unit == 0 ? 0 : 1);
		BufferedImage im = new BufferedImage(w * unit, h * unit, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				Pair<String, Integer, Integer> p = mapdata[y + i][x + j];
				im.getGraphics().drawImage(ImageLibrary.getIcon(p).getImage(), j * unit, i * unit, null);
			}
		}
		return im.getSubimage(r.x - x * unit, r.y - y * unit, Math.min(r.width, im.getWidth() - (r.x - x * unit)), Math.min(r.height, im.getHeight() - (r.y - y * unit)));
	}

	public BufferedImage getStaticMap() {
		int unit = ImageLibrary.pixel_width[0];
		BufferedImage im = new BufferedImage(mapdata[0].length * unit, mapdata.length * unit, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < mapdata.length; ++i) {
			for (int j = 0; j < mapdata[i].length; ++j) {
				Pair<String, Integer, Integer> p = mapdata[i][j];
				im.getGraphics().drawImage(ImageLibrary.getIcon(p).getImage(), j * unit, i * unit, null);
			}
		}
		return im;
	}

	public String toString() {
		String str = "";

		// Write map data to str
		str += "\n" + mapdata.length + "\n" + mapdata[0].length;
		for (int i = 0; i < mapdata.length; ++i) {
			for (int j = 0; j < mapdata[0].length; ++j) {
				str += "\n" + mapdata[i][j];
			}
		}
		return centerx + "\n" + centery + "\n" + name + str;
	}

	public void save(File file) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(File file) {
		ArrayList<String> data = FileParser.parseFile(file.getAbsolutePath());
		centerx = Integer.parseInt(data.get(0));
		centery = Integer.parseInt(data.get(1));
		name = data.get(2);
		int h = Integer.parseInt(data.get(3));
		int w = Integer.parseInt(data.get(4));
		mapdata = new Pair[h][w];
		int index = 5;
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				String[] ary = data.get(index++ ).split(":");
				mapdata[i][j] = new Pair<String, Integer, Integer>(ary[0], Integer.parseInt(ary[1]), Integer.parseInt(ary[2]));
			}
		}
	}

	public void resize(int x, int y, int w, int h) {
		centerx += x;
		centery += y;
		Pair<String, Integer, Integer>[][] data = new Pair[h][w];
		fillMap(data, fill);
		for (int i = 0; i < mapdata.length; ++i) {
			for (int j = 0; j < mapdata[i].length; ++j) {
				int a = x + j;
				int b = y + i;
				if (a >= 0 && b >= 0 && a < w && b < h) {
					data[b][a] = mapdata[i][j];
				}
			}
		}
		mapdata = data;
	}
}
