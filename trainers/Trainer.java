package trainers;

import java.util.ArrayList;

import objects.Point;
import objects.TileMap;
import pokemon.Pokemon;
import pokemon.Stats;
import util.FileParser;

public class Trainer extends Person {
	public static ArrayList<Trainer> all_trainers = new ArrayList<Trainer>();

	public String victory_outro, defeat_outro;
	public ArrayList<Point> vision = new ArrayList<Point>();
	public ArrayList<Pokemon> team = new ArrayList<Pokemon>();
	private ArrayList<String> team_data = new ArrayList<String>();

	public Trainer() {}

	public Trainer(String name) {
		Trainer t = lookUp(name);
		name = t.name;
		male = t.male;
		cash = t.cash;
		intro = t.intro;
		victory_outro = t.victory_outro;
		defeat_outro = t.defeat_outro;
		mapname = t.mapname;
		x = t.x;
		y = t.y;
		direction = t.direction;
		dialog = t.dialog;
		team = t.team;
		vision = t.vision;
	}

	public boolean canBattle() {
		for (Pokemon p : team) {
			if (p.stats.state != Stats.FAINT)
				return true;
		}
		return false;
	}

	public String staticToString() {
		String space = "+++++++++++\n", s = "\n", all = "";
		all += name + s + (male ? "Male" : "Female") + s + cash + s;
		all += intro + s + victory_outro + s + defeat_outro + s;
		all += mapname + s + x + "," + y + s + direction + s;
		for (String str : dialog)
			all += str + s;
		all += space + space;
		return all;
	}

	public static ArrayList<Trainer> loadTrainers(ArrayList<String> data) {
		ArrayList<Trainer> all = new ArrayList<Trainer>();
		int index = 0;
		while (index < data.size()) {
			Trainer t = new Trainer(data.get(index++ ));
			t.mapname = data.get(index++ );
			t.map = TileMap.MAPS.get(t.mapname);
			String[] ary = data.get(index++ ).split(",");
			t.x = Integer.parseInt(ary[0]);
			t.y = Integer.parseInt(ary[1]);
			t.setDirection(data.get(index++ ));
			String line = data.get(index++ );
			while (!Pokemon.isUniform(line, '+')) {
				String[] a = line.split(",");
				Point p = new Point(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
				t.vision.add(p);
				line = data.get(index++ );
			}
			++index;
			all.add(t);
		}
		return all;
	}

	public Pokemon get_first_pokemon() {
		for (Pokemon p : team)
			if (p.stats.current_health > 0)
				return p;
		return null;
	}

	public String toString() {
		String str = "\n", all = name + str + mapname + str + x + "," + y + str + direction + str;
		for (Point p : vision)
			all += p.x + "," + p.y + str;
		return all + "+++++++++++++\n";
	}

	public static Trainer lookUp(String name) {
		for (Trainer t : all_trainers)
			if (t.name.equalsIgnoreCase(name))
				return t;
		return null;
	}

	public static void init() {
		ArrayList<String> data = FileParser.parseFile("src/data/Trainer_Data.txt");
		Trainer t;
		int index = 0;
		while (index < data.size()) {
			t = new Trainer();
			t.name = data.get(index++ );
			t.male = data.get(index++ ).equalsIgnoreCase("male");
			t.cash = Integer.parseInt(data.get(index++ ));
			t.intro = data.get(index++ );
			t.victory_outro = data.get(index++ );
			t.defeat_outro = data.get(index++ );
			t.mapname = data.get(index++ );
			String[] ary = data.get(index++ ).split(",");
			t.x = Integer.parseInt(ary[0]);
			t.y = Integer.parseInt(ary[1]);
			t.setDirection(data.get(index++ ));
			String line = data.get(index++ );
			while (!Pokemon.isUniform(line, '+')) {
				t.dialog.add(line);
				line = data.get(index++ );
			}
			line = data.get(index++ );
			while (!Pokemon.isUniform(line, '+')) {
				t.team_data.add(line);
				line = data.get(index++ );
			}
			all_trainers.add(t);
		}
	}

	public void reloadPokemon() {
		team = Pokemon.loadPokemon(team_data);
	}
}
