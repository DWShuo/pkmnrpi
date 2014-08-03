package console;

import game.GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import objects.TileMap;
import util.ImageLibrary;
import util.Pair;

public class MapEditor extends JPanel implements ComponentListener {
	public EditWindow creation;
	public JScrollPane[] selections;
	public SelectWindow[] selectwindows;
	public Pair<String, Integer, Integer> paint_bucket = TileMap.fill;
	public TileMap tmap;
	public JFrame frame;
	public JLabel bar_label;
	public boolean bucketfill = false;
	public String clipboard;

	public int width = 1200, height = 450, total, doorx, doory;

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
		GameState.initilize_all();
		tmap = new TileMap("src/default.map");
		init();
	}

	public void init() {
		removeAll();
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		selections = new JScrollPane[4];
		selectwindows = new SelectWindow[4];
		creation = new EditWindow(this);

		selectwindows[0] = new SelectWindow(this, ImageLibrary.getSheet("Land"));
		selectwindows[1] = new SelectWindow(this, ImageLibrary.getSheet("Roof"));
		selectwindows[2] = new SelectWindow(this, ImageLibrary.getSheet("Building"));
		selectwindows[3] = new SelectWindow(this, ImageLibrary.getSheet("Misc"));
		selections[0] = new JScrollPane(selectwindows[0]);
		selections[1] = new JScrollPane(selectwindows[1]);
		selections[2] = new JScrollPane(selectwindows[2]);
		selections[3] = new JScrollPane(selectwindows[3]);
		selections[0].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		selections[1].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		selections[2].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		selections[3].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		selections[0].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		selections[1].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		selections[2].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		selections[3].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		selections[0].getViewport().setViewPosition(new Point(0, 0));
		selections[1].getViewport().setViewPosition(new Point(0, 0));
		selections[2].getViewport().setViewPosition(new Point(0, 0));
		selections[3].getViewport().setViewPosition(new Point(0, 0));

		int scrollbar = 20;
		total = selectwindows[0].sheet.width + selectwindows[1].sheet.width + selectwindows[2].sheet.width + selectwindows[3].sheet.width + 4 * scrollbar;

		add(selections[0]);
		add(selections[1]);
		add(selections[2]);
		add(selections[3]);
		add(creation);

		resizeAll();
	}

	public void resizeAll() {
		setPreferredSize(new Dimension(width, height));
		creation.setPreferredSize(new Dimension(width - total, height));
		int scrollbar = 20;
		selections[0].setPreferredSize(new Dimension(selectwindows[0].sheet.width + scrollbar, height));
		selections[1].setPreferredSize(new Dimension(selectwindows[1].sheet.width + scrollbar, height));
		selections[2].setPreferredSize(new Dimension(selectwindows[2].sheet.width + scrollbar, height));
		selections[3].setPreferredSize(new Dimension(selectwindows[3].sheet.width + scrollbar, height));
	}

	public void save(File file) {
		tmap.save(file);
		GameState.save(new File("src/data/game state.txt"));
	}

	public void load(File file) {
		tmap = new TileMap(file.getAbsolutePath());
		creation.repaint();
		frame.pack();
		repaint();
	}

	public void clearAll(String name) {
		tmap = new TileMap(25, 25, name);
		tmap.fillMap(paint_bucket);
		init();
		frame.pack();
		repaint();
	}

	public void setBarLabel(JLabel l) {
		bar_label = l;
	}

	public static void main(String[] args) {

		JFrame f = new JFrame("Map Editor");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MapEditor m = new MapEditor(f);
		f.add(m);
		f.addComponentListener(m);
		f.setJMenuBar(new MEBar(m));
		f.pack();
		f.setResizable(true);
		f.setVisible(true);
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		width = getWidth();
		height = getHeight();
		resizeAll();
	}

	@Override
	public void componentShown(ComponentEvent e) {}
}
