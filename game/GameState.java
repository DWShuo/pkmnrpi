package game;

import java.util.ArrayList;

import Pokedex.Pokedex;
import objects.*;
import util.ImageLibrary;

public class GameState {
	public TileMap map;
	public ArrayList<Person> people;
	public static final Clock clock = new Clock();
	
	public static void initilize_all() {
		ImageLibrary.init();
		Pokedex.init();
	}
}
