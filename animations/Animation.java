package animations;

import game.GameBoard;
import game.GameState;

import java.awt.image.BufferedImage;

public abstract class Animation {
	public int[] counts, delta;
	public int increment;
	public BufferedImage[] frames;
	public BufferedImage final_frame;
	public Sprite sprite;
	public GameBoard board;

	public void calculateDelta() {
		for (int i = 0; i < counts.length; ++i) {
			if (counts[i] == 0) {
				delta[i] = 0;
				continue;
			}
			delta[i] = counts[i] / increment;
			counts[i] -= delta[i];
		}
		--increment;
	}

	public void animate() {
		board.moveSprite(delta[0], delta[1], sprite);
		if (increment == 0)
			sprite.setImage(final_frame);
		sprite.setImage(frames[increment % frames.length]);
	}

	public abstract void finish();

	public void run() {
		GameState.clock.animate(this);
	}
}
