package game;

import javax.swing.JPanel;

import objects.Move;

public class BattlePanel extends JPanel {
	private BattleEngine engine;

	public BattlePanel(BattleEngine e) {
		engine = e;
		engine.self.name = engine.self.name;
		// TODO: set up graphics
	}

	// This should animate the main player in engine as he comes onto the field
	public void animate_walkon_self() {
		// TODO: get correct sprite from engine.self
		// TODO: animate sprite
	}

	// This should animate the opponent in engine as they arrive
	public void animate_walkon_opponent() {
		// TODO: get correct sprite from engine.opponent
		// TODO: get intro text from engine.opponent
		// TODO: animate sprite
		// TODO: animate text
	}

	// This should animate a pokemon from your side as it arrives
	public void animate_walkon_pkmn_left() {
		// TODO: get correct sprite from engine.friend
		// TODO: animate sprite
		// TODO: play intro sound
		// TODO: display stats
	}

	// This should animate the enemy pokemon as it arrives
	public void animate_walkon_pkmn_right() {
		// TODO: get correct sprite from engine.enemy
		// TODO: animate sprite
		// TODO: play intro sound
		// TODO: display stats
	}

	// This should animate the attack sequence originating from left side
	public void animate_attack_from_left(Move m) {
		// TODO: get move animation. Use tackle as default for now
		// TODO: animate the pokemon and addition move animations at same time
		// TODO: play attack sound
		// TODO: play hit sound
		// TODO: flicker opponet pokemon
		// TODO: animate damage
	}

	// This should animate the attack sequence originating from right side
	public void animate_attack_from_right(Move m) {
		// TODO: get move animation. Use tackle as default for now
		// TODO: animate the pokemon and addition move animations at same time
		// TODO: play attack sound
		// TODO: play hit sound
		// TODO: flicker opponet pokemon
		// TODO: animate damage
	}

	public void display_moves() {
		// TODO: use data from engine.friend to get all the moves
		// TODO: Set up display
	}
}
