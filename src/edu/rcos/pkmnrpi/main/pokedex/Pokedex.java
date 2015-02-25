//Author: Tommy Fang
package edu.rcos.pkmnrpi.main.Pokedex;

import edu.rcos.pkmnrpi.main.game.GameEngine;
import edu.rcos.pkmnrpi.main.game.GameState;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.rcos.pkmnrpi.main.pokemon.Pokemon;
import edu.rcos.pkmnrpi.main.util.Searchable;
import edu.rcos.pkmnrpi.main.util.panels.ScalePanel;

/**
 * This class handles all of the GUI construction for the pokedex panel.
 * Additionally it supports the search bar with a lookup table.
 */
public class Pokedex extends JPanel implements Searchable, PokedexUI {
	private static final int width = 400, height = 400 - PokedexSearchBar.pixel_height;
	public static HashMap<String, Integer> pkmn_lookup = new HashMap<String, Integer>();

	private JPanel contents;
	private ScalePanel portrait;
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
		int picwidth = 210, bar = PokedexSearchBar.pixel_height;
		int pichieght = (height / 4) * 3;

		// INITILIZATIONS!!!!
		contents = new JPanel();
		portrait = new ScalePanel(portrait_icon.getImage(), picwidth, pichieght);
		JPanel center = new JPanel();
		JPanel left = new JPanel();
		maintext = new PokedexTextArea(width, height - pichieght);
		name = new PokedexNameArea(width - picwidth, 30);
		info = new PokedexFieldArea(width - picwidth, pichieght - 30);
		search = new PokedexSearchBar(width, this);

		// Set all preferred sizes
		this.setPreferredSize(new Dimension(width, height + bar));
		this.setMaximumSize(new Dimension(width, height + bar));
		contents.setPreferredSize(new Dimension(width, height));
		center.setPreferredSize(new Dimension(width, pichieght));
		left.setPreferredSize(new Dimension(width - picwidth, pichieght));

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
	}

	public void cleanup() {
		portrait.image = portrait_icon.getImage();
		portrait.repaint();
		name.setNumber("");
		info.update((Pokemon) null);
		maintext.text = "";
		maintext.repaint();
	}

	// Loads a specific pokemon's info onto the pokedex page
	public void search(String str) {
		if (str.equals("")) {
			cleanup();
			return;
		}
		Pokemon next = getPokemon(str);
		if (next == null)
			return;
		portrait.image = new ImageIcon("data/tilesets/pokemon_sprites/" + next.name.toLowerCase() + ".jpg").getImage();
		portrait.paintImmediately(portrait.getBounds());
		name.setNumber(next.ID + "");
		info.update(next);
		maintext.text = next.description;
		engine.contents.repaint();
	}

	// Returns the instance of the pokemon that the text names.
	public static Pokemon getPokemon(String name) {
		Integer result = pkmn_lookup.get(name.toLowerCase());
		// System.out.println(name + " " + result);
		if (result == null)
			return null;
		return GameState.POKEMON[result - 1];
	}
}
