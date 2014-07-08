package console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import objects.TileMap;
import objects.Tile;
import util.ImageLibrary;
import util.Tileizer;

public class MapEditor extends JPanel implements ActionListener, MouseListener {
	private JPanel creation;
	private JPanel[] selections;
	private JScrollPane viewer;
	private Tile[][][] tiles;
	public int s_width = 10, c_width = 50, c_height = 25;
	public int paint_bucket = ImageLibrary.DEFAULT_ICON;
	private TileMap tmap;
	private JFrame frame;
	private JButton left, right, up, down;
	private JLabel bar_label;
	public boolean bucketfill = false;

	public MapEditor(JFrame f) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame = f;
		this.setBackground(Color.gray.darker());
		ImageLibrary.init();
		tmap = new TileMap(c_width, c_height);
		tmap.load(new File("src/sample.map"));
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
		this.setSize(new Dimension((c_width + s_width * 3 + 3) * (Tileizer.WIDTH), c_height * (Tileizer.WIDTH)));
		this.setLayout(new FlowLayout());
		tiles = new Tile[9][][];
		selections = new JPanel[8];
		creation = new JPanel();

		Dimension viewsize = new Dimension(c_width * Tileizer.WIDTH + 28, c_height * Tileizer.WIDTH + 28);

		JPanel dirp = new JPanel();
		dirp.setLayout(new BorderLayout(0, 0));
		dirp.setPreferredSize(new Dimension(viewsize.width + 20, viewsize.height + 20));
		up.setPreferredSize(new Dimension(viewsize.width + 20, 15));
		down.setPreferredSize(new Dimension(viewsize.width + 20, 15));
		left.setPreferredSize(new Dimension(15, viewsize.height + 20));
		right.setPreferredSize(new Dimension(15, viewsize.height + 20));

		creation.setLayout(new GridLayout(tmap.mapdata.length, tmap.mapdata[0].length, 0, 0));
		creation.setPreferredSize(new Dimension(tmap.mapdata[0].length * Tileizer.WIDTH, tmap.mapdata.length * Tileizer.WIDTH));

		viewer = new JScrollPane(creation);
		viewer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		viewer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		viewer.setPreferredSize(viewsize);

		JTabbedPane tabs1 = new JTabbedPane();
		JTabbedPane tabs2 = new JTabbedPane();
		JTabbedPane tabs3 = new JTabbedPane();
		tabs1.setSize(s_width * Tileizer.WIDTH + 20, c_height * Tileizer.WIDTH + 5);
		tabs2.setSize(s_width * Tileizer.WIDTH + 20, c_height * Tileizer.WIDTH + 5);
		tabs3.setSize(s_width * Tileizer.WIDTH + 20, c_height * Tileizer.WIDTH + 5);

		for (int i = 0; i < 8; ++i) {
			selections[i] = new JPanel();
			selections[i].setLayout(new GridLayout(c_height, s_width, 0, 0));
			selections[i].setSize(s_width * Tileizer.WIDTH, c_height * Tileizer.WIDTH);
		}
		tabs1.addTab("DAY", selections[0]);
		tabs1.addTab("2", selections[1]);
		tabs2.addTab("ROOF", selections[2]);
		tabs2.addTab("2", selections[3]);
		tabs3.addTab("MISC", selections[4]);
		tabs3.addTab("2", selections[5]);
		tabs3.addTab("3", selections[6]);
		tabs2.addTab("WALL", selections[7]);

		dirp.add(up, BorderLayout.NORTH);
		dirp.add(down, BorderLayout.SOUTH);
		dirp.add(left, BorderLayout.WEST);
		dirp.add(right, BorderLayout.EAST);
		dirp.add(viewer, BorderLayout.CENTER);

		this.add(tabs1);
		this.add(tabs2);
		this.add(tabs3);
		this.add(dirp);

		MELabelListener1 l1 = new MELabelListener1(this);
		MELabelListener2 l2 = new MELabelListener2(this);

