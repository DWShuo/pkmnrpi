package battle;

import game.GamePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import objects.LayeredPanel;
import animations.Clock;
import animations.Sprite;
import pokemon.Move;
import pokemon.Pokemon;
import util.ImageLibrary;
import util.Library;

public class BattlePanel extends JLayeredPane implements BattleUI {
	private BattleEngine engine;

	public BattleHUD hud;
	public BattleEnemyHUD ehud;
	public BattleText text;
	public GamePanel foreg;
	public Sprite back, front;
	public Sprite[] temps;
	public int width = 400, height = 400;

	public BattlePanel(BattleEngine e) {
		super();
		engine = e;
		hud = new BattleHUD();
		ehud = new BattleEnemyHUD();
		text = new BattleText(e);
		text.setText("");
		foreg = new GamePanel();
		foreg.setPreferredSize(new Dimension(width, height));
		JLabel background = new JLabel(ImageLibrary.getSolidColor(Color.white, width, height));
		setBackground(Color.white);

		hud.focus = engine.self.get_first_pokemon();
		ehud.focus = engine.enemy;

		temps = new Sprite[10];
		front = new Sprite("src/tilesets/pokemon_sprites/" + engine.enemy.name.toLowerCase() + ".png");
		temps[0] = new Sprite(ImageLibrary.back_sprites[Library.national_numbers.get(hud.focus.ID) - 1].getImage());
		temps[0].width *= 2;
		temps[0].height *= 2;
		back = engine.self.bigsprite;

		front.scaleToFit(width - BattleEnemyHUD.width, height - BattleHUD.height - text.height - 20);
		front.x = -front.width;

		back.scaleToFit(width - BattleHUD.width, height - BattleEnemyHUD.height - text.height - 20);
		back.y = BattleHUD.height + BattleEnemyHUD.height - back.height;
		back.x = width;

		engine.enemy.sprite = front;
		engine.friend.sprite = back;

		foreg.sprites.add(front);
		foreg.sprites.add(back);

		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		add(foreg, 0);
		add(ehud, 1);
		add(hud, 1);
		add(text, 1);
		add(background, 5);
		foreg.setBounds(0, 0, width, height);
		ehud.setBounds(0, 0, BattleEnemyHUD.width, BattleEnemyHUD.height);
		hud.setBounds(width - BattleHUD.width, BattleEnemyHUD.height, BattleHUD.width, BattleHUD.height);
		text.setBounds(0, BattleEnemyHUD.height + BattleHUD.height, width, height - BattleEnemyHUD.height - BattleHUD.height);
		background.setBounds(0, 0, width, height);
	}

	// This should animate the main player in engine as he comes onto the field
	public void walkOn() {
		boolean st = Clock.manual;
		Clock.manual = true;
		int frames = 60, buf = 15;
		for (int i = 0; i < frames; ++i) {
			nap(Clock.FRAME_WAIT);
			if (back.x -buf != 0)
				back.x -= (back.x-buf) / (frames - i);
			int gap = (BattleEnemyHUD.width - front.x);
			if (gap != 0)
				front.x += gap / (frames - i);
			foreg.repaint();
		}
		back.x = buf;
		front.x = BattleEnemyHUD.width;
		foreg.repaint();
		nap(100);
		ehud.on = true;
		ehud.repaint();
		text.layText("A wild " + engine.enemy.name.toUpperCase() + " appeared!");
		nap(225);
		walkOnPokemon(engine.friend);
		hud.on = true;
		hud.repaint();
		nap(100);
		engine.enter();
		Clock.manual = st;
	}

	// This should animate a pokemon as it arrives
	public void walkOnPokemon(Pokemon p) {
		boolean st = Clock.manual;
		Clock.manual = true;
		int frames = 30;
		for (int i = 0; i < frames; ++i) {
			nap(Clock.FRAME_WAIT);
			int gap = back.width + back.x;
			if (gap != 0)
				back.x -= gap / (frames - i);
		}
		foreg.sprites.remove(back);
		nap(200);
		text.layText("Go! " + p.name.toUpperCase() + "!");
		nap(450);
		back = temps[0];
		back.scaleToFit(width - BattleHUD.width, height - BattleEnemyHUD.height - text.height - 20);
		back.x = -back.width;
		back.y = height - back.height - text.height;
		foreg.sprites.add(back);
		foreg.repaint();
		frames = 30;
		for (int i = 0; i < frames; ++i) {
			nap(Clock.FRAME_WAIT);
			int gap = (20 - back.x);
			if (gap != 0)
				back.x += gap / (frames - i);
			foreg.repaint();
		}
		Clock.manual = st;
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

	public void makeSelection() {
		text.state = 1;
		text.select = 0;
		text.repaint();
	}
}
