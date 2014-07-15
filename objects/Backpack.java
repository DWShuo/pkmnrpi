package objects;

import items.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import pokemon.Pokemon;
import pokemon.moves.Move;

public class Backpack {
	public static Item[] all_items;

	public ArrayList<Ball> balls = new ArrayList<Ball>();
	public ArrayList<Move> moves = new ArrayList<Move>();
	public ArrayList<MiscItem> misc = new ArrayList<MiscItem>();
	public ArrayList<EvoItem> evolution = new ArrayList<EvoItem>();
	public ArrayList<RecoveryItem> recovery = new ArrayList<RecoveryItem>();
	public ArrayList<HoldItem> hold = new ArrayList<HoldItem>();
	public ArrayList<Vitamin> vitamin = new ArrayList<Vitamin>();
	public ArrayList<KeyItem> keys = new ArrayList<KeyItem>();

	public Backpack(ArrayList<String> data) {
		ArrayList<String> moveset = new ArrayList<String>();
		ArrayList<String> ballset = new ArrayList<String>();
		ArrayList<String> miscset = new ArrayList<String>();
		ArrayList<String> evoset = new ArrayList<String>();
		ArrayList<String> recoset = new ArrayList<String>();
		ArrayList<String> holdset = new ArrayList<String>();
		ArrayList<String> vitaset = new ArrayList<String>();
		ArrayList<String> keyset = new ArrayList<String>();
		int count = 0;
		String line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			moveset.add(line);
			line = data.get(count++ );
		}
		line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			ballset.add(line);
			line = data.get(count++ );
		}
		line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			miscset.add(line);
			line = data.get(count++ );
		}
		line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			evoset.add(line);
			line = data.get(count++ );
		}
		line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			recoset.add(line);
			line = data.get(count++ );
		}
		line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			holdset.add(line);
			line = data.get(count++ );
		}
		line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			vitaset.add(line);
			line = data.get(count++ );
		}
		line = data.get(count++ );
		while (!Pokemon.isUniform(line, '+')) {
			keyset.add(line);
			line = data.get(count++ );
		}
		misc = MiscItem.loadAll(miscset);
		balls = Ball.loadAll(ballset);
		moves = Move.loadAll(moveset);
		evolution = EvoItem.loadAll(evoset);
		recovery = RecoveryItem.loadAll(recoset);
		hold = HoldItem.loadAll(holdset);
		vitamin = Vitamin.loadAll(vitaset);
		keys = KeyItem.loadAll(keyset);
	}

	public void addItem(Move m) {
		for (Move mo : moves)
			if (mo.name.equalsIgnoreCase(m.name))
				return;
		moves.add(m);
	}

	public void addItem(Item i) {
		Object items = null;
		if (i.type == Item.MISC) {
			items = misc;
		} else if (i.type == Item.EVO) {
			items = evolution;
		} else if (i.type == Item.HOLD) {
			items = hold;
		} else if (i.type == Item.KEY) {
			items = keys;
		} else if (i.type == Item.POKEBALL) {
			items = balls;
		} else if (i.type == Item.REC) {
			items = recovery;
		} else if (i.type == Item.VIT) {
			items = vitamin;
		} else
			return;
		for (Item a : (ArrayList<Item>) items)
			if (a.name.equalsIgnoreCase(i.name)) {
				a.count++ ;
				return;
			}
		((ArrayList<Item>) items).add(i);
	}

	public static void init() {
		String filename = "src/data/Item_Data.txt";
		ArrayList<String> a = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				a.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Item> all = new ArrayList<Item>();
		int count = 0;
		String line = a.get(count++ );
		while (!Pokemon.isUniform(line, '=')) {
			all.add(new Ball(line));
			line = a.get(count++ );
		}
		line = a.get(count++ );
		while (!Pokemon.isUniform(line, '=')) {
			all.add(new EvoItem(line));
			line = a.get(count++ );
		}
		line = a.get(count++ );
		while (!Pokemon.isUniform(line, '=')) {
			all.add(new KeyItem(line));
			line = a.get(count++ );
		}
		line = a.get(count++ );
		while (!Pokemon.isUniform(line, '=')) {
			all.add(new Vitamin(line));
			line = a.get(count++ );
		}
		line = a.get(count++ );
		while (!Pokemon.isUniform(line, '=')) {
			all.add(new MiscItem(line));
			line = a.get(count++ );
		}
		line = a.get(count++ );
		while (!Pokemon.isUniform(line, '=')) {
			all.add(new RecoveryItem(line));
			line = a.get(count++ );
		}
		line = a.get(count++ );
		while (!Pokemon.isUniform(line, '=')) {
			all.add(new HoldItem(line));
			line = a.get(count++ );
		}
		int max = 0;
		for (Item i : all)
			if (i.ID > max)
				max = i.ID;
		all_items = new Item[max+1];
		for (int i = 0; i < max; ++i)
			all_items[i] = new MiscItem();
		for (Item i : all)
			all_items[i.ID] = i;
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