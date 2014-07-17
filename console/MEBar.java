package console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import util.ImageLibrary;

/**
 * This is the menu bar class
 */
public class MEBar extends JMenuBar implements ActionListener {
	private MapEditor editor;
	private JMenuItem save, n, load, pen, bucket, doorflag, textflag, center;
	private JFileChooser filer;
	private JLabel label;

	public MEBar(MapEditor e) {
		editor = e;
		filer = new JFileChooser();
		JMenu menu = new JMenu("File");
		JMenu tools = new JMenu("Tools");
		JMenu flags = new JMenu("Flags");

		save = new JMenuItem("Save");
		n = new JMenuItem("New");
		load = new JMenuItem("Load");
		bucket = new JMenuItem("Bucket");
		pen = new JMenuItem("Pencil");
		label = new JLabel(ImageLibrary.blank);
		e.setBarLabel(label);
		doorflag = new JMenuItem("Door");
		textflag = new JMenuItem("Text");
		center = new JMenuItem("Center");

		add(menu);
		add(label);
		add(tools);
		add(flags);

		menu.add(save);
		menu.add(n);
		menu.add(load);

		tools.add(pen);
		tools.add(bucket);

		flags.add(doorflag);
		flags.add(textflag);
		flags.add(center);

		save.addActionListener(this);
		n.addActionListener(this);
		load.addActionListener(this);
		pen.addActionListener(this);
		bucket.addActionListener(this);
		doorflag.addActionListener(this);
		textflag.addActionListener(this);
		center.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			if (filer.showOpenDialog(editor) == JFileChooser.APPROVE_OPTION) {
				editor.save(filer.getSelectedFile());
			}
		} else if (e.getSource() == n) {
			editor.clearAll();
		} else if (e.getSource() == load) {
			if (filer.showSaveDialog(editor) == JFileChooser.APPROVE_OPTION) {
				editor.load(filer.getSelectedFile());
			}
		} else if (e.getSource() == pen) {
			editor.bucketfill = false;
		} else if (e.getSource() == bucket) {
			editor.bucketfill = true;
		} else if (e.getSource() == doorflag) {
			editor.flagged = 1;
		} else if (e.getSource() == textflag) {
			editor.flagged = 2;
			String str = (String) JOptionPane.showInputDialog(editor, "", "Custom Text Flag", JOptionPane.PLAIN_MESSAGE, ImageLibrary.icons[1459], null, "This is a sign.");

			if ((str != null) && (str.length() > 0)) {
				editor.clipboard = str;
			}
		} else if (e.getSource() == center) {
			editor.flagged = 3;
		}
	}

}
