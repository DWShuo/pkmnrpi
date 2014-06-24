//Author: Tommy Fang
package Pokedex;

import game.GameState;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import objects.Pokemon;
import util.ImageLibrary;
import util.Searchable;

public class Pokedex extends JPanel implements Searchable {
	private static final int width = 228, height = 228;
	private static Pokemon[] all_pokemon;
	public static HashMap<String, Integer> pkmn_lookup;

	private JPanel contents;
	private JLabel portrait;
	private PokedexTextArea maintext, name, info;
	public PokedexSearchBar search;

	public static void main(String[] args) {
		GameState.initilize_all();
		JFrame frame = new JFrame();
		Pokedex dex = new Pokedex();
		frame.add(dex);
		frame.addKeyListener(dex.search);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	public Pokedex() {
		int picwidth = 56+2*17;
		contents = new JPanel();
		portrait = new JLabel();
		JPanel center = new JPanel();
		JPanel left = new JPanel();
		JPanel temp = new JPanel();
		maintext = new PokedexTextArea(width, height / 2);
		name = new PokedexTextArea(width - picwidth, height / 6);
		info = new PokedexTextArea(width - picwidth, height / 3);
		search = new PokedexSearchBar(width, this);

		setPreferredSize(new Dimension(width, height + PokedexSearchBar.pixel_height));
		setMaximumSize(new Dimension(width, height + PokedexSearchBar.pixel_height));

		contents.setPreferredSize(new Dimension(width, height));
		portrait.setPreferredSize(new Dimension(56, 56));
		center.setPreferredSize(new Dimension(width, height / 2));
		left.setPreferredSize(new Dimension(width / 2, height / 2));
		temp.setPreferredSize(new Dimension(56, 56));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		temp.setBorder(new EmptyBorder(29, 17, 29, 17));

		left.add(name);
		left.add(info);

		temp.add(portrait);

		center.add(temp);
		center.add(left);

		contents.add(center);
		contents.add(maintext);

		add(search);
		add(contents);

		name.set_text("NAME:", 20);
		info.set_text("HEIGHT: * WEIGHT:", 20);

		search("Lugia");
	}

	public void search(String str) {
		Pokemon next = getPokemon(str);
		if (next == null)
			return;
		int id = next.ID;
		if (id > 200)
			id -= 2;
		else
			id-- ;
		portrait.setIcon(ImageLibrary.front_sprites[id]);
		name.set_text("NAME: " + str, 0);
		info.set_text(next.weight + " kg * " + next.height + " m", 0);
		maintext.set_text(next.description, 50);
	}

	public static Pokemon getPokemon(String name) {
		Integer result = pkmn_lookup.get(name.toLowerCase());
		if (result == null)
			return null;
		return all_pokemon[result];
	}

	public static void init() {
		all_pokemon = Pokemon.load_all("src/data/Pokemon_Data.txt");
	}

}
