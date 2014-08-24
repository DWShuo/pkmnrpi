package game;

import java.awt.Graphics;

import javax.swing.JPanel;

import objects.TileMap;

public class GamePort extends JPanel {
	public TileMap map;
	public GamePanel panel;
	public GameBoard board;

	public GamePort(GameBoard b, TileMap m, GamePanel p) {
		map = m;
		panel = p;
		board = b;
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		panel.painter(g);
	}
}
