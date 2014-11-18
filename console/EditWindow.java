package console;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import objects.Canvas;
import util.Flag;
import util.Pair;

public class EditWindow extends JPanel implements MouseListener, MouseMotionListener {

	public MapEditor editor;
	public JScrollPane view;
	public Canvas background;
	public ContextMenu menu;
	public int flag = 0;

	public EditWindow(MapEditor e) {
		editor = e;
		setLayout(null);
		menu = new ContextMenu(editor);
		background = new Canvas(editor.tmap, menu);

		view = new JScrollPane(background);
		view.getViewport().setViewPosition(new Point(0, 0));
		view.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		view.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		background.view = view.getViewport();
		background.addMouseListener(this);
		background.addMouseListener(menu);
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
			background.update();
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

	public boolean isValid(Point p) {
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

	public Point press = new Point(0, 0);

	private double radius(Point a) {
		return Math.sqrt((a.x - press.x) * (a.x - press.x) + (a.y - press.y) * (a.y - press.y));
	}

	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		if (radius(e.getPoint()) > 8)
			;
		int x = e.getX();
		int y = e.getY();
		if (x != 0)
			x /= 16;
		if (y != 0)
			y /= 16;
		if (y < 0 || x < 0 || y >= editor.tmap.mapdata.length || x >= editor.tmap.mapdata[0].length)
			return;
		paintTo(x, y, editor.paint_bucket);
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		press = e.getPoint();
		int x = e.getX();
		int y = e.getY();
		if (x != 0)
			x /= 16;
		if (y != 0)
			y /= 16;
		if (y < 0 || x < 0 || y >= editor.tmap.mapdata.length || x >= editor.tmap.mapdata[0].length)
			return;
		paintTo(x, y, editor.paint_bucket);
		if (e.getClickCount() == 2) {
			fillFrom(new Point(x, y), editor.paint_bucket);
			fillFrom(new Point(x + 1, y), editor.paint_bucket);
			fillFrom(new Point(x, y + 1), editor.paint_bucket);
			fillFrom(new Point(x - 1, y), editor.paint_bucket);
			fillFrom(new Point(x, y - 1), editor.paint_bucket);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		press = e.getPoint();
	}
}
