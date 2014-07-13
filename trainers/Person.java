package trainers;

import game.GameBoard;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import animations.Sprite;
import objects.Thing;

public class Person extends Thing {
	public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	public String name;
	public ArrayList<String> dialog;
	public Sprite sprite;
	public BufferedImage[] walk, bike;
	public int animation_flag;
	public boolean on_bike;

	public Person() {}

	public String toString() {
		return name;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		sprite.x = x * GameBoard.tsize;
		sprite.y = y * GameBoard.tsize;
	}

	public void set_direction(int d) {
		direction = d;
		int[] lookup = { 7, 4, 0, 2 };
		sprite.setImage(walk[lookup[direction]]);
	}
}
