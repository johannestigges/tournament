package de.tigges.tournament;

import de.tigges.tournament.logic.RandomUtil;
import de.tigges.tournament.logic.RoundLogic;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;

public class TournamentFactory {

	public static Tournament create (int players, int rounds) {
		Tournament tournament = new Tournament();
		for (int i=0; i < players; i++) {
			tournament.getPlayers().add(new Player("Player " + i));
		}
		for (int i=0; i < rounds; i++) {
			new RoundLogic().createRoundComplete(tournament);
		}
		for (Round round: tournament.getRounds()) {
			for (Match match: round.getMatches()) {
				match.setScore(RandomUtil.nextInt(3), RandomUtil.nextInt(3));
			}
		}
		return tournament;
	}
}
