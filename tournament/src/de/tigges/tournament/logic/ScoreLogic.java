package de.tigges.tournament.logic;

import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;

/**
 * contains the logic to calculate the player scores.
 * 
 * @author johannes
 */
public class ScoreLogic {

	/**
	 * calculate all player scores for one tournament
	 * <p>
	 * <li>the initial score of a player is the handicap
	 * <li>for every match, a match score is added, see {@link #addMatchScore(Player, Match)}
	 * @param tournament
	 */
	public void calculateScores (Tournament tournament) {
		for (Player player: tournament.getPlayers()) {
			player.setScore(player.getHandicap());
			for (Round round: tournament.getRounds()) {
				for (Match match: round.getMatches()) {
					addMatchScore(player, match);
				}
			}
		}			
	}
	
	/**
	 * adds the score of one match to the score of a player
	 * <p>
	 * at the moment, this logic simply adds the match score 
	 * (home or away) to the player score, if the player is
	 * in the home or away team. 
	 *  
	 * ToDo: take into account the player handicaps?
	 * 
	 * @param player
	 * @param match
	 */
	public void addMatchScore (Player player, Match match) {
		int matchScore = 0;
		if (match.getHomeTeam().contains(player)) {
			matchScore = match.getHomeScore();
		} else if (match.getAwayTeam().contains(player)) {
			matchScore = match.getAwayScore();
		}
		if (matchScore != 0) {
			player.setScore(player.getScore() + matchScore);
		}
	}
}
