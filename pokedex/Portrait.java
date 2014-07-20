package pokedex;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Portrait extends JPanel {
	private static final ImageIcon icon = new ImageIcon("src/tilesets/pokedex_background.png");
	public BufferedImage image;
	public int width, height;

	public Portrait(int x, int y) {
		width = x;
		height = y;
		image = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		Dimension d = new Dimension(x, y);
		setSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		setImage(icon.getImage());
	}

	public void paint(Graphics g) {
		super.paintComponents(g);
		g.drawImage(image, 0, 0, null);
	}

	public void setIcon(ImageIcon i) {
		setImage(i.getImage());
	}

	public void setImage(Image i) {
		i = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		image.getGraphics().drawImage(i, 0, 0, null);
		repaint();
	}
}
