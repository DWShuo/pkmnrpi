package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import pokemon.Pokemon;
import battle.BattleEngine;
import util.Pair;

public class Spawn {
	public static HashMap<String, Spawn> ALL = new HashMap<String, Spawn>();

	public Rectangle bounds = new Rectangle();
	public HashMap<Integer, ArrayList<Pair<String, Double, Integer>>> chances = new HashMap<Integer, ArrayList<Pair<String, Double, Integer>>>();

	public Spawn() {}

	public boolean roll(GameEngine e) {
		if (!bounds.contains(e.board.player.x, e.board.player.y))
			return false;
		ArrayList<Pair<String, Double, Integer>> temp = chances.get(e.board.map.mapdata[e.board.player.y + e.board.map.centery][e.board.player.x + e.board.map.centerx]);
		if (temp == null)
			return false;
		Collections.shuffle(temp);
		for (Pair<String, Double, Integer> p : temp) {
			if (Math.random() > p.b)
				continue;
			e.battle = new BattleEngine(e, new Pokemon(p.a, (int) (p.c + Math.random() * 5)));
			e.startBattle();
			return true;
		}
		return false;
	}

	public static void init() {
		Spawn s = new Spawn();
		s.bounds = new Rectangle(-24, -42, 50, 75);
		ArrayList<Pair<String, Double, Integer>> list = new ArrayList<Pair<String, Double, Integer>>();
		list.add(new Pair<String, Double, Integer>("Bayleef", .3, 30));
		list.add(new Pair<String, Double, Integer>("Chikorita", .3, 24));
		list.add(new Pair<String, Double, Integer>("Pidgeot", .3, 45));
		s.chances.put(1519, list);
		list = new ArrayList<Pair<String, Double, Integer>>();
		list.add(new Pair<String, Double, Integer>("Meganium", .3, 38));
		list.add(new Pair<String, Double, Integer>("Typhlosion", .3, 40));
		list.add(new Pair<String, Double, Integer>("Feraligatr", .3, 39));
		s.chances.put(1520, list);
		ALL.put("Default", s);
	}
}
