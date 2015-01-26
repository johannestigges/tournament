package de.tigges.tournament.model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;


public class Match extends Id {
	private final ObservableList<Player> homeTeam;
	private final ObservableList<Player> awayTeam;
	private final IntegerProperty homeScore;
	private final IntegerProperty awayScore;
	
	public Match() {
		this.homeScore = new SimpleIntegerProperty();
		this.awayScore = new SimpleIntegerProperty();
		this.homeTeam = FXCollections.observableArrayList(); 
		this.awayTeam = FXCollections.observableArrayList();
	}
	
	public Match (ObservableList<Player> homeTeam, ObservableList<Player> awayTeam) {
		this();
		setHomeTeam(homeTeam);
		setAwayTeam(awayTeam);
	}

	public void setHomeTeam(List<Player> homeTeam) {
		this.homeTeam.clear();
		this.homeTeam.addAll(homeTeam);
	}
	
	@XmlElement(name="homeTeam")
	public ObservableList<Player> getHomeTeam() {
		return homeTeam;
	}
	
	public void setAwayTeam(ObservableList<Player> awayTeam) {
		this.awayTeam.clear();
		this.awayTeam.addAll(awayTeam);
	}
	
	@XmlElement(name="awayTeam")
	public ObservableList<Player> getAwayTeam() {
		return awayTeam;
	}

	public int getHomeScore() {
		return homeScore.get();
	}
	public void setHomeScore(int homeScore) {
		this.homeScore.set(homeScore);
	}
	public IntegerProperty homeScoreProperty() {
		return homeScore;
	}
	public int getAwayScore() {
		return awayScore.get();
	}
	public void setAwayScore(int awayScore) {
		this.awayScore.set(awayScore);
	}
	public IntegerProperty awayScoreProperty() {
		return awayScore;
	}

	public void setScore (int homeScore, int awayScore) {
		setHomeScore(homeScore);
		setAwayScore(awayScore);
	}

	public boolean isHomeTeam (ObservableList<Player> team) {
		return team.size() == homeTeam.size() &&  
				homeTeam.containsAll(team) && 
				team.containsAll(homeTeam);
	}
	public boolean isAwayTeam (ObservableList<Player> team) {
		return team.size() == awayTeam.size() && 
				awayTeam.containsAll(team) && 
				team.containsAll(awayTeam);
	}
}
