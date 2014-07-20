package pokedex;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import util.ImageLibrary;
import util.panels.PatternPanel;

public class PokedexNameArea extends JLayeredPane implements PokedexUI {
	private int count;
	public TextGrid labels;
	private JPanel center;
	private String source;
	public PatternPanel background = new PatternPanel(PatternPanel.NAME_AREA);
	public int color = ImageLibrary.BLACK;

	public PokedexNameArea(int width, int height) {
		super();
		setPreferredSize(new Dimension(width, height));

		// Calculate cell count
		count = (width - 20 - 50) / TSIZE.width - 1;

		center = new JPanel();
		center.setPreferredSize(new Dimension(TSIZE.width * count, TSIZE.height));
		center.setLayout(new GridLayout(1, count, 0, 0));
		center.setOpaque(false);

		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(TSIZE.width * 5, TSIZE.height));
		left.setLayout(new GridLayout(1, 5, 0, 0));
		left.setOpaque(false);
		int ncolor = ImageLibrary.ICE;
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("N")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("a")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("m")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("e")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf(":")]));

		labels = new TextGrid();
		labels.grid = new JLabel[1][count];
		labels.text = new char[1][count];
		for (int i = 0; i < count; ++i) {
			labels.grid[0][i] = new JLabel(ImageLibrary.blank);
			labels.text[0][i] = ' ';
			center.add(labels.grid[0][i]);
		}

		add(left, 1);
		add(center, 0);
		add(background, 2);

		background.setBounds(0, 0, width, height);
		left.setBounds(10, (height - TSIZE.height) / 2, TSIZE.width * 5, TSIZE.height);
		center.setBounds(60, (height - TSIZE.height) / 2, count * TSIZE.width, TSIZE.height);

	}
}
