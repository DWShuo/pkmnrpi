package edu.rcos.pkmnrpi.main.items;

import java.util.List;


public class Vitamin extends Item {

	public Vitamin() {}

	public Vitamin(List<String> data) {
		super(data);
	}

	public Vitamin(String data) {
		super(data + "," + Item.HOLD);
	}

	@Override
	public String toString() {
		return null;
	}

	public static List<Vitamin> loadAll(List<String> data) {
		return null;
	}
}
