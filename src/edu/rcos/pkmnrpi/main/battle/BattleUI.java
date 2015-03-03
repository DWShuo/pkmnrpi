package edu.rcos.pkmnrpi.main.battle;

import java.awt.Color;

import javax.swing.ImageIcon;

public interface BattleUI {
	public static final Color blueish = new Color(28, 140, 255);
	public static final Color greenish = new Color(0, 187, 0);
	public static final Color redish = new Color(255, 3, 3);
	public static final Color yellowish = new Color(255, 169, 11);

	public static final ImageIcon level = new ImageIcon("data/tilesets/sprites/level.png");
	public static final ImageIcon hp = new ImageIcon("data/tilesets/sprites/hp2.png");
	public static final ImageIcon green_light = new ImageIcon("data/tilesets/sprites/green light.png");
	public static final ImageIcon yellow_light = new ImageIcon("data/tilesets/sprites/yellow light.png");
	public static final ImageIcon red_light = new ImageIcon("data/tilesets/sprites/red light.png");
	public static final ImageIcon physical_box = new ImageIcon("data/tilesets/sprites/physical.png");
	public static final ImageIcon special_box = new ImageIcon("data/tilesets/sprites/spec.png");
	public static final ImageIcon status_box = new ImageIcon("data/tilesets/sprites/status.png");

}
