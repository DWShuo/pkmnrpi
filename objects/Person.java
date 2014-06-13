package objects;

import java.util.ArrayList;

public class Person extends Thing{
	//private Pokemon[] my_pkmn;
	public String name;
	public ArrayList<String> dialog;

	public Person() {
	}

	public String toString() {
		return name;
	}

	public Sprite getSprite() {
		return null;
	}
}
