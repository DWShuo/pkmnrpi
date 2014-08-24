package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import animations.Sprite;
import animations.SpriteBoard;
import objects.LayeredPanel;
import objects.TileMap;
import trainers.Person;
import trainers.Trainer;
import util.ImageLibrary;

public class GameBoard extends JScrollPane implements KeyListener, SpriteBoard {
	public static final int tilew = 25, tileh = 25, tsize = ImageLibrary.pixel_width[0];
	public static final String default_map = "src/maps/park.map";
	public static final Dimension area = new Dimension(ImageLibrary.pixel_width[0] * tilew, ImageLibrary.pixel_width[0] * tileh);
	public static final int buffer = 7;

	public LayeredPanel background;
	public GamePanel foreground;
	public TileMap map;
	public Trainer player;
	public GameEngine engine;

	public GameBoard(GameEngine en) {
		super();
		engine = en;
		// Set the look and feel to Nimbus
		try {

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

		loadMap("src/maps/ALL.map");
		this.setViewportView(background);
		this.setBackground(Color.black);
		initilizePlayer();
	}

	public void loadMap(String filename) {
		map = new TileMap(filename);
		BufferedImage im = map.getStaticMap();
		foreground = new GamePanel();
		background = new LayeredPanel(new ImageIcon(im), foreground);
		background.setPreferredSize(new Dimension(im.getWidth(), im.getHeight()));
	}

	public void keyPressed(KeyEvent e) {
	}

	public boolean canMove(int direction) {
		int x = player.x + map.centerx;
		int y = player.y + map.centery;
		if (x < 0 || y < 0)
			return false;
		if (x > map.mapdata[0].length || y > map.mapdata.length)
			return false;
		if (direction == Person.UP) {
			y--;
		} else if (direction == Person.DOWN) {
			y++;
		} else if (direction == Person.RIGHT) {
			x++;
		} else if (direction == Person.LEFT) {
			x--;
		}
		return ImageLibrary.canWalk(map.mapdata[y][x]);
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		new GameEngine();
	}

	public void checkWildPokemon() {
		for (String str : GameState.SPAWNS.keySet()) {
			if (str.equalsIgnoreCase(map.name)) {
				if (GameState.SPAWNS.get(str).roll(engine))

					return;
			}
		}
	}

	private void initilizePlayer() {
		player = engine.state.self;
		player.map = map;
		player.setLocation(player.x, player.y);
		foreground.sprites.add(player.sprite);
		player.px = 13;
		player.py = 13;
		movePanel((player.x + map.centerx) * tsize - tilew * tsize / 2, (player.y + map.centery) * tsize - tileh * tsize / 2);
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
