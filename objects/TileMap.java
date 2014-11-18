package objects;

import game.GameState;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.FileParser;
import util.ImageLibrary;
import util.Pair;

public class TileMap {
	public static Pair<String, Integer, Integer> fill = new Pair<String, Integer, Integer>("Land", 3, 15);

	public Pair<String, Integer, Integer>[][] mapdata;
	public int centerx, centery;
	public String name;

	@SuppressWarnings("unchecked")
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
		r = bound(r);
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

	private Rectangle bound(Rectangle r) {
		int x = Math.max(0, Math.min(mapdata[0].length * 16 - 1, r.x));
		int y = Math.max(0, Math.min(mapdata.length * 16 - 1, r.y));
		int w = Math.max(1, Math.min(mapdata[0].length * 16 - x, r.width));
		int h = Math.max(1, Math.min(mapdata.length * 16 - y, r.height));
		return new Rectangle(x, y, w, h);
	}

	public static void main(String[] args) {
		GameState.initilize_all();
		new TileMap("src/maps/ALL.map").export("sample");
	}

	public void export(String filename) {
		try {

			Rectangle[][] ary = slice();
			for (int i = 0; i < ary.length; ++i)
				for (int j = 0; j < ary[i].length; ++j)
					ImageIO.write(getSubMap(ary[i][j]), "png", new File("src/maps/test/" + i + "_" + j + "_" + filename + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Rectangle[][] slice() {
		Dimension d = new Dimension(300 * 16, 300 * 16);
		int w = mapdata[0].length * 16;
		int h = mapdata.length * 16;
		int a = w / d.width + (d.width % w == 0 ? 0 : 1);
		int b = h / d.height + (d.height % h == 0 ? 0 : 1);
		Rectangle[][] ary = new Rectangle[b][a];
		for (int i = 0; i < b; ++i) {
			for (int j = 0; j < a; ++j) {
				ary[i][j] = new Rectangle(j * d.height, i * d.width, d.width, d.height);
			}
		}
		return ary;
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
		return centerx + "\n" + centery + "\n" + name + "\n" + mapdata.length + "\n" + mapdata[0].length + "\n" + str;
	}

	public Pair<String, Integer, Integer> get(Point a) {
		return mapdata[a.y][a.x];
	}

	public void save(File file) {
		ArrayList<String> data = new ArrayList<String>();
		data.add("" + centerx);
		data.add("" + centery);
		data.add(name);
		data.add("" + mapdata.length);
		data.add("" + mapdata[0].length);
		for (int i = 0; i < mapdata.length; ++i) {
			for (int j = 0; j < mapdata[0].length; ++j) {
				data.add("" + mapdata[i][j]);
			}
		}
		FileParser.saveFile(data, file);
	}

	@SuppressWarnings("unchecked")
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
				String[] ary = data.get(index++).split(":");
				mapdata[i][j] = new Pair<String, Integer, Integer>(ary[0], Integer.parseInt(ary[1]), Integer.parseInt(ary[2]));
			}
		}
	}

	@SuppressWarnings("unchecked")
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

	public int getHeight() {
		return 16 * mapdata.length;
	}

	public int getWidth() {
		return 126 * mapdata[0].length;
	}
}
