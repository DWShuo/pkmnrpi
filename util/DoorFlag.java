package util;

public class DoorFlag extends Flag {
	public int dx, dy;
	public String destination;

	public DoorFlag(String data) {
		super(data);
		String[] ary = extra.split(":");
		dx = Integer.parseInt(ary[0]);
		dy = Integer.parseInt(ary[1]);
		destination = ary[2];
	}

	public String toString() {
		return mapname + "," + x + "," + y + "," + type + "," + dx + ":" + dy + ":" + destination;
	}
}
