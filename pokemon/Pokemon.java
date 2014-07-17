package pokemon;

import java.util.ArrayList;
import java.util.HashMap;

import pokedex.Pokedex;
import pokedex.PokedexUI;
import pokemon.moves.Move;
import util.FileParser;

public class Pokemon implements PokedexUI {
	public static final int FIRE = 0, WATER = 1, GRASS = 2, GROUND = 3, ROCK = 4, DARK = 5, GHOST = 6, STEEL = 7, ELECTRIC = 8, FLYING = 9, DRAGON = 10, ICE = 11, PSYCHIC = 12,
			POISON = 13, FIGHTING = 14, NORMAL = 15;
	public static Pokemon[] all_pokemon;

	public String name, species, growth_rate, description;
	public boolean male = true;
	public int type, t2 = -1;
	public double height, weight;
	public int ID;
	public Stats stats;
	public int catch_rate, base_exp, base_happiness;
	public HashMap<Integer, String> evolutions = new HashMap<Integer, String>();
	public HashMap<Integer, Move> learnset = new HashMap<Integer, Move>();
	public ArrayList<Move> known_moves = new ArrayList<Move>(), tmset = new ArrayList<Move>();

	public Pokemon() {
		name = "Pidgey";
		species = "Bird";
		type = FLYING;
		ID = 16;
		height = .3;
		weight = 1.8;
		catch_rate = 225;
		base_exp = 55;
		growth_rate = "Medium Slow";
		base_happiness = 70;
		description = "This is a bird.";
	}

	// Used when loading pokemon by name
	public Pokemon(String name) {
		Pokemon p = Pokedex.getPokemon(name);
		this.name = p.name;
		species = p.species;
		male = p.male;
		type = p.type;
		t2 = p.t2;
		height = p.height;
		weight = p.weight;
		ID = p.ID;
		catch_rate = p.catch_rate;
		base_exp = p.base_exp;
		growth_rate = p.growth_rate;
		base_happiness = p.base_happiness;
		evolutions = p.evolutions;
		learnset = p.learnset;
		tmset = p.tmset;
		description = p.description;
	}

	// Loads all static pokemon info
	public static void init() {
		ArrayList<String> info = FileParser.parseFile("src/data/Pokemon_Data.txt");
		int index = 0;
		all_pokemon = new Pokemon[251];
		Pokemon p;
		while (index < info.size()) {
			p = new Pokemon();
			p.ID = Integer.parseInt(stripLabel(info.get(index++ )));
			p.name = stripLabel(info.get(index++ ));
			String types = stripLabel(info.get(index++ ));
			if (types.contains("/")) {
				String[] ar = types.split("/");
				p.type = getType(ar[0]);
				p.t2 = getType(ar[1]);
			} else
				p.type = getType(types);
			p.species = stripLabel(info.get(index++ ));
			p.height = Double.parseDouble(stripLabel(info.get(index++ )));
			p.weight = Double.parseDouble(stripLabel(info.get(index++ )));
			p.catch_rate = Integer.parseInt(stripLabel(info.get(index++ )));
			p.base_exp = Integer.parseInt(stripLabel(info.get(index++ )));
			p.base_happiness = Integer.parseInt(stripLabel(info.get(index++ )));
			p.growth_rate = stripLabel(info.get(index++ ));
			p.description = stripLabel(info.get(index++ ));

			p.evolutions = new HashMap<Integer, String>();
			while (!isUniform(info.get(index), '*')) {
				String[] ar = info.get(index++ ).split(",");
				p.evolutions.put(Integer.parseInt(ar[0]), ar[1]);
			}
			index++ ;
			p.learnset = new HashMap<Integer, Move>();
			while (!isUniform(info.get(index), '*')) {
				String[] ar = info.get(index++ ).split(",");
				p.learnset.put(Integer.parseInt(ar[0]), Move.lookup(ar[1]));
			}
			index++ ;
			p.tmset = new ArrayList<Move>();
			while (!isUniform(info.get(index), '*'))
				p.tmset.add(Move.lookup(info.get(index++ )));

			all_pokemon[p.ID - 1] = p;
		}
	}

	public void loadMoveSet(ArrayList<String> data) {
		known_moves = new ArrayList<Move>();
		for (String str : data)
			known_moves.add(Move.lookup(str));
	}

	public void generateStats(int lvl) {

	}

	// Loads a list of non static Pokemon from save data.
	public static ArrayList<Pokemon> loadPokemon(ArrayList<String> data) {
		ArrayList<Pokemon> lst = new ArrayList<Pokemon>();
		int count = 0;
		while (count < data.size()) {
			Pokemon p = new Pokemon(data.get(count++ ));
			String line = data.get(count++ );
			while (!isUniform(line, '*')) {
				p.known_moves.add(Move.lookup(line));
				line = data.get(count++ );
			}
			line = data.get(count++ );
			ArrayList<String> info = new ArrayList<String>();
			while (!isUniform(line, '*')) {
				info.add(line);
				line = data.get(count++ );
			}
			p.stats = new Stats(info);
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

	public String toString() {
		String str = "\n", all = name + str;
		for (Move m : known_moves)
			all += m.name + str;
		all += "*************\n";
		all += stats.toString();
		return all + "*************\n";
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
