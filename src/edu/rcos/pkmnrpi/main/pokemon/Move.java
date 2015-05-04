package edu.rcos.pkmnrpi.main.pokemon;

import java.util.ArrayList;

import edu.rcos.pkmnrpi.main.animations.Sprite;
import edu.rcos.pkmnrpi.main.game.GameState;

//TODO: determine how to calculate status changes and special effects.
public class Move implements Comparable<Move> {
	public static final int PHYSICAL = 20, SPECIAL = 30, STATUS = 40;

	public int type, damage, pp, pp_max, lvl_req, tm, category,speed_priority;
	public String name, description, effect;
	public double crit_chance, hit_chance;
	public String sound = "data/sounds/moves/tackle.mid";
	public Sprite sprite = new Sprite("data/tilesets/sprites/tackle.png");

	public Move() {}

	public String toString() {
		return name + "," + Pokemon.getType(type) + "," + category + "," + damage + "," + (int) hit_chance * 100 + "," + pp_max;
	}

	public void animate(int direction) {
		if (direction == 0) { // Friend is attacking

		} else if (direction == 1) { // Enemy is attacking

		}
	}

	public static ArrayList<Move> loadAll(ArrayList<String> data) {
		ArrayList<Move> all = new ArrayList<Move>();
		for (String str : data)
			all.add(lookup(str));
		return all;
	}

	public static Move lookup(int id) {
		for (Move m : GameState.MOVES)
			if (m.tm == id)
				return m;
		return null;
	}

	public static Move lookup(String name) {
		for (Move m : GameState.MOVES)
			if (m.name.equalsIgnoreCase(name))
				return m;
		return null;
	}

	@Override
	public int compareTo(Move a) {
		return name.compareTo(a.name);
	}
}