		int unit = s_width * c_height;
		// Initilize day 1
		tiles[1] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				tiles[1][i][j] = new Tile(ImageLibrary.icons[ImageLibrary.start_counts[3] + count], j, i, ImageLibrary.start_counts[3] + count);
				tiles[1][i][j].addMouseListener(l1);
				selections[0].add(tiles[1][i][j]);
			}
		}
		// Initilize day 2
		tiles[2] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				if (count + unit < ImageLibrary.icon_counts[3]) {
					tiles[1][i][j] = new Tile(ImageLibrary.icons[ImageLibrary.start_counts[3] + unit + count], j, i, ImageLibrary.start_counts[3] + unit + count);
					tiles[1][i][j].addMouseListener(l1);
					selections[1].add(tiles[1][i][j]);
				} else
					selections[1].add(Tile.blank(j, i));
			}
		}
		// Initilize roof 1
		tiles[3] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				tiles[1][i][j] = new Tile(ImageLibrary.icons[ImageLibrary.start_counts[1] + count], j, i, ImageLibrary.start_counts[1] + count);
				tiles[1][i][j].addMouseListener(l1);
				selections[2].add(tiles[1][i][j]);
			}
		}
		// Initilize roof 2
		tiles[4] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				if (unit + count < ImageLibrary.icon_counts[1]) {
					tiles[1][i][j] = new Tile(ImageLibrary.icons[ImageLibrary.start_counts[1] + unit + count], j, i, ImageLibrary.start_counts[1] + unit + count);
					tiles[1][i][j].addMouseListener(l1);
					selections[3].add(tiles[1][i][j]);
				} else
					selections[3].add(Tile.blank(j, i));
			}
		}
		// Initilize misc 1
		tiles[5] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				tiles[1][i][j] = new Tile(ImageLibrary.icons[count], j, i, count);
				tiles[1][i][j].addMouseListener(l1);
				selections[4].add(tiles[1][i][j]);
			}
		}
		// Initilize misc 2
		tiles[6] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				tiles[1][i][j] = new Tile(ImageLibrary.icons[unit + count], j, i, unit + count);
				tiles[1][i][j].addMouseListener(l1);
				selections[5].add(tiles[1][i][j]);
			}
		}
		// Initilize misc 3
		tiles[7] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				if (unit + unit + count < ImageLibrary.icon_counts[0]) {
					tiles[1][i][j] = new Tile(ImageLibrary.icons[unit + unit + count], j, i, unit + unit + count);
					tiles[1][i][j].addMouseListener(l1);
					selections[6].add(tiles[1][i][j]);
				} else
					selections[6].add(Tile.blank(j, i));
			}
		}
		// Initilize walls
		tiles[8] = new Tile[c_height][s_width];
		for (int i = 0; i < c_height; ++i) {
			for (int j = 0; j < s_width; ++j) {
				int count = i * s_width + j;
				if (count < ImageLibrary.icon_counts[2]) {
					tiles[1][i][j] = new Tile(ImageLibrary.icons[ImageLibrary.start_counts[2] + count], j, i, ImageLibrary.start_counts[2] + count);
					tiles[1][i][j].addMouseListener(l1);
					selections[7].add(tiles[1][i][j]);
				} else
					selections[7].add(Tile.blank(j, i));
			}
		}

		// Initilize new map
		tiles[0] = new Tile[tmap.mapdata.length + 1][tmap.mapdata[0].length + 1];
		for (int i = 0; i < tmap.mapdata.length; ++i) {
			for (int j = 0; j < tmap.mapdata[0].length; ++j) {
				Tile temp = new Tile(ImageLibrary.icons[tmap.mapdata[i][j]], j, i, tmap.mapdata[i][j]);
				creation.add(temp);
				tiles[0][i][j] = temp;
				temp.addMouseListener(this);
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

	public void clearAll() {
		tmap = new TileMap(c_width, c_height);
		tmap.fill_map(paint_bucket);
		init();
		frame.pack();
		repaint();
	}

	public void selected(Object o) {
		Tile t = (Tile) o;
		paint_bucket = t.idx;
		bar_label.setIcon(ImageLibrary.icons[paint_bucket]);
		tmap.fill = paint_bucket;
	}

	public void applyTo(Object o) {
		Tile t = (Tile) o;
		t.setIcon(ImageLibrary.icons[paint_bucket]);
		if (bucketfill) {
			fillTo(t.x, t.y, tmap.mapdata[t.y][t.x]);
		}
		tmap.mapdata[t.y][t.x] = paint_bucket;
		t.idx = paint_bucket;
		repaint();
	}

	public void fillTo(int x, int y, int tile) {
		if (x < 0 || y < 0 || x >= c_width || y >= c_height)
			return;
		if (tmap.mapdata[y][x] != tile || tmap.mapdata[y][x] == paint_bucket)
			return;
		tmap.mapdata[y][x] = paint_bucket;
		tiles[0][y][x].idx = paint_bucket;
		fillTo(x - 1, y, tile);
		fillTo(x + 1, y, tile);
		fillTo(x, y - 1, tile);
		fillTo(x, y + 1, tile);
	}

	public void setBarLabel(JLabel l) {
		bar_label = l;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Map Editor");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MapEditor m = new MapEditor(f);
		f.add(m);
		f.setJMenuBar(new MEBar(m));
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean flagged = true;
		tmap.fill = paint_bucket;
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

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Tile t = (Tile) e.getSource();
		System.out.println(t.idx);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
