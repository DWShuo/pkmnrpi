package edu.rcos.pkmnrpi.main.pokemon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rcos.pkmnrpi.main.animations.Sprite;
import edu.rcos.pkmnrpi.main.battle.BattleEngine;
import edu.rcos.pkmnrpi.main.pokedex.Pokedex;
import edu.rcos.pkmnrpi.main.pokedex.PokedexUI;
import edu.rcos.pkmnrpi.main.pokemon.ai.MostDamageAI;

public class Pokemon implements PokedexUI { //, AI {
	public static final int
		FIRE = 0,
		WATER = 1,
		GRASS = 2,
		GROUND = 3,
		ROCK = 4,
		DARK = 5,
		GHOST = 6,
		STEEL = 7,
		ELECTRIC = 8,
		FLYING = 9,
		DRAGON = 10,
		ICE = 11,
		PSYCHIC = 12,
		POISON = 13,
		FIGHTING = 14,
		NORMAL = 15,
		BUG = 16;
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

	public String name, species, description;
	public Sprite sprite;
	public boolean male = true, wild = false;
	public int type, type2 = -1;
	public double height = 1, weight = 10;
	public int ID;
	public Stats stats = new Stats();
	public int catchRate, baseExperience, baseHappiness;
	public Map<String, String> evolutions = new HashMap<String, String>();
	public List<Integer> ages = new ArrayList<Integer>();
	public List<Move> history = new ArrayList<Move>(), learnset = new ArrayList<Move>();
	public List<Move> knownMoves = new ArrayList<Move>(), tmset = new ArrayList<Move>();
	private AI ai = new MostDamageAI();
	
	public Pokemon() {
		name = "Pidgey";
		species = "Bird";
		type = FLYING;
		ID = 16;
		height = .3;
		weight = 1.8;
		catchRate = 225;
		baseExperience = 55;
		baseHappiness = 70;
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
		type2 = p.type2;
		height = p.height;
		weight = p.weight;
		ID = p.ID;
		catchRate = p.catchRate;
		baseExperience = p.baseExperience;
		baseHappiness = p.baseHappiness;
		evolutions = p.evolutions;
		learnset = p.learnset;
		tmset = p.tmset;
		ages = p.ages;
		description = p.description;
		stats = new Stats(p.stats);
	}
	
	public AI getAI() {
		return ai;
	}

	public void setAI(AI ai) {
		this.ai = ai;
	}

	public int calculateEXP(Pokemon other, BattleEngine en) {
		double a = wild ? 1.5 : 1;
		double b = baseExperience;
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
				if (knownMoves.size() > 3)
					continue;
				// if (Math.random() < ((double) ages.get(i)) / stats.level)
				knownMoves.add(m);
			}
		}
		// System.out.println(known_moves.size());
	}

	public static int calculateDamage(Pokemon attacker, Pokemon defender, Move atk, double crit, double type) {
		if (atk.category == Move.STATUS)
			return 0;
		double modifier = STAB(attacker, atk) * type * crit * (.85 + Math.random() * .15);
		double level = attacker.stats.level;
		double attack = atk.category == Move.PHYSICAL ?
				attacker.stats.attack : attacker.stats.special_attack;
		double defense = atk.category == Move.PHYSICAL ?
				defender.stats.defense : defender.stats.special_defense;
		double base = atk.damage;
		return (int) (((2 * level + 10) / 250 * attack / defense * base + 2) * modifier);
	}

	public static double critRoll(Move m) {
		return m.crit_chance > Math.random() ? (1.3 + Math.random() * .5) : 1;
	}

	public static double STAB(Pokemon p, Move m) {
		return p.type == m.type || p.type2 == m.type ? 1.5 : 1;
	}

	public static double typeModifier(Pokemon p, Move m) {
		double base = (p.type2 >= 0) ? TE[m.type][p.type2] : 1;
		return base * TE[m.type][p.type];
	}

	public String staticToString() {
		String all = "", str = "\n";
		all += ID + str + name + str + getType(type);
		if (type2 > 0)
			all += "/" + getType(type2);
		all += str + species + str + height + str + weight + str + catchRate + str;
		all += baseHappiness + str + description + str;
		return all;
	}

	public void loadMoveSet(List<String> data) {
		knownMoves = new ArrayList<Move>();
		for (String str : data)
			knownMoves.add(Move.lookup(str));
	}

	public int expToNextLevel() {
		return levelToEXP(stats.level + 1, stats.growth_rate);
	}

	public static int levelToEXP(int n, String growthRate) {
		if (growthRate.equalsIgnoreCase("Medium Slow")) {
			return ((6 * n * n * n) / 5) - 15 * n * n + 100 * n - 140;
		} else if (growthRate.equalsIgnoreCase("Medium Fast")) {
			return n * n * n;
		} else if (growthRate.equalsIgnoreCase("Slow")) {
			return 5 * n * n * n / 4;
		} else if (growthRate.equalsIgnoreCase("Fast")) {
			return 4 * n * n * n / 5;
		} else if (growthRate.equalsIgnoreCase("Fluctuating")) {
			int k = 1;
			if (n <= 15)
				k = (n + 1) / 3 + 24;
			else if (n <= 36)
				k = n + 14;
			else
				k = n / 2 + 32;
			return n * n * n * k / 50;
		} else if (growthRate.equalsIgnoreCase("Erratic")) {
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
	public static List<Pokemon> loadPokemon(List<String> data) {
		List<Pokemon> lst = new ArrayList<Pokemon>();
		int count = 0;
		while (count < data.size()) {
			Pokemon p = new Pokemon(data.get(count++ ));
			String line = data.get(count++ );
			while (!isUniform(line, '*')) {
				p.knownMoves.add(Move.lookup(line));
				line = data.get(count++ );
			}
			line = data.get(count++ );
			List<String> info = new ArrayList<String>();
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
		for (Move m : knownMoves)
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

	// Implement AI here
	public Move decide(Pokemon enemy) {
		return ai.decide(this, enemy);
	}
}
