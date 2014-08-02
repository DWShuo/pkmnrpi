package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import pokemon.Pokemon;
import battle.BattleEngine;
import util.Pair;

public class Spawn {
	public String mapname;
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
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.startBattle();
			return true;
		}
		return false;
	}

	public ArrayList<String> save() {
		ArrayList<String> ary = new ArrayList<String>();
		ary.add(mapname);
		ary.add(bounds.x + ":" + bounds.y + ":" + bounds.width + ":" + bounds.height);
		for (Integer i : chances.keySet()) {
			ary.add(i + "");
			for (Pair<?, ?, ?> p : chances.get(i)) {
				ary.add(p.toString());
			}
			ary.add("#######");
		}
		ary.add(")))))))");
		return ary;
	}
}
