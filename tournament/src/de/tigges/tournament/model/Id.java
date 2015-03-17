package de.tigges.tournament.model;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Base class for a Bean having an identifier
 * 
 * @author johannes
 *
 */
public class Id {
	/**
	 * static maximum id
	 */
	protected static int max_id = 0;
	
	private final IntegerProperty id;
	
	public Id() {
		this (++max_id);
	}
	
	public Id (int id) {
		this.id = new SimpleIntegerProperty(id);
	}

	@XmlElement(name="id")
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public IntegerProperty idProperty() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			return getClass().isAssignableFrom(obj.getClass()) &&
					getId() == ((Id)obj).getId();
		}
	}
}
