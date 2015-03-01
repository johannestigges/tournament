package de.tigges.tournament.model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
