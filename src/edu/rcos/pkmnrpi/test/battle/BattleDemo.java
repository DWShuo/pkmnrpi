package edu.rcos.pkmnrpi.test.battle;

import java.util.Arrays;
import java.util.List;

import edu.rcos.pkmnrpi.main.game.GameState;
import edu.rcos.pkmnrpi.main.pokemon.AI;
import edu.rcos.pkmnrpi.main.pokemon.Move;
import edu.rcos.pkmnrpi.main.pokemon.Pokemon;
import edu.rcos.pkmnrpi.main.pokemon.ai.MostDamageAI;
import edu.rcos.pkmnrpi.main.pokemon.ai.MostDamageChanceAndTypeAI;
import edu.rcos.pkmnrpi.main.util.FileParser;

/**
 * Not an actual test, but rather a program you can run
 * to simulate a battle. For manual testing purposes
 * @author Austin Gulati
 */
public class BattleDemo {
	
	private static final String MOVES_PATH = "data/game/move_info.csv";
	private static final String POKEMON_PATH = "";  // TODO: make initPokemon() take this
	private static final AI firstAI = new MostDamageChanceAndTypeAI();
	private static final AI secondAI = new MostDamageAI();
	private static final boolean DEBUG = false;
	private static final int TOTAL = 10000;
	
	public static void main(String[] args) {
		// Load game state
		// Would like to split this out if possible
		GameState.initMoves(FileParser.parseFile(MOVES_PATH));
		GameState.initPokemon();
		List<Pokemon> pokemon = Arrays.asList(GameState.POKEMON);
		int firstWins = 0;
		int ties = 0;
		
		for (int i = 0; i < TOTAL; ++i) {
			Pokemon firstSpecies = pokemon.get((int) (Math.random() * 16));
			Pokemon first = new Pokemon(firstSpecies.name, 25);
			Pokemon secondSpecies = pokemon.get((int) (Math.random() * 16));
			Pokemon second = new Pokemon(secondSpecies.name, 25);
		
			while (first.stats.current_health > 0 && second.stats.current_health > 0) {
				if (DEBUG) System.out.println(formatShort(first) + " vs " + formatShort(second));
				
				// Much of this logic should be made available
				Move firstMove = firstAI.decide(first, second);
				Move secondMove = secondAI.decide(second, first);
				
				Move firstToAct = firstMove;
				if (firstMove.speed_priority > secondMove.speed_priority) {
					firstToAct = firstMove;
				} else if (secondMove.speed_priority > firstMove.speed_priority) {
					firstToAct = secondMove;
				} else if (first.stats.speed > second.stats.speed) {
					firstToAct = firstMove;
				} else {
					firstToAct = secondMove;
				}
				
				if (firstToAct == firstMove) {
					makeMove(first, firstMove, second);
					makeMove(second, secondMove, first);
				} else {
					makeMove(second, secondMove, first);
					makeMove(first, firstMove, second);
				}
			}
			
			if (first.stats.current_health <= 0 && second.stats.current_health <= 0) {
				if (DEBUG) System.out.println("Tie!");
				ties++;
			} else if (first.stats.current_health <= 0) {
				if (DEBUG) System.out.println("Winner (second): " + formatShort(second));
			} else {
				if (DEBUG) System.out.println("Winner (first): " + formatShort(first));
				firstWins++;
			}
			
			if (DEBUG) System.out.println("---");
		}
		
		System.out.println(firstWins + "-" + ties + "-" + (TOTAL-firstWins-ties));
	}
	
	private static String formatShort(Pokemon pokemon) {
		return pokemon.name + " (lvl:" + pokemon.stats.level + ")" + 
			" (hp:" + pokemon.stats.current_health + "/" +
				pokemon.stats.max_health + ")";
	}
	
	private static void makeMove(Pokemon attacker, Move move, Pokemon defender) {
		if (DEBUG) System.out.println(attacker.name + " uses " + move.name + " on " + defender.name);
		
		double bonus = Pokemon.typeModifier(defender, move);
		double crit = Pokemon.critRoll(move);
		int damage = Pokemon.calculateDamage(attacker, defender, move, crit, bonus);
		if (DEBUG) System.out.println("It deals " + damage + " damage!");
		defender.stats.current_health -= damage;
	}
	
}
