package edu.rcos.pkmnrpi.main.objects;

import javax.swing.Icon;
import javax.swing.JLabel;

import edu.rcos.pkmnrpi.main.util.ImageLibrary;
import edu.rcos.pkmnrpi.main.util.Tileizer;

public class Tile extends JLabel {
	public int x, y, idx;

	public Tile() {
		super();
	}

	public Tile(String s) {
		super(s);
	}

	public Tile(Icon i) {
		super(i);
	}

	public Tile(String s, int i) {
		super(s, i);
	}

	public Tile(Icon icon, int i) {
		super(icon, i);
	}

	public Tile(String s, Icon icon, int i) {
		super(s, icon, i);
	}

	public Tile(Icon icon, int x, int y, int idx) {
		super(icon);
		this.x = x;
		this.y = y;
		this.idx = idx;
		setSize(Tileizer.WIDTH, Tileizer.WIDTH);
		setVisible(true);
	}

	public Tile(int x, int y, int idx) {
		super();
		this.x = x;
		this.y = y;
		this.idx = idx;
	}

	public static Tile blank(int x, int y) {
		Tile temp = new Tile(ImageLibrary.blank, x, y, 0);
		temp.setSize(Tileizer.WIDTH, Tileizer.WIDTH);
		temp.setVisible(true);
		return temp;
	}
}
