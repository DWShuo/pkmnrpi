package battle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import pokemon.Pokemon;

public class BattleHUD extends JPanel implements BattleUI {
	public static final int width = 220, height = 150;
	public static final Font font = new Font("Pokemon GB", Font.TRUETYPE_FONT, 24);
	public static final Font med = new Font("Pokemon GB", Font.TRUETYPE_FONT, 22);
	public static final Font small = new Font("Pokemon GB", Font.TRUETYPE_FONT, 16);
	public static final double[] xpos = { 0, .87, .9, .9, .85, .85, .82, .82, .1, .1, 0 };
	public static final double[] ypos = { .9, .9, .87, .65, .65, .82, .82, .88, .88, .83, .9 };
	public static final double[] xpos2 = { .9, .9, .88, .82, .82, .28, .28, .09, .09, .85, .85, .92 };
	public static final double[] ypos2 = { .7, .52, .48, .48, .56, .56, .48, .48, .58, .58, .7, .7 };

	public int offsetx = 0, offsety = 0;
	public Pokemon focus;
	public Polygon shape, hpbar;
	public boolean on = false;

	public BattleHUD() {
		super();
		setBackground(Color.white);
		formShapes();
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
	}

	private void formShapes() {
		int[] x = new int[xpos.length];
		int[] y = new int[ypos.length];
		for (int i = 0; i < xpos.length; ++i) {
			x[i] = (int) (xpos[i] * width) + offsetx;
			y[i] = (int) (ypos[i] * height) + offsety;
		}
		int[] a = new int[xpos2.length];
		int[] b = new int[ypos2.length];
		shape = new Polygon(x, y, x.length);
		for (int i = 0; i < xpos2.length; ++i) {
			a[i] = (int) (xpos2[i] * width) + offsetx;
			b[i] = (int) (ypos2[i] * height) + offsety;
		}
		hpbar = new Polygon(a, b, a.length);
	}

	public void drawHPBar(Graphics g) {
		if (focus.stats.max_health == 0 || focus.stats.current_health == 0)
			return;
		double dif = ((double) focus.stats.current_health) / ((double) focus.stats.max_health);
		g.setColor(greenish);
		if (dif <= .5)
			g.setColor(yellowish);
		else if (dif <= .2)
			g.setColor(redish);
		g.fillRect((int) (xpos2[5] * width) + offsetx, (int) ((ypos2[5] - .04) * height) + offsety, (int) (width * (xpos2[4] - xpos2[5]) * dif + .5), 3);
	}

	public void drawEXPBar(Graphics g) {
		int exp = focus.expToNextLevel();
		int next = Pokemon.levelToEXP(focus.stats.level, focus.stats.growth_rate);
		g.setColor(blueish);
		focus.stats.total_exp = Math.max(next, focus.stats.total_exp);
		int bitgap = (int) (width * xpos[6] - width * xpos[8]);
		int gap = exp - next;
		g.fillRect((int) (width * xpos[8] + bitgap * (1 - (double) focus.stats.exp / gap)) + offsetx, (int) (height * ypos[6]) + 3 + offsety, (int) (bitgap
				* ((double) focus.stats.exp / gap) + .5), 3);
	}

	public void setPokemon(Pokemon p) {
		focus = p;
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		if (!on)
			return;
		g.setColor(Color.black);
		g.fillPolygon(shape);
		if (focus == null)
			return;
		g.setFont(font);
		g.fillPolygon(hpbar);
		g.drawImage(hp.getImage(), (int) (xpos2[7] * width) + 10 + offsetx, (int) (ypos2[7] * height) + 3 + offsety, null);
		int gap = width - g.getFontMetrics().stringWidth(focus.name);
		if (gap != 0)
			gap /= 2;
		g.drawString(focus.name.toUpperCase(), gap, (int) (23 + height * .12));
		g.drawImage(level.getImage(), (int) (width * .47 + offsetx), (int) (height * .32) + offsety, null);
		g.setFont(med);
		g.drawString("" + focus.stats.level, (int) (width * .47 + 16 + offsetx), (int) (height * .32) + 14 + offsety);
		// g.setFont(small);
		String hp = focus.stats.current_health + "/ " + focus.stats.max_health;
		g.drawString(hp, (int) (width * .82 - g.getFontMetrics().stringWidth(hp) + offsetx), (int) (height * .75) + offsety);
		drawHPBar(g);
		drawEXPBar(g);
	}
}
