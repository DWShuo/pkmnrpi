package battle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import pokemon.Pokemon;

public class BattleEnemyHUD extends JPanel {
	public static final int width = 220, height = 150;
	public static final Font font = new Font("Pokemon GB", Font.TRUETYPE_FONT, 20);
	public static final Font small = new Font("Pokemon GB", Font.TRUETYPE_FONT, 16);
	public static final double[] xpos = { .98, .05, .02, .02, .08, .08, .11, .86, .86, .98 };
	public static final double[] ypos = { .65, .65, .6, .35, .35, .6, .63, .63, .58, .65 };
	public static final double[] xpos2 = { .1, .92, .92, .83, .83, .22, .22, .1, .1 };
	public static final double[] ypos2 = { .44, .44, .36, .36, .42, .42, .36, .36, .44 };
	public static final ImageIcon level = new ImageIcon("src/tilesets/level.png");
	public static final ImageIcon hp = new ImageIcon("src/tilesets/hp.png");
	public static final Color greenish = new Color(0, 187, 0);
	public static final Color redish = new Color(255, 3, 3);
	public static final Color yellowish = new Color(255, 169, 11);

	public Pokemon focus;
	public Polygon shape, hpbar;

	public BattleEnemyHUD() {
		super();
		setBackground(Color.white);
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
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
		// setBackground(Color.red);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillPolygon(shape);
		if (focus == null)
			return;
		g.fillPolygon(hpbar);
		g.setFont(font);
		g.drawImage(hp.getImage(), (int) (xpos2[7] * width) + 5, (int) (ypos2[7] * height) + 3, null);
		g.drawString(focus.name.toUpperCase(), 5, 25);
		g.drawImage(level.getImage(), width / 2, 30, null);
		g.drawString("" + focus.stats.level, width / 2 + 20, 45);

		if (focus.stats.max_health == 0 || focus.stats.current_health == 0)
			return;
		double dif = ((double) focus.stats.current_health) / ((double) focus.stats.max_health);
		g.setColor(greenish);
		if (dif <= .5)
			g.setColor(yellowish);
		else if (dif <= .2)
			g.setColor(redish);
		g.fillRect((int) (xpos2[5] * width), (int) ((ypos[5] - .22) * height), (int) (width * (xpos2[4] - xpos2[5]) * dif), 3);
	}
}
