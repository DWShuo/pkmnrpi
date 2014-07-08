package animations;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Sprite {
	public int x, y, width, height;
	private Image image;

	public Sprite(Image i) {
		image = i;
	}

	public void setImage(Image i) {
		image = i;
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	public Image getImage() {
		return image;
	}

	public Sprite(String filename) {
		image = new ImageIcon(filename).getImage();
	}

	public void paint(Graphics g) {
		g.drawImage(image, x, y, null);
	}
}
