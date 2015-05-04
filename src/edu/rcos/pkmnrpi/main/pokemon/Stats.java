package edu.rcos.pkmnrpi.main.pokemon;

import java.util.List;

import edu.rcos.pkmnrpi.main.pokedex.Pokedex;
import edu.rcos.pkmnrpi.main.util.FileParser;

// Data holder class for Pokemon
public class Stats {
	public static final int NORM = 0, FAINT = 1, PAR = 2, SLEEP = 3, POISONED = 4;

	public int max_health, attack, defense, special_attack, special_defense, speed;
	public int[] base = new int[6], ev = { 1, 1, 1, 1, 1, 1 }, iv = { 1, 1, 1, 1, 1, 1 };
	public int level, exp, total_exp, happiness, state, current_health;
	public String growth_rate;

	public Stats() {}

	public Stats(Stats s) {
		max_health = s.max_health;
		current_health = s.current_health;
		attack = s.attack;
		defense = s.defense;
		special_attack = s.special_attack;
		special_defense = s.special_defense;
		speed = s.speed;
		level = s.level;
		exp = s.exp;
		total_exp = s.total_exp;
		happiness = s.happiness;
		state = s.state;
		base = deepCopy(s.base);
		ev = deepCopy(s.ev);
		iv = deepCopy(s.iv);
		growth_rate = s.growth_rate;
	}

	public void calibrate(int level) {
		this.level = level;
		max_health = ((iv[0] + 2 * base[0] + ev[0] / 4) * level / 100) + 10 + level;
		attack = ((iv[1] + 2 * base[1] + ev[1] / 4) * level / 100) + 5;
		defense = ((iv[2] + 2 * base[2] + ev[2] / 4) * level / 100) + 5;
		special_attack = ((iv[3] + 2 * base[3] + ev[3] / 4) * level / 100) + 5;
		special_defense = ((iv[4] + 2 * base[4] + ev[4] / 4) * level / 100) + 5;
		speed = ((iv[5] + 2 * base[5] + ev[5] / 4) * level / 100) + 5;
	}

	private static int[] parseArray(String str) {
		String[] ary = str.split(",");
		int[] a = new int[ary.length];
		for (int i = 0; i < ary.length; ++i)
			a[i] = Integer.parseInt(ary[i]);
		return a;
	}

	public void merge(List<String> data) {
		int index = 0;
		level = Integer.parseInt(data.get(index++ ));
		ev = parseArray(data.get(index++ ));
		iv = parseArray(data.get(index++ ));
		calibrate(level);
		String line = data.get(index++ );
		if (line.equalsIgnoreCase("max"))
			current_health = max_health;
		line = data.get(index++ );
		if (line.equalsIgnoreCase("lookup")) {
			total_exp = Pokemon.levelToEXP(level, growth_rate);
			exp = 0;
		}
		happiness = Integer.parseInt(data.get(index++ ));
		state = Integer.parseInt(data.get(index++ ));
	}

	public int getTotalStatValue() {
		return max_health + attack + defense + special_attack + special_defense + speed;
	}

	public String toString() {
		String str = "\n";
		return max_health + str + current_health + str + attack + str + defense + str + special_attack + str + special_defense + str + speed + str + level + str + exp + str
				+ total_exp + str + happiness + str + state + str;
	}

	public static int sum(int[] a) {
		int i = 0;
		for (int b : a)
			i += b;
		return i;
	}

	public void initIV() {
		for (int i = 0; i < 6; ++i)
			iv[i] = genIV();
	}

	private static int genIV() {
		return (int) (5 + Math.random() * 20);
	}

	public static void init() {
		List<String> data = FileParser.parseFile("data/game/base_stats.txt");
		for (String line : data) {
			String[] ary = line.split(",");
			Pokemon p = Pokedex.getPokemon(ary[0]);
			String rate = p.stats.growth_rate;
			p.stats = new Stats();
			p.stats.base[0] = Integer.parseInt(ary[1]);
			p.stats.base[1] = Integer.parseInt(ary[2]);
			p.stats.base[2] = Integer.parseInt(ary[3]);
			p.stats.base[3] = Integer.parseInt(ary[4]);
			p.stats.base[4] = Integer.parseInt(ary[5]);
			p.stats.base[5] = Integer.parseInt(ary[6]);
			p.stats.level = 0;
			p.stats.happiness = p.baseHappiness;
			p.stats.exp = p.stats.total_exp = 0;
			p.stats.growth_rate = rate;
		}
	}

	public int[] deepCopy(int[] a) {
		if (a == null)
			return null;
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; ++i)
			b[i] = a[i];
		return b;
	}
}
