package battle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import pokemon.Pokemon;

public class BattleEnemyHUD extends JPanel implements BattleUI {
	public static final int width = 220, height = 150;
	public static final Font font = new Font("Pokemon GB", Font.TRUETYPE_FONT, 24);
	public static final Font med = new Font("Pokemon GB", Font.TRUETYPE_FONT, 22);
	public static final Font small = new Font("Pokemon GB", Font.TRUETYPE_FONT, 16);
	public static double[] xpos = { 1, .13, .11, .11, .15, .15, .17, .9, .9, .999 };
	public static double[] ypos = { .52, .52, .48, .26, .26, .45, .5, .5, .45, .52 };
	public static double[] xpos2 = { .18, .93, .93, .9, .9, .36, .36, .18, .18 };
	public static double[] ypos2 = { .38, .38, .28, .28, .36, .36, .28, .28, .38 };

	public Pokemon focus;
	public Polygon shape, hpbar;
	public int offsetx, offsety;
	public boolean on = false;

	public BattleEnemyHUD() {
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
			x[i] = (int) (xpos[i] * width);
			y[i] = (int) (ypos[i] * height);
		}
		int[] a = new int[xpos2.length];
		int[] b = new int[ypos2.length];
		shape = new Polygon(x, y, x.length);
		for (int i = 0; i < xpos2.length; ++i) {
			a[i] = (int) (xpos2[i] * width);
			b[i] = (int) (ypos2[i] * height);
		}
		hpbar = new Polygon(a, b, a.length);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		if (!on)
			return;
		g.setColor(Color.black);
		g.fillPolygon(shape);
		if (focus == null)
			return;
		g.fillPolygon(hpbar);
		g.setFont(font);
		g.drawImage(hp.getImage(), (int) (xpos2[7] * width) + 8 + offsetx, (int) (ypos2[7] * height) + 3 + offsety, null);
		g.drawString(focus.name.toUpperCase(), 20 + offsetx, 23 + offsety);
		g.drawImage(level.getImage(), width / 2 + 13 + offsetx, 30 + offsety, null);
		g.setFont(med);
		g.drawString("" + focus.stats.level, width / 2 + 32 + offsetx, 45 + offsety);

		if (focus.stats.max_health == 0 || focus.stats.current_health == 0)
			return;
		double dif = ((double) focus.stats.current_health) / ((double) focus.stats.max_health);
		g.setColor(greenish);
		if (dif <= .5)
			g.setColor(yellowish);
		else if (dif <= .2)
			g.setColor(redish);
		g.fillRect((int) (xpos2[5] * width) + offsetx, (int) ((ypos2[0] - .04) * height) + offsety - 3, (int) (width * (xpos2[4] - xpos2[5]) * dif + .5), 3);
	}
}
