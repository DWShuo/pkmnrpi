package game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import objects.Backpack;
import objects.TileMap;
import animations.Clock;
import animations.Sprite;
import Pokedex.Pokedex;
import pokemon.Move;
import pokemon.Pokemon;
import pokemon.Stats;
import trainers.Trainer;
import util.FileParser;
import util.Flag;
import util.ImageLibrary;
import util.Library;
import util.Pair;

/**
 * This class will initialize all static data, as well as load, hold, and save temporary data.
 */
public class GameState {
	// Animation clock
	public static Clock clock;
	// Walkable Terrain
	public static HashMap<String, ArrayList<Dimension>> TERRAIN = new HashMap<String, ArrayList<Dimension>>();
	// Icon tile maps
	public static HashMap<String, TileMap> MAPS = new HashMap<String, TileMap>();
	// Wild Pokemon spawn locations
	public static HashMap<String, Spawn> SPAWNS = new HashMap<String, Spawn>();
	// Items and events
	public static ArrayList<Flag> FLAGS = new ArrayList<Flag>();
	public static HashMap<String, ArrayList<Flag>> FLAG_POOL = new HashMap<String, ArrayList<Flag>>();
	public static Move[] MOVES;
	public static ArrayList<Trainer> TRAINERS = new ArrayList<Trainer>();
	public static Pokemon[] POKEMON;

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

	public static void save(File file) {
		ArrayList<String> data = new ArrayList<String>();
		FileParser.saveFile(saveTerrain(), "src/data/walkable_tiles.txt");
		FileParser.saveFile(saveSpawns(), "src/data/spawn_locations.txt");
		FileParser.saveFile(saveMoves(), "src/data/move_info.csv");
		data.addAll(saveFlags());
		data.add("((((((((((");
		data.addAll(saveTrainers());

		FileParser.saveFile(data, file);
	}

	private static ArrayList<String> saveTerrain() {
		ArrayList<String> ary = new ArrayList<String>();
		for (String str : TERRAIN.keySet())
			for (Dimension d : TERRAIN.get(str))
				ary.add(d.width + ":" + d.height + ":" + str);
		return ary;
	}

	private static ArrayList<String> saveSpawns() {
		ArrayList<String> ary = new ArrayList<String>();
		for (String str : SPAWNS.keySet()) {
			ary.addAll(SPAWNS.get(str).save());
		}
		return ary;
	}

	private static ArrayList<String> saveFlags() {
		ArrayList<String> ary = new ArrayList<String>();
		for (Flag f : FLAGS)
			ary.add(f.toString());
		return ary;
	}

	private static ArrayList<String> saveMoves() {
		ArrayList<String> ary = new ArrayList<String>();
		for (Move m : MOVES) {
			ary.add(m.toString());
		}
		return ary;
	}

