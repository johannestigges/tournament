package de.tigges.tournament.logic;

import java.util.List;
import java.util.Random;

/**
 * Helper class with static methods concerning random numbers.
 * 
 * @see Random
 * @author johannes
 *
 */
public class RandomUtil {

	private static final Random random = new Random();
	
	/**
	 * returns a random number r with 0 <= r < bound
	 * @param bound
	 * @return
	 */
	public static int nextInt(int bound) {
		return random.nextInt(bound);
	}
	
	/**
	 * returns the index of a random element of a list
	 * @param list
	 * @return random list index or -1, if the list is empty
	 */
	public static int getRandomIndex(List<?> list) {
		return (list == null || list.isEmpty()) ? -1 : nextInt(list.size());
	}
	
	/**
	 * returns a random element of a list
	 * @param list
	 * @return
	 */
	public static <E> E getRandomElement(List<E> list) {
		return list.get(getRandomIndex(list));
	}
	
	/**
	 * removes a random element from a list and returns the removed element
	 * @param list
	 * @return removed random element
	 */
	public static <E> E removeRandomElement (List<E> list) {
		return list.remove(getRandomIndex(list));
	}
}
