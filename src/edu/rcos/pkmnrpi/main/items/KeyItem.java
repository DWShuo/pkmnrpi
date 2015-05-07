package edu.rcos.pkmnrpi.main.items;

import java.util.List;


public class KeyItem extends Item {

	public KeyItem() {}

	public KeyItem(List<String> data) {
		super(data);
	}

	public KeyItem(String data) {
		super(data + "," + Item.KEY);
	}

	@Override
	public String toString() {
		return null;
	}

	public static List<KeyItem> loadAll(List<String> data) {
		return null;
	}
}