	private static ArrayList<String> saveTrainers() {
		ArrayList<String> ary = new ArrayList<String>();
		for (Trainer t : TRAINERS)
			ary.addAll(t.saveStaticInfo());
		return ary;
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
		// Initilize Static Libraries
		Library.init();
		ImageLibrary.init();
		// Load Static Info Files
		initTerrain(FileParser.parseFile("src/data/walkable_tiles.txt"));
		initSpawns(FileParser.parseFile("src/data/spawn_locations.txt"));
		initMoves(FileParser.parseFile("src/data/move_info.csv"));
		// Load Game State File
		ArrayList<ArrayList<String>> data = FileParser.parseSeperatedFile("src/data/game state.txt", '(');
		initFlags(data.get(0));
		initTrainers(data.get(1));
		initPokemon();
		// signs
		// npcs
		for (Trainer t : TRAINERS)
			t.reloadPokemon();
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
		for (Flag f : FLAGS)
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

	private static void initTerrain(ArrayList<String> data) {
		for (String str : data) {
			String[] line = str.split(":");
			Dimension d = new Dimension(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
			add(line[2], d);
		}
	}

	private static void initSpawns(ArrayList<String> data) {
		int index = 0;
		while (index < data.size()) {
			Spawn s = new Spawn();
			s.mapname = data.get(index++ );
			String[] ary = data.get(index++ ).split(":");
			s.bounds = new Rectangle(Integer.parseInt(ary[0]), Integer.parseInt(ary[1]), Integer.parseInt(ary[2]), Integer.parseInt(ary[3]));
			String line = data.get(index++ );
			while (!Pokemon.isUniform(line, ')')) {
				String id = line;
				ArrayList<Pair<String, Double, Integer>> list = new ArrayList<Pair<String, Double, Integer>>();
				line = data.get(index++ );
				while (!Pokemon.isUniform(line, '#')) {
					ary = line.split(":");
					list.add(new Pair<String, Double, Integer>(ary[0], Double.parseDouble(ary[1]), Integer.parseInt(ary[2])));
					line = data.get(index++ );
				}
				line = data.get(index++ );
				s.chances.put(id, list);
			}
			SPAWNS.put(s.mapname, s);
		}
	}

	private static void initFlags(ArrayList<String> data) {
		for (String str : data) {
			if (str.length() == 0)
				continue;
			new Flag(str);
		}
	}

	private void initPlayer(ArrayList<String> info) {
		self = Trainer.loadTrainers(info).get(0);
		self.team = team;
		self.walk = new BufferedImage[10];
		for (int i = 0; i < 10; ++i)
			self.walk[i] = ImageLibrary.player[i];
		self.bike = new BufferedImage[10];
		for (int i = 0; i < 10; ++i)
			self.bike[i] = ImageLibrary.player[i + 10];
		self.sprite = new Sprite(self.walk[0]);
		self.bigsprite = new Sprite("src/tilesets/sprites/trainer back.png");
	}

	private static void initTrainers(ArrayList<String> data) {
		Trainer t;
		int index = 0;
		while (index < data.size()) {
			t = new Trainer();
			t.name = data.get(index++ );
			t.male = data.get(index++ ).equalsIgnoreCase("male");
			t.cash = Integer.parseInt(data.get(index++ ));
			t.intro = data.get(index++ );
			t.victory_outro = data.get(index++ );
			t.defeat_outro = data.get(index++ );
			t.mapname = data.get(index++ );
			t.x = Integer.parseInt(data.get(index++ ));
			t.y = Integer.parseInt(data.get(index++ ));
			t.setDirection(data.get(index++ ));
			String line = data.get(index++ );
			while (!Pokemon.isUniform(line, '+')) {
				t.dialog.add(line);
				line = data.get(index++ );
			}
			line = data.get(index++ );
			while (!Pokemon.isUniform(line, '+')) {
				t.team_data.add(line);
				line = data.get(index++ );
			}
			TRAINERS.add(t);
		}
	}

	private static void initMoves(ArrayList<String> data) {
		ArrayList<Move> all = new ArrayList<Move>();
		for (String line : data) {
			String[] ary = line.split(",");
			Move m = new Move();
			m.name = ary[0];
			m.type = Pokemon.getType(ary[1].toUpperCase());
			m.category = parseCategory(ary[2]);
			m.damage = Integer.parseInt(ary[3]);
			double acc = Double.parseDouble(ary[4]);
			m.hit_chance = acc == 0 ? 0 : acc / 100.0;
			m.pp = m.pp_max = Integer.parseInt(ary[5]);
			if (ary.length == 7)
				m.description = ary[6];
			all.add(m);
		}
		GameState.MOVES = order_moves(all);
	}

	// Loads all static pokemon info
	private static void initPokemon() {
		ArrayList<String> info = FileParser.parseFile("src/data/pokemon data.txt");
		int index = 0;
		POKEMON = new Pokemon[251];
		Pokemon p;
		while (index < info.size()) {
			p = new Pokemon();
			p.ID = Integer.parseInt(info.get(index++ ));
			p.name = info.get(index++ );
			String types = info.get(index++ );
			if (types.contains("/")) {
				String[] ar = types.split("/");
				p.type = Pokemon.getType(ar[0]);
				p.t2 = Pokemon.getType(ar[1]);
			} else
				p.type = Pokemon.getType(types);
			p.species = info.get(index++ );
			String str = info.get(index++ );
			if (isDouble(str))
				p.height = Double.parseDouble(str);
			str = info.get(index++ );
			if (isDouble(str))
				p.weight = Double.parseDouble(str);
			str = info.get(index++ );
			if (isDouble(str))
				p.catch_rate = Integer.parseInt(str);
			str = info.get(index++ );
			if (isDouble(str))
				p.base_exp = Integer.parseInt(str);
			p.base_happiness = Integer.parseInt(info.get(index++ ));
			p.stats.growth_rate = info.get(index++ );
			p.description = info.get(index++ );

			p.evolutions = new HashMap<String, String>();
			str = info.get(index++ );
			while (!Pokemon.isUniform(str, '*')) {
				if (str.contains(",")) {
					String[] ar = str.split(",");
					p.evolutions.put(ar[1], ar[0]);
				}
				str = info.get(index++ );
			}
			index++ ;
			p.learnset = new ArrayList<Move>();
			p.ages = new ArrayList<Integer>();
			str = info.get(index++ );
			while (!Pokemon.isUniform(str, '*')) {
				if (str.contains(",")) {
					String[] ar = str.split(",");
					p.ages.add(Integer.parseInt(ar[1]));
					p.learnset.add(Move.lookup(ar[0]));
				}
				str = info.get(index++ );
			}
			index++ ;
			p.tmset = new ArrayList<Move>();
			str = info.get(index++ );
			while (!Pokemon.isUniform(str, '*')) {
				p.tmset.add(Move.lookup(str));
				str = info.get(index++ );
			}

			POKEMON[p.ID - 1] = p;
			Pokedex.pkmn_lookup.put(p.name.toLowerCase(), p.ID);
		}
		Stats.init();
	}

	private static boolean isDouble(String str) {
		String good = "1234567890.";
		for (char c : str.toCharArray())
			if (!good.contains(c + ""))
				return false;
		return true;
	}

	private static int parseCategory(String str) {
		str = str.toUpperCase();
		if (str.equals("PHYSICAL"))
			return Move.PHYSICAL;
		else if (str.equals("SPECIAL"))
			return Move.SPECIAL;
		else if (str.equals("STATUS"))
			return Move.STATUS;
		return Integer.parseInt(str);
	}

	private static Move[] order_moves(ArrayList<Move> all) {
		Collections.sort(all);
		Move[] mo = new Move[all.size()];
		int index = 0;
		for (Move m : all)
			mo[index++ ] = m;
		return mo;
	}

	public static void add(String key, Dimension d) {
		if (TERRAIN.keySet().contains(key))
			TERRAIN.get(key).add(d);
		else {
			TERRAIN.put(key, new ArrayList<Dimension>());
			TERRAIN.get(key).add(d);
		}
	}

	public void loadLocations(ArrayList<String> data) {
		for (String str : data)
			new Flag(str);
	}
}
