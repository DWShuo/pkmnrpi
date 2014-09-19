package objects;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LayeredPanel extends JPanel {
	private static final ImageIcon def = new ImageIcon("src/bliss.jpg");
	private Dimension pref = new Dimension(400, 300);

	private static class ClearPanel extends JPanel {
		public ClearPanel() {
			super();
			setOpaque(false);
		}
	}

	public JComponent background;
	public JPanel foreground;

	public LayeredPanel() {
		setLayout(null);
		background = new JLabel(def);
		((JLabel) background).setVerticalAlignment(JLabel.TOP);
		((JLabel) background).setHorizontalAlignment(JLabel.LEFT);
		setForeground(new ClearPanel());
	}

	public LayeredPanel(JPanel a, JPanel b) {
		setLayout(null);
		background = b;
		foreground = a;
	}

	public LayeredPanel(JPanel p) {
		setLayout(null);
		background = new JLabel(def);
		((JLabel) background).setVerticalAlignment(JLabel.TOP);
		((JLabel) background).setHorizontalAlignment(JLabel.LEFT);
		setForeground(p);
	}

	public LayeredPanel(Icon i) {
		setLayout(null);
		background = new JLabel(i);
		((JLabel) background).setVerticalAlignment(JLabel.TOP);
		((JLabel) background).setHorizontalAlignment(JLabel.LEFT);
		setForeground(new ClearPanel());
	}

	public LayeredPanel(Icon i, JPanel p) {
		setLayout(null);
		background = new JLabel(i);
		((JLabel) background).setVerticalAlignment(JLabel.TOP);
		((JLabel) background).setHorizontalAlignment(JLabel.LEFT);
		setForeground(p);
	}

	public LayeredPanel(Dimension d) {
		setLayout(null);
		pref = d;
	}

	public void setBackground(Icon i) {
		if (background instanceof JLabel)
			((JLabel) background).setIcon(i);
	}

	public void setForeground(JPanel p) {
		foreground = p;
		setPreferredSize(pref);
	}

	public void setPreferredSize(Dimension d) {
		if (d == null)
			d = new Dimension(0, 0);
		super.setPreferredSize(d);
		pref = d;
		foreground.setBounds(0, 0, d.width, d.height);
		background.setBounds(0, 0, d.width, d.height);
		removeAll();
		add(foreground);
		add(background);
		foreground.setBounds(0, 0, d.width, d.height);
		background.setBounds(0, 0, d.width, d.height);
	}
}
