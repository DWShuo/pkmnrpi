package edu.rcos.pkmnrpi.main.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.rcos.pkmnrpi.main.animations.Sprite;

public class GamePanel extends JPanel {
	public List<Sprite> sprites;

	public GamePanel() {
		super();
		sprites = new ArrayList<Sprite>();
		setOpaque(false);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		for (Sprite s : sprites)
			s.paint(g);
	}
}
