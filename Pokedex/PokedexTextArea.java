// @author Theodore Tenedorio

package Pokedex;

import game.GameState;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.ImageLibrary;
import util.PatternPanel;
import util.ScalePanel;

// This is a class that acts like a text area using pre-defined icons to draw the fonts.
public class PokedexTextArea extends JPanel implements ActionListener {
	public static final ImageIcon blank = PokedexSearchBar.blank;
	public static final PatternPanel background = new PatternPanel(PatternPanel.TEXT_AREA);

	private int row, col, index, width, height;
	private Dimension[] loc;
	private JLabel[][] text;
	private String source = "";
	private Timer time;
	public int color = ImageLibrary.black;

	// The dimensions of the area cannot change, and must be passed to the
	// constructor.
	public PokedexTextArea(int width, int height) {
		super();
		setLayout(null); // Needed to overlap on background
		setPreferredSize(new Dimension(width, height));
		this.width = width;
		this.height = height;

		// Calculate the number of text cells
		int w = width / 7 - 4;
		int h = (height) / 12 - 2;

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(h, w, 0, 0));
		center.setPreferredSize(new Dimension(w * 7, h * 12));
		center.setOpaque(false);

		// Initilize all of the text labels
		text = new JLabel[h][w];
		for (int i = 0; i < h; ++i) {
			text[i] = new JLabel[w];
			for (int j = 0; j < w; ++j) {
				text[i][j] = new JLabel(blank);
				center.add(text[i][j]);
			}
		}
		add(center);
		add(background);
		background.setBounds(0, 0, width, height);
		center.setBounds((width - w * 7) / 2, (height - h * 12) / 2, w * 7, h * 12);
	}

	// Text must be added afterwards and will be placed in character by
	// character at the given interval
	public void set_text(String str, int mills) {
		if (time != null)
			time.stop();
		time = new Timer(mills, this);
		source = str;
		if (!set_text_locations())
			return;
		index = 0;
		clear_text();
		time.start();
	}

	public void clear_text() {
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < text[i].length; ++j) {
				text[i][j].setIcon(blank);
			}
		}
	}

	// A helper function to determine which cell each letter will belong to
	private boolean set_text_locations() {
		index = row = col = 0;
		loc = new Dimension[source.length()];

		// Check each word to see if there is room on the current line
		String[] lst = source.split(" ");
		for (String word : lst) {
			// If there is set the locations and move on
			if (is_room_for_word(word)) {
				for (int i = 0; i < word.length(); ++i)
					loc[index++ ] = new Dimension(row, col++ );
				index++ ;
				col++ ;
			} else { // Otherwise move to the next row and do the same
				row++ ;
				col = 0;
				// If you are out of rows or the word is too big for the line,
				// quit.
				if (row == text.length || !is_room_for_word(word))
					return false;
				for (int i = 0; i < word.length(); ++i)
					loc[index++ ] = new Dimension(row, col++ );
				index++ ;
				col++ ;
			}
		}
		return true; // Report success to brethren
	}

	// Another helper function. Mostly for readability
	private boolean is_room_for_word(String word) {
		return text[row].length - col > word.length();
	}

	public void actionPerformed(ActionEvent e) {
		// Skip all spaces
		while (index < source.length() && source.charAt(index) == ' ')
			index++ ;
		if (index == source.length()) {
			time.stop();
			return;
		}
		Dimension location = loc[index];
		int id = PokedexSearchBar.all_valid_chars.indexOf("" + source.charAt(index++ ));
		text[location.width][location.height].setIcon(ImageLibrary.text[color][id]);
	}

	public static void main(String[] args) {
		GameState.initilize_all();
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int w = 300, h = 100;
		f.setBounds(600, 200, w, h);
		PokedexTextArea a = new PokedexTextArea(w, h);
		f.add(a);
		f.pack();
		f.setVisible(true);

		a.set_text("WARNING!!!! THIS IS FUCKING AWESOME. this is so freaking cool i wonder if the text wrapping is working. Ill just have to give it a spin:", 80);
	}
}
