package objects;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Sprite {
	public int x, y;
	public Image image;

	public Sprite(Image i) {
		image = i;
	}

	public Sprite(String filename) {
		image = new ImageIcon(filename).getImage();
	}

	public void paint(Graphics g) {
		g.drawImage(image, x, y, null);
	}
}
