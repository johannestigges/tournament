package de.tigges.tournament.logic;

import java.util.Iterator;
import java.util.List;

import de.tigges.tournament.model.Id;

/**
 * Helper class with static utility methods dealing with lists
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
	public static <T extends Id> T find (List<T> list, int id) {
		for (T element: list) {
			if (element.getId() == id) {
				return element;
			}
		}
		return null;
	}
	
	/**
	 * check, if a list contains an element with a specific {@link Id}
	 * @param list
	 * @param id
	 * @return true, if the list contains an element with the given id
	 */
	public static <T extends Id> boolean contains (List<T> list, int id) {
		return find(list, id) != null;
	}
	
	/**
	 * searches for a specific element in a list
	 * <p>
	 * If the element is in the list, it is removed from the list and returned
	 * @param list
	 * @param elementToRemove
	 * @return removed element or null
	 */
	public static <T extends Id> T remove(List<T> list, T elementToRemove) {
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
	public static <T extends Id> void removeAll (List<T> list, List<T> elementsToRemove) {
		for (T elementToRemove: elementsToRemove) {
			remove(list, elementToRemove);
		}
	}
	
	/**
	 * searches for am element with a given {@link Id} and removes it from the list
	 * @param list
	 * @param id
	 * @return found and removed element or null
	 */
	public static <T extends Id> T findAndRemove (List<T> list, int id) {
		T element = find(list, id);
		if (element != null) {
			list.remove(element);
		}
		return element;
	}
}
