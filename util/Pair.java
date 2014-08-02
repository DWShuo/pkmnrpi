package util;

public class Pair<T1, T2, T3> implements Comparable<Pair<T1, T2, T3>> {
	public T1 a;
	public T2 b;
	public T3 c;

	public Pair(T1 x, T2 y, T3 z) {
		a = x;
		b = y;
		c = z;
	}

	public String toString() {
		return a.toString() + ":" + b.toString() + ":" + c.toString();
	}

	@Override
	public int compareTo(Pair<T1, T2, T3> p) {
		return a.equals(p.a) && b.equals(p.b) && c.equals(p.c) ? 0 : 1;
	}
}
