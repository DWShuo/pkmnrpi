package items;

import java.util.ArrayList;

public class EvoItem extends Item {

	public EvoItem() {}

	public EvoItem(ArrayList<String> data) {
		super(data);
	}

	public EvoItem(String data) {
		super(data + "," + Item.EVO);
	}

	@Override
	public String toString() {
		return null;
	}

	public static ArrayList<EvoItem> loadAll(ArrayList<String> data) {
		return null;
	}
}
