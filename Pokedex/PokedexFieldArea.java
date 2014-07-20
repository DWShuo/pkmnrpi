package pokedex;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import util.ImageLibrary;
import util.panels.PatternPanel;

public class PokedexFieldArea extends JLayeredPane implements PokedexUI {
	public TextGrid labels;
	private int count;
	public String[] source;
	private JPanel[] center;
	public PatternPanel background = new PatternPanel(PatternPanel.TEXT_AREA);
	public int color = ImageLibrary.BLACK;

	public PokedexFieldArea(int width, int height, String[] fields) {
		super();
		setPreferredSize(new Dimension(width, height));
		source = fields;

		// Calculate cell count
		count = width / TSIZE.width - 5;
		int hgap = (width - TSIZE.width * count) / 2;
		int vgap = (height - TSIZE.height * fields.length) / (fields.length + 1);
		labels = new TextGrid();
		labels.grid = new JLabel[fields.length][];
		labels.text = new char[fields.length][];
		center = new JPanel[fields.length];

		Dimension size = new Dimension(TSIZE.width * count, TSIZE.height);
		for (int i = 0; i < fields.length; ++i) {
			// Create field panel
			center[i] = new JPanel();
			center[i].setPreferredSize(size);
			center[i].setLayout(new GridLayout(1, count, 0, 0));
			center[i].setOpaque(false);

			labels.grid[i] = new JLabel[count - 1];
			labels.text[i] = new char[count - 1];
			for (int k = 0; k < labels.grid[i].length; ++k) {
				labels.grid[i][k] = new JLabel(ImageLibrary.blank);
				labels.text[i][k] = ' ';
				center[i].add(labels.grid[i][k]);
			}
			add(center[i], i);
			center[i].setBounds(hgap, vgap + (vgap + TSIZE.height) * i, count * TSIZE.width, TSIZE.height);
		}
		add(background, center.length);
		background.setBounds(0, 0, width, height);
		labels.setText(source);
	}

	public void setText(String[] ary) {
		ArrayList<String> t = new ArrayList<String>();
		for (int i = 0; i < ary.length; ++i) {
			t.add(source[i] + ary[i]);
		}
		labels.setText(t);
	}
}
