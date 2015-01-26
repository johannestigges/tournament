package de.tigges.tournament.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tournament")
public class Tournament {
	private final ObservableList<Round> rounds;
	private final ObservableList<Player> players;
	private final IntegerProperty teamSize;
	private final IntegerProperty courts;
	
	public Tournament() {
		this.rounds = FXCollections.observableArrayList();
		this.players = FXCollections.observableArrayList();
		this.teamSize = new SimpleIntegerProperty();
		this.courts = new SimpleIntegerProperty();
	}

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
	public ObservableList<Round> getRounds() {
		return rounds;
	}
	public void setRounds(ObservableList<Round> rounds) {
		this.rounds.clear();
		this.rounds.addAll(rounds);
	}
	
	@XmlElement(name="player")
	public ObservableList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ObservableList<Player> players) {
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
