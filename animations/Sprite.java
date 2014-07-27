package animations;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Sprite {
	public int x, y, width, height;
	private Image image;

	public Sprite(Image i) {
		setImage(i);
	}

	public void setImage(Image i) {
		image = i;
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	public Image getImage() {
		return image;
	}

	public void scaleToFit(int x, int y) {
		double ratio = Math.min(((double) x) / width, ((double) y) / height);
		width *= ratio;
		height *= ratio;
	}

	public Sprite(String filename) {
		setImage(new ImageIcon(filename).getImage());
	}

	public void paint(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
	}
}
