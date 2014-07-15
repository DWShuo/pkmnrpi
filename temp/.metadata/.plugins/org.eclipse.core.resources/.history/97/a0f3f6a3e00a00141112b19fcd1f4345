package objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Backpack {
	public static Item[] all_items;

	public Backpack(ArrayList<String> data) {
		// TODO
	}

	public static void init() {
		String filename = "src/data/Item_Data.txt";
		ArrayList<Item> all = new ArrayList<Item>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				all.add(new Item(line));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		all_items = orderItems(all);
	}

	public static Item[] orderItems(ArrayList<Item> lst) {
		Collections.sort(lst);
		Item[] items = new Item[lst.size()];
		int index = 0;
		for (Item i : lst)
			items[index++ ] = i;
		return items;
	}
}
