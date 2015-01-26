package de.tigges.tournament.logic;

import java.util.Iterator;

import javafx.collections.ObservableList;
import de.tigges.tournament.model.Id;

/**
 * Helperclass with static utility methods concerning lists
 * 
 * @author johannes
 */
public class ListUtil {

	/**
	 * find an element with a specific {@link Id} in a list
	 * @param list
	 * @param id
	 * @return found element or null
	 */
	public static <T extends Id> T find (ObservableList<T> list, int id) {
		for (T element: list) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}
	
	/**
	 * check, wether a list contains an element with a specific id
	 * @param list
	 * @param id
	 * @return true, if the list contains an element with the given id
	 */
	public static <T extends Id> boolean contains (ObservableList<T> list, int id) {
		return find(list, id) != null;
	}
	
	/**
	 * searches for a specific element in a list
	 * <p>
	 * If the element is in the list, it is removed from the list and returned
	 * @param list
	 * @param elementToRemove
	 * @return found end removed element or null
	 */
	public static <T extends Id> T remove(ObservableList<T> list, T elementToRemove) {
		Iterator<T> iter = list.iterator();
		while (iter.hasNext()) {
			T element = iter.next();
			if (element.getId() == elementToRemove.getId()) {
				iter.remove();
				return element;
			}
		}
		return null;
	}
	
	/**
	 * removes all <code>elementsToRemove</code> from a <code>list</code>
	 * 
	 * @param list remove elements from this list
	 * @param elementsToRemove these are the elements to remove
	 */
	public static <T extends Id> void removeAll (ObservableList<T> list, ObservableList<T> elementsToRemove) {
		for (T elementToRemove: elementsToRemove) {
			remove(list, elementToRemove);
		}
	}
}
