package pokemon.moves;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import pokemon.Pokemon;
import util.FileParser;

//TODO: determine how to calculate status changes and special effects.
public class Move implements Comparable<Move> {
	public static Move[] all_moves;

	public int type, damage, pp, pp_max, lvl_req, tm;
	public String name, category, description, effect;
	public double crit_chance, hit_chance;

	public Move() {}

	public Move(ArrayList<String> data) {
		name = Pokemon.stripLabel(data.get(0));
		type = Pokemon.getType(Pokemon.stripLabel(data.get(1)));
		category = Pokemon.stripLabel(data.get(2));
		damage = Integer.parseInt(Pokemon.stripLabel(data.get(3)));
		hit_chance = Double.parseDouble(Pokemon.stripLabel(data.get(4))) / 100.0;
		// TODO crit_chance = ?
		// TODO TM#
		pp = pp_max = Integer.parseInt(Pokemon.stripLabel(data.get(5)));
		description = Pokemon.stripLabel(data.get(6));
		effect = Pokemon.stripLabel(data.get(7));
	}

	public static void init() {
		ArrayList<String> data = FileParser.parseFile("src/data/moves.txt");
		ArrayList<Move> all = new ArrayList<Move>();
		for (String line : data) {
			String[] ary = line.split(",");
			Move m = new Move();
			m.name = ary[0];
			m.description = ary[1];
			m.type = Pokemon.getType(ary[2].toUpperCase());
			m.pp = m.pp_max = Integer.parseInt(ary[3]);
			m.damage = Integer.parseInt(ary[4]);
			double acc = Double.parseDouble(ary[5]);
			m.hit_chance = acc == 0 ? 0 : 100.0 / acc;
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
		String str = "";
		for (Move m : all_moves)
			str += m.name + ", ";
		return null;
	}

	@Override
	public int compareTo(Move a) {
		return name.compareTo(a.name);
	}
}
