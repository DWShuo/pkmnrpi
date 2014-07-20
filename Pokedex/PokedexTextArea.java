// @author Theodore Tenedorio

package pokedex;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.ImageLibrary;
import util.panels.PatternPanel;

// This is a class that acts like a text area using pre-defined icons to draw the fonts.
public class PokedexTextArea extends JLayeredPane implements PokedexUI {
	public PatternPanel background = new PatternPanel(PatternPanel.TEXT_AREA);

	public TextGrid text;
	private JPanel center;
	public int color = ImageLibrary.BLACK;

	// The dimensions of the area cannot change, and must be passed to the
	// constructor.
	public PokedexTextArea(int width, int height) {
		super();
		setPreferredSize(new Dimension(width, height));

		// Calculate the number of text cells
		int w = width / TSIZE.width - 5;
		int h = height / TSIZE.height - 2;

		center = new JPanel();
		center.setLayout(new GridLayout(h, w, 0, 0));
		center.setPreferredSize(new Dimension(w * TSIZE.width, h * TSIZE.height));
		center.setOpaque(false);

		// Initilize all of the text labels
		text = new TextGrid();
		text.grid = new JLabel[h][w];
		text.text = new char[h][w];
		for (int i = 0; i < h; ++i) {
			text.grid[i] = new JLabel[w];
			text.text[i] = new char[w];
			for (int j = 0; j < w; ++j) {
				text.grid[i][j] = new JLabel(ImageLibrary.blank);
				text.text[i][j] = ' ';
				center.add(text.grid[i][j]);
			}
		}
		add(center, 0);
		add(background, 1);
		background.setBounds(0, 0, width, height);
		center.setBounds((width - w * TSIZE.width) / 2, (height - h * TSIZE.height) / 2, w * TSIZE.width, h * TSIZE.height);
	}
}
