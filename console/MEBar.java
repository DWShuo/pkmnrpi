package console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.ImageLibrary;

public class MEBar extends JMenuBar implements ActionListener {
	private MapEditor editor;
	private JMenuItem save, n, load;
	private JFileChooser filer;
	private JLabel label;

	public MEBar(MapEditor e) {
		editor = e;
		filer = new JFileChooser();
		JMenu menu = new JMenu("File");

		save = new JMenuItem("Save");
		n = new JMenuItem("New (Broken)");
		load = new JMenuItem("Load");
		label = new JLabel(ImageLibrary.blank);
		e.set_bar_label(label);

		add(menu);
		add(label);

		menu.add(save);
		menu.add(n);
		menu.add(load);

		save.addActionListener(this);
		n.addActionListener(this);
		load.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			if (filer.showOpenDialog(editor) == JFileChooser.APPROVE_OPTION) {
				editor.save(filer.getSelectedFile());
			}
		} else if (e.getSource() == n) {
			editor.clear_all();
		} else if (e.getSource() == load) {
			if (filer.showSaveDialog(editor) == JFileChooser.APPROVE_OPTION) {
				editor.load(filer.getSelectedFile());
			}
		}
	}

}
