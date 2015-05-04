package edu.rcos.pkmnrpi.main.pokemon.ai;

import java.util.List;

import edu.rcos.pkmnrpi.main.pokemon.AI;
import edu.rcos.pkmnrpi.main.pokemon.Move;
import edu.rcos.pkmnrpi.main.pokemon.Pokemon;

/**
 * Randomly selects a move to make.
 * @author Austin Gulati
 */
public class RandomAI implements AI {

	@Override
	public Move decide(Pokemon me, Pokemon enemy) {
		List<Move> moves = me.knownMoves;
		int i = (int) (Math.random() * moves.size());
		return moves.get(i);
	}

}
