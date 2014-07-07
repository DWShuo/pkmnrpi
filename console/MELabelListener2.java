package console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

public class MELabelListener2 implements MouseListener, ActionListener {
	private MapEditor editor;
	private static boolean pressed;
	private static Timer time;

	public MELabelListener2(MapEditor e) {
		editor = e;
		time = new Timer(750, this);
		time.setInitialDelay(750);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (pressed) {
			editor.applyTo(e.getSource());
			time.restart();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		editor.applyTo(e.getSource());
		time.restart();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pressed = false;
		time.stop();
	}
}
