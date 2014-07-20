package pokedex;

import java.util.ArrayList;

import javax.swing.JLabel;

import pokemon.Pokemon;
import util.ImageLibrary;

public class TextGrid implements PokedexUI {
	public JLabel[][] grid;
	public char[][] text;
	public int color = ImageLibrary.BLACK;

	public TextGrid() {}

	public void clear() {
		for (int i = 0; i < grid.length; ++i)
			for (int k = 0; k < grid[i].length; ++k) {
				text[i][k] = ' ';
				grid[i][k].setIcon(ImageLibrary.blank);
			}
	}

	public void setText(String[] ary) {
		clear();
		int max = Math.min(ary.length, grid.length);
		for (int i = 0; i < max; ++i) {
			String str = ary[i];
			if (str.length() <= grid[i].length)
				writeString(str, 0, i);
			else
				writeString(str.substring(0, grid[i].length), 0, i);
		}
	}

	public void setText(ArrayList<String> ary) {
		clear();
		int max = Math.min(ary.size(), grid.length);
		for (int i = 0; i < max; ++i) {
			String str = ary.get(i);
			if (str.length() <= grid[i].length)
				writeString(str, 0, i);
			else
				writeString(str.substring(0, grid[i].length), 0, i);
		}
	}

	public void setText(String str, int mills) {
		int x = 0, y = 0;
		clear();
		String token = tokenize(str, ' ');
		str = clipToken(str, token);
		token = strip(token, ' ');
		while (!token.isEmpty()) {
			if (x + token.length() < grid[y].length) {
				writeString(token, x, y, mills);
				x += token.length();
				if (x + 1 < grid[y].length) {
					text[y][x] = ' ';
					grid[y][x++ ].setIcon(ImageLibrary.blank);
				}
				token = tokenize(str, ' ');
				str = clipToken(str, token);
				token = strip(token, ' ');
			} else if ( ++y < grid.length) {
				x = 0;
			} else
				return;
		}
	}

	public void setText(String str) {
		int x = 0, y = 0;
		clear();
		String token = tokenize(str, ' ');
		str = clipToken(str, token);
		token = strip(token, ' ');
		while (!token.isEmpty()) {
			if (x + token.length() < grid[y].length) {
				writeString(token, x, y);
				x += token.length();
				if (x + 1 < grid[y].length) {
					text[y][x] = ' ';
					grid[y][x++ ].setIcon(ImageLibrary.blank);
				}
				token = tokenize(str, ' ');
				str = clipToken(str, token);
				token = strip(token, ' ');
			} else if ( ++y < grid.length) {
				x = 0;
			} else
				return;
		}
	}

	public void writeString(String str, int x, int y, int mills) {
		str = cleanString(str);
		for (char c : str.toCharArray()) {
			text[y][x] = c;
			grid[y][x++ ].setIcon(ImageLibrary.text[color][valid_chars.indexOf(c)]);
			try {
				//Thread.sleep(mills);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void writeString(String str, int x, int y) {
		str = cleanString(str);
		for (char c : str.toCharArray()) {
			text[y][x] = c;
			grid[y][x++ ].setIcon(ImageLibrary.text[color][valid_chars.indexOf(c)]);
		}
	}

	public static String cleanString(String str) {
		for (char c : bad_chars.toCharArray())
			str = str.replace(c, ' ');
		return str;
	}

	// Returns the next token of the string. separator included.
	public static String tokenize(String str, char split) {
		String s = "";
		for (char c : str.toCharArray())
			if (c == split) {
				return s + c;
			} else
				s += c;
		return s;
	}

	public static String clipToken(String full, String token) {
		if (full.equals(token))
			return "";
		return full.substring(token.length(), full.length());
	}

	// Returns the string with all tailing chars removed.
	public static String strip(String str, char id) {
		if (str.isEmpty())
			return "";
		if (Pokemon.isUniform(str, id))
			return "";
		while (str.charAt(0) == id) {
			str = str.substring(1);
		}
		while (str.charAt(str.length() - 1) == id) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
}
