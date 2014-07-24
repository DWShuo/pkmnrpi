package pokedex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import pokemon.Pokemon;

public class StatGraph extends JPanel {
	public static final int MAX = 156;
	public static final Color gr = new Color(152, 197, 66);
	public static final Color yl = new Color(247, 193, 37);
	public static final Color rd = new Color(228, 69, 27);
	public static final Font font = new Font("Monospaced", Font.TRUETYPE_FONT, 12);

	public Pokemon mon;
	public Dimension box = new Dimension(100, 100);

	public StatGraph() {
		super();
		setOpaque(false);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		paintAt(g, 0, 0);
	}

	public void paintAt(Graphics g, int x, int y) {
		int gap = g.getFontMetrics().stringWidth("Defense");
		int stop = box.height / 6;
		int buf = stop / 2 + g.getFontMetrics().getHeight() / 2 - 5;
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString("Health", x, y + buf);
		g.drawString("Attack", x, y + stop + buf);
		g.drawString("Defense", x, y + stop * 2 + buf);
		g.drawString("Sp.Atk", x, y + stop * 3 + buf);
		g.drawString("Sp.Def", x, y + stop * 4 + buf);
		g.drawString("Speed", x, y + stop * 5 + buf);
		if (mon == null)
			return;
		int[] data = mon.stats.base;
		for (int i = 0; i < data.length; ++i) {
			paintStat(g, x + gap + 3, y + i * stop, box.width - gap - 3, stop, data[i]);
		}
	}

	public static void paintStat(Graphics g, int x, int y, int w, int h, int stat) {
		Color paint = gr;
		if (stat < 100)
			paint = yl;
		if (stat < 70)
			paint = rd;
		fillGradientBox(g, paint.darker(), x, y, w * stat / MAX, h);
		fillGradientBox(g, paint, x + 1, y + 1, w * stat / MAX - 2, h - 2);
	}

	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		box = d;
		repaint();
	}

	public static void fillGradientBox(Graphics g, Color c, int x, int y, int w, int h) {
		Graphics2D g2d = (Graphics2D) g;
		Color color1 = c.brighter();
		Color color2 = c.darker();
		int buffer = 10;
		GradientPaint gp = new GradientPaint(x, y - buffer, color1, x, y + h + buffer, color2);
		g2d.setPaint(gp);
		g2d.fillRect(x, y, w, h);
	}
}
