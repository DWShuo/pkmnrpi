package game;

import java.util.ArrayList;

import pokemon.Pokemon;

public class ScoreCard {
	public int damage_done, damage_taken, battles_won, battles_lost;
	public int trainers_defeated, trianers_lost;
	public double hours_played;
	public ArrayList<String> victory = new ArrayList<String>();
	public ArrayList<String> defeat = new ArrayList<String>();
	public ArrayList<String> badges = new ArrayList<String>();

	public ScoreCard() {}

	public ScoreCard(ArrayList<String> data) {
		int index = 0;
		damage_done = Integer.parseInt(data.get(index++ ));
		damage_taken = Integer.parseInt(data.get(index++ ));
		battles_won = Integer.parseInt(data.get(index++ ));
		battles_lost = Integer.parseInt(data.get(index++ ));
		trainers_defeated = Integer.parseInt(data.get(index++ ));
		trianers_lost = Integer.parseInt(data.get(index++ ));
		hours_played = Double.parseDouble(data.get(index++ ));
		String line = data.get(index++ );
		while (!Pokemon.isUniform(line, '+')) {
			victory.add(line);
			line = data.get(index++ );
		}
		line = data.get(index++ );
		while (!Pokemon.isUniform(line, '+')) {
			defeat.add(line);
			line = data.get(index++ );
		}
		line = data.get(index++ );
		while (!Pokemon.isUniform(line, '+')) {
			badges.add(line);
			line = data.get(index++ );
		}
		line = data.get(index++ );
	}

	public String toString() {
		String s = "\n", all = damage_done + s + damage_taken + s;
		all += battles_won + s + battles_lost + s + trainers_defeated + s;
		all += trianers_lost + s + hours_played + s;
		for (String str : victory)
			all += str + s;
		all += "++++++++++++\n";
		for (String str : defeat)
			all += str + s;
		all += "++++++++++++\n";
		for (String str : badges)
			all += str + s;
		all += "++++++++++++\n";
		return all;
	}

}
