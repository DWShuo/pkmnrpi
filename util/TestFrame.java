package util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
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
		l.setBackground(Color.red);
		l.setOpaque(false);
		JPanel p = new JPanel();
		add(p);
		p.add(l);
		p.setOpaque(false);
		setBackground(Color.blue);
		pack();
		setVisible(true);
	}

	public TestFrame(Image i) {
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

	public TestFrame(Icon i) {
		super("Test Frame");
		JLabel l = new JLabel(i);
		l.setVerticalAlignment(JLabel.TOP);
		l.setHorizontalAlignment(JLabel.RIGHT);
		JPanel p = new JPanel();
		add(p);
		p.add(l);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Image i = new ImageIcon("src/tilesets/pokedex_background.png").getImage();
		int w = i.getWidth(null);
		int h = i.getHeight(null);
		BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		im.getGraphics().drawImage(i, 0, 0, null);
		int bitcolor = im.getRGB(0, 0);
		Color c = new Color(bitcolor);
		System.out.println(bitcolor);
		System.out.println(c);
		new TestFrame(i);
	}
}
