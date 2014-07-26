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
	public Move selection, counter;
	public int stage, turn;

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
		counter = enemy.decide(friend);
		stage = 2;
		if (counter.speed_priority > selection.speed_priority) {
			turn = 1;
			friend.attack(enemy, counter, this, true);
		} else if (counter.speed_priority < selection.speed_priority) {
			turn = 0;
			enemy.attack(friend, selection, this, false);
		} else if (enemy.stats.speed > friend.stats.speed) {
			turn = 1;
			friend.attack(enemy, counter, this, true);
		} else {
			turn = 0;
			enemy.attack(friend, selection, this, false);
		}
	}

	public void enter() {
		if (stage == 0) {// End of intro
			stage++ ;
			panel.makeSelection();
		} else if (stage == 1) {// interpret selection
			selection = friend.known_moves.get(panel.text.select);
			startTurn();
		} else if (stage == 2) {// counter
			System.out.println("working " + turn);
			stage = 0;
			if (turn == 1)
				enemy.attack(friend, selection, this, false);
			else
				friend.attack(enemy, counter, this, true);
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
