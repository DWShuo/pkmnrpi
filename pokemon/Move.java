package pokemon;

import java.util.ArrayList;
import java.util.Collections;

import util.FileParser;

//TODO: determine how to calculate status changes and special effects.
public class Move implements Comparable<Move> {
	public static Move[] all_moves;
	public static final int PHYSICAL = 20, SPECIAL = 30, STATUS = 40;

	public int type, damage, pp, pp_max, lvl_req, tm, category;
	public String name, description, effect;
	public double crit_chance, hit_chance;
	public int speed_priority;

	public Move() {}

	public Move(ArrayList<String> data) {
		name = Pokemon.stripLabel(data.get(0));
		type = Pokemon.getType(Pokemon.stripLabel(data.get(1)));
		category = parseCategory(Pokemon.stripLabel(data.get(2)));
		damage = Integer.parseInt(Pokemon.stripLabel(data.get(3)));
		hit_chance = Double.parseDouble(Pokemon.stripLabel(data.get(4))) / 100.0;
		// TODO crit_chance = ?
		// TODO TM#
		pp = pp_max = Integer.parseInt(Pokemon.stripLabel(data.get(5)));
		description = Pokemon.stripLabel(data.get(6));
		effect = Pokemon.stripLabel(data.get(7));
	}

	public static void init() {
		ArrayList<String> data = FileParser.parseFile("src/data/move_info.txt");
		ArrayList<Move> all = new ArrayList<Move>();
		for (String line : data) {
			String[] ary = line.split(",");
			Move m = new Move();
			m.name = ary[0];
			m.type = Pokemon.getType(ary[1].toUpperCase());
			m.category = parseCategory(ary[2]);
			m.damage = Integer.parseInt(ary[3]);
			double acc = Double.parseDouble(ary[4]);
			m.hit_chance = acc == 0 ? 0 : 100.0 / acc;
			m.pp = m.pp_max = Integer.parseInt(ary[5]);
			if (ary.length == 7)
				m.description = ary[6];
			all.add(m);
		}
		all_moves = order_moves(all);
	}

	private static Move[] order_moves(ArrayList<Move> all) {
		Collections.sort(all);
		Move[] mo = new Move[all.size()];
		int index = 0;
		for (Move m : all)
			mo[index++ ] = m;
		return mo;
	}

	public static ArrayList<Move> loadAll(ArrayList<String> data) {
		ArrayList<Move> all = new ArrayList<Move>();
		for (String str : data)
			all.add(lookup(str));
		return all;
	}

	public static Move lookup(int id) {
		for (Move m : all_moves)
			if (m.tm == id)
				return m;
		return null;
	}

	public static Move lookup(String name) {
		for (Move m : all_moves)
			if (m.name.equalsIgnoreCase(name))
				return m;
		return null;
	}

	@Override
	public int compareTo(Move a) {
		return name.compareTo(a.name);
	}

	public static int parseCategory(String str) {
		str = str.toUpperCase();
		if (str.equals("PHYSICAL"))
			return PHYSICAL;
		else if (str.equals("SPECIAL"))
			return SPECIAL;
		else if (str.equals("STATUS"))
			return STATUS;
		return PHYSICAL;
	}
}
