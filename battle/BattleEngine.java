package battle;

import animations.Clock;
import game.GameEngine;
import pokemon.Move;
import pokemon.MoveAnimation;
import pokemon.Pokemon;
import pokemon.Stats;
import trainers.Person;
import trainers.Trainer;

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
		engine.dex.search(p.name);
	}

	// Start a trainer battle
	public BattleEngine(GameEngine e, Person p) {
		engine = e;
		opponent = (Trainer) p;
		self = (Trainer) engine.board.player;
		friend = self.get_first_pokemon();
		enemy = opponent.get_first_pokemon();
		panel = new BattlePanel(this);
	}

	public void startTurn() {
		counter = enemy.decide(friend);
		stage = 2;
		if (counter.speed_priority > selection.speed_priority) {
			turn = 1;
			attack(enemy, friend, counter);
		} else if (counter.speed_priority < selection.speed_priority) {
			turn = 0;
			attack(friend, enemy, selection);
		} else if (enemy.stats.speed > friend.stats.speed) {
			turn = 1;
			attack(enemy, friend, counter);
		} else {
			turn = 0;
			attack(friend, enemy, selection);
		}
		Clock.nap(225);
		enter();
	}

	public void checkYourDeath() {
		if (friend.stats.current_health > 1)
			return;
		friend.stats.state = Stats.FAINT;
		panel.text.layText(friend.name.toUpperCase() + " has fainted!");
		if (!self.canBattle()) {
			engine.loseBattle();
			return;
		}
		// Select next pokemon
		return;
	}

	public boolean checkEnemyDeath() {
		if (enemy.stats.current_health > 0)
			return false;
		enemy.stats.state = Stats.FAINT;
		panel.text.layText("Enemy " + enemy.name.toUpperCase() + " fainted!");
		Clock.nap(1000);
		int exp = enemy.calculateEXP(friend, this);
		panel.text.layText(friend.name.toUpperCase() + " gainted " + exp + " EXP. Points!");
		int t = exp, lvl = 0;
		while (t > Pokemon.levelToEXP(friend.stats.level + lvl + 1, friend.stats.growth_rate)) {
			t -= Pokemon.levelToEXP(friend.stats.level + lvl + 1, friend.stats.growth_rate);
			lvl++ ;
		}
		int frames = 30;
		for (int i = 0; i < frames; ++i) {
			Clock.nap(Clock.FRAME_WAIT);
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
			panel.foreg.paintImmediately(panel.foreg.getBounds());
			if (cap) {
				Clock.nap(Clock.FRAME_WAIT);
				friend.stats.exp = 0;
				panel.foreg.paintImmediately(panel.foreg.getBounds());
			}
		}
		if (lvl > 0)
			friend.levelUp(lvl);

		if (opponent == null || !opponent.canBattle()) {
			stage = 9;
			return true;
		}
		return true;
		// enemy select next pokemon
	}

	public void start() {
		panel.walkOn();
	}

	public void enter() {
		if (stage == 0) {// End of intro
			if (checkEnemyDeath())
				return;
			stage++ ;
			panel.makeSelection();
		} else if (stage == 1) {// interpret selection
			selection = friend.known_moves.get(panel.text.select);
			startTurn();
		} else if (stage == 2) {// counter;
			if (checkEnemyDeath())
				return;
			Clock.nap(300);
			stage = 0;
			if (turn == 1)
				attack(friend, enemy, selection);
			else
				attack(enemy, friend, counter);
			Clock.nap(200);
			enter();
		} else if (stage == 9) {
			engine.endBattle();
		}
	}

	public void attack(Pokemon a, Pokemon b, Move m) {
		if (!isHit(m)) {
			System.out.println("Miss");
			// animate miss
			return;
		}
		MoveAnimation ani = new MoveAnimation(this, m, a, b);
		ani.run();
	}

	public boolean isHit(Move m) {
		return true;
	}

	public void animateHP(Pokemon p, int damage, int frames) {
		for (int i = 0; i < frames && damage != 0; ++i) {
			Clock.nap(Clock.FRAME_WAIT);
			int dif = damage / (frames - i);
			damage -= dif;
			p.stats.current_health = Math.min(Math.max(p.stats.current_health - dif, 0), p.stats.max_health);
			if (p == friend)
				panel.hud.paintImmediately(panel.hud.getBounds());
			else if (p == enemy)
				panel.ehud.paintImmediately(panel.ehud.getBounds());
			else {
				System.out.println("Warning!!");
			}
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
