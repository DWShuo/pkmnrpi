package pokemon;

import java.util.ArrayList;

// Data holder class for Pokemon
public class Stats {
	public int max_health, current_health, attack, defense, special_attack, special_defense, speed, level, exp, total_exp, happiness;

	public Stats() {}

	public Stats(Stats s) {
		max_health = s.max_health;
		current_health = s.current_health;
		attack = s.attack;
		defense = s.defense;
		special_attack = s.special_attack;
		special_defense = s.special_defense;
		speed = s.speed;
		level = s.level;
		exp = s.exp;
		total_exp = s.total_exp;
		happiness = s.happiness;
	}

	public Stats(int[] data) {
		int index = 0;
		max_health = data[index++ ];
		current_health = data[index++ ];
		attack = data[index++ ];
		defense = data[index++ ];
		special_attack = data[index++ ];
		special_defense = data[index++ ];
		speed = data[index++ ];
		level = data[index++ ];
		exp = data[index++ ];
		total_exp = data[index++ ];
		happiness = data[index++ ];
	}

	public Stats(ArrayList<String> data) {
		int index = 0;
		max_health = Integer.parseInt(data.get(index++ ));
		current_health = Integer.parseInt(data.get(index++ ));
		attack = Integer.parseInt(data.get(index++ ));
		defense = Integer.parseInt(data.get(index++ ));
		special_attack = Integer.parseInt(data.get(index++ ));
		special_defense = Integer.parseInt(data.get(index++ ));
		speed = Integer.parseInt(data.get(index++ ));
		level = Integer.parseInt(data.get(index++ ));
		exp = Integer.parseInt(data.get(index++ ));
		total_exp = Integer.parseInt(data.get(index++ ));
		happiness = Integer.parseInt(data.get(index++ ));
	}

	public int getTotalStatValue() {
		return max_health + attack + defense + special_attack + special_defense + speed;
	}

	public String toString() {
		String str = "\n";
		return max_health + str + current_health + str + attack + str + defense + str + special_attack + str + special_defense + str + speed + str + level + str + exp + str
				+ total_exp + str + happiness + str;
	}
}
