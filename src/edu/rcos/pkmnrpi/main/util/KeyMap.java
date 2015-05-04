package edu.rcos.pkmnrpi.main.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyMap <A, B> {
	// the string is what KeyEvent.getKeyText(e.getKeyCode()); returns
	// the integer is the unique id of the event
	private Map<A, B> data = new HashMap<A, B>();
	private Map<B, A> receiver = new HashMap<B, A>();
	// this is a list of all pressed keys.
	private List<B> pressed = new ArrayList<B>();

	public KeyMap() {
	}

	public boolean containsA(A a) {
		return data.keySet().contains(a);
	}

	public boolean containsB(B b) {
		return receiver.keySet().contains(b);
	}

	public A getA(B b) {
		return receiver.get(b);
	}

	public B getB(A a) {
		return data.get(a);
	}

	public void put(A a, B b) {
		data.put(a, b);
		receiver.put(b, a);
	}

	public void pressA(A a) {
		pressB(data.get(a));
	}

	public void pressB(B b) {
		if (!pressed.contains(b))
			pressed.add(b);
	}

	public void releaseA(A a) {
		releaseB(data.get(a));
	}

	public void releaseB(B b) {
		// Must cast int to Object, so that the correct remove function is
		// called
		// (value removal, not index removal)!!
		pressed.remove((Object) b);
	}

	public int pressCount() {
		return pressed.size();
	}
}
