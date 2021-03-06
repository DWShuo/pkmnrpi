package edu.rcos.pkmnrpi.main.items;

import java.util.ArrayList;
import java.util.List;

import edu.rcos.pkmnrpi.main.objects.Thing;

public abstract class Item extends Thing implements Comparable<Item> {
	public static final int POKEBALL = 0, MISC = 1, KEY = 2, EVO = 3, REC = 4, HOLD = 5, VIT = 6;
	
	public String name;
	public int ID;
	public int type = 1;
	public int count = 1;

	public Item() {}

	public Item(List<String> data) {
		name = data.get(0);
		count = Integer.parseInt(data.get(1));
	}

	public Item(String data) {
		String[] ary = data.split(",");
		ID = Integer.parseInt(ary[0]);
		name = ary[1];
		type = Integer.parseInt(ary[2]);
	}

	@Override
	public abstract String toString();

	@Override
	public int compareTo(Item o) {
		return name.compareTo(o.name);
	}

	public static List<? extends Item> loadAll(List<String> data) {
		List<Item> b = new ArrayList<Item>();
		int count = 0;
		while (count < data.size()) {
			List<String> temp = new ArrayList<String>();
			temp.add(data.get(count++ ));
			temp.add(data.get(count++ ));
			b.add(new MiscItem(temp));
		}
		return b;
	}
}
