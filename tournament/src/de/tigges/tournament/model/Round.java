package de.tigges.tournament.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;

public class Round {

	private final IntegerProperty round;
	private final ObservableList<Match> matches;
	private final ObservableList<Player> players;
	private final ObservableList<Player> pausedPlayers;
	
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
	public ObservableList<Player> getPausedPlayers() {
		return pausedPlayers;
	}
	public void setPausedPlayers(ObservableList<Player> pausedPlayers) {
		this.pausedPlayers.clear();
		this.pausedPlayers.addAll(pausedPlayers);
	}
	
	@XmlElement(name="player")
	public ObservableList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ObservableList<Player> players) {
		this.players.clear();
		this.players.addAll(players);
	}

	@XmlElement(name="match")
	public ObservableList<Match> getMatches() {
		return matches;
	}
	public void setMatches(ObservableList<Match> matches) {
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
