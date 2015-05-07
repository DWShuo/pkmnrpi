package edu.rcos.pkmnrpi.main.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import edu.rcos.pkmnrpi.main.battle.BattleEngine;
import edu.rcos.pkmnrpi.main.console.MapEditor;
import edu.rcos.pkmnrpi.main.pokedex.Pokedex;
import edu.rcos.pkmnrpi.main.pokemon.Pokemon;
import edu.rcos.pkmnrpi.main.util.KeyMapper;
import edu.rcos.pkmnrpi.main.util.Pair;

/**
 * This is the main class. Hurrah for the root of all things. Praise the sun!
 */
public class GameEngine {
	public static int width = 400, height = 400;
	public static boolean battling;

	private GameState state;
	private GameBoard board;
	private KeyMapper keymap;
	private Pokedex dex;
	private JFrame frame;
	private JPanel contents;
	private BattleEngine battle;
	private JLayeredPane window;
	private MainMenu mainMenu;
	
	public GameEngine() {
		// This MUST be the first line. Initializes static data.
		GameState.initializeAll();
		state = new GameState(this);
		board = new GameBoard(this);
		dex = new Pokedex(this);
		frame = new JFrame("Pokemon RPI");
		keymap = new KeyMapper(this);
		contents = new JPanel();
		mainMenu = new MainMenu(this);
		window = new JLayeredPane();
		window.setPreferredSize(new Dimension(width, height));
		window.setLayout(null);
		window.add(board, JLayeredPane.DEFAULT_LAYER);
		window.add(mainMenu, JLayeredPane.MODAL_LAYER);
		mainMenu.setBounds(0, 0, 400, 400);
		board.setBounds(0, 0, 400, 400);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 200);

		contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));
		contents.setBackground(Color.gray.darker().darker());

		contents.add(dex);
		contents.add(window);

		frame.add(contents);
		frame.pack();
		frame.setVisible(true);
	}
	
	public GameBoard getBoard() {
		return board;
	}
	
	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public void setBoard(GameBoard board) {
		this.board = board;
	}

	public KeyMapper getKeymap() {
		return keymap;
	}

	public void setKeymap(KeyMapper keymap) {
		this.keymap = keymap;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public JPanel getContents() {
		return contents;
	}

	public void setContents(JPanel contents) {
		this.contents = contents;
	}

	public BattleEngine getBattle() {
		return battle;
	}

	public void setBattle(BattleEngine battle) {
		this.battle = battle;
	}
	
	public Pokedex getDex() {
		return dex;
	}

	public void setDex(Pokedex dex) {
		this.dex = dex;
	}

	public void startBattle(Pair<String, Double, Integer> p) {
		battling = true;
		battle = new BattleEngine(this, new Pokemon(p.a, (int) (p.c + Math.random() * 5)));
		window.add(battle.panel, 0);
		battle.panel.setBounds(0, 0, 400, 400);
		battle.start();
	}

	public void endBattle() {
		battling = false;
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

	public void launchGame() {
		window.remove(mainMenu);
		window.repaint();
		focusBoard();
		frame.requestFocus();
	}
	
	public void launchMapEditor() {
		frame.dispose();
		MapEditor editor = new MapEditor(frame);
		frame.add(editor);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	public void exit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public static void main(String[] args) {
		new GameEngine();
	}
}
