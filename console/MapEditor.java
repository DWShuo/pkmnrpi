package console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import objects.TileMap;
import objects.Tile;
import util.ImageLibrary;
import util.Tileizer;

public class MapEditor extends JPanel implements ActionListener {
	private JPanel selection, creation;
	private JScrollPane viewer;
	private Tile[][] mytiles, newtiles;
	public int s_width = 10, c_width = 25, c_height = 25;
	public int paint_bucket = ImageLibrary.DEFAULT_ICON;
	private TileMap tmap;
	private JFrame frame;
	private JButton left, right, up, down;
	private JLabel bar_label;

	public MapEditor(JFrame f) {
		frame = f;
		this.setBackground(Color.gray.darker());
		ImageLibrary.init();
		tmap = new TileMap(c_width, c_height);
		tmap.load(new File("src/default.map"));
		left = new JButton(new ImageIcon("src/left.png"));
		left.addActionListener(this);
		left.setBackground(Color.gray.brighter());
		right = new JButton(new ImageIcon("src/right.png"));
		right.addActionListener(this);
		right.setBackground(Color.gray.brighter());
		up = new JButton(new ImageIcon("src/up.png"));
		up.addActionListener(this);
		up.setBackground(Color.gray.brighter());
		down = new JButton(new ImageIcon("src/down.png"));
		down.addActionListener(this);
		down.setBackground(Color.gray.brighter());
		init();
	}

	public void init() {
		this.removeAll();
		this.setSize(new Dimension((c_width + s_width + 3) * (Tileizer.WIDTH), c_height * (Tileizer.WIDTH)));

		Dimension viewsize = new Dimension(c_width * Tileizer.WIDTH + 28, c_height * Tileizer.WIDTH + 28);

		JPanel dirp = new JPanel();
		dirp.setLayout(new BorderLayout(0, 0));
		dirp.setPreferredSize(new Dimension(viewsize.width + 20, viewsize.height + 20));
		up.setPreferredSize(new Dimension(viewsize.width + 20, 15));
		down.setPreferredSize(new Dimension(viewsize.width + 20, 15));
		left.setPreferredSize(new Dimension(15, viewsize.height + 20));
		right.setPreferredSize(new Dimension(15, viewsize.height + 20));

		creation = new JPanel();
		creation.setLayout(new GridLayout(tmap.mapdata.length, tmap.mapdata[0].length, 0, 0));
		creation.setPreferredSize(new Dimension(tmap.mapdata[0].length * Tileizer.WIDTH, tmap.mapdata.length * Tileizer.WIDTH));

		viewer = new JScrollPane(creation);
		viewer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		viewer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		viewer.setPreferredSize(viewsize);

		selection = new JPanel();
		selection.setLayout(new GridLayout(c_height, s_width, 0, 0));
		selection.setSize(s_width * Tileizer.WIDTH, c_height * Tileizer.WIDTH);

		dirp.add(up, BorderLayout.NORTH);
		dirp.add(down, BorderLayout.SOUTH);
		dirp.add(left, BorderLayout.WEST);
		dirp.add(right, BorderLayout.EAST);
		dirp.add(viewer, BorderLayout.CENTER);

		this.add(selection);
		this.add(dirp);

		MELabelListener1 l1 = new MELabelListener1(this);
		MELabelListener2 l2 = new MELabelListener2(this);

		// Initilize Pallet
		mytiles = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				if (i * s_width + j >= ImageLibrary.icons.length) {
					Tile temp = new Tile(ImageLibrary.blank, j, i, 0);
					temp.setSize(Tileizer.WIDTH, Tileizer.WIDTH);
					selection.add(temp);
					mytiles[i][j] = temp;
					temp.setVisible(true);
					temp.addMouseListener(l1);
				} else {
					Tile temp = new Tile(ImageLibrary.icons[i * s_width + j], j, i, i * s_width + j);
					temp.setSize(Tileizer.WIDTH, Tileizer.WIDTH);
					selection.add(temp);
					mytiles[i][j] = temp;
					temp.setVisible(true);
					temp.addMouseListener(l1);
				}
			}
		}

		// Initilize new map
		newtiles = new Tile[tmap.mapdata.length + 1][tmap.mapdata[0].length + 1];
		for (int i = 0; i < tmap.mapdata.length; ++i) {
			for (int j = 0; j < tmap.mapdata[0].length; ++j) {
				Tile temp = new Tile(ImageLibrary.icons[tmap.mapdata[i][j]], j, i, i * tmap.mapdata[0].length + j);
				temp.setSize(Tileizer.WIDTH, Tileizer.WIDTH);
				creation.add(temp);
				newtiles[i][j] = temp;
				temp.setVisible(true);
				temp.addMouseListener(l2);
			}
		}

	}

	public void save(File file) {
		tmap.save(file);
	}

	public void load(File file) {
		tmap.load(file);
		init();
		frame.pack();
		repaint();
	}

	// BROKEN
	public void clear_all() {
		super.removeAll();
		init();
		repaint();
	}

	public void selected(Object o) {
		Tile t = (Tile) o;
		paint_bucket = t.idx;
		bar_label.setIcon(ImageLibrary.icons[paint_bucket]);
		tmap.fill = paint_bucket;
	}

	public void apply_to(Object o) {
		Tile t = (Tile) o;
		t.setIcon(ImageLibrary.icons[paint_bucket]);
		tmap.mapdata[t.y][t.x] = paint_bucket;
		t.idx = paint_bucket;
		repaint();
	}

	public void set_bar_label(JLabel l) {
		bar_label = l;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Map Editor");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MapEditor m = new MapEditor(f);
		f.add(m);
		f.setJMenuBar(new MEBar(m));
		f.pack();
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean flagged = true;
		if (e.getSource() == left) {
			tmap.buffer_left_cols(25);
		} else if (e.getSource() == right) {
			tmap.buffer_right_cols(25);
		} else if (e.getSource() == up) {
			tmap.buffer_top_rows(25);
		} else if (e.getSource() == down) {
			tmap.buffer_bottom_rows(25);
		} else
			flagged = false;
		if (flagged) {
			init();
			frame.pack();
			repaint();
		}
	}

}
