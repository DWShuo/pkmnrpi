package console;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import util.ImageLibrary;
import util.Pair;

public class ContextMenu extends JPopupMenu implements MouseListener, MouseMotionListener, ActionListener {
	public JMenuItem select = new JMenuItem("Copy"), paste = new JMenuItem("Paste"), paint = new JMenuItem("Select");

	public MapEditor editor;
	public Point start, temp, current;
	public Pair<String, Integer, Integer>[][] selection;
	public boolean operating;
	private Component focus;

	public ContextMenu(MapEditor e) {
		editor = e;
		add(paint);
		add(select);
		add(paste);
		select.addActionListener(this);
		paste.addActionListener(this);
		paint.addActionListener(this);
	}

	public void show(Component c, int x, int y) {
		super.show(c, x, y);
	}

	public void mouseReleased(MouseEvent e) {
		temp = convert(e.getPoint());
		focus = e.getComponent();
		if (operating) {
			operating = false;
			focus.addMouseListener(editor.creation);
			if (e.isPopupTrigger()) {
				show(focus, e.getX(), e.getY());
			} else {
				select(start, current);
			}
		} else if (!operating && SwingUtilities.isRightMouseButton(e)) {
			show(focus, e.getX(), e.getY());
		}
	}

	private Point bind(Point a) {
		return new Point(Math.max(0, Math.min(a.x, editor.tmap.mapdata[0].length)), Math.max(0, Math.min(a.y, editor.tmap.mapdata.length)));
	}

	@SuppressWarnings("unchecked")
	private void select(Point a, Point b) {
		Point c = bind(new Point(Math.min(a.x, b.x), Math.min(a.y, b.y)));
		Point d = bind(new Point(Math.max(a.x, b.x), Math.max(a.y, b.y)));
		++d.x;
		++d.y;
		selection = new Pair[d.y - c.y][d.x - c.x];
		for (int i = 0; i < selection.length; ++i) {
			for (int j = 0; j < selection[i].length; ++j) {
				selection[i][j] = editor.tmap.mapdata[c.y + i][c.x + j];
			}
		}
		System.gc();
	}

	private void paste(Point a) {
		for (int i = 0; i < selection.length && i + a.y < editor.tmap.mapdata.length; ++i) {
			for (int j = 0; j < selection[i].length && j + a.x < editor.tmap.mapdata[i + a.y].length; ++j) {
				editor.tmap.mapdata[i + a.y][j + a.x] = selection[i][j];
			}
		}
		editor.creation.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == select) {
			current = start = temp;
			operating = true;
			focus.addMouseMotionListener(this);
			focus.removeMouseListener(editor.creation);
		} else if (e.getSource() == paste) {
			operating = false;
			paste(temp);
		} else if (e.getSource() == paint) {
			editor.paint_bucket = editor.tmap.mapdata[temp.y][temp.x];
			editor.bar_label.setIcon(ImageLibrary.getIcon(editor.paint_bucket));
		}
	}

	private Point convert(Point a) {
		return new Point(a.x / 16, a.y / 16);
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
		current = convert(e.getPoint());
		editor.creation.repaint();
	}
}
