package de.tigges.tournament.logic;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;

/**
 * This class contains all logic concerning a player
 * 
 * @author johannes
 */
public class PlayerLogic {

	/**
	 * calculate a player that must pause in the actual round
	 * <p>
	 * This is done by selecting the player who has the lowest number of paused
	 * rounds. If more than one players have the same lowest ruond number,
	 * a random player is chosen.
	 * 
	 * @param actualRound
	 * @param previousRounds all previous rounds
	 * @return id of the selected player who has to pause in the actual round
	 */
	public int calculatePausedPlayerId(Round actualRound, ObservableList<Round> previousRounds) {
		// must make a copy of the actual players, because the 'score' of each player 
		// now means 'number of paused rounds'
		ObservableList<Player> possiblePausedPlayers = createNew(actualRound.getPlayers());
		
		calculatePausedPlayerScores(possiblePausedPlayers, previousRounds);
		return selectPlayerWithLowestScore(possiblePausedPlayers);
	}
	
	/**
	 * adds a player to a team
	 * <p>
	 * <li>The new team player must be one player of <code>actual Round players</code>.
	 * <li>The new team player cannot play in another match of the actual round.
	 * Then we have a list of possible players.
	 * <p>
	 * With this list of possible partners, create a scored list. The score tells,
	 * how often the possible player has already been a team member in the previous rounds.
	 * <p>
	 * Select the partner with the lowest score = select the player who wasn't in the players
	 * team.
	 * 
	 * @param team
	 * @param availablePlayers
	 * @param previousRounds all previous rounds
	 * 
	 * @return the id of the selected partner
	 */
	public void addTeamPlayer(ObservableList<Player> team, ObservableList<Player> availablePlayers, ObservableList<Round> previousRounds) {
		if (team.isEmpty()) {
			team.add(RandomUtil.removeRandomElement(availablePlayers));
			return;
		}
		ObservableList<Player> scoredPlayers = createNew(availablePlayers);
		
		for (Round round: previousRounds) {
			for (Match match: round.getMatches()) {
				addTeamScore(team, scoredPlayers, match.getHomeTeam());
				addTeamScore(team, scoredPlayers, match.getAwayTeam());
			}
		}
		int newPlayerId = selectPlayerWithLowestScore(scoredPlayers);
		Player newPlayer = ListUtil.findAndRemove(availablePlayers, newPlayerId);
		team.add(newPlayer);
	}
		

	private void addTeamScore(ObservableList<Player> team, ObservableList<Player> availablePlayers,	ObservableList<Player> matchTeam) {
		for (Player player: availablePlayers) {
			if (matchTeam.contains(player)) {
				for (Player teamPlayer: team) {
					if (matchTeam.contains(teamPlayer)) {
						player.increaseScore(1);
					}
				}
			}
		}
	}

	/**
	 * calculate the 'paused player score' for a list of players.
	 * <p>
	 * every paused round increases the score by 1
	 * 
	 * @param possiblePausedPlayers
	 * @param previousRounds
	 */
	public void calculatePausedPlayerScores(ObservableList<Player> possiblePausedPlayers, ObservableList<Round> previousRounds) {
		for (Round round: previousRounds) {
			for (Player player: round.getPausedPlayers()) {
				Player p = ListUtil.find(possiblePausedPlayers, player.getId());
				if (p != null) {
					p.incrementScore();
				}
			}
		}
	}
	
	/**
	 * select the player in the given list with the lowest score value.
	 * <p>
	 * If there are more than one players with the same minimum score,
	 * a random player with minimum score is selected.
	 * <p>
	 * <strong>Warning</warning>
	 * The list will be destroyed in this method.
	 * 
	 * @param players
	 * @return id of the selected player
	 */
	private int selectPlayerWithLowestScore (ObservableList<Player> players) {
		// find the lowest score in the list
		int minScore = Integer.MAX_VALUE;
		for (Player player: players) {
			if (player.getScore() < minScore) {
				minScore = player.getScore();
			}
		}
		// remove all elements with a higher score
		Iterator<Player> iter = players.iterator();
		while (iter.hasNext()) {
			Player player = iter.next();
			if (player.getScore() > minScore) {
				iter.remove();
			}
		}
		// select a random element from the list
		return players.size() > 0 ? RandomUtil.getRandomElement(players).getId() : 0;
	}
	
	/**
	 * creates a new list of players
	 * <P>
	 * for each player in the given list, a new player with the same id
	 * is created in the new list.
	 * 
	 * @param list
	 * @return
	 */
	public ObservableList<Player> createNew(ObservableList<Player> list) {
		ObservableList<Player> newList = FXCollections.observableArrayList();
		for (Player player: list) {
			newList.add(new Player(player));
		}
		return newList;
	}
}
