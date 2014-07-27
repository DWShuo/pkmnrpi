package pokemon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import animations.Sprite;
import battle.BattleEngine;
import pokedex.Pokedex;
import pokedex.PokedexUI;
import util.FileParser;

public class Pokemon implements PokedexUI, AI {
	public static final int FIRE = 0, WATER = 1, GRASS = 2, GROUND = 3, ROCK = 4, DARK = 5, GHOST = 6, STEEL = 7, ELECTRIC = 8, FLYING = 9, DRAGON = 10, ICE = 11, PSYCHIC = 12,
			POISON = 13, FIGHTING = 14, NORMAL = 15, BUG = 16;
	public static final double[][] TE = {// Type Effectiveness
			{
					.5, .5, 2, 1, .5, 1, 1, 2, 1, 1, .5, 2, 1, 1, 1, 1, 2
			}, {
					2, .5, .5, 2, 2, 1, 1, 1, 1, 1, .5, 1, 1, 1, 1, 1, .5
			}, {
					.5, 2, .5, 2, 2, 1, 1, .5, 1, .5, .5, 1, 1, .5, 1, 1, .5
			}, {
					2, 1, .5, 1, 2, 1, 1, 2, 2, 0, 1, 1, 1, 2, 1, 1, .5
			}, {
					2, 1, 1, .5, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, .5, 1, 2
			}, {
					1, 1, 1, 1, 1, .5, 2, 1, 1, 1, 1, 1, 2, 1, .5, 1, 1
			}, {
					1, 1, 1, 1, 1, .5, 2, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1
			}, {
					.5, .5, 1, 1, 2, 1, 1, .5, .5, 1, 1, 2, 1, 1, 1, 1, 1
			}, {
					1, 2, .5, 0, 1, 1, 1, 1, .5, 2, .5, 1, 1, 1, 1, 1, 1
			}, {
					1, 1, 2, 1, .5, 1, 1, .5, .5, 1, 1, 1, 1, 1, 2, 1, 2
			}, {
					1, 1, 1, 1, 1, 1, 1, .5, 1, 1, 2, 1, 1, 1, 1, 1, 1
			}, {
					.5, .5, 2, 2, 1, 1, 1, .5, 1, 2, 2, .5, 1, 1, 1, 1, 1
			}, {
					1, 1, 1, 1, 1, 0, 1, .5, 1, 1, 1, 1, .5, 2, 2, 1, 1
			}, {
					1, 1, 1, .5, .5, 1, .5, 0, 1, 1, 1, 1, 1, .5, 1, 1, 1
			}, {
					1, 1, 1, 1, 2, 2, 0, 2, 1, .5, 1, 2, .5, .5, 1, 2, .5
			}, {
					1, 1, 1, 1, .5, 1, 0, .5, 1, 1, 1, 1, 1, 1, 1, 1, 1
			}, {
					.5, 1, 2, 1, 1, 2, .5, .5, 1, .5, 1, 1, 2, .5, .5, 1, 1
			},
	};
	public static Pokemon[] all_pokemon;

