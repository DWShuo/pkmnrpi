package edu.rcos.pkmnrpi.main.items;

import java.util.List;


public class EvoItem extends Item {

	public EvoItem() {}

	public EvoItem(List<String> data) {
		super(data);
	}

	public EvoItem(String data) {
		super(data + "," + Item.EVO);
	}

	@Override
	public String toString() {
		return null;
	}

	public static List<EvoItem> loadAll(List<String> data) {
		return null;
	}
}
