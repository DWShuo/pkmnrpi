package Pokedex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import util.panels.PatternPanel;

public class PokedexNameArea extends JPanel implements PokedexUI {
	public int width, height;
	public String no = "";

	public PokedexNameArea(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));

	}

	public void setNumber(String n) {
		no = n;
		repaint();
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		PatternPanel.paintNameArea(g, width, height);
		g.setColor(Color.black);
		g.setFont(largefont);
		g.drawString("ID: " + no, 5, height - 8);
	}
}