	public String name, species, description;
	public Sprite sprite;
	public boolean male = true, wild = false;
	public int type, t2 = -1;
	public double height = 1, weight = 10;
	public int ID;
	public Stats stats = new Stats();
	public int catch_rate, base_exp, base_happiness;
	public HashMap<String, String> evolutions = new HashMap<String, String>();
	public ArrayList<Integer> ages = new ArrayList<Integer>();
	public ArrayList<Move> history = new ArrayList<Move>(), learnset = new ArrayList<Move>();
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
		base_happiness = 70;
		description = "This is a bird.";
	}

	// Generates wild pokemon
	public Pokemon(String name, int level) {
		deepCopy(name);
		stats.initIV();
		stats.calibrate(level);
		stats.current_health = stats.max_health;
		selectRandomMoves();
		wild = true;
	}

	// Used when loading pokemon by name
	public Pokemon(String name) {
		deepCopy(name);
	}

	private void deepCopy(String name) {
		Pokemon p = Pokedex.getPokemon(name);
		if (p == null)
			throw new IllegalArgumentException(name + " was not recognized as a Pokemon name...");
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
		base_happiness = p.base_happiness;
		evolutions = p.evolutions;
		learnset = p.learnset;
		tmset = p.tmset;
		ages = p.ages;
		description = p.description;
		stats = new Stats(p.stats);
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
			p.stats.growth_rate = info.get(index++ );
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
			p.learnset = new ArrayList<Move>();
			p.ages = new ArrayList<Integer>();
			str = info.get(index++ );
			while (!isUniform(str, '*')) {
				if (str.contains(",")) {
					String[] ar = str.split(",");
					p.ages.add(Integer.parseInt(ar[1]));
					p.learnset.add(Move.lookup(ar[0]));
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
		Stats.init();
	}

	public int calculateEXP(Pokemon other, BattleEngine en) {
		double a = wild ? 1.5 : 1;
		double b = base_exp;
		double e = 1;// change for lucky egg
		double l = stats.level;
		double s = 1; // number of pokemon to divide by
		return (int) (a * b * e * l / (7 * s));
	}

	public void levelUp(int n) {
		// int[] temps = { stats.max_health, stats.attack, stats.defense, stats.special_attack, stats.special_defense, stats.speed };
		stats.calibrate(stats.level + n);
		// int[] diffs = { stats.max_health - temps[0], stats.attack - temps[1], stats.defense - temps[2], stats.special_attack - temps[3], stats.special_defense - temps[4],
		// stats.speed - temps[5] };
		// TODO: display diffs
	}

	private void selectRandomMoves() {
		for (int i = ages.size() - 1; i >= 0; --i) {
			if (ages.get(i) <= stats.level) {
				Move m = learnset.get(i);
				if (!history.contains(m))
					history.add(m);
				if (known_moves.size() > 3)
					continue;
				// if (Math.random() < ((double) ages.get(i)) / stats.level)
				known_moves.add(m);
			}
		}
		// System.out.println(known_moves.size());
	}

	public static int calculateDamage(Pokemon attacker, Pokemon defender, Move atk, BattleEngine e, double crit, double type) {
		if (atk.category == Move.STATUS)
			return 0;
		double modifier = STAB(attacker, atk) * type * crit * (.85 + Math.random() * .15);
		double level = attacker.stats.level;
		double attack = atk.category == Move.PHYSICAL ? attacker.stats.attack : attacker.stats.special_attack;
		double defense = atk.category == Move.PHYSICAL ? defender.stats.defense : defender.stats.special_defense;
		double base = atk.damage;
		return (int) (((2 * level + 10) / 250 * attack / defense * base + 2) * modifier);
	}

	public static double critRoll(Move m) {
		return m.crit_chance > Math.random() ? (1.3 + Math.random() * .5) : 1;
	}

	public static double STAB(Pokemon p, Move m) {
		return p.type == m.type || p.t2 == m.type ? 1.5 : 1;
	}

	public static double typeModifier(Pokemon p, Move m) {
		double base = (p.t2 >= 0) ? TE[m.type][p.t2] : 1;
		return base * TE[m.type][p.type];
	}

	public String staticToString() {
		String all = "", str = "\n";
		all += ID + str + name + str + getType(type);
		if (t2 > 0)
			all += "/" + getType(t2);
		all += str + species + str + height + str + weight + str + catch_rate + str;
		all += base_happiness + str + description + str;
		return all;
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

	public int expToNextLevel() {
		return levelToEXP(stats.level + 1, stats.growth_rate);
	}

	public static int levelToEXP(int n, String growth_rate) {
		if (growth_rate.equalsIgnoreCase("Medium Slow")) {
			return ((6 * n * n * n) / 5) - 15 * n * n + 100 * n - 140;
		} else if (growth_rate.equalsIgnoreCase("Medium Fast")) {
			return n * n * n;
		} else if (growth_rate.equalsIgnoreCase("Slow")) {
			return 5 * n * n * n / 4;
		} else if (growth_rate.equalsIgnoreCase("Fast")) {
			return 4 * n * n * n / 5;
		} else if (growth_rate.equalsIgnoreCase("Fluctuating")) {
			int k = 1;
			if (n <= 15)
				k = (n + 1) / 3 + 24;
			else if (n <= 36)
				k = n + 14;
			else
				k = n / 2 + 32;
			return n * n * n * k / 50;
		} else if (growth_rate.equalsIgnoreCase("Erratic")) {
			int k = 1;
			int r = 100;
			if (n <= 50)
				k = 100 - n;
			else if (n <= 68)
				k = 150 - n;
			else if (n <= 98) {
				k = (1911 - 10 * n) / 3;
				r = 500;
			}
			return n * n * n * k / r;
		}
		return n * n * n;
	}

	public void generateStats(int lvl) {
		stats.level = lvl;
		stats.total_exp = levelToEXP(lvl, stats.growth_rate);
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
			p.stats.merge(info);
			lst.add(p);
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

	public boolean equals(Pokemon p) {
		return name.equalsIgnoreCase(p.name);
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

	public static Color typeColor(int i) {
		if (i == 0) {
			return new Color(240, 128, 48);
		} else if (i == 1) {
			return new Color(104, 144, 240);
		} else if (i == 2) {
			return new Color(120, 200, 80);
		} else if (i == 3) {
			return new Color(224, 192, 104);
		} else if (i == 4) {
			return new Color(184, 160, 56);
		} else if (i == 5) {
			return new Color(112, 88, 72);
		} else if (i == 6) {
			return new Color(112, 88, 152);
		} else if (i == 7) {
			return new Color(184, 184, 208);
		} else if (i == 8) {
			return new Color(248, 208, 48);
		} else if (i == 9) {
			return new Color(168, 144, 240);
		} else if (i == 10) {
			return new Color(112, 56, 248);
		} else if (i == 11) {
			return new Color(152, 216, 216);
		} else if (i == 12) {
			return new Color(248, 88, 136);
		} else if (i == 13) {
			return new Color(160, 64, 160);
		} else if (i == 14) {
			return new Color(192, 48, 40);
		} else if (i == 15) {
			return new Color(168, 168, 120);
		} else if (i == 16) {
			return new Color(168, 184, 32);
		}
		return new Color(168, 168, 120);
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
		} else if (str.equals("BUG")) {
			return 16;
		}
		return 15;
	}

	@Override
	public Move decide(Pokemon p) {
		Move m = known_moves.get(0);
		for (Move mo : known_moves)
			if (m.damage < mo.damage)
				m = mo;
		return m;
	}
}
