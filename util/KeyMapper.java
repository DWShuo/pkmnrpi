package util;

import game.GameBoard;
import game.GameEngine;
import game.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import animations.BoardAnimation;
import animations.WalkAnimation;
import trainers.Person;

/**
 * This class handles key events for the entire board. It should support key re-mapping.
 */
public class KeyMapper implements KeyListener {
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
		boolean background_motion = false;
		int direction = 0;
		if (in == map.get(Person.UP)) {
			direction = Person.UP;
			// Dont move the board if you aren't in the middle.
			if (player.y < board.map.mapdata.length - GameBoard.buffer)
				background_motion = true;
		} else if (in == map.get(Person.DOWN)) {
			direction = Person.DOWN;
			if (player.y > GameBoard.buffer)
				background_motion = true;
		} else if (in == map.get(Person.RIGHT)) {
			direction = Person.RIGHT;
			if (player.x > GameBoard.buffer)
				background_motion = true;
		} else if (in == map.get(Person.LEFT)) {
			direction = Person.LEFT;
			if (player.x < board.map.mapdata[0].length - GameBoard.buffer)
				background_motion = true;
		} else
			return;
		player.setDirection(direction);
		// Check for obstruction
		if (!board.canMove(direction)) {
			board.foreground.repaint();
			return;
		}
		WalkAnimation ani = new WalkAnimation(board, player, 1);
		GameState.clock.animate(ani);
		if (background_motion)
			GameState.clock.animate(new BoardAnimation(board, direction));
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}