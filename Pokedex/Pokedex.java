//Author: Tommy Fang
package Pokedex;

import game.GameState;

import java.awt.Dimension;
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
	private PokedexTextArea maintext;
	private PokedexFieldArea info;
	private PokedexNameArea name;
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
		int picbuf = 15;
		int picwidth = 56 + 2 * picbuf;
		String[] titles = { "M:", "Kgs:" };
		contents = new JPanel();
		portrait = new JLabel();
		JPanel center = new JPanel();
		JPanel left = new JPanel();
		JPanel temp = new JPanel();
		maintext = new PokedexTextArea(width, height / 2);
		name = new PokedexNameArea(width - picwidth, 30);
		info = new PokedexFieldArea(width - picwidth, height / 2 - 30, titles);
		search = new PokedexSearchBar(width, this);

		setPreferredSize(new Dimension(width, height
				+ PokedexSearchBar.pixel_height));
		setMaximumSize(new Dimension(width, height
				+ PokedexSearchBar.pixel_height));

		contents.setPreferredSize(new Dimension(width, height));
		portrait.setPreferredSize(new Dimension(56, 56));
		center.setPreferredSize(new Dimension(width, height / 2));
		left.setPreferredSize(new Dimension(width / 2, height / 2));
		temp.setPreferredSize(new Dimension(56, 56));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		temp.setBorder(new EmptyBorder(29, picbuf, 29, picbuf));

		left.add(name);
		left.add(info);

		temp.add(portrait);

		center.add(temp);
		center.add(left);

		contents.add(center);
		contents.add(maintext);

		add(search);
		add(contents);

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
			id--;
		portrait.setIcon(ImageLibrary.front_sprites[id]);
		name.set_text(str);
		String[] data = { next.height + "", next.weight + "" };
		info.set_all_text(data);
		maintext.set_text(next.description, 50);
	}

	public static Pokemon getPokemon(String name) {
		Integer result = pkmn_lookup.get(name.toLowerCase());
		if (result == null)
			return null;
		return all_pokemon[result];
	}
}

