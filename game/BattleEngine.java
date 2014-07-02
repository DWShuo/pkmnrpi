package game;

import objects.Move;
import objects.Person;
import objects.Pokemon;

public class BattleEngine {
	public BattlePanel panel;
	public GameEngine engine;
	public Pokemon enemy, friend;
	public Person self, opponent; // opponent will be null if fighting wild pokemon
	
	// Start a wild encounter with a pokemon
	public BattleEngine(GameEngine e, Pokemon p) {
		engine = e;
		self = engine.board.player;
		friend = self.get_first_pokemon();
		enemy = p;
		panel = new BattlePanel(this);
		panel.animate_walkon_self();
		panel.animate_walkon_pkmn_left();
		panel.display_moves();
	}

	// Start a trainer battle
	public BattleEngine(GameEngine e, Person p) {
		engine = e;
		opponent = p;
		self = engine.board.player;
		friend = self.get_first_pokemon();
		enemy = p.get_first_pokemon();
		panel = new BattlePanel(this);
		panel = new BattlePanel(this);
		panel.animate_walkon_self();
		panel.animate_walkon_opponent();
		panel.display_moves();
	}
	
	// Called by BattlePanel when a player selects what move to use.
	public void selection_made(Move m) {
		
	}
}
