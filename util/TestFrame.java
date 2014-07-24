package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import battle.BattlePanel;

// This class only displays a buffered image in a window.
public class TestFrame extends JFrame {
	public TestFrame(BufferedImage i) {
		super("Test Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel l = new JLabel(i);
		l.setVerticalAlignment(JLabel.TOP);
		l.setHorizontalAlignment(JLabel.RIGHT);
		JPanel p = new JPanel();
		add(p);
		p.add(l);
		pack();
		setVisible(true);
	}

	public TestFrame() {
		super("Test Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		TestPanel pan = new TestPanel();
		add(pan);
		setBounds(200, 400, 300, 300);
		pack();
		setVisible(true);
	}

	public TestFrame(BattlePanel p) {
		super("Test Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(p);
		setBounds(200, 400, p.width, p.height);
		pack();
		setVisible(true);

	}

	public TestFrame(JPanel p) {
		super("Test Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(p);
		setBounds(200, 400, 300, 300);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/data/font.ttf")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		new TestFrame();
	}
}
