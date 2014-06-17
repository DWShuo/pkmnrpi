package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PatternPanel extends JPanel implements ComponentListener {
	public static final int TEXT_AREA = 0;
	public static final Color blackish = new Color(90, 90, 90);
	public static final Color redish = new Color(237, 49, 49);
	public static final Color pinkish = new Color(255, 156, 165);
	public int x = 100, y = 100, pattern;

	public PatternPanel(int style) {
		super();
		pattern = style;
		addComponentListener(this);
	}

	public void paint(Graphics g) {
		super.paintComponents(g);
		//Graphics2D g = (Graphics2D) gg;
		if (pattern == TEXT_AREA) {
			g.setColor(blackish);
			g.fillRoundRect(0, 0, x, y, 5, 5);
			g.setColor(redish);
			int gap = 3;
			g.fillRoundRect(gap, gap, x - 2 * gap, y - 2 * gap, 5, 5);
			g.setColor(pinkish);
			int buff = 10;
			g.fillRect(gap + buff, gap, x - 2 * gap - 2 * buff, y - 2 * gap);
			g.setColor(Color.white);
			g.fillRect(gap + buff + 2, gap + 1, x - 2 * gap - 2 * buff - 4, y - 2 * gap - 2);
		}
	}

	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {}

	public void componentResized(ComponentEvent e) {
		x = getWidth();
		y = getHeight();
		repaint();
	}

	public void componentShown(ComponentEvent e) {}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(400, 200, 300, 300);
		PatternPanel p = new PatternPanel(0);
		f.add(p);
		f.setVisible(true);
		
	}

}
