package objects;

import game.GameBoard;
import game.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Clock extends Timer implements ActionListener {
	public static final int FRAME_WAIT = 20;
	public static final int BACK = 0, FORE = 1;

	public boolean[] flags = { false, false };
	private int x, y, frames, sf, sx, sy, direction, inc;
	private GameBoard board;
	private GamePanel panel;
	private Person player;

	public Clock() {
		super(FRAME_WAIT, null);
		addActionListener(this);
	}

	public boolean isAnimating() {
		for (boolean b : flags)
			if (!b)
				return false;
		return true;
	}

	public void animate_sprite(GamePanel pan, Person p, int x, int y, int f, int dir) {
		sx = x;
		sy = y;
		sf = f;
		player = p;
		flags[FORE] = true;
		direction = dir;
		panel = pan;
		inc = 0;
		start();
	}

	public void animate_background(GameBoard b, int x, int y, int f) {
		this.x = x;
		this.y = y;
		assert (f > 0);
		frames = f;
		board = b;
		flags[BACK] = true;
		start();
	}

	public void actionPerformed(ActionEvent e) {
		if (flags[BACK]) {
			int a = x / frames;
			int b = y / frames;
			x -= a;
			y -= b;
			frames-- ;
			if (frames == 0) {
				flags[BACK] = false;
				if (is_alone(BACK))
					stop();
			}
			board.movePanel(a, b);
		}
		if (flags[FORE]) {
			int a = sx / sf;
			int b = sy / sf;
			sx -= a;
			sy -= b;
			sf-- ;
			if (sf == 0) {
				flags[FORE] = false;
				if (is_alone(FORE))
					stop();
			}
			player.sprite.x += a;
			player.sprite.y += b;
			if (direction == Person.UP) {
				player.sprite.image = player.walk[7 + inc++ % 3];
			} else if (direction == Person.DOWN) {
				player.sprite.image = player.walk[4 + inc++ % 3];
			} else if (direction == Person.RIGHT) {
				player.sprite.image = player.walk[inc++ % 2];
			} else if (direction == Person.LEFT) {
				player.sprite.image = player.walk[2 + inc++ % 2];
			}
			panel.repaint();
		}
	}

	private boolean is_alone(int i) {
		for (int j = 0; j < flags.length; ++j)
			if (j != i && flags[j])
				return false;
		return true;
	}
}
