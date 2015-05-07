package edu.rcos.pkmnrpi.main.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.rcos.pkmnrpi.main.util.FontLibrary;

public class MainMenu extends JPanel {

	private final String BTN_NEW_GAME = "New Game";
	private final String BTN_LOAD_GAME = "Load Game";
	private final String BTN_MAP_EDITOR = "Map Editor";
	private final String BTN_QUIT = "Quit";
	private final Border ITEM_BORDER = BorderFactory.createEmptyBorder(25, 25, 20, 25);
	private final Border OVERALL_BORDER = BorderFactory.createEmptyBorder(25, 25, 20, 25);
	
	private FontLibrary fontLibrary;
	private Map<String, JButton> menuItems = new LinkedHashMap<>();
	private final GameEngine engine;
	
	public MainMenu(GameEngine eng) {
		super();
		
		// Set some attributes
		setOpaque(false);
		setBorder(OVERALL_BORDER);
		setVisible(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Set some instance variables
		engine = eng;
		fontLibrary = FontLibrary.getInstance();
		
		// Create title
		JLabel title = new JLabel("Pokemon RPI");
		title.setFont(fontLibrary.getLargeFont());
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setBorder(ITEM_BORDER);
		title.setForeground(Color.WHITE);
		title.setBackground(Color.BLACK);
		add(title);
		
		// Create menu items
		menuItems.put(BTN_NEW_GAME, null);
		menuItems.put(BTN_LOAD_GAME, null);
		menuItems.put(BTN_MAP_EDITOR, null);
		menuItems.put(BTN_QUIT, null);
		renderMenuItems();
		
		// Add click handlers
		menuItems.get(BTN_NEW_GAME).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				engine.getState().load(GameState.DEFAULT_SAVE);
				engine.launchGame();
			}
			
		});
		
		menuItems.get(BTN_LOAD_GAME).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(GameState.SAVE_DIRECTORY));
				fileChooser.showOpenDialog(engine.getFrame());
				engine.getState().load(fileChooser.getSelectedFile().getAbsolutePath());
				engine.launchGame();
			}
			
		});
		
		menuItems.get(BTN_MAP_EDITOR).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				engine.launchMapEditor();
			}
			
		});
		
		menuItems.get(BTN_QUIT).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				engine.exit();
			}
			
		});
	}
	
	private void renderMenuItems() {
		for(String menuItem : menuItems.keySet()) {
			JButton item = new JButton(menuItem);
			item.setAlignmentX(CENTER_ALIGNMENT);
			item.setMinimumSize(new Dimension(300, 50));
			item.setFont(fontLibrary.getMediumFont());
			item.setBorder(ITEM_BORDER);
			add(item);
			menuItems.put(menuItem, item);
		}
	}
	
}
