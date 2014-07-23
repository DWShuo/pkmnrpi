package pokedex;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.LayeredPanel;

public class Portrait extends JPanel implements PokedexUI {
	public LayeredPanel pan;
	private JPanel panel;
	private JLabel label;
	public int width, height;

	public Portrait(int x, int y) {
		width = x;
		height = y;
		Dimension d = new Dimension(x, y);
		setSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		panel = new JPanel();
		panel.setOpaque(false);
		pan = new LayeredPanel(new ImageIcon(portrait_icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)), panel);
		add(pan);
		setIcon(portrait_icon);
	}

	public void setIcon(ImageIcon i) {
		Image image = i.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		label = new JLabel(new ImageIcon(image));
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		label.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		panel.removeAll();
		panel.add(label);
		repaint();
	}
}
