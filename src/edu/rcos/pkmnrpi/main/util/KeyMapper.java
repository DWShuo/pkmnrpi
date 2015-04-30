package edu.rcos.pkmnrpi.main.util;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.rcos.pkmnrpi.main.animations.WalkAnimation;
import edu.rcos.pkmnrpi.main.game.GameBoard;
import edu.rcos.pkmnrpi.main.game.GameEngine;
import edu.rcos.pkmnrpi.main.trainers.Person;

/**
 * This class handles key events for the entire board. It should support key
 * re-mapping.
 */
public class KeyMapper implements KeyListener {
	public static final int ENTER = -1;
	public GameEngine engine;
	private GameBoard board;
	private Person player;
	public static KeyMap<Integer, Integer> map;
	public boolean inmotion = false;
	public int input;

	public KeyMapper(GameEngine e) {
		engine = e;
		board = e.board;
		player = board.player;
		load_default();
	}

	// This loads the default key mapping
	public static void load_default() {
		map = new KeyMap<Integer, Integer>();
		map.put(Person.UP, 38);
		map.put(Person.DOWN, 40);
		map.put(Person.RIGHT, 39);
		map.put(Person.LEFT, 37);
		map.put(ENTER, 10);
	}

	// This provides a portal for users to change key mapping.
	public void change_map() {
		// TODO: float a window that lets you change key bindings.
	}

	// Attempt to initialize a key based event.
	public void listen() {
		System.out.println("GOT EVENT");
		if (!inmotion)
			return;
		int dir = getDirection(input);
		if (GameEngine.battling) {
			if (dir >= 0)
				engine.battle.move(dir);
			else if (dir == -1)
				engine.battle.enter();
			return;
		}
		// Don't listen for input while walking.
		if (WalkAnimation.iswalking)
			return;

		Point bound = engine.board.getViewport().getViewPosition();
		if (dir >= 0)
			WalkAnimation.run(board, player, dir, needScroll(bound, dir));
	}

	// This returns true when the background should scroll when the player
	// walks.
	public boolean needScroll(Point bound, int dir) {
		if (dir == Person.UP)
			return bound.y - GameBoard.tsize >= 0 && player.py >= 13;
		else if (dir == Person.DOWN)
			return bound.y + engine.board.getViewport().getHeight() + GameBoard.tsize <= engine.board.background.getHeight() && player.py <= 13;
		else if (dir == Person.RIGHT)
			return bound.x + engine.board.getViewport().getWidth() + GameBoard.tsize <= engine.board.background.getWidth() && player.px >= 13;
		else if (dir == Person.LEFT)
			return bound.x + GameBoard.tsize >= 0 && player.px <= 13;
		return false;
	}

	// This returns the direction of the mapped key or -2 if it isnt mapped to a
	// direction, -1 for enter.
	public int getDirection(int key) {
		if (key == map.getB(Person.UP))
			return Person.UP;
		else if (key == map.getB(Person.DOWN))
			return Person.DOWN;
		else if (key == map.getB(Person.RIGHT))
			return Person.RIGHT;
		else if (key == map.getB(Person.LEFT))
			return Person.LEFT;
		else if (key == map.getB(ENTER))
			return -1;
		return -2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("KeyEvent");
		input = e.getKeyCode();
		if (!map.containsB(input)) {
			System.out.println("Skipping un known input'" + input + "'");
			return;
		}
		inmotion = true;
		listen();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		inmotion = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}