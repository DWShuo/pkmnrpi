package edu.rcos.pkmnrpi.main.items;

import edu.rcos.pkmnrpi.main.game.GameEngine;

import java.util.ArrayList;

public class Ball extends Item {
	public static final int POKEBALL = 0, GREATBALL = 1, ULTRABALL = 2, MASTERBALL = 3, FASTBALL = 4, HEAVYBALL = 5, LEVELBALL = 6, LUREBALL = 7, LOVEBALL = 8, MOONBALL = 9,
			SAFARIBALL = 10, FRIENDBALL = 11;

	public int balltype;

	public Ball(String name) {
		super(name + "," + Item.POKEBALL);
	}

	public Ball(ArrayList<String> data) {
		super(data);
		type = Item.POKEBALL;
	}

	public static ArrayList<Ball> loadAll(ArrayList<String> data) {
		ArrayList<Ball> b = new ArrayList<Ball>();
		int count = 0;
		while (count < data.size()) {
			String[] ary = data.get(count++ ).split(",");
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(ary[0]);
			temp.add(ary[1]);
			b.add(new Ball(temp));
		}
		return b;
	}

	public static ArrayList<Ball> staticLoad() {
		ArrayList<Ball> list = new ArrayList<Ball>();
		list.add(new Ball("pokeball"));
		list.add(new Ball("greatball"));
		list.add(new Ball("ultraball"));
		list.add(new Ball("masterball"));
		list.add(new Ball("fastball"));
		list.add(new Ball("heavyball"));
		list.add(new Ball("levelball"));
		list.add(new Ball("lureball"));
		list.add(new Ball("loveball"));
		list.add(new Ball("moonball"));
		list.add(new Ball("safariball"));
		list.add(new Ball("friendball"));
		return list;
	}

	@Override
	public String toString() {
		return null;
	}

	public double applyCatchRateMultiplier(GameEngine e, double rate) {
		if (balltype == POKEBALL)
			return pokeball(e, rate);
		if (balltype == GREATBALL)
			return greatball(e, rate);
		if (balltype == ULTRABALL)
			return ultraball(e, rate);
		if (balltype == MASTERBALL)
			return masterball(e, rate);
		if (balltype == FASTBALL)
			return fastball(e, rate);
		if (balltype == HEAVYBALL)
			return heavyball(e, rate);
		if (balltype == LEVELBALL)
			return levelball(e, rate);
		if (balltype == LUREBALL)
			return lureball(e, rate);
		if (balltype == LOVEBALL)
			return loveball(e, rate);
		if (balltype == MOONBALL)
			return moonball(e, rate);
		if (balltype == SAFARIBALL)
			return safariball(e, rate);
		if (balltype == FRIENDBALL)
			return friendball(e, rate);
		return 0;
	}

	public static int getID(String str) {
		if (str.equalsIgnoreCase("POKEBALL"))
			return POKEBALL;
		if (str.equalsIgnoreCase("GREATBALL"))
			return GREATBALL;
		if (str.equalsIgnoreCase("ULTRABALL"))
			return ULTRABALL;
		if (str.equalsIgnoreCase("MASTERBALL"))
			return MASTERBALL;
		if (str.equalsIgnoreCase("FASTBALL"))
			return FASTBALL;
		if (str.equalsIgnoreCase("HEAVYBALL"))
			return HEAVYBALL;
		if (str.equalsIgnoreCase("LEVELBALL"))
			return LEVELBALL;
		if (str.equalsIgnoreCase("LUREBALL"))
			return LUREBALL;
		if (str.equalsIgnoreCase("LOVEBALL"))
			return LOVEBALL;
		if (str.equalsIgnoreCase("MOONBALL"))
			return MOONBALL;
		if (str.equalsIgnoreCase("SAFARIBALL"))
			return SAFARIBALL;
		if (str.equalsIgnoreCase("FRIENDBALL"))
			return FRIENDBALL;
		return 0;
	}

	private double pokeball(GameEngine e, double rate) {
		return rate;
	}

	private double greatball(GameEngine e, double rate) {
		return 1.5 * rate;
	}

	private double ultraball(GameEngine e, double rate) {
		return 2 * rate;
	}

	private double masterball(GameEngine e, double rate) {
		return 225 * rate;
	}

	private double fastball(GameEngine e, double rate) {
		return e.state.enemy.stats.speed > 100 ? 4 * rate : 1 * rate;
	}

	private double heavyball(GameEngine e, double rate) {
		double w = e.state.enemy.weight;
		return rate + (w < 995.4 ? -20 : (w < 1493.2 ? 20 : (w < 1990.8 ? 30 : 40)));
	}

	private double levelball(GameEngine e, double rate) {
		int lvl1 = e.state.enemy.stats.level;
		int lvl2 = e.state.defender.stats.level;
		double out = ((double) lvl2) / lvl1;
		if (out < 1)
			out = 1;
		if (out > 5)
			out = 5;
		return rate * out;
	}

	private double lureball(GameEngine e, double rate) {
		return rate;
	}

	private double loveball(GameEngine e, double rate) {
		boolean matching = e.state.enemy.species.equalsIgnoreCase(e.state.defender.species);
		boolean opposing = e.state.enemy.male != e.state.defender.male;
		return matching && opposing ? 8 * rate : rate;
	}

	private double moonball(GameEngine e, double rate) {
		return rate;
	}

	private double safariball(GameEngine e, double rate) {
		return 1.5 * rate;
	}

	private double friendball(GameEngine e, double rate) {
		// TODO: make caught pokemon happier
		return rate;
	}
}
