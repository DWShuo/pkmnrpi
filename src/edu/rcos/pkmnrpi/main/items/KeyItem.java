package edu.rcos.pkmnrpi.main.items;

import java.util.ArrayList;

public class KeyItem extends Item {

	public KeyItem() {}

	public KeyItem(ArrayList<String> data) {
		super(data);
	}

	public KeyItem(String data) {
		super(data + "," + Item.KEY);
	}

	@Override
	public String toString() {
		return null;
	}

	public static ArrayList<KeyItem> loadAll(ArrayList<String> data) {
		return null;
	}
}
