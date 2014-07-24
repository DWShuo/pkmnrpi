package pokedex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import pokemon.Pokemon;
import util.panels.PatternPanel;

public class PokedexFieldArea extends JPanel implements PokedexUI {
	public PatternPanel background = new PatternPanel(PatternPanel.TEXT_AREA);
	public int width, height, window = 150, open = 100, gap = 17, vgap = 10;
	public StatGraph stats = new StatGraph();

	public PokedexFieldArea(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		PatternPanel.paintTextArea(g, width, height);
		if (stats.mon == null)
			return;
		stats.setPreferredSize(new Dimension(window, open));
		stats.paintAt(g, (width - window) / 2, height - open - vgap);
		g.setFont(font);
		g.setColor(Color.black);
		int h = g.getFontMetrics().getHeight();
		g.drawString(stats.mon.name.toUpperCase(), gap, vgap + h);
		g.setFont(smallfont);
		g.drawString(stats.mon.species, gap, 2 * (vgap + h));
		g.setFont(font);
		g.drawString("Height: " + stats.mon.height, gap, 3 * (vgap + h));
		g.drawString("Weight: " + stats.mon.weight, gap, 4 * (vgap + h));
		g.drawString("Type1: " + Pokemon.getType(stats.mon.type), gap, 5 * (vgap + h));
		g.drawString("Type2: " + Pokemon.getType(stats.mon.t2 == -1 ? stats.mon.type : stats.mon.t2), gap, 6 * (vgap + h));
	}

	public void update(Pokemon p) {
		stats.mon = p;
		repaint();
	}
}
