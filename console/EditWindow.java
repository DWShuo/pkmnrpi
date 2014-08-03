package console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import util.Flag;
import util.Pair;

public class EditWindow extends JPanel implements MouseListener, MouseMotionListener {

	private class Canvas extends JPanel {
		public BufferedImage image, projection;
		public int width, height;
		public boolean superbig;
		public EditWindow window;
		public Rectangle viewposition, drawposition;
		public long lastupdate;

		public Canvas(EditWindow w, BufferedImage b) {
			window = w;
			setImage(b);
		}

		public void setImage(BufferedImage i) {
			image = i;
			width = i.getWidth();
			height = i.getHeight();
			superbig = width * height > 250000;
			setPreferredSize(new Dimension(width, height));
			repaint();
		}

		private void update() {
			viewposition = window.view.getViewport().getViewRect();
			drawposition = bound(new Rectangle(viewposition.x - viewposition.width / 2, viewposition.y - viewposition.height / 2, viewposition.width * 2, viewposition.height * 2));
			projection = window.editor.tmap.getSubMap(drawposition);
			lastupdate = System.currentTimeMillis();
		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);
			if (superbig) {
				if (System.currentTimeMillis() - lastupdate > 100)
					update();
				g.fillRect(drawposition.x, drawposition.y, drawposition.width, drawposition.height);
				g.drawImage(projection, drawposition.x, drawposition.y, null);
			} else {
				g.fillRect(0, 0, width, height);
				g.drawImage(image, 0, 0, null);
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

	public MapEditor editor;
	public JScrollPane view;
	public Canvas background;
	public int flag = 0;

	public EditWindow(MapEditor e) {
		editor = e;
		setLayout(null);
		background = new Canvas(this, editor.tmap.getStaticMap());

		view = new JScrollPane(background);
		view.getViewport().setViewPosition(new Point(0, 0));
		view.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		view.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		background.addMouseListener(this);
		background.addMouseMotionListener(this);

		add(view);
		view.setBounds(0, 0, 200, 200);
	}

	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		view.setBounds(0, 0, d.width, d.height);
	}

	public void repaint() {
		if (editor != null && editor.tmap != null && background != null) {
			if (background.superbig)
				background.update();
			else
				background.setImage(editor.tmap.getStaticMap());
		}
		super.repaint();
	}

	private static boolean isIn(ArrayList<Point> ls, Point p) {
		for (Point o : ls) {
			if (o.x == p.x && o.y == p.y)
				return true;
		}
		return false;
	}

	public void fillFrom(Point p, Pair<String, Integer, Integer> pa) {
		ArrayList<Point> current = new ArrayList<Point>();
		current.add(p);
		Pair<String, Integer, Integer> type = editor.tmap.mapdata[p.y][p.x];
		ArrayList<Point> next = addNeighboors(p, current, type);
		while (!next.isEmpty()) {
			ArrayList<Point> extra = new ArrayList<Point>();
			for (Point a : next) {
				current.add(a);
				extra.addAll(addNeighboors(a, current, type));
			}
			next = extra;
		}

		for (Point a : current) {
			editor.tmap.mapdata[a.y][a.x] = editor.paint_bucket;
		}
	}

	private boolean isValid(Point p) {
		return p.x >= 0 && p.y >= 0 && p.x < editor.tmap.mapdata[0].length && p.y < editor.tmap.mapdata.length;
	}

	public ArrayList<Point> addNeighboors(Point p, ArrayList<Point> ls, Pair<String, Integer, Integer> type) {
		Point a = new Point(p.x + 1, p.y);
		Point b = new Point(p.x - 1, p.y);
		Point c = new Point(p.x, p.y + 1);
		Point d = new Point(p.x, p.y - 1);
		ArrayList<Point> next = new ArrayList<Point>();
		if (isValid(a) && !isIn(ls, a) && type.compareTo(editor.tmap.mapdata[a.y][a.x]) == 0) {
			ls.add(a);
			next.add(a);
		}
		if (isValid(b) && !isIn(ls, b) && type.compareTo(editor.tmap.mapdata[b.y][b.x]) == 0) {
			ls.add(b);
			next.add(b);
		}
		if (isValid(c) && !isIn(ls, c) && type.compareTo(editor.tmap.mapdata[c.y][c.x]) == 0) {
			ls.add(c);
			next.add(c);
		}
		if (isValid(d) && !isIn(ls, d) && type.compareTo(editor.tmap.mapdata[d.y][d.x]) == 0) {
			ls.add(d);
			next.add(d);
		}
		return next;
	}

	public void paintTo(int x, int y, Pair<String, Integer, Integer> p) {
		if (x != 0)
			x /= 16;
		if (y != 0)
			y /= 16;
		if (y < 0 || x < 0 || y >= editor.tmap.mapdata.length || x >= editor.tmap.mapdata[0].length)
			return;
		if (flag != 0) {
			if (flag == 1) {
				editor.tmap.centerx = x;
				editor.tmap.centery = y;
				flag = 0;
			} else if (flag == 2) {
				flag = 0;
				Flag f = new Flag(editor.tmap.name + "," + x + "," + y + ",0," + editor.clipboard);
				Flag.addFlag(f);
			} else if (flag == 4) {
				flag = 0;
				Flag f = new Flag(editor.tmap.name + "," + x + "," + y + ",1," + editor.doorx + ":" + editor.doory + ":" + editor.clipboard);
				Flag.addFlag(f);
			}
			return;
		}
		if (editor.bucketfill)
			fillFrom(new Point(x, y), p);
		editor.tmap.mapdata[y][x] = p;
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
		paintTo(e.getX(), e.getY(), editor.paint_bucket);
	}

	public void mouseMoved(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		paintTo(e.getX(), e.getY(), editor.paint_bucket);
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}
}
