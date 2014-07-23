package battle;

import game.GamePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import objects.LayeredPanel;
import animations.Sprite;
import pokemon.moves.Move;
import util.ImageLibrary;
import util.Library;
import util.TestFrame;

public class BattlePanel extends LayeredPanel {
	private BattleEngine engine;

	public BattleHUD hud;
	public BattleEnemyHUD ehud;
	public BattleText text;
	public Sprite back, front;
	public GamePanel fore = new GamePanel();

	public BattlePanel(BattleEngine e) {
		super(new Dimension(400, 400));
		background = new JPanel();
		setForeground(fore);
		engine = e;
		hud = new BattleHUD();
		ehud = new BattleEnemyHUD();
		text = new BattleText(e);
		int width = 400, height = 400;
		setBackground(Color.white);

		hud.focus = engine.self.get_first_pokemon();
		ehud.focus = engine.enemy;

		front = new Sprite("src/tilesets/pokemon_sprites/" + engine.enemy.name.toLowerCase() + ".png");
		back = new Sprite(ImageLibrary.back_sprites[Library.national_numbers.get(hud.focus.ID) - 1].getImage());

		front.width *= 2;
		front.height *= 2;
		front.x = BattleEnemyHUD.width;

		back.width *= 2;
		back.height *= 2;
		back.y = BattleHUD.height + BattleEnemyHUD.height - back.height;

		fore.sprites.add(front);
		fore.sprites.add(back);

		background.setBackground(Color.white);
		background.setLayout(null);
		background.add(ehud);
		background.add(hud);
		background.add(text);

		ehud.setBounds(0, 0, BattleEnemyHUD.width, BattleEnemyHUD.height);
		hud.setBounds(width - BattleHUD.width, BattleEnemyHUD.height, BattleHUD.width, BattleHUD.height);
		text.setBounds(0, BattleEnemyHUD.height + BattleHUD.height, width, height - (BattleEnemyHUD.height + BattleHUD.height));
	}

	// This should animate the main player in engine as he comes onto the field
	public void walkOn() {
		// TODO: get correct sprite from engine.self
		// TODO: animate sprite
	}

	// This should animate the opponent in engine as they arrive
	public void walkOnEnemy() {
		// TODO: get correct sprite from engine.opponent
		// TODO: get intro text from engine.opponent
		// TODO: animate sprite
		// TODO: animate text
	}

	// This should animate a pokemon from your side as it arrives
	public void walkOnPokemon() {
		// TODO: get correct sprite from engine.friend
		// TODO: animate sprite
		// TODO: play intro sound
		// TODO: display stats
	}

	// This should animate the enemy pokemon as it arrives
	public void walkOnPokemonEnemy() {
		// TODO: get correct sprite from engine.enemy
		// TODO: animate sprite
		// TODO: play intro sound
		// TODO: display stats
	}

	// This should animate the attack sequence originating from left side
	public void attack(Move m) {
		// TODO: get move animation. Use tackle as default for now
		// TODO: animate the pokemon and addition move animations at same time
		// TODO: play attack sound
		// TODO: play hit sound
		// TODO: flicker opponet pokemon
		// TODO: animate damage
	}

	// This should animate the attack sequence originating from right side
	public void enemyAttack(Move m) {
		// TODO: get move animation. Use tackle as default for now
		// TODO: animate the pokemon and addition move animations at same time
		// TODO: play attack sound
		// TODO: play hit sound
		// TODO: flicker opponet pokemon
		// TODO: animate damage
	}

	public void displayMoves() {
		// TODO: use data from engine.friend to get all the moves
		// TODO: Set up display
	}
}
