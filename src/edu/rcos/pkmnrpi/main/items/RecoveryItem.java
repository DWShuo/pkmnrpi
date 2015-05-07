package edu.rcos.pkmnrpi.main.items;

import java.util.List;


public class RecoveryItem extends Item {

	public RecoveryItem() {}

	public RecoveryItem(List<String> data) {
		super(data);
	}

	public RecoveryItem(String data) {
		super(data + "," + Item.REC);
	}

	@Override
	public String toString() {
		return null;
	}

	public static List<RecoveryItem> loadAll(List<String> data) {
		return null;
	}
}
