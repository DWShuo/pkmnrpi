package battle;

import animations.Clock;
import game.GameEngine;
import game.GameState;
import pokemon.Move;
import pokemon.Pokemon;
import pokemon.Stats;
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
		nap(225);
	}

	public void checkDeath() {
		if (friend.stats.current_health < 1) {
			friend.stats.state = Stats.FAINT;
			panel.text.layText(friend.name.toUpperCase() + " has fainted!");
			if (!self.canBattle()) {
				engine.loseBattle();
				return;
			}
			// Select next pokemon
			return;
		}
		if (enemy.stats.current_health > 0)
			return;
		enemy.stats.state = Stats.FAINT;
		panel.text.layText("Enemy " + enemy.name.toUpperCase() + " fainted!");
		nap(250);
		int exp = enemy.calculateEXP();
		System.out.println(exp);
		panel.text.layText(friend.name.toUpperCase() + " gainted " + exp + " EXP. Points!");
		int t = exp, lvl = 0;
		while (t > Pokemon.levelToEXP(friend.stats.level + lvl + 1, friend.stats.growth_rate)) {
			t -= Pokemon.levelToEXP(friend.stats.level + lvl + 1, friend.stats.growth_rate);
			lvl++ ;
		}
		int frames = 30;
		for (int i = 0; i < frames; ++i) {
			nap(Clock.FRAME_WAIT);
			if (exp == 0)
				break;
			int gap = exp / (frames - i);
			exp -= gap;
			boolean cap = false;
			if (friend.expToNextLevel() < gap) {
				gap -= friend.expToNextLevel();
				// TODO: play level up sound
				cap = true;
			}
			friend.stats.exp += gap;
			friend.stats.total_exp += gap;
			panel.foreg.repaint();
			if (cap) {
				nap(Clock.FRAME_WAIT);
				friend.stats.exp = 0;
				panel.foreg.repaint();
			}
		}
		if (lvl > 0)
			friend.levelUp(lvl);

		if (opponent == null || !opponent.canBattle()) {
			engine.endBattle();
			return;
		}
		// enemy select next pokemon
	}

	public void start() {
		panel.walkOn();
	}

	public void enter() {
		if (stage == 0) {// End of intro
			checkDeath();
			stage++ ;
			panel.makeSelection();
		} else if (stage == 1) {// interpret selection
			selection = friend.known_moves.get(panel.text.select);
			startTurn();
		} else if (stage == 2) {// counter;
			checkDeath();
			nap(300);
			stage = 0;
			if (turn == 1)
				enemy.attack(friend, selection, this, false);
			else
				friend.attack(enemy, counter, this, true);
		}
	}

	public static void nap(long time) {
		boolean temp = Clock.manual;
		Clock.manual = true;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Clock.manual = temp;
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
