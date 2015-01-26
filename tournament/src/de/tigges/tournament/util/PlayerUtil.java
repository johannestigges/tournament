package de.tigges.tournament.util;

import javafx.collections.ObservableList;
import de.tigges.tournament.model.Player;

public class PlayerUtil {

	public static String formatPlayerList (ObservableList<Player> players, String separator) {
		if (players == null || players.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < players.size(); i++) {
			sb.append(players.get(i).getName());
			if (i < players.size() - 1) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}
}
