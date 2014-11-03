package util;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyMap {
	// the string is what KeyEvent.getKeyText(e.getKeyCode()); returns
	// the integer is the unique id of the event
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	private HashMap<Integer, String> revr = new HashMap<Integer, String>();
	// this is a list of all pressed keys.
	private ArrayList<Integer> pressed = new ArrayList<Integer>();

	public KeyMap() {
	}

	public boolean contains(String str) {
		return data.keySet().contains(str);
	}

	public boolean contains(int i) {
		return revr.keySet().contains(i);
	}

	public int get(String str) {
		return data.get(str);
	}

	public String get(int i) {
		return revr.get(i);
	}

	public void put(int i, String str) {
		data.put(str, i);
		revr.put(i, str);
	}

	public void press(String str) {
		press(data.get(str));
	}

	public void press(int i) {
		if (!pressed.contains(i))
			pressed.add(i);
	}

	public void release(String str) {
		release(data.get(str));
	}

	public void release(int i) {
		// Must cast int to Object, so that the correct remove function is
		// called
		// (value removal, not index removal)!!
		pressed.remove((Object) i);
	}

	public int pressCount() {
		return pressed.size();
	}
}
