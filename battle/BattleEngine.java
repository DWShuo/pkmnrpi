package battle;

import game.GameEngine;
import pokemon.Pokemon;
import pokemon.moves.Move;
import trainers.Person;
import trainers.Trainer;
import util.TestFrame;

public class BattleEngine {
	public BattlePanel panel;
	public GameEngine engine;
	public Pokemon enemy, friend;
	public Trainer self, opponent; // opponent will be null if fighting wild pokemon

	// Start a wild encounter with a pokemon
	public BattleEngine(GameEngine e, Pokemon p) {
		engine = e;
		self = engine.board.player;
		friend = self.get_first_pokemon();
		enemy = p;
		panel = new BattlePanel(this);
		new TestFrame(panel);
	}

	// Start a trainer battle
	public BattleEngine(GameEngine e, Person p) {
		engine = e;
		opponent = (Trainer) p;
		self = (Trainer) engine.board.player;
		friend = self.get_first_pokemon();
		enemy = opponent.get_first_pokemon();
		panel = new BattlePanel(this);
		panel = new BattlePanel(this);
	}

	// Called by BattlePanel when a player selects what move to use.
	public void selection_made(Move m) {

	}
}
