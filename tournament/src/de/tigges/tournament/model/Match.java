package de.tigges.tournament.model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlElement;

/**
 * Java bean representing one match
 * <p>
 * A match has the following properties
 * <li>{@link #homeTeam} list of players building the home team
 * <li>{@link #awayTeam} list of players building the away team
 * <li>{@link #homeScore} the score of the home team
 * <li<{@link #awayScore} the score of the away team
 *  
 * @author johannes
 *
 */
public class Match extends Id {
	/**
	 * List of {@link Player}s building the home team
	 */
	private final List<Player> homeTeam;
	/**
	 * List of {@link Player}s building the away team
	 */
	private final List<Player> awayTeam;
	/**
	 * The score of the {@link #homeTeam}
	 */
	private final IntegerProperty homeScore;
	/**
	 * The score of the {@link #awayTeam}
	 */
	private final IntegerProperty awayScore;
	
	public Match() {
		this.homeScore = new SimpleIntegerProperty();
		this.awayScore = new SimpleIntegerProperty();
		this.homeTeam = FXCollections.observableArrayList(); 
		this.awayTeam = FXCollections.observableArrayList();
	}
	
	public Match (List<Player> homeTeam, List<Player> awayTeam) {
		this();
		setHomeTeam(homeTeam);
		setAwayTeam(awayTeam);
	}

	public void setHomeTeam(List<Player> homeTeam) {
		this.homeTeam.clear();
		this.homeTeam.addAll(homeTeam);
	}
	
	@XmlElement(name="homeTeam")
	public List<Player> getHomeTeam() {
		return homeTeam;
	}
	
	public void setAwayTeam(List<Player> awayTeam) {
		this.awayTeam.clear();
		this.awayTeam.addAll(awayTeam);
	}
	
	@XmlElement(name="awayTeam")
	public List<Player> getAwayTeam() {
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

	/**
	 * set the score of the match
	 * @param homeScore score of the home team
	 * @param awayScore score of the away team
	 */
	public void setScore (int homeScore, int awayScore) {
		setHomeScore(homeScore);
		setAwayScore(awayScore);
	}

	/**
	 * checks, whether the given team is the {@link #homeTeam}
	 * @param team
	 * @return
	 */
	public boolean isHomeTeam (List<Player> team) {
		return team.size() == homeTeam.size() &&  
				homeTeam.containsAll(team) && 
				team.containsAll(homeTeam);
	}
	
	/**
	 * checks, whether the given team is the {@link #awayTeam}
	 * @param team
	 * @return
	 */
	public boolean isAwayTeam (List<Player> team) {
		return team.size() == awayTeam.size() && 
				awayTeam.containsAll(team) && 
				team.containsAll(awayTeam);
	}
}
