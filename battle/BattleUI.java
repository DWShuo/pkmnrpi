package battle;

import java.awt.Color;

import javax.swing.ImageIcon;

public interface BattleUI {
	public static final Color blueish = new Color(28, 140, 255);
	public static final Color greenish = new Color(0, 187, 0);
	public static final Color redish = new Color(255, 3, 3);
	public static final Color yellowish = new Color(255, 169, 11);

	public static final ImageIcon level = new ImageIcon("src/tilesets/sprites/level.png");
	public static final ImageIcon hp = new ImageIcon("src/tilesets/sprites/hp.png");
	public static final ImageIcon green_light = new ImageIcon("src/tilesets/sprites/green light.png");
	public static final ImageIcon yellow_light = new ImageIcon("src/tilesets/sprites/yellow light.png");
	public static final ImageIcon red_light = new ImageIcon("src/tilesets/sprites/red light.png");
	public static final ImageIcon physical_box = new ImageIcon("src/tilesets/sprites/physical.png");
	public static final ImageIcon special_box = new ImageIcon("src/tilesets/sprites/spec.png");
	public static final ImageIcon status_box = new ImageIcon("src/tilesets/sprites/status.png");

}
