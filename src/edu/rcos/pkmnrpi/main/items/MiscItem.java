package edu.rcos.pkmnrpi.main.items;

import java.util.List;


public class MiscItem extends Item {

	public MiscItem() {}

	public MiscItem(List<String> data) {
		super(data);
	}

	public MiscItem(String data) {
		super(data + "," + Item.MISC);
	}

	@Override
	public String toString() {
		return null;
	}

	public static List<MiscItem> loadAll(List<String> data) {
		return null;
	}
}
