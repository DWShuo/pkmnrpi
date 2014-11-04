package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import util.Pair;

public class Spawn {
	public String mapname;
	public Rectangle bounds = new Rectangle();
	public HashMap<String, ArrayList<Pair<String, Double, Integer>>> chances = new HashMap<String, ArrayList<Pair<String, Double, Integer>>>();

	public Spawn() {
	}
	public boolean roll(GameEngine e) {
		if (!bounds.contains(e.board.player.x, e.board.player.y))
			return false;
		Pair<String, Integer, Integer> pp = e.board.map.mapdata[e.board.player.y + e.board.map.centery][e.board.player.x + e.board.map.centerx];
		ArrayList<Pair<String, Double, Integer>> temp = chances.get(pp.a + ":" + pp.b + ":" + pp.c);
		if (temp == null)
			return false;
		Collections.shuffle(temp);
		for (Pair<String, Double, Integer> p : temp) {
			if (Math.random() > p.b)
				continue;
			
			e.startBattle(p);
			return true;
		}
		return false;
	}

	public ArrayList<String> save() {
		ArrayList<String> ary = new ArrayList<String>();
		ary.add(mapname);
		ary.add(bounds.x + ":" + bounds.y + ":" + bounds.width + ":" + bounds.height);
		for (String i : chances.keySet()) {
			ary.add(i);
			for (Pair<?, ?, ?> p : chances.get(i)) {
				ary.add(p.toString());
			}
			ary.add("#######");
		}
		ary.add(")))))))");
		return ary;
	}
}
