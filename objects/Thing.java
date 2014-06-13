package objects;

import java.util.ArrayList;

public abstract class Thing {
	public static final int NORTH = 0, WEST = 1, SOUTH = 2, EAST = 3;
	public int x, y;
	public int direction;
	protected ArrayList<Sprite> sprites;
	
	public abstract Sprite getSprite();
	public abstract String toString();
}
