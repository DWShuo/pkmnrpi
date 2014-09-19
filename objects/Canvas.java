package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JViewport;

import console.ContextMenu;
import console.EditWindow;

public class Canvas extends JPanel {
	public static final int BW = 200;

	public BufferedImage projection;
	public int width, height;
	public TileMap map;
	public JViewport view;
	public Rectangle viewposition, drawposition;
	public long lastupdate;
	public ContextMenu menu;

	public Canvas(TileMap a, JViewport v, ContextMenu m) {
		menu = m;
		map = a;
		view = v;
	}

	public void update() {
		width = map.mapdata[0].length * 16;
		height = map.mapdata.length * 16;
		setPreferredSize(new Dimension(width, height));
		viewposition = view.getViewRect();
		drawposition = bound(new Rectangle(viewposition.x - BW, viewposition.y - BW, viewposition.width + BW * 2, viewposition.height + BW * 2));
		projection = map.getSubMap(drawposition);
		lastupdate = System.currentTimeMillis();
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);

		if (System.currentTimeMillis() - lastupdate > 100)
			update();
		// g.fillRect(drawposition.x, drawposition.y, drawposition.width,
		// drawposition.height);
		g.drawImage(projection, drawposition.x, drawposition.y, null);

		if (menu != null && menu.operating) {
			g.setColor(new Color(64, 128, 128, 128));
			g.fillRect(menu.start.x * 16, menu.start.y * 16, (menu.current.x - menu.start.x + 1) * 16, (menu.current.y - menu.start.y + 1) * 16);
		}
	}

	private Rectangle bound(Rectangle r) {
		int x = Math.max(0, Math.min(width - 1, r.x));
		int y = Math.max(0, Math.min(height - 1, r.y));
		int w = Math.max(1, Math.min(width - x, r.width));
		int h = Math.max(1, Math.min(height - y, r.height));
		return new Rectangle(x, y, w, h);
	}
}
