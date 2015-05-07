package edu.rcos.pkmnrpi.main.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.rcos.pkmnrpi.main.items.Ball;
import edu.rcos.pkmnrpi.main.items.EvoItem;
import edu.rcos.pkmnrpi.main.items.HoldItem;
import edu.rcos.pkmnrpi.main.items.Item;
import edu.rcos.pkmnrpi.main.items.KeyItem;
import edu.rcos.pkmnrpi.main.items.MiscItem;
import edu.rcos.pkmnrpi.main.items.RecoveryItem;
import edu.rcos.pkmnrpi.main.items.Vitamin;
import edu.rcos.pkmnrpi.main.pokemon.Move;
import edu.rcos.pkmnrpi.main.pokemon.Pokemon;

public class Backpack {
	public List<Ball> balls = new ArrayList<Ball>();
	public List<Move> moves = new ArrayList<Move>();
	public List<MiscItem> misc = new ArrayList<MiscItem>();
	public List<EvoItem> evolution = new ArrayList<EvoItem>();
	public List<RecoveryItem> recovery = new ArrayList<RecoveryItem>();
	public List<HoldItem> hold = new ArrayList<HoldItem>();
	public List<Vitamin> vitamin = new ArrayList<Vitamin>();
	public List<KeyItem> keys = new ArrayList<KeyItem>();

	public Backpack(List<String> data) {
		List<String> moveset = new ArrayList<String>();
		List<String> ballset = new ArrayList<String>();
		List<String> miscset = new ArrayList<String>();
		List<String> evoset = new ArrayList<String>();
		List<String> recoset = new ArrayList<String>();
		List<String> holdset = new ArrayList<String>();
		List<String> vitaset = new ArrayList<String>();
		List<String> keyset = new ArrayList<String>();
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

	@SuppressWarnings("unchecked")
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

	public static Item[] orderItems(List<Item> lst) {
		Collections.sort(lst);
		Item[] items = new Item[lst.size()];
		int index = 0;
		for (Item i : lst)
			items[index++ ] = i;
		return items;
	}
}
