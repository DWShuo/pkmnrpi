package game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import battle.BattleEngine;
import pokedex.Pokedex;
import util.KeyMapper;

/**
 * This is the main class. Hurrah for the root of all things. Praise the sun!
 */
public class GameEngine {
	public static int width = 400, height = 400;

	public GameState state;
	public GameBoard board;
	public KeyMapper keymap;
	public Pokedex dex;
	public JFrame frame;
	public JPanel contents;
	public BattleEngine battle;
	public JLayeredPane window;

	public GameEngine() {
		// This MUST be the first line. Initializes static data.
		GameState.initilize_all();
		state = new GameState(this);
		board = new GameBoard(this);
		dex = new Pokedex(this);
		frame = new JFrame("Pokemon RPI");
		keymap = new KeyMapper(this);
		contents = new JPanel();
		window = new JLayeredPane();
		window.setPreferredSize(new Dimension(width, height));
		window.setLayout(null);
		window.add(board, 1);
		board.setBounds(0, 0, 400, 400);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 200);
		frame.addKeyListener(keymap);

		contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));
		contents.setBackground(Color.gray.darker().darker());

		contents.add(dex);
		contents.add(window);

		frame.add(contents);
		frame.pack();
		frame.setVisible(true);

		// battle = new BattleEngine(this, new Pokemon("Meganium", 40));
		// startBattle();
		// TODO: Play intro credits
		// TODO: select save file or start new game
	}

	public void startBattle() {
		// TODO: transition animation
		window.add(battle.panel, 0);
		dex.search(battle.enemy.name);
		battle.panel.setBounds(0, 0, 400, 400);
		battle.start();
	}

	public void endBattle() {
		window.remove(battle.panel);
		dex.cleanup();
		battle = null;
		window.repaint();
	}

	public void loseBattle() {
		endBattle();
	}

	public void focusPokedex() {
		frame.removeKeyListener(keymap);
		frame.addKeyListener(dex.search);
	}

	public void focusBoard() {
		frame.removeKeyListener(dex.search);
		frame.addKeyListener(keymap);
	}

	public static void main(String[] args) {
		new GameEngine();
	}
}
