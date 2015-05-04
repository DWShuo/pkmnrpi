// @author Theodore Tenedorio

package edu.rcos.pkmnrpi.main.pokedex;

import static edu.rcos.pkmnrpi.main.pokedex.PokedexUI.font;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.rcos.pkmnrpi.main.util.panels.PatternPanel;

// This is a class that acts like a text area using pre-defined icons to draw the fonts.
public class PokedexTextArea extends JPanel implements PokedexUI {

	public int width, height, gap = 20, vgap = 5;
	public String text = "";

	// The dimensions of the area cannot change, and must be passed to the
	// constructor.
	public PokedexTextArea(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		PatternPanel.paintTextArea(g, width, height);
		g.setFont(font);
		g.setColor(Color.black);
		List<String> lines = parseText(g.getFontMetrics(), text);
		int buffer = g.getFontMetrics().getHeight() + 5;
		for (int i = 0; i < lines.size(); ++i) {
			g.drawString(lines.get(i), gap, vgap + (i + 1) * buffer);
		}
	}

	public List<String> parseText(FontMetrics metric, String str) {
		String[] words = str.split("\\s+");
		List<String> data = new ArrayList<String>();
		String line = "";
		for (int i = 0; i < words.length; ++i) {
			String temp = line + " " + words[i];
			if (metric.stringWidth(temp) > width - gap - gap) {
				data.add(line);
				line = words[i];
			} else
				line = temp;
		}
		data.add(line);
		return data;
	}
}
