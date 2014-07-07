package console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.ImageLibrary;

/**
 * This is the menu bar class
 */
public class MEBar extends JMenuBar implements ActionListener {
	private MapEditor editor;
	private JMenuItem save, n, load, pen, bucket;
	private JFileChooser filer;
	private JLabel label;

	public MEBar(MapEditor e) {
		editor = e;
		filer = new JFileChooser();
		JMenu menu = new JMenu("File");
		JMenu tools = new JMenu("Tools");

		save = new JMenuItem("Save");
		n = new JMenuItem("New");
		load = new JMenuItem("Load");
		bucket = new JMenuItem("Bucket");
		pen = new JMenuItem("Pencil");
		label = new JLabel(ImageLibrary.blank);
		e.setBarLabel(label);

		add(menu);
		add(label);
		add(tools);

		menu.add(save);
		menu.add(n);
		menu.add(load);

		tools.add(pen);
		tools.add(bucket);

		save.addActionListener(this);
		n.addActionListener(this);
		load.addActionListener(this);
		pen.addActionListener(this);
		bucket.addActionListener(this);
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
		}
	}

}
