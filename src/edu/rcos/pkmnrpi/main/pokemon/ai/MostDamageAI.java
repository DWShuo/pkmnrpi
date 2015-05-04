package edu.rcos.pkmnrpi.main.pokemon.ai;

import java.util.List;

import edu.rcos.pkmnrpi.main.pokemon.AI;
import edu.rcos.pkmnrpi.main.pokemon.Move;
import edu.rcos.pkmnrpi.main.pokemon.Pokemon;

/**
 * Selects the move that does the most damage.
 * @author Austin Gulati
 */
public class MostDamageAI implements AI {

	@Override
	public Move decide(Pokemon me, Pokemon enemy) {
		List<Move> moves = me.knownMoves;
		Move mostDamageMove = moves.get(0);
		for (Move move : moves) {
			if (getDamage(move, me, enemy)
					> getDamage(mostDamageMove, me, enemy)) {
				mostDamageMove = move;
			}
		}
		return mostDamageMove;
	}
	
	private int getDamage(Move move, Pokemon me, Pokemon enemy) {
		return move.damage;
	}

}
