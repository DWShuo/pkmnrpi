package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import objects.Backpack;
import animations.Clock;
import pokemon.Pokemon;
import pokemon.moves.Move;
import trainers.Trainer;
import util.ImageLibrary;

/**
 * This class will initialize all static data, as well as load, hold, and save temporary data.
 */
public class GameState {
	public static Clock clock;

	public ArrayList<Pokemon> caught, equip;
	public Backpack pack, items;
	public GameEngine engine;

	// Initialize new game.
	public GameState(GameEngine e) {
		engine = e;
		clock = new Clock();
	}

	// Load save game
	public GameState(GameEngine e, String filename) {
		engine = e;
		load(filename);
	}

	// load save from file.
	public void load(String filename) {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			ArrayList<String> lst = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (Pokemon.isUniform(line, '=')) {
					data.add(lst);
					lst = new ArrayList<String>();
				} else
					lst.add(line);
			}
			br.close();
			loadData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Parse the string save data into a save game
	public void loadData(ArrayList<ArrayList<String>> data) throws IndexOutOfBoundsException {
		int index = 0;
		// Load Trainer Locations
		ArrayList<String> list = data.get(index++ );
		loadLocations(list);
		// Load Item Set
		list = data.get(index++ );
		items = new Backpack(list);
		// Load Your Items
		list = data.get(index++ );
		pack = new Backpack(list);
		// Load Equip Pokemon
		list = data.get(index++ );
		equip = Pokemon.loadPokemon(list);
		// Load Caught Pokemon
		list = data.get(index++ );
		caught = Pokemon.loadPokemon(list);
	}
	
	public void loadLocations(ArrayList<String> data) {
		
	}

	public static void initilize_all() {
		ImageLibrary.init();
		Pokemon.init();
		Move.init();
		Backpack.init();
		//signs
		Trainer.init();
		//npcs
	}
}
