package game;

import objects.*;
import util.ImageLibrary;

/**
 * This class will initialize all static data, as well as load, hold, and save
 * temporary data.
 */
public class GameState {
	public static Clock clock;

	public GameEngine engine;

	// Initialize new game.
	public GameState(GameEngine e) {
		engine = e;
		clock = new Clock(e.board);
	}

	// Load save game
	public GameState(GameEngine e, String filename) {
		engine = e;
		load(filename);
	}

	// TODO: load save from file.
	public void load(String filename) {

	}

	public static void initilize_all() {
		ImageLibrary.init();
		Pokemon.init();
		Move.init();
	}
}
