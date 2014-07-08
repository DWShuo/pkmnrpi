package pokedex;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import util.ImageLibrary;
import util.panels.PatternPanel;

public class PokedexFieldArea extends JLayeredPane implements PokedexUI {
	private JLabel[][] labels;
	private int count;
	private JPanel[] center;
	public PatternPanel background = new PatternPanel(PatternPanel.TEXT_AREA);
	public int color = ImageLibrary.black;

	public PokedexFieldArea(int width, int height, String[] fields) {
		super();
		setPreferredSize(new Dimension(width, height));

		// Calculate cell count
		count = (width - 14) / 7 - 3;
		int hgap = (width - 7 * count) / 2;
		int vgap = (height - 14 * fields.length) / (fields.length + 1);
		labels = new JLabel[fields.length][];
		center = new JPanel[fields.length];

		Dimension size = new Dimension(7 * count, 14);
		for (int i = 0; i < fields.length; ++i) {
			// Create field panel
			center[i] = new JPanel();
			center[i].setPreferredSize(size);
			center[i].setLayout(new GridLayout(1, count, 0, 0));
			center[i].setOpaque(false);

			// Staticly set the field name
			for (char c : fields[i].toCharArray())
				center[i].add(new JLabel(ImageLibrary.text[color][valid_chars
						.indexOf(c)]));
			center[i].add(new JLabel(blank));

			labels[i] = new JLabel[count - fields[i].length() - 1];
			for (int k = 0; k < labels[i].length; ++k) {
				labels[i][k] = new JLabel(blank);
				center[i].add(labels[i][k]);
			}
			add(center[i], i);
			center[i].setBounds(hgap, vgap + (vgap + 14) * i, count * 7, 14);
		}
		add(background, center.length);
		background.setBounds(0, 0, width, height);
	}

	public void set_all_text(String[] ary) {
		assert (ary.length == center.length);
		for (int i = 0; i < ary.length; ++i)
			for (int k = 0; k < ary[i].length(); ++k)
				if (k < labels[i].length)
					labels[i][k].setIcon(ImageLibrary.text[color][valid_chars
							.indexOf(ary[i].charAt(k))]);
	}

	public void set_text(String str, int index) {
		assert (index > 0);
		assert (index < center.length);
		for (int k = 0; k < str.length(); ++k)
			if (k < labels[index].length)
				labels[index][k].setIcon(ImageLibrary.text[color][valid_chars
						.indexOf(str.charAt(k))]);
	}

	public void clear_text() {
		for (JLabel[] ary : labels)
			for (JLabel l : ary)
				l.setIcon(blank);
	}
}
