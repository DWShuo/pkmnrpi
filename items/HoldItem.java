package items;

import java.util.ArrayList;

public class HoldItem extends Item {

	public HoldItem() {}

	public HoldItem(ArrayList<String> data) {
		super(data);
	}

	public HoldItem(String data) {
		super(data + "," + Item.HOLD);
	}

	@Override
	public String toString() {
		return null;
	}

	public static ArrayList<HoldItem> loadAll(ArrayList<String> data) {
		return null;
	}
}
