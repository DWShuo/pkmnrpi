package Pokedex;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import util.ImageLibrary;
import util.panels.PatternPanel;

public class PokedexNameArea extends JLayeredPane implements PokedexUI {
	private int count;
	private JLabel[] labels;
	private JPanel center;
	private String source;
	public PatternPanel background = new PatternPanel(PatternPanel.NAME_AREA);
	public int color = ImageLibrary.black;

	public PokedexNameArea(int width, int height) {
		super();
		setPreferredSize(new Dimension(width, height));

		// Calculate cell count
		assert (width > 80);
		count = (width - 20 - 50) / 7 - 1;

		center = new JPanel();
		center.setPreferredSize(new Dimension(7 * count, 14));
		center.setLayout(new GridLayout(1, count, 0, 0));
		center.setOpaque(false);

		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(7 * 5, 14));
		left.setLayout(new GridLayout(1, 5, 0, 0));
		left.setOpaque(false);
		int ncolor = ImageLibrary.ICE;
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("N")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("a")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("m")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf("e")]));
		left.add(new JLabel(ImageLibrary.text[ncolor][valid_chars.indexOf(":")]));

		labels = new JLabel[count];
		for (int i = 0; i < count; ++i) {
			labels[i] = new JLabel(blank);
			center.add(labels[i]);
		}

		add(left, 1);
		add(center, 0);
		add(background, 2);

		background.setBounds(0, 0, width, height);
		left.setBounds(10, (height - 14) / 2, 7 * 5, 14);
		center.setBounds(60, (height - 14) / 2, count * 7, 14);

	}

	public void set_text(String str) {
		source = str;
		for (int i = 0; i < source.length(); ++i) {
			if (i > count)
				return;
			int id = PokedexSearchBar.valid_chars
					.indexOf("" + source.charAt(i));
			labels[i].setIcon(ImageLibrary.text[color][id]);
		}
	}

	public void clear_text() {
		for (JLabel l : labels)
			l.setIcon(blank);
	}
}
