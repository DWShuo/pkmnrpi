package edu.rcos.pkmnrpi.main.util.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScalePanel extends JPanel implements ComponentListener {
	private int w, h;
	public Image image;

	public ScalePanel(Image i, int x, int y) {
		super();
		w = x;
		h = y;
		setPreferredSize(new Dimension(x, y));
		addComponentListener(this);
		image = i;
		repaint();
	}

	public void paint(Graphics g) {
		super.paintComponents(g);
		g.drawImage(image, 0, 0, w, h, null);
	}

	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {}

	public void componentResized(ComponentEvent e) {
		w = getWidth();
		h = getHeight();
		setPreferredSize(new Dimension(w, h));
		repaint();
	}

	public void componentShown(ComponentEvent e) {}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(300, 200, 400, 400);
		f.add(new ScalePanel((new ImageIcon("data/bliss.jpg")).getImage(), 400, 400));
		f.setVisible(true);
	}
}
