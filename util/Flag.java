package util;

import game.GameState;

import java.util.ArrayList;

public class Flag {
	public static final int TEXT = 0, DOOR = 1, ITEM = 2;

	public int x, y, type;
	public String mapname, extra = "";

	public Flag(String data) {
		String[] ary = data.split(",");
		mapname = ary[0];
		x = Integer.parseInt(ary[1]);
		y = Integer.parseInt(ary[2]);
		type = Integer.parseInt(ary[3]);
		extra = ary[4];
		addFlag(this);
	}

	public static void addFlag(Flag f) {
		ArrayList<Flag> rm = new ArrayList<Flag>();
		for (Flag fl : GameState.FLAGS)
			if (f.x == fl.x && f.y == fl.y && f.mapname.equals(fl.mapname))
				rm.add(fl);
		for (Flag fl : rm)
			removeFLag(fl);
		GameState.FLAGS.add(f);
		if (GameState.FLAG_POOL.containsKey(f.mapname))
			GameState.FLAG_POOL.get(f.mapname).add(f);
		else {
			ArrayList<Flag> t = new ArrayList<Flag>();
			t.add(f);
			GameState.FLAG_POOL.put(f.mapname, t);
		}
	}

	public static void removeFLag(Flag f) {
		GameState.FLAG_POOL.get(f.mapname).remove(f);
		GameState.FLAGS.remove(f);
	}

	public String toString() {
		return mapname + "," + x + "," + y + "," + type + "," + extra;
	}

	public static void save() {
		ArrayList<String> ary = new ArrayList<String>();
		for (Flag f : GameState.FLAGS)
			ary.add(f.toString());
		FileParser.saveFile(ary, "src/data/Flag_Data.txt");
	}
}
