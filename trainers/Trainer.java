package trainers;

import pokemon.Pokemon;

public abstract class Trainer extends Person {
	public static Trainer[] all_trainers;
	
	private Pokemon[] pkmn;
	
	public static Trainer resolveSubClass(String name) {
		if(name.toLowerCase() == "firebreather")
			return new Firebreather();
		return null;
	}

	public Pokemon get_first_pokemon() {
		Pokemon pk = null;
		for (Pokemon p : pkmn)
			if (p.stats.current_health > 0) {
				pk = p;
				break;
			}
		return pk;
	}
	
	public static void init() {
		
	}
}
