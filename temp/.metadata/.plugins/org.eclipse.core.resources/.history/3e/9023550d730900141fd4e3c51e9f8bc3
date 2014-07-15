package objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import pokemon.Pokemon;

//TODO: determine how to calculate status changes and special effects.
public class Move {
	public static final int PARALIZE = 0, BURN = 1, SLEEP = 2, CONFUSE = 3, POISON = 4, FREEZE = 5, HYPERBEAM = 6, SOLARBEAM = 7, WRAP = 8, FOCUS_PUNCH = 9, FLY = 10, DIG = 11,
			DOUBLE_SLAP = 12, FURRY_SWIPE = 13, BULLET_SEED = 15, ARM_THRUST = 16, COMET_PUNCH = 17, ROCK_BLAST = 18;
	public static Move[] all_moves;

	public int type, damage, pp, pp_max, lvl_req;
	public String name, category, description, effect;
	public double crit_chance, hit_chance;

	public Move(ArrayList<String> data) {
		name = Pokemon.strip_label(data.get(0));
		type = Pokemon.getType(Pokemon.strip_label(data.get(1)));
		category = Pokemon.strip_label(data.get(2));
		damage = Integer.parseInt(Pokemon.strip_label(data.get(3)));
		hit_chance = Double.parseDouble(Pokemon.strip_label(data.get(4))) / 100.0;
		// TODO crit_chance = ?
		pp = pp_max = Integer.parseInt(Pokemon.strip_label(data.get(5)));
		description = Pokemon.strip_label(data.get(6));
		effect = Pokemon.strip_label(data.get(7));
	}

	public static void init() {
		String filename = "src/data/Move_Data.txt";
		ArrayList<Move> all = new ArrayList<Move>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			ArrayList<String> lst = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (Pokemon.is_uniform(line, '-')) {
					if(lst.size() == 0)
						continue;
					all.add(new Move(lst));
					lst = new ArrayList<String>();
				} else
					lst.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		all_moves = order_moves(all);
	}

	private static Move[] order_moves(ArrayList<Move> all) {
		return null;
	}
}
