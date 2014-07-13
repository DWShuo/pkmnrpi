package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import pokedex.Pokedex;
import pokedex.PokedexUI;
import pokemon.moves.Move;

public class Pokemon implements PokedexUI {
	public static final int FIRE = 0, WATER = 1, GRASS = 2, GROUND = 3, ROCK = 4, DARK = 5, GHOST = 6, STEEL = 7, ELECTRIC = 8, FLYING = 9, DRAGON = 10, ICE = 11, PSYCHIC = 12,
			POISON = 13, FIGHTING = 14, NORMAL = 15;
	public static Pokemon[] all_pokemon;

	public String name, species, description;
	public boolean male = true;
	public int type, t2 = -1;
	public double height, weight;
	public int ID;
	public Stats stats;
	public int catch_rate, base_exp, growth_rate;
	public HashMap<Integer, String> evolutions, learnset;
	public ArrayList<String> tmset;
	public ArrayList<Move> known_moves;

	// Used when loading pokemon by name
	public Pokemon(String name) {
		Pokemon p = Pokedex.getPokemon(name);
		name = p.name;
		species = p.species;
		description = p.description;
		male = p.male;
		type = p.type;
		t2 = p.t2;
		height = p.height;
		weight = p.weight;
		ID = p.ID;
		stats = new Stats(p.stats);
		catch_rate = p.catch_rate;
		base_exp = p.base_exp;
		growth_rate = p.growth_rate;
		evolutions = p.evolutions;
		learnset = p.learnset;
		tmset = p.tmset;
	}

	// To be used with pokedex info only
	public Pokemon(ArrayList<String> info) {
		int index = 0;

		ID = Integer.parseInt(stripLabel(info.get(index++ )));
		name = stripLabel(info.get(index++ ));
		String types = stripLabel(info.get(index++ ));
		if (types.contains("/")) {
			String[] ar = types.split("/");
			type = getType(ar[0]);
			t2 = getType(ar[1]);
		} else
			type = getType(types);
		species = stripLabel(info.get(index++ ));
		height = Double.parseDouble(stripLabel(info.get(index++ )));
		weight = Double.parseDouble(stripLabel(info.get(index++ )));

		evolutions = new HashMap<Integer, String>();
		while (!info.get(index).equalsIgnoreCase("//Stats")) {
			String[] ar = info.get(index++ ).split(" ");
			evolutions.put(Integer.parseInt(ar[0]), ar[1]);
		}
		index++ ;
		stats = new Stats();
		stats.max_health = Integer.parseInt(stripLabel(info.get(index++ )));
		stats.attack = Integer.parseInt(stripLabel(info.get(index++ )));
		stats.defense = Integer.parseInt(stripLabel(info.get(index++ )));
		stats.special_attack = Integer.parseInt(stripLabel(info.get(index++ )));
		stats.special_defense = Integer.parseInt(stripLabel(info.get(index++ )));
		stats.speed = Integer.parseInt(stripLabel(info.get(index++ )));

		while (!info.get(index).equalsIgnoreCase("//Training"))
			index++ ;
		index++ ;
		catch_rate = Integer.parseInt(stripLabel(info.get(index++ )));
		base_exp = Integer.parseInt(stripLabel(info.get(index++ )));
		growth_rate = Integer.parseInt(stripLabel(info.get(index++ )));
		// Should remove quotes too
		description = stripLabel(info.get(index++ )).replaceAll("^\"|\"$", "");

		while (!info.get(index).equalsIgnoreCase("//Learnset"))
			index++ ;
		index++ ;
		learnset = new HashMap<Integer, String>();
		while (!info.get(index).equalsIgnoreCase("//HM/TM")) {
			String[] ar = info.get(index++ ).split(" ");
			learnset.put(Integer.parseInt(ar[0]), ar[1]);
		}
		index++ ;
		tmset = new ArrayList<String>();
		while (index < info.size())
			tmset.add(info.get(index++ ));
	}

	public void loadMoveSet(ArrayList<String> data) {
		known_moves = new ArrayList<Move>();
		for (String str : data)
			known_moves.add(Move.lookup(str));
	}

	public void generateStats(int lvl) {

	}

	public static void init() {
		String filename = "src/data/Pokemon_Data.txt";
		ArrayList<Pokemon> all = new ArrayList<Pokemon>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			ArrayList<String> lst = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (isUniform(line, '-')) {
					all.add(new Pokemon(lst));
					lst = new ArrayList<String>();
				} else
					lst.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		all_pokemon = orderPokemon(all);
	}

	public static Pokemon[] orderPokemon(ArrayList<Pokemon> lst) {
		Pokemon[] all = new Pokemon[251];
		Pokedex.pkmn_lookup = new HashMap<String, Integer>();
		for (Pokemon p : lst) {
			all[p.ID - 1] = p;
			Pokedex.pkmn_lookup.put(p.name.toLowerCase(), p.ID - 1);
		}
		return all;
	}

	// Loads a list of Pokemon from save data.
	public static ArrayList<Pokemon> loadPokemon(ArrayList<String> data) {
		ArrayList<Pokemon> lst = new ArrayList<Pokemon>();
		int count = 0;
		while (count < data.size()) {
			String name = data.get(count++ );
			ArrayList<String> moveset = new ArrayList<String>();
			String line;
			boolean end;
			do {
				line = data.get(count++ );
				end = isUniform(line, ':');
				if (!end)
					moveset.add(line);
			} while (!end);
			ArrayList<String> statlist = new ArrayList<String>();
			do {
				line = data.get(count++ );
				end = isUniform(line, '*');
				if (!end)
					statlist.add(line);
			} while (!end);
			Pokemon p = new Pokemon(name);
			p.loadMoveSet(moveset);
			p.stats = new Stats(statlist);
		}
		return lst;
	}

	public static boolean isUniform(String str, char c) {
		if (str.isEmpty())
			return false;
		for (char h : str.toCharArray())
			if (c != h)
				return false;
		return true;
	}

	public static String stripLabel(String str) {
		if (str.indexOf(":") == str.length() - 1)
			return "";
		String temp = str.substring(str.indexOf(":") + 2);
		for (char c : bad_chars.toCharArray())
			temp = temp.replace(c, ' ');
		while (temp.endsWith(" "))
			temp = temp.substring(0, temp.length() - 1);
		return temp;
	}

	public static int getType(String str) {
		if (str == "FIRE") {
			return 0;
		} else if (str == "WATER") {
			return 1;
		} else if (str == "GRASS") {
			return 2;
		} else if (str == "GROUND") {
			return 3;
		} else if (str == "ROCK") {
			return 4;
		} else if (str == "DARK") {
			return 5;
		} else if (str == "GHOST") {
			return 6;
		} else if (str == "STEEL") {
			return 7;
		} else if (str == "ELECTRIC") {
			return 8;
		} else if (str == "FLYING") {
			return 9;
		} else if (str == "DRAGON") {
			return 10;
		} else if (str == "ICE") {
			return 11;
		} else if (str == "PSYCHIC") {
			return 12;
		} else if (str == "POISON") {
			return 13;
		} else if (str == "FIGHTING") {
			return 14;
		} else if (str == "NORMAL") {
			return 15;
		}
		return 15;
	}
}
