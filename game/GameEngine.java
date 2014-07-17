package game;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pokedex.Pokedex;
import util.KeyMapper;

/**
 * This is the main class. Hurrah for the root of all things. Praise the sun!
 */
public class GameEngine {
	public GameState state;
	public GameBoard board;
	public KeyMapper keymap;
	public Pokedex dex;
	public JFrame frame;

	public GameEngine() {
		// This MUST be the first line. Initializes static data.
		GameState.initilize_all();
		state = new GameState(this);
		board = new GameBoard(this);
		dex = new Pokedex(this);
		frame = new JFrame("Pokemon RPI");
		keymap = new KeyMapper(this);
		JPanel contents = new JPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 200);
		frame.addKeyListener(keymap);

		contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));
		contents.setBackground(Color.gray.darker().darker());

		contents.add(dex);
		contents.add(board);

		frame.add(contents);
		frame.pack();
		frame.setVisible(true);

		// TODO: Play intro credits
		// TODO: select save file or start new game
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
