package util.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import util.TestFrame;

public class GridPanel extends JPanel implements ComponentListener {
	public int width, height;
	public Image image;

	public GridPanel(String filename) {
		super();
		setOpaque(false);
		ImageIcon icon = new ImageIcon(filename);
		image = icon.getImage();
		setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	}

	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		width = d.width;
		height = d.height;
		repaint();
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, width, height, null);
		g.setColor(Color.black);
		for (int i = 1; i < 10; ++i) {
			g.drawLine(0, i * height / 10, width, i * height / 10);
			g.drawLine(i * width / 10, 0, i * width / 10, height);
		}
	}

	public static void main(String[] args) {
		new TestFrame(new GridPanel("C:\\Users\\Ted\\Desktop\\sample test.png"));
		new TestFrame(new GridPanel("src/tilesets/current.png"));
		new TestFrame(new GridPanel("C:\\Users\\Ted\\Desktop\\bottom.png"));
		new TestFrame(new GridPanel("src/tilesets/bottom.png"));
	}

	public void componentResized(ComponentEvent e) {
		setPreferredSize(getSize());
	}

	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {}

	public void componentShown(ComponentEvent e) {}
}
