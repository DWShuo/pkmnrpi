package game;

import objects.*;
import util.ImageLibrary;

public class GameState {
	public static final Clock clock = new Clock();
	
	public GameEngine engine;
	
	// Initilize new game.
	public GameState(GameEngine e) {
		engine = e;
	}
	
	// Load save game
	public GameState(GameEngine e, String filename) {
		engine = e;
		load(filename);
	}
	
	//TODO: load save from file.
	public void load(String filename) {
		
	}
	
	public static void initilize_all() {
		ImageLibrary.init();
		Pokemon.init();
		Move.init();
	}
}
