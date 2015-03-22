package de.tigges.tournament.ui.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;
import de.tigges.ui.BaseController;

public class ShowOverviewController extends BaseController<Tournament> {
	@FXML
	private WebView webView;

	private final StringBuilder sb = new StringBuilder();

	@Override
	public void onStart() {
		showTag("h1", resolve("Rounds"));
		for (Round round : getData().getRounds()) {
			showRound(round);
		}
		showTag("h1", resolve("Players"));
		for (Player player : getData().getPlayers()) {
			showPlayer(player);
		}
		show();
	}

	private void showRound(Round round) {
		showTag("h2", resolve("Round") + " " + round.getRound());
		if (round.getMatches().isEmpty() == false) {
			showTagStart("table");
			showTagStart("tr");
			showTag("th", resolve("HomeTeam"));
			showTag("th", "");
			showTag("th", resolve("AwayTeam"));
			showTag("th", resolve("HomeScore"));
			showTag("th", "");
			showTag("th", resolve("AwayScore"));
			showTagEnd("tr");
			for (Match match : round.getMatches()) {
				showTagStart("tr");
				showPlayerMatch(match.getHomeTeam(), match.getAwayTeam(),
						match.getHomeScore(), match.getAwayScore(), null);
				showTagEnd("tr");
			}
			showTagEnd("table");
		}
	}

	private void showPlayer(Player player) {
		showTag("h2", player.getName());
		showTag("h3", resolve("Handicap") + ": " + player.getHandicap()
				+ "&nbsp;&nbsp;" + resolve("Score") + ": " + player.getScore());
		showTagStart("table");
		showTagStart("tr");
		showTag("th", resolve("Round"));
		showTag("th", resolve("HomeTeam"));
		showTag("th", "");
		showTag("th", resolve("AwayTeam"));
		showTag("th", resolve("HomeScore"));
		showTag("th", "");
		showTag("th", resolve("AwayScore"));
		showTagEnd("tr");
		for (Round round : mainApp.getData().getRounds()) {
			showPlayerRound(round, player);
		}
		showTagEnd("table");
	}

	private void showPlayerRound(Round round, Player player) {
		showTagStart("tr");
		showTag("td", "" + round.getRound());
		if (round.getPausedPlayers().contains(player)) {
			showTagStart("td", "colspan", "10");
			sb.append(resolve("Paused"));
			showTagEnd("td");
		} else {
			for (Match match : round.getMatches()) {
				showPlayerMatch(match, player);
			}
		}
		showTagEnd("tr");
	}

	private void showPlayerMatch(Match match, Player player) {
		if (match.getHomeTeam().contains(player)) {
			showPlayerMatch(match.getHomeTeam(), match.getAwayTeam(),
					match.getHomeScore(), match.getAwayScore(), player);
		} else if (match.getAwayTeam().contains(player)) {
			showPlayerMatch(match.getAwayTeam(), match.getHomeTeam(),
					match.getAwayScore(), match.getHomeScore(), player);
		}
	}

	private void showPlayerMatch(List<Player> homeTeam, List<Player> awayTeam,
			int homeScore, int awayScore, Player player) {
		showPlayerTeam(homeTeam, player);
		showTag("td", "-");
		showPlayerTeam(awayTeam, player);
		showTag("td", "" + homeScore);
		showTag("td", ":");
		showTag("td", "" + awayScore);
	}

	private void showPlayerTeam(List<Player> team, Player player) {
		showTagStart("td");
		for (Player p : team) {
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
		// System.out.println(sb.toString());
		webView.getEngine().loadContent(sb.toString());
	}

	private void showTag(String tag, String text) {
		showTagStart(tag);
		sb.append(text);
		showTagEnd(tag);
	}

	private void showTagStart(String tag, String... attributes) {
		sb.append('<').append(tag);
		int i = 0;
		while (i < attributes.length) {
			sb.append(' ').append(attributes[i++]).append("=\"")
					.append(attributes[i++]).append('\"');
		}
		sb.append(">");
	}

	private void showTagEnd(String tag) {
		sb.append("</").append(tag).append(">\n");
	}
}
