//Author: Tommy Fang
package pokedex;

import game.GameEngine;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pokemon.Pokemon;
import util.ImageLibrary;
import util.Searchable;

/**
 * This class handles all of the GUI construction for the pokedex panel. Additionally it supports the search bar with a lookup table.
 */
public class Pokedex extends JPanel implements Searchable {
	private static final int width = 500, height = 400;
	public static HashMap<String, Integer> pkmn_lookup = new HashMap<String, Integer>();

	private JPanel contents;
	private Portrait portrait;
	private PokedexTextArea maintext;
	private PokedexFieldArea info;
	private PokedexNameArea name;
	public PokedexSearchBar search;
	public GameEngine engine;

	public static void main(String[] args) {
		new GameEngine();
	}

	public Pokedex(GameEngine e) {
		engine = e;
		int picwidth = 200;
		String[] titles = { "ID No: ", "Type: ", "Height: ", "Weight: ", "Catch Rate: " };// Find something prettier...

		// INITILIZATIONS!!!!
		contents = new JPanel();
		portrait = new Portrait(picwidth, height / 2);
		JPanel center = new JPanel();
		JPanel left = new JPanel();
		maintext = new PokedexTextArea(width, height / 2);
		name = new PokedexNameArea(width - picwidth, 30);
		info = new PokedexFieldArea(width - picwidth, height / 2 - 30, titles);
		search = new PokedexSearchBar(width, this);

		// Set all preferred sizes
		this.setPreferredSize(new Dimension(width, height + PokedexSearchBar.pixel_height));
		this.setMaximumSize(new Dimension(width, height + PokedexSearchBar.pixel_height));
		contents.setPreferredSize(new Dimension(width, height));
		center.setPreferredSize(new Dimension(width, height / 2));
		left.setPreferredSize(new Dimension(width / 2, height / 2));

		// Set all layouts
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

		left.add(name);
		left.add(info);

		center.add(portrait);
		center.add(left);

		contents.add(center);
		contents.add(maintext);

		add(search);
		add(contents);

		// search("Lugia");
	}

	// Loads a specific pokemon's info onto the pokedex page
	public void search(String str) {
		Pokemon next = getPokemon(str);
		if (next == null)
			return;
		int id = next.ID;
		if (id > 200)
			id -= 2;
		else
			id-- ;
		portrait.setIcon(new ImageIcon("src/tilesets/pokemon_sprites/" + next.name.toLowerCase() + ".jpg"));
		name.set_text(str);
		String type = Pokemon.getType(next.type) + (next.t2 >= 0 ? "/" + Pokemon.getType(next.t2) : "");
		String[] data = { next.ID + "", type, next.height + "", next.weight + "", next.catch_rate + "" };
		info.set_all_text(data);
		maintext.set_text(next.description, 50);
	}

	// Returns the instance of the pokemon that the text names.
	public static Pokemon getPokemon(String name) {
		Integer result = pkmn_lookup.get(name.toLowerCase());
		// System.out.println(name + " " + result);
		if (result == null)
			return null;
		return Pokemon.all_pokemon[result];
	}
}
