package objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Person extends Thing{
	public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	//private Pokemon[] my_pkmn;
	public String name;
	public ArrayList<String> dialog;
	public Sprite sprite;
	public BufferedImage[] walk, bike;
	public int animation_flag;
	public boolean on_bike;

	public Person() {
	}

	public String toString() {
		return name;
	}

	public Sprite getSprite() {
		return null;
	}
}
