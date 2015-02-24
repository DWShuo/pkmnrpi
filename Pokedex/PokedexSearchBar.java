package Pokedex;

import game.GameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import util.panels.PatternPanel;

/**
 * This class is in charge of listening to and animating the search bar of the pokedex.
 */
public class PokedexSearchBar extends JPanel implements KeyListener, PokedexUI, MouseListener, ActionListener {
	public static final int pixel_height = 22;
	public static final String default_message = "Click here to search";

	private int width;
	private Pokedex pokedex;
	private String current = "";
	public Timer time;
	public boolean flicker;

	public PokedexSearchBar(int width, Pokedex s) {
		super();
		this.width = width;
		time = new Timer(300, this);
		pokedex = s;
		setDefaultText();
		setPreferredSize(new Dimension(width, pixel_height));
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		PatternPanel.paintTextArea(g, width, pixel_height);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(current, 20, pixel_height - 4);
		int gap = 20 + g.getFontMetrics().stringWidth(current);
		if (flicker)
			g.fillRect(gap, 4, g.getFontMetrics().stringWidth("M"), g.getFontMetrics().getHeight());
	}

	public void keyPressed(KeyEvent e) {
		// Catch enter key
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// System.out.println("ENTER");
			if (pokedex == null)
				return;
			pokedex.search(current);
			loseFocus();
			repaint();
			return;
		}
		// Catch backspace key
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			// System.out.println("BACKSPACE");
			current = current.substring(0, current.length() - 1);
			repaint();
			return;
		}
		char c = e.getKeyChar();
		current += c;
		repaint();
	}

	// This gains the key focus from the game engine, as well
	// as clearing text, and starting the flicker
	public void setFocus() {
		pokedex.engine.focusPokedex();
		current = "";
		flicker = true;
		repaint();
		time.start();
	}

	// This returns key focus to the game board, stops the flicker, and
	// sets the default text on the search bar.
	public void loseFocus() {
		time.stop();
		flicker = false;
		addMouseListener(this);
		pokedex.engine.focusBoard();
		setDefaultText();
	}

	// This clears all current text, then writes the default message onto
	// the search bar.
	public void setDefaultText() {
		current = default_message;
		repaint();
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public static void main(String[] args) {
		new GameEngine();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		flicker = !flicker;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.removeMouseListener(this);
		setFocus();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
