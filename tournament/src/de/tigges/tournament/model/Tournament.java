package de.tigges.tournament.model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean representing a complete tournament
 * <p>
 * A tournament has the following properties
 * <li>{@link #teamSize} the number of players in each team
 * <li>{@link #courts} the maximum number of matches in every round
 * <li>{@link #players} list of all players
 * <li>{@link #rounds} list of tournament rounds
 * <p>
 * Before adding a round, the {@link #teamSize} and {@link #courts}
 * must be set.
 * 
 * @author johannes
 */
@XmlRootElement(name="tournament")
public class Tournament {
	private final List<Round> rounds;
	private final List<Player> players;
	private final IntegerProperty teamSize;
	private final IntegerProperty courts;
	
	public Tournament() {
		this.rounds = FXCollections.observableArrayList();
		this.players = FXCollections.observableArrayList();
		this.teamSize = new SimpleIntegerProperty();
		this.courts = new SimpleIntegerProperty();
	}

	/**
	 * init this tournament by copying all data from the
	 * given tournament
	 * 
	 * @param tournament
	 */
	public void init (Tournament tournament) {
		clear();
		if (tournament != null) {
			getPlayers().addAll(tournament.getPlayers());
			getRounds().addAll(tournament.getRounds());
			setTeamSize(tournament.getTeamSize());
			setCourts(tournament.getCourts());
		}
	}
	
	public void clear() {
		getPlayers().clear();
		getRounds().clear();
	}
	
	@XmlElement(name="round")
	public List<Round> getRounds() {
		return rounds;
	}
	public void setRounds(List<Round> rounds) {
		this.rounds.clear();
		this.rounds.addAll(rounds);
	}
	
	@XmlElement(name="player")
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players.clear();
		this.players.addAll(players);
	}
	
	@XmlElement
	public int getTeamSize() {
		return teamSize.get();
	}
	public void setTeamSize(int teamSize) {
		this.teamSize.set(teamSize);
	}
	public IntegerProperty teamSize() {
		return teamSize;
	}

	@XmlElement
	public int getCourts() {
		return courts.get();
	}
	public void setCourts(int courts) {
		this.courts.set(courts);
	}
	public IntegerProperty courts() {
		return courts;
	}
}
