package de.tigges.tournament;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import de.tigges.tournament.logic.RandomUtil;
import de.tigges.tournament.logic.RoundLogic;
import de.tigges.tournament.logic.ScoreLogic;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;

public class TestTournament {

	@Test
	public void test() {
		printTournament(createTournament(17,8));
		printTournament(createTournament(5, 5));
	}
	
	@Test
	public void testLoadAndSave() throws JAXBException {
		File file = new File("testtournament.xml");
		
		Tournament tournament = createTournament(24, 5);

		JAXBContext context = JAXBContext.newInstance(Tournament.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Marshalling and saving XML to the file.
        m.marshal(tournament, file);
        
        Unmarshaller um = context.createUnmarshaller();

        // Reading XML from the file and unmarshalling.
        tournament = (Tournament) um.unmarshal(file);
        assert tournament.getPlayers().size() == 24;
        assert tournament.getRounds().size() == 5;
	}

	private Tournament createTournament (int players, int rounds) {
		Tournament tournament = new Tournament();
		for (int i=0; i<players; i++) {
			tournament.getPlayers().add(new Player(String.format("%2d", i)));
		}
		RoundLogic roundLogic = new RoundLogic();
		for (int i=0; i < rounds; i++) {
			roundLogic.addRound(tournament);
		}

		for (Round round: tournament.getRounds()) {
			for (Match match: round.getMatches()) {
				match.setScore(RandomUtil.nextInt(3), RandomUtil.nextInt(3));
			}
		}
		new ScoreLogic().calculateScores(tournament);
		return tournament;
	}
	
	private void printTournament(Tournament tournament) {
		System.out.printf("Tournament with %d players and %d rounds%n", 
				tournament.getPlayers().size(), tournament.getRounds().size());
		for (Round round: tournament.getRounds()) {
			System.out.printf("  Round %d with paused players %s%n", 
					round.getRound(),printPlayers(round.getPausedPlayers()));
			for (Match match: round.getMatches()) {
				System.out.printf("    Match %s : %s Score %d:%d%n", 
						printPlayers(match.getHomeTeam()),printPlayers(match.getAwayTeam()),
						match.getHomeScore(), match.getAwayScore());
			}
		}
		for (Player player: tournament.getPlayers()) {
			System.out.printf("Player %s has total score %d%n", player.getName(), player.getScore());
		}
	}

	private String printPlayers(List<Player> players) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (Player player:players) {
			sb.append(player.getName()).append(' ');
		}
		sb.append(']');
		return sb.toString();
	}
}