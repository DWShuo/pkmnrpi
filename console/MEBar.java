package console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.ImageLibrary;

/**
 * This is the menu bar class
 */
public class MEBar extends JMenuBar implements ActionListener {
	private MapEditor editor;
	private JMenuItem save, n, load, pen, bucket, doorflag, textflag, center, resize, walk, off, show, derez;
	private JFileChooser filer;
	private JLabel label;
	private JMenu menu, tools, flags;

	public MEBar(MapEditor e) {
		editor = e;
		filer = new JFileChooser();
		filer.setCurrentDirectory(new File("src/maps"));
		menu = new JMenu("File");
		tools = new JMenu("Tools");
		flags = new JMenu("Flags");

		save = new JMenuItem("Save");
		n = new JMenuItem("New");
		load = new JMenuItem("Load");
		bucket = new JMenuItem("Bucket");
		pen = new JMenuItem("Pencil");
		label = new JLabel(ImageLibrary.getIcon(editor.paint_bucket));
		e.setBarLabel(label);
		doorflag = new JMenuItem("Door");
		textflag = new JMenuItem("Text");
		center = new JMenuItem("Center");
		resize = new JMenuItem("Resize");
		walk = new JMenuItem("Set Walk");
		off = new JMenuItem("Walk OFF");
		show = new JMenuItem("Display Selection");
		derez = new JMenuItem(new ImageIcon("src/tilesets/sprites/derez.png"));

		add(menu);
		add(label);
		add(tools);
		add(flags);
		add(derez);

		menu.add(save);
		menu.add(n);
		menu.add(load);
		menu.add(resize);
		menu.add(show);

		tools.add(pen);
		tools.add(bucket);

		flags.add(doorflag);
		flags.add(textflag);
		flags.add(center);
		flags.add(walk);

		save.addActionListener(this);
		n.addActionListener(this);
		load.addActionListener(this);
		pen.addActionListener(this);
		bucket.addActionListener(this);
		doorflag.addActionListener(this);
		textflag.addActionListener(this);
		center.addActionListener(this);
		resize.addActionListener(this);
		walk.addActionListener(this);
		off.addActionListener(this);
		show.addActionListener(this);
		derez.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			if (filer.showOpenDialog(editor) == JFileChooser.APPROVE_OPTION) {
				editor.save(filer.getSelectedFile());
			}
		} else if (e.getSource() == n) {
			String str = (String) JOptionPane.showInputDialog(editor, "", "New Map Name", JOptionPane.PLAIN_MESSAGE, ImageLibrary.getIcon("Building", 6, 4), null, "Name");

			if ((str != null) && (str.length() > 0))
				editor.clearAll(str);
		} else if (e.getSource() == load) {
			if (filer.showSaveDialog(editor) == JFileChooser.APPROVE_OPTION) {
				editor.load(filer.getSelectedFile());
			}
		} else if (e.getSource() == pen) {
			editor.bucketfill = false;
		} else if (e.getSource() == bucket) {
			editor.bucketfill = true;
		} else if (e.getSource() == doorflag) {
			JTextField xField = new JTextField(5);
			xField.setText("0");
			JTextField yField = new JTextField(5);
			yField.setText("0");
			JTextField zField = new JTextField(5);
			zField.setText("" + editor.tmap.mapdata[0].length);

			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			myPanel.add(new JLabel("Enter an exit Map name"));
			myPanel.add(zField);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Enter an X offset"));
			myPanel.add(yField);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Enter a Y offset"));
			myPanel.add(xField);

			int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				int x = Integer.parseInt(xField.getText());
				int y = Integer.parseInt(yField.getText());
				editor.doorx = x;
				editor.doory = y;
				editor.clipboard = zField.getText();
				editor.repaint();
				editor.creation.repaint();
			}
		} else if (e.getSource() == textflag) {
			String str = (String) JOptionPane.showInputDialog(editor, "", "Custom Text Flag", JOptionPane.PLAIN_MESSAGE, ImageLibrary.getIcon("Building", 6, 4), null,
					"This is a sign.");

			if ((str != null) && (str.length() > 0)) {
				editor.clipboard = str;
				editor.creation.flag = 2;
			}
		} else if (e.getSource() == center) {
			editor.creation.flag = 1;
		} else if (e.getSource() == resize) {
			JTextField xField = new JTextField(5);
			xField.setText("0");
			JTextField yField = new JTextField(5);
			yField.setText("0");
			JTextField aField = new JTextField(5);
			aField.setText("" + editor.tmap.mapdata[0].length);
			JTextField bField = new JTextField(5);
			bField.setText("" + editor.tmap.mapdata.length);

			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			myPanel.add(new JLabel("Enter new WIDTH"));
			myPanel.add(aField);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Enter new HEIGHT"));
			myPanel.add(bField);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Enter an X OFFSET"));
			myPanel.add(xField);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Enter a Y OFFSET"));
			myPanel.add(yField);

			int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				int x = Integer.parseInt(xField.getText());
				int y = Integer.parseInt(yField.getText());
				int a = Integer.parseInt(aField.getText());
				int b = Integer.parseInt(bField.getText());
				editor.tmap.resize(x, y, a, b);
				editor.creation.background.lastupdate = 0;
				editor.creation.background.repaint();
				// editor.repaint();
				// editor.creation.repaint();
			}
		} else if (e.getSource() == walk) {
			for (SelectWindow a : editor.selectwindows) {
				a.flag = 1;
			}
			flags.remove(walk);
			flags.add(off);
		} else if (e.getSource() == off) {
			for (SelectWindow a : editor.selectwindows) {
				a.flag = 0;
			}
			flags.remove(off);
			flags.add(walk);
		} else if (e.getSource() == show) {
			editor.displaySelections();
		}
		if (e.getSource() == derez) {
			System.gc();
		}
	}

}
