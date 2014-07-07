package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import objects.Person;
import objects.Sprite;
import objects.TileMap;
import util.ImageLibrary;
import util.LayeredPanel;

public class GameBoard extends JScrollPane implements KeyListener {
	public static final int tilew = 25, tileh = 25, tsize = ImageLibrary.pixel_width[0];
	public static final String default_map = "src/sample.map";
	public static final Dimension area = new Dimension(ImageLibrary.pixel_width[0] * tilew, ImageLibrary.pixel_width[0] * tileh);
	public static final int buffer = 7;

	public LayeredPanel background;
	public GamePanel foreground;
	public TileMap map;
	public Person player;

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

		loadMap(default_map);
		this.setViewportView(background);
		initilizePlayer();
	}

	public void loadMap(String filename) {
		map = new TileMap(filename);
		BufferedImage im = map.get_static_map();
		foreground = new GamePanel();
		foreground.sprites = map.get_sprites();
		background = new LayeredPanel(new ImageIcon(im), foreground);
		background.setPreferredSize(new Dimension(im.getWidth(), im.getHeight()));
	}

	public void keyPressed(KeyEvent e) {
	}

	public boolean canMove(int direction) {
		int x = player.x;
		int y = player.y;
		if (direction == Person.UP) {
			y--;
		} else if (direction == Person.DOWN) {
			y++;
		} else if (direction == Person.RIGHT) {
			x++;
		} else if (direction == Person.LEFT) {
			x--;
		}
		return ImageLibrary.walk_tiles[map.mapdata[y][x]];
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

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

	private void initilizePlayer() {
		player = new Person();
		player.name = "Phonyx";
		player.walk = new BufferedImage[10];
		for (int i = 0; i < 10; ++i)
			player.walk[i] = ImageLibrary.player[i];
		player.bike = new BufferedImage[10];
		for (int i = 0; i < 10; ++i)
			player.bike[i] = ImageLibrary.player[i + 10];
		player.sprite = new Sprite(player.walk[0]);
		player.set_location(5, 5);
		foreground.sprites.add(player.sprite);
	}

	public void moveSprite(int xmove, int ymove, Sprite s) {
		xmove += s.x;
		ymove += s.y;
		xmove = Math.max(0, xmove);
		ymove = Math.max(0, ymove);
		xmove = Math.min(xmove, map.mapdata[0].length * tsize - s.width);
		ymove = Math.min(ymove, map.mapdata.length * tsize - s.height);
		s.x = xmove;
		s.y = ymove;
		foreground.repaint();
	}

	public void movePanel(int xmove, int ymove) {
		Point pt = viewport.getViewPosition();
		int maxxextend = map.mapdata[0].length * tsize - tilew * tsize, maxyextend = map.mapdata.length * tsize - tileh * tsize;
		pt.x += xmove;
		pt.y += ymove;

		pt.x = Math.max(0, pt.x);
		pt.x = Math.min(maxxextend, pt.x);
		pt.y = Math.max(0, pt.y);
		pt.y = Math.min(maxyextend, pt.y);

		viewport.setViewPosition(pt);
	}
}
