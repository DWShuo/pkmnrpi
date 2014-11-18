package animations;

import game.GameBoard;
import game.GameState;

import java.awt.image.BufferedImage;

import trainers.Person;
import util.ImageLibrary;

public class WalkAnimation extends Animation {
	public Person person;
	public static boolean iswalking;
	public int distance;

	public WalkAnimation(GameBoard b, Person p, int d) {
		int offset = 0;
		distance = d;
		// if (p.on_bike)
		// offset = 10;
		if (p.direction == Person.UP) {
			BufferedImage[] ary = { ImageLibrary.player[offset + 7], ImageLibrary.player[offset + 8], ImageLibrary.player[offset + 9] };
			frames = ary;
			int[] temp = { 0, -distance * GameBoard.tsize };
			counts = temp;
		} else if (p.direction == Person.DOWN) {
			BufferedImage[] ary = { ImageLibrary.player[offset + 4], ImageLibrary.player[offset + 5], ImageLibrary.player[offset + 6] };
			frames = ary;
			int[] temp = { 0, distance * GameBoard.tsize };
			counts = temp;
		} else if (p.direction == Person.LEFT) {
			BufferedImage[] ary = { ImageLibrary.player[offset + 2], ImageLibrary.player[offset + 3] };
			frames = ary;
			int[] temp = { -distance * GameBoard.tsize, 0 };
			counts = temp;
		} else if (p.direction == Person.RIGHT) {
			BufferedImage[] ary = { ImageLibrary.player[offset], ImageLibrary.player[offset + 1] };
			frames = ary;
			int[] temp = { distance * GameBoard.tsize, 0 };
			counts = temp;
		}
		final_frame = frames[0];
		sprite = p.sprite;
		board = b;
		delta = new int[counts.length];
		person = p;
		increment = 6 * distance - 1;
		if (distance == 0)
			increment = 5;
	}

	public void animate() {
		super.animate();
		if (distance == 0)
			return;
		if (increment <= 0) {
			if (person.direction == Person.UP) {
				--person.y;
			} else if (person.direction == Person.DOWN) {
				++person.y;
			} else if (person.direction == Person.RIGHT) {
				++person.x;
			} else if (person.direction == Person.LEFT) {
				--person.x;
			}
			board.checkWildPokemon();
		}
	}

	public static void run(GameBoard b, Person p, int direction, boolean background_motion) {
		iswalking = true;
		if (p.direction != direction) {
			p.setDirection(direction);
			//Clock.nap(1000);
			b.checkWildPokemon();
			iswalking = false;
			b.engine.keymap.listen();
			return;
		}
		System.out.println("Moving");
		// Check for obstruction
		WalkAnimation ani;
		if (!b.canMove(direction)) {
			ani = new WalkAnimation(b, p, 0);
			GameState.clock.animate(ani);
			return;
		}
		ani = new WalkAnimation(b, p, 1);
		GameState.clock.animate(ani);
		if (background_motion)
			GameState.clock.animate(new BoardAnimation(b, direction));
		else if (direction == Person.UP) {
			b.player.py--;
		} else if (direction == Person.DOWN) {
			b.player.py++;
		} else if (direction == Person.LEFT) {
			b.player.px--;
		} else if (direction == Person.RIGHT) {
			b.player.px++;
		}
	}

	@Override
	public void finish() {
		iswalking = false;
		board.engine.keymap.listen();
	}
}
