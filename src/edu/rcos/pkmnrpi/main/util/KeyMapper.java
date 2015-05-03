package edu.rcos.pkmnrpi.main.util;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.rcos.pkmnrpi.main.animations.WalkAnimation;
import edu.rcos.pkmnrpi.main.game.GameBoard;
import edu.rcos.pkmnrpi.main.game.GameEngine;
import edu.rcos.pkmnrpi.main.trainers.Person;
import edu.rcos.pkmnrpi.main.trainers.Trainer;

/**
 * This class handles key events for the entire board. It should support key
 * re-mapping.
 */
public class KeyMapper implements KeyListener {
	public static final int ENTER = -1;
	public GameEngine engine;
	public static KeyMap<Integer, Integer> map;
	public boolean inmotion = false;
	public int input;

	public KeyMapper(GameEngine eng) {
		engine = eng;
		loadDefault();
	}

	// This loads the default key mapping
	public static void loadDefault() {
		map = new KeyMap<Integer, Integer>();
		map.put(Person.UP, 38);
		map.put(Person.DOWN, 40);
		map.put(Person.RIGHT, 39);
		map.put(Person.LEFT, 37);
		map.put(ENTER, 10);
	}

	// This provides a portal for users to change key mapping.
	public void changeMap() {
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
				engine.getBattle().move(dir);
			else if (dir == -1)
				engine.getBattle().enter();
			return;
		}
		// Don't listen for input while walking.
		if (WalkAnimation.iswalking)
			return;

		Point bound = engine.getBoard().getViewport().getViewPosition();
		if (dir >= 0)
			WalkAnimation.run(engine.getBoard(), engine.getBoard().getPlayer(), dir, needScroll(bound, dir));
	}

	// This returns true when the background should scroll when the player
	// walks.
	public boolean needScroll(Point bound, int dir) {
		Trainer player = engine.getBoard().getPlayer();
		if (dir == Person.UP)
			return bound.y - GameBoard.tsize >= 0 && player.py >= 13;
		else if (dir == Person.DOWN)
			return bound.y + engine.getBoard().getViewport().getHeight() + GameBoard.tsize <= engine.getBoard().background.getHeight() && player.py <= 13;
		else if (dir == Person.RIGHT)
			return bound.x + engine.getBoard().getViewport().getWidth() + GameBoard.tsize <= engine.getBoard().background.getWidth() && player.px >= 13;
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