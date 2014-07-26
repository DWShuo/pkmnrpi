package util;

import game.GameBoard;
import game.GameEngine;
import game.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import animations.WalkAnimation;
import trainers.Person;

/**
 * This class handles key events for the entire board. It should support key re-mapping.
 */
public class KeyMapper implements KeyListener {
	public static final int ENTER = 5;
	public GameEngine engine;
	private GameBoard board;
	private Person player;
	public static HashMap<Integer, String> map;

	public KeyMapper(GameEngine e) {
		engine = e;
		board = e.board;
		player = board.player;
		load_default();
	}

	// This loads the default key mapping
	public static void load_default() {
		map = new HashMap<Integer, String>();
		map.put(Person.UP, "Up");
		map.put(Person.DOWN, "Down");
		map.put(Person.RIGHT, "Right");
		map.put(Person.LEFT, "Left");
		map.put(ENTER, "Enter");
	}

	// This provides a portal for users to change key mapping.
	public static void change_map() {
		// TODO: float a window that lets you change key bindings.
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String in = KeyEvent.getKeyText(e.getKeyCode());
		// Only listen for events when animations are done.
		if (GameState.clock.isAnimating())
			return;
		boolean battling = engine.battle != null;
		if (in.equalsIgnoreCase(map.get(Person.UP))) {
			if (battling)
				engine.battle.move(Person.UP);
			else
				WalkAnimation.run(board, player, Person.UP, player.y < board.map.mapdata.length - GameBoard.buffer);
		} else if (in.equalsIgnoreCase(map.get(Person.DOWN))) {
			if (battling)
				engine.battle.move(Person.DOWN);
			else
				WalkAnimation.run(board, player, Person.DOWN, player.y > GameBoard.buffer);
		} else if (in.equalsIgnoreCase(map.get(Person.RIGHT))) {
			if (battling)
				engine.battle.move(Person.RIGHT);
			else
				WalkAnimation.run(board, player, Person.RIGHT, player.x > GameBoard.buffer);
		} else if (in.equalsIgnoreCase(map.get(Person.LEFT))) {
			if (battling)
				engine.battle.move(Person.LEFT);
			else
				WalkAnimation.run(board, player, Person.LEFT, player.x < board.map.mapdata[0].length - GameBoard.buffer);
		} else if (in.equalsIgnoreCase(map.get(ENTER))) {
			if (battling)
				engine.battle.enter();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}