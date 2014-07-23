package battle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import pokedex.TextGrid;
import util.panels.PatternPanel;

public class BattleText extends JPanel {
	public int state;
	public BattleEngine engine;

	public BattleText(BattleEngine e) {
		engine = e;
		setBackground(Color.white);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		PatternPanel.paintBattleArea(g, 400, 100);
		g.setColor(Color.black);
		g.setFont(new Font("Pokemon GB", Font.TRUETYPE_FONT, 16));
		if (state == 0) {
			challenge(g);
		}
	}

	public void challenge(Graphics g) {
		wrapText(g, "A wild " + engine.enemy.name.toUpperCase() + " wants to battle!", 15, 30, 23);
	}

	public static void wrapText(Graphics g, String str, int x, int y, int n) {
		ArrayList<String> text = new ArrayList<String>();
		String line = tokenizer(str, n, ' ');
		while (!line.isEmpty()) {
			text.add(line);
			str = TextGrid.clipToken(str, line);
			line = tokenizer(str, n, ' ');
		}
		int gap = 25, offset = 5;
		for (int i = 0; i < text.size(); ++i)
			g.drawString(text.get(i), 15, offset + gap * (i + 1));
	}

	public static String tokenizer(String str, int max, char spacer) {
		String all = "", line = TextGrid.tokenize(str, spacer);
		while (all.length() + line.length() <= max) {
			all += line;
			str = TextGrid.clipToken(str, line);
			line = TextGrid.tokenize(str, spacer);
			if (line.isEmpty())
				return all;
		}
		return all;
	}

}
