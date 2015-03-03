package edu.rcos.pkmnrpi.main.console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.rcos.pkmnrpi.main.game.GameState;
import edu.rcos.pkmnrpi.main.objects.SpriteSheet;
import edu.rcos.pkmnrpi.main.util.ImageLibrary;
import edu.rcos.pkmnrpi.main.util.Pair;

public class SelectWindow extends JPanel implements MouseListener, MouseMotionListener {
	public MapEditor editor;
	public SpriteSheet sheet;
	public int flag = 0;

	public SelectWindow(MapEditor e, SpriteSheet s) {
		editor = e;
		sheet = s;
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(sheet.width, sheet.height));
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(sheet.base.getImage(), 0, 0, null);
		if (flag == 0)
			return;
		ArrayList<Dimension> ary = GameState.TERRAIN.get(sheet.name);
		if (ary == null)
			return;
		for (Dimension d : ary) {
			g.setColor(Color.red);
			g.fillOval(d.width * 16 + 5, d.height * 16 + 5, 6, 6);
		}
	}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		int x = e.getX() == 0 ? 0 : e.getX() / sheet.cut.width;
		int y = e.getY() == 0 ? 0 : e.getY() / sheet.cut.height;
		// System.out.println(x + ", " + y);
		if (y >= sheet.iconmap.length)
			return;
		editor.paint_bucket = new Pair<String, Integer, Integer>(sheet.name, x, y);
		editor.bar_label.setIcon(ImageLibrary.getIcon(editor.paint_bucket));
		if (flag == 0)
			return;
		GameState.add(sheet.name, new Dimension(x, y));
		repaint();
	}

	public void mouseReleased(MouseEvent e) {}

}
