package util;

import java.util.ArrayList;
import java.util.HashMap;

public class Flag {
	public static ArrayList<Flag> all_flags = new ArrayList<Flag>();
	public static HashMap<String, ArrayList<Flag>> map_flag_pools = new HashMap<String, ArrayList<Flag>>();
	public static final String FILENAME = "src/data/Flag_Data.txt";
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
		for (Flag fl : all_flags)
			if (f.x == fl.x && f.y == fl.y && f.mapname.equals(fl.mapname))
				rm.add(fl);
		for (Flag fl : rm)
			removeFLag(fl);
		all_flags.add(f);
		if (map_flag_pools.containsKey(f.mapname))
			map_flag_pools.get(f.mapname).add(f);
		else {
			ArrayList<Flag> t = new ArrayList<Flag>();
			t.add(f);
			map_flag_pools.put(f.mapname, t);
		}
	}

	public static void removeFLag(Flag f) {
		map_flag_pools.get(f.mapname).remove(f);
		all_flags.remove(f);
	}

	public String toString() {
		return mapname + "," + x + "," + y + "," + type + "," + extra;
	}

	public static void save() {
		ArrayList<String> ary = new ArrayList<String>();
		for (Flag f : all_flags)
			ary.add(f.toString());
		FileParser.saveFile(ary, FILENAME);
	}

	public static void init() {
		for (String str : FileParser.parseFile(FILENAME)) {
			new Flag(str);
		}
	}
}
