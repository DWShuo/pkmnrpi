package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import objects.TileMap;
import util.ImageLibrary;
import util.LayeredPanel;
import util.TestFrame;

public class GameBoard extends JScrollPane implements KeyListener {
	private static final int tilew = 25, tileh = 25, tsize = ImageLibrary.pixel_width[0];
	private static final String default_map = "src/default.map";
	private static final Dimension area = new Dimension(ImageLibrary.pixel_width[0] * tilew, ImageLibrary.pixel_width[0] * tileh);

	private LayeredPanel background;
	private GamePanel foreground;
	private TileMap map;

	public GameBoard() {
		super();
		// Set the look and feel to Nimbus
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
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
		setSize(area);
		setPreferredSize(area);
		setMaximumSize(area);
		setMinimumSize(area);
		setVisible(true);

		load_map(default_map);
		this.setViewportView(background);
	}

	public void load_map(String filename) {
		map = new TileMap(filename);
		BufferedImage im = map.get_static_map();
		foreground = new GamePanel();
		foreground.sprites = map.get_sprites();
		background = new LayeredPanel(new ImageIcon(im), foreground);
		background.setPreferredSize(new Dimension(im.getWidth(), im.getHeight()));
	}

	public void keyPressed(KeyEvent e) {
		String in = KeyEvent.getKeyText(e.getKeyCode());
		if (in == "Up") {
			movePanel(0, -1);
		} else if (in == "Down") {
			movePanel(0, 1);
		} else if (in == "Right") {
			movePanel(1, 0);
		} else if (in == "Left") {
			movePanel(-1, 0);
		}
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public static void main(String[] args) {
		ImageLibrary.init();
		JFrame f = new JFrame("Pokemon RPI");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameBoard b = new GameBoard();
		f.add(b);
		f.addKeyListener(b);
		f.pack();
		f.setVisible(true);
	}

	private int getMaxXExtent() {
		return map.mapdata[0].length * tsize - tilew * tsize;
	}

	private int getMaxYExtent() {
		return map.mapdata.length * tsize - tileh * tsize;
	}

	private void movePanel(int xmove, int ymove) {
		Point pt = viewport.getViewPosition();
		pt.x += xmove;
		pt.y += ymove;

		pt.x = Math.max(0, pt.x);
		pt.x = Math.min(getMaxXExtent(), pt.x);
		pt.y = Math.max(0, pt.y);
		pt.y = Math.min(getMaxYExtent(), pt.y);

		viewport.setViewPosition(pt);
	}
	
	private void animate_screen(int x, int y, int frames) {
		
	}
	
	private void animate_sprite() {
		
	}
}
