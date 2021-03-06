package de.tigges.tournament.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Java bean representing one player
 * <p>
 * a player has the following properties
 * <li>{@link #name}
 * <li>{@link #score}
 * <li>{@link #handicap}
 * 
 * @author johannes
 */
public class Player extends Id {
	
	/**
	 * The name of the player
	 */
	private final StringProperty name;
	/**
	 * the total score of the player
	 */
	private final IntegerProperty score;
	/**
	 * the handicap.
	 * <p>
	 * the handicap can influence the score (and perhaps
	 * the algorithm for selecting teams).
	 * <p>
	 * At the momemt, the handicap is not used anywhere...
	 */
	private final IntegerProperty handicap;
	
	public Player() {
		this.name = new SimpleStringProperty();
		this.score = new SimpleIntegerProperty(); 
		this.handicap = new SimpleIntegerProperty(); 
	}

	public Player(String name) {
		this();
		setName(name);
	}

	public Player(int id) {
		this();
		setId(id);
	}
	
	/**
	 * copy all data from another player except the score!
	 * @param player
	 */
	public Player(Player player) {
		this(player.getId());
		setName(player.getName());
		setHandicap(player.getHandicap());
	}
	
	public String getName() {
		return name.get();
	}
	public void setName (String name)  {
		this.name.set(name);
	}
	public StringProperty nameProperty() {
		return name;
	}
	
	public Integer getScore() {
		return score.get();
	}
	public void setScore(Integer score) {
		this.score.set(score);
	}
	public IntegerProperty scoreProperty() {
		return score;
	}
	
	public Integer getHandicap() {
		return handicap.get();
	}
	public void setHandicap(Integer handicap) {
		this.handicap.set(handicap);
	}
	public IntegerProperty handicapProperty() {
		return handicap;
	}
	
	@Override
	public String toString() {
		return getName() == null ? String.format("id(%d)",getId()) : getName();
	}
	
	/**
	 * add the amount to the score
	 * @param amount
	 * @return the new score
	 */
	public int increaseScore (int amount) {
		int newScore = score.get() + amount;
		score.set(newScore);
		return newScore;
	}
}