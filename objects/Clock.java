package objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Clock extends Timer implements ActionListener {
	public static final int FRAME_WAIT = 40;

	public Clock() {
		super(FRAME_WAIT, null);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
