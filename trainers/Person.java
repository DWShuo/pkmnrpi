package trainers;

import game.GameBoard;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import animations.Sprite;
import objects.Thing;
import objects.TileMap;

public class Person extends Thing {
	public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	public String name, intro;
	public ArrayList<String> dialog = new ArrayList<String>();
	public Sprite sprite, bigsprite;
	public TileMap map;
	public BufferedImage[] walk, bike;
	public int animation_flag, cash, px, py;
	public boolean male;

	public Person() {}

	public String toString() {
		return name;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		int offx = 0, offy = 0;
		if (map != null) {
			offx = map.centerx;
			offy = map.centery;
		}
		sprite.x = (x + offx) * GameBoard.tsize;
		sprite.y = (y + offy) * GameBoard.tsize;
	}

	public void setDirection(int d) {
		direction = d;
		int[] lookup = { 7, 4, 0, 2 };
		sprite.setImage(walk[lookup[direction]]);
	}

	public void setDirection(String str) {
		str = str.toUpperCase();
		if (str.equals("NORTH")) {
			direction = UP;
		} else if (str.equals("SOUTH")) {
			direction = DOWN;
		} else if (str.equals("EAST")) {
			direction = RIGHT;
		} else if (str.equals("WEST")) {
			direction = LEFT;
		}
	}
}
