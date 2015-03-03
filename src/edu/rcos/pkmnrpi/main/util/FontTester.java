package edu.rcos.pkmnrpi.main.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class FontTester {

	public static interface Filter {
		public boolean isValid(String str);
	}

	private static class PokeFilter implements Filter {
		public PokeFilter() {}

		public boolean isValid(String str) {
			return str.contains("Poke");
		}
	}

	private static class Sample extends JPanel {
		public static final String test = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public int width = 600, height = 40;
		public Font font;

		public Sample(String name) {
			super();
			setPreferredSize(new Dimension(width, height));
			font = new Font(name, Font.TRUETYPE_FONT, 20);
		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			g.setFont(font);
			g.setColor(Color.black);
			g.drawString(font.getName() + " " + test, 10, 18);
		}
	}

	public FontTester(Filter filter) {
		ArrayList<String> names = new ArrayList<String>();
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			for (Font f : ge.getAllFonts())
				names.add(f.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		for (String name : names) {
			if (filter != null && !filter.isValid(name))
				continue;
			view.add(new Sample(name));
		}
		JScrollPane pane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JFrame f = new JFrame("Font Tester");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(pane);
		f.setBounds(100, 200, 600, 500);
		f.setMaximumSize(new Dimension(1000, 500));
		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("data/fonts/pfont.ttf")));
			for (Font f : ge.getAllFonts())
				System.out.println(f.getName());
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		new FontTester(new PokeFilter());
		new FontTester(null);
	}
}
