// @author Theodore Tenedorio

package Pokedex;

import game.GameState;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.ImageLibrary;
import util.panels.PatternPanel;

// This is a class that acts like a text area using pre-defined icons to draw the fonts.
public class PokedexTextArea extends JLayeredPane implements ActionListener, ComponentListener {
	public static final ImageIcon blank = PokedexSearchBar.blank;
	public PatternPanel background = new PatternPanel(PatternPanel.TEXT_AREA);

	private int row, col, index, width, height, w, h;
	private Dimension[] loc;
	private JLabel[][] text;
	private JPanel center;
	private String source = "";
	private Timer time;
	public int color = ImageLibrary.black;

	// The dimensions of the area cannot change, and must be passed to the
	// constructor.
	public PokedexTextArea(int width, int height) {
		super();
		setPreferredSize(new Dimension(width, height));
		this.width = width;
		this.height = height;

		// Calculate the number of text cells
		w = width / 7 - 5;
		h = height / 12 - 2;

		center = new JPanel();
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
		add(center, 0);
		add(background, 1);
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
			if(word == "*") {
				row++;
				col = 0;
			// If there is set the locations and move on
			} else if (is_room_for_word(word)) {
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
		source = source.replace('*', ' ');
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

	public void componentHidden(ComponentEvent arg0) {}

	public void componentMoved(ComponentEvent arg0) {}

	public void componentResized(ComponentEvent arg0) {
		width = getWidth();
		height = getHeight();
		background.setBounds(0, 0, width, height);
		center.setBounds((width - w * 7) / 2, (height - h * 12) / 2, w * 7, h * 12);
	}

	public void componentShown(ComponentEvent arg0) {}
}
