package objects;

public class Item extends Thing implements Comparable<Item> {
	public static int POKEBALL = 0, ITEM = 1, KEY = 2;
	public String name;
	public int ID;
	public int type;

	public Item(String data) {
		String[] ary = data.split(",");
		ID = Integer.parseInt(ary[0]);
		name = ary[1];
		type = Integer.parseInt(ary[2]);
	}

	@Override
	public String toString() {
		return ID + "," + name + "," + type;
	}

	@Override
	public int compareTo(Item o) {
		return name.compareTo(o.name);
	}

}
