//Author: Tommy Fang
package Pokedex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pokedex extends JPanel {

	private static final String JTextField = null;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new Pokedex());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	public Pokedex() {
		JPanel center = new JPanel(), top = new JPanel(),bottom = new JPanel(), right = new JPanel(), middle = new JPanel(), 
				Name_ID = new JPanel(), Type = new JPanel(), 
				Race = new JPanel(), traits = new JPanel(), space = new JPanel();
		;
		JPanel portrait = new JPanel();
		bottom.setPreferredSize(new Dimension(112,56));
		top.setPreferredSize(new Dimension(112,56));
		portrait.setPreferredSize(new Dimension(56,56));
		middle.setPreferredSize(new Dimension(20,56));
		right.setPreferredSize(new Dimension(36,56));
		top.setBackground(Color.red);
		portrait.setBackground(Color.black);
		middle.setBackground(Color.blue);
		right.setBackground(Color.orange);
		bottom.setBackground(Color.green);
		
		JTextField search = new JTextField();
		this.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(2, 1));
		this.add(center, BorderLayout.CENTER);
		this.add(search, BorderLayout.NORTH);
		center.setLayout(new GridLayout(2, 1));
		// JPanel top = new JPanel();
		center.add(top);
		center.add(bottom);
		top.setLayout(new GridLayout(1,3));
		// JPanel right = new JPanel(), middle = new JPanel();
		top.add(portrait);
		top.add(middle);
		top.add(right);
		middle.setLayout(new GridLayout(2, 1));
		// JPanel traits = new JPanel();
		// JPanel space = new JPanel();
		//middle.add(space);
		//middle.add(traits);
		// JPanel Name_ID = new JPanel(),Type = new JPanel(),Race = new
		// JPanel();
		right.add(Name_ID);
		right.add(Type);
		right.add(Race);
	}

	public void paint(Graphics g) {
		super.paintComponents(g);
		Dimension d = this.getPreferredSize();
	};

}
