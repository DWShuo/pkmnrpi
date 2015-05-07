package edu.rcos.pkmnrpi.main.battle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.rcos.pkmnrpi.main.animations.Clock;
import edu.rcos.pkmnrpi.main.pokemon.Move;
import edu.rcos.pkmnrpi.main.pokemon.Pokemon;
import edu.rcos.pkmnrpi.main.util.panels.PatternPanel;

public class BattleText extends JPanel implements BattleUI {
	public Font normal = new Font("Pokemon GB", Font.TRUETYPE_FONT, 16);
	public Font small = new Font("Monospace", Font.TRUETYPE_FONT, 10);
	public Font title = new Font("Monospace", Font.TRUETYPE_FONT, 19);
	public int state;
	public BattleEngine engine;
	public int select, width = 400, height = 100, offsetx, offsety;
	private String message = "Default";

	public BattleText(BattleEngine e) {
		engine = e;
		setBackground(Color.white);
	}

	public void setText(String str) {
		message = str;
		paintImmediately(getBounds());
	}

	public void layText(String str) {
		state = 0;
		boolean st = Clock.manual;
		Clock.manual = true;
		for (int i = 0; i < str.length(); ++i) {
			Clock.nap(Clock.FRAME_WAIT);
			setText(str.substring(0, i + 1));
		}
		setText(str);
		Clock.manual = st;
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		PatternPanel.paintBattleArea(g, width, height);
		if (state == 0) {
			write(g, message);
		}
		if (state == 1) {
			drawMoves(g);
		}
	}

	public void drawMoves(Graphics g) {
		for (int i = 0; i < engine.friend.knownMoves.size(); ++i) {
			drawMove(g, engine.friend.knownMoves.get(i), (i < 2 ? 15 : width / 2 + 15) + offsetx, (i % 2 == 1 ? height / 2 + 10 : 20) + offsety, i == select);
		}
	}

	public void drawMove(Graphics g, Move m, int x, int y, boolean selected) {
		g.setFont(title);
		Rectangle rec = g.getFontMetrics().getStringBounds(m.name, g).getBounds();
		int w = rec.width + 25, h = 22;
		if (selected) {
			fillGradientBox(g, Color.black, x - 2, y - 2, w + 4, h + 4);
		}
		fillGradientBox(g, Pokemon.typeColor(m.type).darker(), x - 1, y - 1, w + 2, h + 2);
		fillGradientBox(g, Pokemon.typeColor(m.type), x, y, w, h);
		g.setColor(Color.black);
		g.drawString(m.name, x + 4, y + h / 2 + rec.height / 2 - 6);
		g.setFont(small);
		double ratio = ((double) m.pp) / m.pp_max;
		String pp = m.pp + "";
		drawButton(g, x + w - 14, y - 6, pp, ratio);
		drawTag(g, x + w - 18, y + h - 8, m);
	}

	public static void fillGradientBox(Graphics g, Color c, int x, int y, int w, int h) {
		Graphics2D g2d = (Graphics2D) g;
		Color color1 = c.brighter();
		Color color2 = c.darker();
		int buffer = 10, gap = 8;
		GradientPaint gp = new GradientPaint(x, y - buffer, color1, x, y + h + buffer, color2);
		g2d.setPaint(gp);
		g2d.fillRect(x + gap, y, w - 2 * gap, h);
		g2d.fillRect(x, y + gap, gap, h - gap - gap);
		g2d.fillRect(x + w - gap, y + gap, gap, h - gap - gap);
		g2d.fillOval(x, y, gap + gap, gap + gap);
		g2d.fillOval(x, y - gap - gap + h, gap + gap, gap + gap);
		g2d.fillOval(x - gap - gap + w, y, gap + gap, gap + gap);
		g2d.fillOval(x - gap - gap + w, y - gap - gap + h, gap + gap, gap + gap);
	}

	public void drawTag(Graphics g, int x, int y, Move m) {
		Image img = physical_box.getImage();
		String line = "" + (m.damage == 0 ? "--" : m.damage);
		if (m.category == Move.SPECIAL)
			img = special_box.getImage();
		else if (m.category == Move.STATUS)
			img = status_box.getImage();
		g.drawImage(img, x, y, null);
		g.setColor(Color.black);
		g.drawString(line, x + 20, y + 14);
	}

	public void drawButton(Graphics g, int x, int y, String pp, double ratio) {
		Image img = green_light.getImage();
		if (ratio <= .2)
			img = red_light.getImage();
		else if (ratio <= .5)
			img = yellow_light.getImage();
		g.drawImage(img, x, y, null);
		g.setColor(Color.black);
		g.drawString(pp, x + 4, y + g.getFontMetrics().getHeight() - 3);
	}

	public void write(Graphics g, String msg) {
		g.setColor(Color.black);
		g.setFont(normal);
		wrapText(g, msg, 15 + offsetx, 30 + offsety);
	}

	public void wrapText(Graphics g, String str, int x, int y) {
		List<String> text = parseText(g.getFontMetrics(), str);
		int gap = 25, offset = 5;
		for (int i = 0; i < text.size(); ++i)
			g.drawString(text.get(i), 15, offset + gap * (i + 1));
	}

	public List<String> parseText(FontMetrics metric, String str) {
		if (str.length() == 0)
			return new ArrayList<String>();
		String[] words = str.split("\\s+");
		List<String> data = new ArrayList<String>();
		String line = words[0];
		for (int i = 1; i < words.length; ++i) {
			String temp = line + " " + words[i];
			if (metric.stringWidth(temp) > width - 10) {
				data.add(line);
				line = words[i];
			} else
				line = temp;
		}
		data.add(line);
		return data;
	}
}
