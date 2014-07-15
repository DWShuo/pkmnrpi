package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import pokedex.Pokedex;
import pokedex.PokedexUI;

public class Pokemon implements PokedexUI {
	// TYPES
	public static final int FIRE = 0, WATER = 1, GRASS = 2, GROUND = 3, ROCK = 4, DARK = 5, GHOST = 6, STEEL = 7, ELECTRIC = 8, FLYING = 9, DRAGON = 10, ICE = 11, PSYCHIC = 12,
			POISON = 13, FIGHTING = 14, NORMAL = 15;
	public static Pokemon[] all_pokemon;
	public String name, species, description;
	public boolean male = true;
	public int type, t2 = -1;
	public double height, weight;
	public int ID;
	public int max_health, attack, defense, speed, spec_attack, spec_defense;
	public int current_health, level;
	public int catch_rate, base_exp, growth_rate;
	public HashMap<Integer, String> evolutions, learnset;
	public ArrayList<String> tmset;

	// To be used with pokedex info only
	public Pokemon(ArrayList<String> info) {
		int index = 0;

		ID = Integer.parseInt(strip_label(info.get(index++ )));
		name = strip_label(info.get(index++ ));
		String types = strip_label(info.get(index++ ));
		if (types.contains("/")) {
			String[] ar = types.split("/");
			type = getType(ar[0]);
			t2 = getType(ar[1]);
		} else
			type = getType(types);
		species = strip_label(info.get(index++ ));
		height = Double.parseDouble(strip_label(info.get(index++ )));
		weight = Double.parseDouble(strip_label(info.get(index++ )));

		evolutions = new HashMap<Integer, String>();
		while (!info.get(index).equalsIgnoreCase("//Stats")) {
			String[] ar = info.get(index++ ).split(" ");
			evolutions.put(Integer.parseInt(ar[0]), ar[1]);
		}
		index++ ;
		max_health = Integer.parseInt(strip_label(info.get(index++ )));
		attack = Integer.parseInt(strip_label(info.get(index++ )));
		defense = Integer.parseInt(strip_label(info.get(index++ )));
		spec_attack = Integer.parseInt(strip_label(info.get(index++ )));
		spec_defense = Integer.parseInt(strip_label(info.get(index++ )));
		speed = Integer.parseInt(strip_label(info.get(index++ )));

		while (!info.get(index).equalsIgnoreCase("//Training"))
			index++ ;
		index++ ;
		catch_rate = Integer.parseInt(strip_label(info.get(index++ )));
		base_exp = Integer.parseInt(strip_label(info.get(index++ )));
		growth_rate = Integer.parseInt(strip_label(info.get(index++ )));
		// Should remove quotes too
		description = strip_label(info.get(index++ )).replaceAll("^\"|\"$", "");

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

	public void generate_stats(int lvl) {

	}

	public static void init() {
		String filename = "src/data/Pokemon_Data.txt";
		ArrayList<Pokemon> all = new ArrayList<Pokemon>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			ArrayList<String> lst = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (is_uniform(line, '-')) {
					all.add(new Pokemon(lst));
					lst = new ArrayList<String>();
				} else
					lst.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		all_pokemon = order_pokemon(all);
	}

	public static Pokemon[] order_pokemon(ArrayList<Pokemon> lst) {
		Pokemon[] all = new Pokemon[251];
		Pokedex.pkmn_lookup = new HashMap<String, Integer>();
		for (Pokemon p : lst) {
			all[p.ID - 1] = p;
			Pokedex.pkmn_lookup.put(p.name.toLowerCase(), p.ID - 1);
		}
		return all;
	}

	public static boolean is_uniform(String str, char c) {
		str.replace(' ', '-');
		for (char h : str.toCharArray())
			if (c != h)
				return false;
		return true;
	}

	public static String strip_label(String str) {
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
