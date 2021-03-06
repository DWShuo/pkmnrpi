package edu.rcos.pkmnrpi.main.animations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

/**
 * This class is used as a generic animation timer class. The reason for having a generic class is to handle a large number of animations simultaniously without lag
 */
public class Clock extends Timer implements ActionListener {
	public static final int FRAME_WAIT = 30;
	public static boolean manual = false;

	private List<Animation> animations = new ArrayList<Animation>();

	// Currently the instance of clock is never used, but the constructor is
	// necessary to provide a valid board class to access.
	public Clock() {
		super(FRAME_WAIT, null);
		addActionListener(this);
	}

	public void animate(Animation a) {
		animations.add(a);
		start();
	}

	// This is just an accessory method to ensure that multiple animations are
	// not attempted at once.
	public boolean isAnimating() {
		return !animations.isEmpty() || manual;
	}

	public static void nap(long time) {
		boolean temp = Clock.manual;
		Clock.manual = true;
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < time)
			;
		Clock.manual = temp;
	}

	public static void nap2(long time) {
		boolean temp = Clock.manual;
		Clock.manual = true;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Clock.manual = temp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Animation> complete = new ArrayList<Animation>();
		for (Animation a : animations) {
			a.calculateDelta();
			a.animate();
			if (a.increment == 0)
				complete.add(a);
		}
		animations.removeAll(complete);
		if (animations.isEmpty())
			stop();
		for (Animation a : complete)
			a.finish();
	}
}
