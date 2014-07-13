package objects;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import animations.Sprite;
import util.ImageLibrary;

public class TileMap {
	public int fill = ImageLibrary.DEFAULT_ICON;
	public int[][] mapdata;

	public TileMap() {
		mapdata = new int[25][25];
		clear_map();
	}

	public TileMap(int x, int y) {
		mapdata = new int[y][x];
		clear_map();
	}

	public TileMap(int[][] map) {
		mapdata = map;
	}

	public TileMap(String filename) {
		load(new File(filename));
	}

	public void fill_map(int tile) {
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				mapdata[i][k] = tile;
	}

	public void clear_map() {
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				mapdata[i][k] = ImageLibrary.DEFAULT_ICON;
	}

	public ArrayList<Sprite> get_sprites() {
		return new ArrayList<Sprite>();
	}

	public BufferedImage get_static_map() {
		int unit = ImageLibrary.pixel_width[0];
		BufferedImage im = new BufferedImage(mapdata[0].length * unit, mapdata.length * unit, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < mapdata.length; ++i) {
			for (int j = 0; j < mapdata[i].length; ++j) {
				im.getGraphics().drawImage(ImageLibrary.icons[mapdata[i][j]].getImage(), j * unit, i * unit, null);
			}
		}
		return im;
	}

	/**
	 * IMPORTANT: SAVE FILE FORMAT
	 * 
	 * [y]:[x]:[]:[].....:[]
	 * 
	 */

	public String toString() {
		String str = "";

		// Write map data to str
		str += mapdata.length + ":" + mapdata[0].length;
		for (int i = 0; i < mapdata.length; ++i) {
			for (int j = 0; j < mapdata[0].length; ++j) {
				str += ":" + mapdata[i][j];
			}
		}
		return str;
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
		String str = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = in.readLine();

			while (line != null) {
				str += line;
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Parse map data
		String[] ary = str.split(":");
		int a = Integer.parseInt(ary[0]);
		int b = Integer.parseInt(ary[1]);
		mapdata = new int[a][b];
		for (int i = 0; i < a; ++i) {
			for (int j = 0; j < b; ++j) {
				mapdata[i][j] = Integer.parseInt(ary[2 + i * b + j]);
			}
		}
	}

	public void buffer_bottom_rows(int n) {
		assert n > 0;
		int[][] temp = new int[mapdata.length + n][mapdata[0].length];
		for (int i = 0; i < temp.length; ++i)
			for (int k = 0; k < temp[0].length; ++k)
				temp[i][k] = fill;
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				temp[i][k] = mapdata[i][k];
		mapdata = temp;
	}

	public void buffer_top_rows(int n) {
		assert n > 0;
		int[][] temp = new int[mapdata.length + n][mapdata[0].length];
		for (int i = 0; i < temp.length; ++i)
			for (int k = 0; k < temp[0].length; ++k)
				temp[i][k] = fill;
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				temp[n + i][k] = mapdata[i][k];
		mapdata = temp;
	}

	public void buffer_right_cols(int n) {
		assert n > 0;
		int[][] temp = new int[mapdata.length][mapdata[0].length + n];
		for (int i = 0; i < temp.length; ++i)
			for (int k = 0; k < temp[0].length; ++k)
				temp[i][k] = fill;
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				temp[i][k] = mapdata[i][k];
		mapdata = temp;
	}

	public void buffer_left_cols(int n) {
		assert n > 0;
		int[][] temp = new int[mapdata.length][mapdata[0].length + n];
		for (int i = 0; i < temp.length; ++i)
			for (int k = 0; k < temp[0].length; ++k)
				temp[i][k] = fill;
		for (int i = 0; i < mapdata.length; ++i)
			for (int k = 0; k < mapdata[0].length; ++k)
				temp[i][k + n] = mapdata[i][k];
		mapdata = temp;
	}
}
