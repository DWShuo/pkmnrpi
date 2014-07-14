package items;

import java.util.ArrayList;

public class RecoveryItem extends Item {

	public RecoveryItem() {}

	public RecoveryItem(ArrayList<String> data) {
		super(data);
	}

	public RecoveryItem(String data) {
		super(data + "," + Item.REC);
	}

	@Override
	public String toString() {
		return null;
	}

	public static ArrayList<RecoveryItem> loadAll(ArrayList<String> data) {
		return null;
	}
}
