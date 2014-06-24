package Pokedex;

import game.GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.ImageLibrary;
import util.Searchable;
import util.panels.PatternPanel;
import util.panels.ScalePanel;

public class PokedexSearchBar extends JPanel implements KeyListener {
	public static final int pixel_height = 22;
	public static final PatternPanel background = new PatternPanel(0);
	public static final ImageIcon blank = ImageLibrary.getSolidColor(Color.white, 7, 14);
	public static final String all_valid_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,.:$ ";

	private JLabel[] text;
	private int max, idx;
	private Searchable pokedex;
	public int color = ImageLibrary.black;
	private String current = "";

	public PokedexSearchBar(int width, Searchable s) {
		super();
		pokedex = s;
		max = width / 7 - 4;
		int gap = (width - 7 * max) / 2;
		// Count is the number of digets that can be displayed.
		assert (max > 0);
		setLayout(null);
		setPreferredSize(new Dimension(width, pixel_height));

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, max, 0, 0));
		center.setPreferredSize(new Dimension(width - 2 * gap, pixel_height - 6));
		center.setOpaque(false);

		text = new JLabel[max];
		for (int i = 0; i < max; ++i) {
			text[i] = new JLabel(blank);
			center.add(text[i]);
		}
		add(center);
		add(background);
		background.setBounds(0, 0, width, pixel_height);
		center.setBounds(gap, 3, width - 2 * gap, pixel_height - 6);
	}

	public void keyPressed(KeyEvent e) {
		// Catch enter key
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			//System.out.println("ENTER");
			if (pokedex == null)
				return;
			pokedex.search(current);
		}
		// Catch backspace key
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			//System.out.println("BACKSPACE");
			if (idx <= 0)
				return;
			text[--idx].setIcon(blank);
			current = current.substring(0, current.length() - 1);
		}
		char c = e.getKeyChar();
		//System.out.println(c);
		int index = all_valid_chars.indexOf(c);
		if (index == -1)
			return;
		if (idx >= max)
			return;
		//new TestFrame(ImageLibrary.text[color][index]);
		text[idx++ ].setIcon(ImageLibrary.text[color][index]);
		current += c;
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public static boolean is_valid_char(char c) {
		return all_valid_chars.contains(c + "");
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Test");
		GameState.initilize_all();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(400, 200, 300, 100);
		PokedexSearchBar b = new PokedexSearchBar(300, null);
		f.add(b);
		f.pack();
		f.addKeyListener(b);
		f.setVisible(true);
	}
}
