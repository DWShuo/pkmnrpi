package edu.rcos.pkmnrpi.main.animations;

import edu.rcos.pkmnrpi.main.game.GameBoard;
import edu.rcos.pkmnrpi.main.trainers.Person;

public class BoardAnimation extends Animation {
	public BoardAnimation(GameBoard b, int direction) {
		board = b;
		int[] temp = { 0, 0 };
		delta = new int[2];
		if (direction == Person.UP) {
			temp[1] = -GameBoard.tsize;
		} else if (direction == Person.DOWN) {
			temp[1] = GameBoard.tsize;
		} else if (direction == Person.RIGHT) {
			temp[0] = GameBoard.tsize;
		} else if (direction == Person.LEFT) {
			temp[0] = -GameBoard.tsize;
		}
		counts = temp;
		increment = 5;
	}

	public void animate() {
		board.movePanel(delta[0], delta[1]);
	}

	@Override
	public void finish() {}
}
