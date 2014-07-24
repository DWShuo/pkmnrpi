package battle;

import game.GameEngine;
import pokemon.Move;
import pokemon.Pokemon;
import trainers.Person;
import trainers.Trainer;
import util.TestFrame;

public class BattleEngine {
	public BattlePanel panel;
	public GameEngine engine;
	public Pokemon enemy, friend;
	public Trainer self, opponent; // opponent will be null if fighting wild pokemon
	public Move selection;
	public int stage;

	// Start a wild encounter with a pokemon
	public BattleEngine(GameEngine e, Pokemon p) {
		engine = e;
		self = engine.board.player;
		friend = self.get_first_pokemon();
		enemy = p;
		panel = new BattlePanel(this);
		// new TestFrame(panel);
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

	public void startTurn() {
		Move attack = enemy.decide(friend);
		if (attack.speed_priority > selection.speed_priority)
			;// enemy attack first
		if (attack.speed_priority < selection.speed_priority)
			;// you attack first
		if (enemy.stats.speed > friend.stats.speed)
			;// enemy attack first
		else
			;// you attack first
	}

	public void enter() {
		if (stage == 0) {// End of intro
			stage++ ;
			panel.makeSelection();

		} else if (stage == 1) {// interpret selection
			selection = friend.known_moves.get(panel.text.select);
			stage++ ;
		} else if (stage == 2) {// end of selection
			startTurn();
		}
	}

	public void move(int direction) {
		if (stage != 1)
			return;
		int a = panel.text.select;
		a += direction == 0 ? 3 : direction == 1 ? 1 : 2;
		panel.text.select = a % 4;
		panel.text.repaint();
	}
}
