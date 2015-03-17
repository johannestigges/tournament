package de.tigges.tournament.model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;

/**
 * Java bean representing one round
 * <p>
 * a round has the following attributes.
 * <li>{@link #round} round number
 * <li>{@link #matches} list of matches
 * <li>{@link #players} list of all players
 * <li>{@link #pausedPlayers} list of players in pause
 * 
 * @author johannes
 */
public class Round {
	/**
	 * the number of this round.
	 */
	private final IntegerProperty round;
	/**
	 * the list of all matches belonging to the round.
	 */
	private final List<Match> matches;
	/**
	 * the list of all players, that really play in this round.
	 */
	private final List<Player> players;
	/**
	 * the list of all players that must pause in this round.
	 */
	private final List<Player> pausedPlayers;
	
	public Round () {
		this.round = new SimpleIntegerProperty();
		this.matches = FXCollections.observableArrayList();
		this.players = FXCollections.observableArrayList();
		this.pausedPlayers = FXCollections.observableArrayList();
		
	}
	
	public Round (int round) {
		this();
		setRound(round);
	}

	@XmlElement(name="pausedPlayer")
	public List<Player> getPausedPlayers() {
		return pausedPlayers;
	}
	public void setPausedPlayers(List<Player> pausedPlayers) {
		this.pausedPlayers.clear();
		this.pausedPlayers.addAll(pausedPlayers);
	}
	
	@XmlElement(name="player")
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players.clear();
		this.players.addAll(players);
	}

	@XmlElement(name="match")
	public List<Match> getMatches() {
		return matches;
	}
	public void setMatches(List<Match> matches) {
		this.matches.clear();
		this.matches.addAll(matches);
	}
	
	public int getRound() {
		return round.get();
	}
	public void setRound(int round) {
		this.round.set(round);
	}
	public IntegerProperty roundProperty() {
		return round;
	}
}
