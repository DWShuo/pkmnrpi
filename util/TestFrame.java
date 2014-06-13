package util;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// This class only displays a buffered image in a window.
public class TestFrame extends JFrame {
	public TestFrame(BufferedImage i) {
		super("Test Frame");
		JLabel l = new JLabel(new ImageIcon(i));
		l.setVerticalAlignment(JLabel.TOP);
		l.setHorizontalAlignment(JLabel.RIGHT);
		JPanel p = new JPanel();
		add(p);
		p.add(l);
		pack();
		setVisible(true);
	}
}
