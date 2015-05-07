package edu.rcos.pkmnrpi.main.items;

import java.util.List;


public class HoldItem extends Item {

	public HoldItem() {}

	public HoldItem(List<String> data) {
		super(data);
	}

	public HoldItem(String data) {
		super(data + "," + Item.HOLD);
	}

	@Override
	public String toString() {
		return null;
	}

	public static List<HoldItem> loadAll(List<String> data) {
		return null;
	}
}
