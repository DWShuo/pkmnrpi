package game;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Pokedex.Pokedex;

public class GameEngine {
	public GameState state;
	public GameBoard board;
	public Pokedex dex;
	public JFrame frame;

	public GameEngine() {
		GameState.initilize_all();
		board = new GameBoard();
		dex = new Pokedex();
		frame = new JFrame("Pokemon RPI");
		JPanel contents = new JPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 200);
		frame.addKeyListener(dex.search);
		frame.addKeyListener(board);
		
		contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));
		contents.setBackground(Color.gray.darker().darker());
		
		contents.add(dex);
		contents.add(board);
		
		frame.add(contents);
		frame.pack();
		frame.setVisible(true);
		
		//TODO: Play intro credits
		//TODO: select save file or start new game
	}

	public static void main(String[] args) {
		new GameEngine();
	}
}
