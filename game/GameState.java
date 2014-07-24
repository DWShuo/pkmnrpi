package game;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import objects.Backpack;
import objects.TileMap;
import animations.Clock;
import pokemon.Move;
import pokemon.Pokemon;
import trainers.Trainer;
import util.FileParser;
import util.Flag;
import util.ImageLibrary;
import util.Library;

/**
 * This class will initialize all static data, as well as load, hold, and save temporary data.
 */
public class GameState {
	public static Clock clock;

	public ArrayList<Pokemon> stored = new ArrayList<Pokemon>(), team = new ArrayList<Pokemon>();
	public ArrayList<Trainer> opponents = new ArrayList<Trainer>();
	public Pokemon enemy, defender;
	public Backpack pack;
	public GameEngine engine;
	public ScoreCard card;
	public Trainer self;

	// Initialize new game.
	public GameState(GameEngine e) {
		engine = e;
		clock = new Clock();
		load("src/saves/default.save");
	}

	// Load save game
	public GameState(GameEngine e, String filename) {
		engine = e;
		load(filename);
	}

	// load save from file.
	public void load(String filename) {
		ArrayList<String> data = FileParser.parseFile(filename);
		int index = 0;
		String line = data.get(index++ );
		ArrayList<String> ary = new ArrayList<String>();
		while (!Pokemon.isUniform(line, '=')) {
			ary.add(line);
			line = data.get(index++ );
		}
		opponents = Trainer.loadTrainers(ary);
		line = data.get(index++ );
		ary = new ArrayList<String>();
		while (!Pokemon.isUniform(line, '=')) {
			ary.add(line);
			line = data.get(index++ );
		}
		loadLocations(ary);
		line = data.get(index++ );
		ary = new ArrayList<String>();
		while (!Pokemon.isUniform(line, '=')) {
			ary.add(line);
			line = data.get(index++ );
		}
		pack = new Backpack(ary);
		line = data.get(index++ );
		ary = new ArrayList<String>();
		while (!Pokemon.isUniform(line, '=')) {
			ary.add(line);
			line = data.get(index++ );
		}
		team = Pokemon.loadPokemon(ary);
		line = data.get(index++ );
		ary = new ArrayList<String>();
		while (!Pokemon.isUniform(line, '=')) {
			ary.add(line);
			line = data.get(index++ );
		}
		stored = Pokemon.loadPokemon(ary);
		line = data.get(index++ );
		ary = new ArrayList<String>();
		while (!Pokemon.isUniform(line, '=')) {
			ary.add(line);
			line = data.get(index++ );
		}
		card = new ScoreCard(ary);
		;
		line = data.get(index++ );
		ary = new ArrayList<String>();
		while (!Pokemon.isUniform(line, '=')) {
			ary.add(line);
			line = data.get(index++ );
		}
		initPlayer(ary);
	}

	public String toString() {
		String space = "==============\n", all = "";
		for (Trainer t : opponents)
			all += t;
		all += space;
		for (Flag f : Flag.all_flags)
			if (f.type == Flag.ITEM)
				all += f;
		all += space;
		all += pack;
		all += space;
		for (Pokemon p : team)
			all += p;
		all += space;
		for (Pokemon p : stored)
			all += p;
		all += space + card + space;
		all += self.staticToString();
		all += space;
		return all;
	}

	public void initPlayer(ArrayList<String> info) {
		self = Trainer.loadTrainers(info).get(0);
		self.team = team;
	}

	public void loadLocations(ArrayList<String> data) {
		for (String str : data)
			new Flag(str);
	}

	public static void initilize_all() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/data/pfont.ttf")));
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Library.init();
		ImageLibrary.init();
		TileMap.init();
		Flag.init();
		Move.init();
		Trainer.init();
		Pokemon.init();
		// signs
		// npcs
		for (Trainer t : Trainer.all_trainers)
			t.reloadPokemon();
	}
}
