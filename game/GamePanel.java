package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import objects.Sprite;

public class GamePanel extends JPanel {
	public ArrayList<Sprite> sprites;

	public GamePanel() {
		super();
		sprites = new ArrayList<Sprite>();
		setBackground(Color.red);
	}

	public void paint(Graphics g) {
		super.paintComponents(g);
		for (Sprite s : sprites)
			s.paint(g);
	}
}
