package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TestPanel extends JPanel {

	public TestPanel() {
		super();
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.gray);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.setFont(new Font("font", Font.PLAIN, 16));
		g.drawString("l: 9", 0, 100);
	}
}
