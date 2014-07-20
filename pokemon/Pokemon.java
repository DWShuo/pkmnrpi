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
	public double height = 1, weight = 10;
	public int ID;
	public Stats stats = new Stats();
	public int catch_rate, base_exp, base_happiness;
	public HashMap<String, String> evolutions = new HashMap<String, String>();
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
		ArrayList<String> info = FileParser.parseFile("src/data/pokemon data.txt");
		int index = 0;
		all_pokemon = new Pokemon[251];
		Pokemon p;
		while (index < info.size()) {
			p = new Pokemon();
			p.ID = Integer.parseInt(info.get(index++ ));
			p.name = info.get(index++ );
			String types = info.get(index++ );
			if (types.contains("/")) {
				String[] ar = types.split("/");
				p.type = getType(ar[0]);
				p.t2 = getType(ar[1]);
			} else
				p.type = getType(types);
			p.species = info.get(index++ );
			String str = info.get(index++ );
			if (isDouble(str))
				p.height = Double.parseDouble(str);
			str = info.get(index++ );
			if (isDouble(str))
				p.weight = Double.parseDouble(str);
			str = info.get(index++ );
			if (isDouble(str))
				p.catch_rate = Integer.parseInt(str);
			str = info.get(index++ );
			if (isDouble(str))
				p.base_exp = Integer.parseInt(str);
			p.base_happiness = Integer.parseInt(info.get(index++ ));
			p.growth_rate = info.get(index++ );
			p.description = info.get(index++ );

			p.evolutions = new HashMap<String, String>();
			str = info.get(index++ );
			while (!isUniform(str, '*')) {
				if (str.contains(",")) {
					String[] ar = str.split(",");
					p.evolutions.put(ar[1], ar[0]);
				}
				str = info.get(index++ );
			}
			index++ ;
			p.learnset = new HashMap<Integer, Move>();
			str = info.get(index++ );
			while (!isUniform(str, '*')) {
				if (str.contains(",")) {
					String[] ar = str.split(",");
					p.learnset.put(Integer.parseInt(ar[1]), Move.lookup(ar[0]));
				}
				str = info.get(index++ );
			}
			index++ ;
			p.tmset = new ArrayList<Move>();
			str = info.get(index++ );
			while (!isUniform(str, '*')) {
				p.tmset.add(Move.lookup(str));
				str = info.get(index++ );
			}

			all_pokemon[p.ID - 1] = p;
			Pokedex.pkmn_lookup.put(p.name.toLowerCase(), p.ID);
		}
	}

	private static boolean isDouble(String str) {
		String good = "1234567890.";
		for (char c : str.toCharArray())
			if (!good.contains(c + ""))
				return false;
		return true;
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
		all += ID + str;
		for (Move m : known_moves)
			all += m.name + str;
		all += "*************\n";
		all += stats.toString();
		return all + "*************\n";
	}

	public static String getType(int i) {
		if (i == 0) {
			return "FIRE";
		} else if (i == 1) {
			return "WATER";
		} else if (i == 2) {
			return "GRASS";
		} else if (i == 3) {
			return "GROUND";
		} else if (i == 4) {
			return "ROCK";
		} else if (i == 5) {
			return "DARK";
		} else if (i == 6) {
			return "GHOST";
		} else if (i == 7) {
			return "STEEL";
		} else if (i == 8) {
			return "ELECTRIC";
		} else if (i == 9) {
			return "FLYING";
		} else if (i == 10) {
			return "DRAGON";
		} else if (i == 11) {
			return "ICE";
		} else if (i == 12) {
			return "PSYCHIC";
		} else if (i == 13) {
			return "POISON";
		} else if (i == 14) {
			return "FIGHTING";
		} else if (i == 15) {
			return "NORMAL";
		}
		return "NORMAL";
	}

	public static int getType(String str) {
		str = str.toUpperCase();
		if (str.equals("FIRE")) {
			return 0;
		} else if (str.equals("WATER")) {
			return 1;
		} else if (str.equals("GRASS")) {
			return 2;
		} else if (str.equals("GROUND")) {
			return 3;
		} else if (str.equals("ROCK")) {
			return 4;
		} else if (str.equals("DARK")) {
			return 5;
		} else if (str.equals("GHOST")) {
			return 6;
		} else if (str.equals("STEEL")) {
			return 7;
		} else if (str.equals("ELECTRIC")) {
			return 8;
		} else if (str.equals("FLYING")) {
			return 9;
		} else if (str.equals("DRAGON")) {
			return 10;
		} else if (str.equals("ICE")) {
			return 11;
		} else if (str.equals("PSYCHIC")) {
			return 12;
		} else if (str.equals("POISON")) {
			return 13;
		} else if (str.equals("FIGHTING")) {
			return 14;
		} else if (str.equals("NORMAL")) {
			return 15;
		}
		return 15;
	}
}
