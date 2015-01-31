package de.tigges.tournament.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;

/**
 * contains all tournament logic concerning one round.
 * 
 * @author johannes
 *
 */
public class RoundLogic {

	private PlayerLogic playerLogic = new PlayerLogic();
	
	/**
	 * creates a new round by calling @{@link #createRoundComplete(Tournament)}
	 * and adds the new round to the tournament
	 * 
	 * @param tournament
	 */
	public void addRound(Tournament tournament) {
		tournament.getRounds().add(createRoundComplete(tournament));
	}
	
	/**
	 *  calculates the number of the next round
	 *  
	 * @param tournament
	 * @return
	 */
	public int calculateNextRound(Tournament tournament) {
		return tournament.getRounds().size() + 1;
	}
	
	/**
	 * creates a new round, calculates the round number
	 * and copies all tournament players as round players.
	 * 
	 * @param tournament
	 * @return
	 */
	public Round createRound(Tournament tournament) {
		Round round = new Round(calculateNextRound(tournament));
		copyAllTournamentPlayers(tournament, round);
		return round;
	}
	
	/**
	 * copy all tournament players as round players
	 * @param tournament
	 * @param round
	 */
	public void copyAllTournamentPlayers(Tournament tournament, Round round) {
		round.getPlayers().clear();
		round.getPlayers().addAll(tournament.getPlayers());
	}
	
	/**
	 * creates a new round
	 * <li>calculate the round number
	 * <li>copy all tournament players as round players
	 * <li>calculate all paused players
	 * <li>generates ll round matches
	 * 
	 * @param tournament
	 * @return round with players, paused players and matches
	 */
	public Round createRoundComplete (Tournament tournament) {
		Round newRound = createRound(tournament);
		calculatePausedPlayers(tournament, newRound);
		createAllRoundMatches(tournament, newRound);
		return newRound;
	}
		
	/**
	 * selects all paused players for a new round
	 * 
	 * @param tournament
	 * @param round
	 */
	public void calculatePausedPlayers(Tournament tournament, Round round) {
		// this is the number of players in one match
		int numberOfMatchPlayers = tournament.getTeamSize() * 2; // home and away team
		// this is the maximum number of players in one round
		int maxNumberOfActivePlayers = tournament.getCourts() * numberOfMatchPlayers;

		while (round.getPlayers().size() > maxNumberOfActivePlayers) {
			addPausedPlayer(tournament.getRounds(), round);
		}
		
		while ((round.getPlayers().size() % numberOfMatchPlayers) != 0) {
			addPausedPlayer(tournament.getRounds(), round);
		}
	}
	
	/**
	 * add a paused player to a round
		*
	 * <li>the selected player is added to the paused player list
	 * <li>the selected player is removed from the round player list
	 * @param previousRounds
	 * @param round
	 */
	public void addPausedPlayer (ObservableList<Round> previousRounds, Round round) {
		int pausedPlayerId = playerLogic.calculatePausedPlayerId(round, previousRounds);
		Player pausedPlayer = ListUtil.find(round.getPlayers(), pausedPlayerId);
		round.getPausedPlayers().add(pausedPlayer);
		ListUtil.remove(round.getPlayers(), pausedPlayer);
	}

	/**
	 * create all matches for one round
	 * <p>
	 * the round must contain a valid list of players
	 * 
	 * @param previousRounds
	 * @param newRound
	 */
	public void createAllRoundMatches (Tournament tournament, Round newRound) {
		Match match;
		while ((match = createMatch(tournament, newRound)) != null) {
			newRound.getMatches().add(match);
		}
	}
	
	/**
	 * calculates the number of matches in one round
	 * 
	 * @param tournament
	 * @param round
	 * @return
	 */
	public int calculateNumberOfMatches (Tournament tournament, Round round) {
		int playersPerMatch = tournament.getTeamSize() * 2; // home and away team
		int possibleMatches = round.getPlayers().size() / playersPerMatch;
		return Math.min(possibleMatches, tournament.getCourts());
	}

	/**
	 * create one match
	 *
	 * ToDo: use {@link PlayerLogic#selectPartner(Player, Round, ObservableList)}
	 * @param previousRounds
	 * @param newRound
	 * @return
	 */
	public Match createMatch(Tournament tournament, Round newRound) {
		if (canAddMatch(tournament, newRound) == false) {
			return null;
		}
		
		ObservableList<Player> availablePlayers = playerLogic.createNew(newRound.getPlayers());

		for (Match match: newRound.getMatches()) {
			availablePlayers.removeAll(match.getHomeTeam());
			availablePlayers.removeAll(match.getAwayTeam());
		}
		
		ObservableList<Player> homeTeam = FXCollections.observableArrayList();
		ObservableList<Player> awayTeam = FXCollections.observableArrayList();
		
		while (homeTeam.size() < tournament.getTeamSize()) {
			playerLogic.addTeamPlayer(homeTeam, availablePlayers, tournament.getRounds());
		}
		
		while (awayTeam.size() < tournament.getTeamSize()) {
			playerLogic.addTeamPlayer(awayTeam, availablePlayers, tournament.getRounds());
		}
				
		Match match = new Match(homeTeam, awayTeam);
		return match;
	}

	/**
	 * check whether a match can be added to a round
	 * @param tournament
	 * @param round
	 * @return
	 */
	public boolean canAddMatch(Tournament tournament, Round round) {
		ObservableList<Player> availablePlayers = FXCollections.observableArrayList(round.getPlayers());
		for (Match match: round.getMatches()) {
			ListUtil.removeAll(availablePlayers, match.getHomeTeam());
			ListUtil.removeAll(availablePlayers, match.getAwayTeam());
		}
		return availablePlayers.size() >= tournament.getTeamSize() * 2;
	}
	
	/**
	 * check whether this team has already played together in a previous round
	 * @param rounds
	 * @param team
	 * @return
	 */
	protected boolean checkTeam (ObservableList<Round> rounds, ObservableList<Player> team) {
		for (Round round: rounds) {
			for (Match match: round.getMatches()) {
				if (match.isHomeTeam(team) || match.isAwayTeam(team)) {
					return true;
				}
			}
		}
		return false;
	}
}