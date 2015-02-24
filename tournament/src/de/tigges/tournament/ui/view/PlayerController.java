package de.tigges.tournament.ui.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebView;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;
import de.tigges.ui.BaseController;

public class PlayerController extends BaseController<Tournament> {

	@FXML
	private ComboBox<Player> players;
	@FXML
	private WebView webView;

	private final StringBuilder sb = new StringBuilder();
	private Player player;

	@Override
	public void onStart() {
		players.setItems(getData().getPlayers());
		players.getSelectionModel().select(0);
	}

	@FXML
	private void handlePlayerSelected() {
		player = players.getSelectionModel().getSelectedItem();
		showPlayer();
	}

	private void showPlayer() {
		if (player == null) {
			webView.getEngine().loadContent("");
		} else {
			sb.delete(0, sb.length());
			showTag("h1", player.getName());
			showTag("h2", resolve("Handicap") + ": " + player.getHandicap() + "&nbsp;&nbsp;" + 
						  resolve("Score")    + ": " + player.getScore());
			showTagStart("table");
			showTagStart("tr");
			showTag("th", resolve("Round"));
			showTag("th", resolve("HomeTeam"));
			showTag("th", "");
			showTag("th", resolve("AwayTeam"));
			showTag("th", resolve("HomeScore"));
			showTag("th", resolve("AwayScore"));
			showTag("th", "");
			showTagEnd("tr");
			for (Round round : mainApp.getData().getRounds()) {
				showRound(round);
			}
			showTagEnd("table");
			show();
		}
	}

	private void showRound(Round round) {
		showTagStart("tr");
		showTag("td", "" + round.getRound());
		if (round.getPausedPlayers().contains(player)) {
			showTagStart("td", "colspan","10");
			sb.append(resolve("Paused"));
			showTagEnd("td");
		} else {
			for (Match match : round.getMatches()) {
				showMatch(match);
			}
		}
		showTagEnd("tr");
	}

	private void showMatch(Match match) {
		if (match.getHomeTeam().contains(player)) {
			showMatch (match.getHomeTeam(), match.getAwayTeam(), match.getHomeScore(), match.getAwayScore());
		} else if (match.getAwayTeam().contains(player)) {
			showMatch (match.getAwayTeam(), match.getHomeTeam(), match.getAwayScore(), match.getHomeScore());
		}
	}

	private void showMatch(ObservableList<Player> homeTeam,
			ObservableList<Player> awayTeam, int homeScore, int awayScore) {
		team(homeTeam);
		showTag("td", "-");
		team(awayTeam);
		showTag("td", "" + homeScore);
		showTag("td", ":");
		showTag("td", "" + awayScore);
	}

	private void team(ObservableList<Player> team) {
		showTagStart("td");
		for (Player p: team) {
			if (p.equals(player)) {
				showTag("b", p.getName());
			} else {
				sb.append(p.getName());
			}
			showTagStart("br/");
		}
		showTagEnd("td");
	}

	private void show() {
//		System.out.println(sb.toString());
		webView.getEngine().loadContent(sb.toString());
	}

	private void showTag(String tag, String text) {
		showTagStart(tag);
		sb.append(text);
		showTagEnd(tag);
	}
	private void showTagStart(String tag,String...attributes) {
		sb.append('<').append(tag);
		int i=0;
		while (i < attributes.length) {
			sb.append(' ').append(attributes[i++]).append("=\"").append(attributes[i++]).append('\"');
		}
		sb.append(">");
	}
	private void showTagEnd(String tag) {
		sb.append("</").append(tag).append(">\n");
	}
}
