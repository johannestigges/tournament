package de.tigges.tournament;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import de.tigges.tournament.logic.RandomUtil;
import de.tigges.tournament.logic.RoundLogic;
import de.tigges.tournament.logic.ScoreLogic;
import de.tigges.tournament.model.Id;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;

public class TestTournament {

	@Test
	public void test() {
		// single tournament with only 1 court
		printTournament(createTournament(1, 1, 5, 5));
		// double tournament with 6 courts
		printTournament(createTournament(2, 6, 17, 8));
		// tournament with large teams
		printTournament(createTournament(6, 2, 23, 20));
	}
	
	@Test
	public void testLoadAndSave() throws JAXBException {
		File file = new File("testtournament.xml");
		
		Tournament tournament = createTournament(6,4,50,10);

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

	private Tournament createTournament (int teamSize, int courts, int players, int rounds) {
		Tournament tournament = new Tournament();
		tournament.setTeamSize(teamSize);
		tournament.setCourts(courts);
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
		checkTournament(tournament);
		System.out.printf("Tournament (%d team players, %d courts) with %d players and %d rounds%n", 
				tournament.getTeamSize(), tournament.getCourts(), tournament.getPlayers().size(), tournament.getRounds().size());
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
	
	/**
	 * consistency checks for a tournament
	 * @param tournament
	 */
	private void checkTournament(Tournament tournament) {
		assertNotNull(tournament);
		assertNotNull(tournament.getTeamSize());
		assertTrue(tournament.getTeamSize() > 0);
		assertNotNull(tournament.getCourts());
		assertTrue(tournament.getCourts() > 0);
		assertNoDoubles(tournament.getPlayers());
		for (Round round: tournament.getRounds()) {
			checkRound(round, tournament);
		}
	}

	/**
	 * consistency checks for one round
	 * @param round
	 * @param tournament
	 */
	private void checkRound(Round round, Tournament tournament) {
		assertTrue(round.getPlayers().size() + round.getPausedPlayers().size() <= tournament.getPlayers().size());
		assertTrue(round.getPlayers().size() % (tournament.getTeamSize() * 2) == 0);
		assertTrue(round.getPlayers().size() <= tournament.getCourts() * tournament.getTeamSize() * 2);
		assertTrue(round.getMatches().size() <= tournament.getCourts());
		assertTrue(tournament.getPlayers().containsAll(round.getPlayers()));
		assertTrue(tournament.getPlayers().containsAll(round.getPausedPlayers()));
		assertDistinct(round.getPlayers(), round.getPausedPlayers());
		assertNoDoubles(round.getPlayers());
		assertNoDoubles(round.getPausedPlayers());
		assertNoDoubles(round.getMatches());
		
		for (Match match: round.getMatches()) {
			checkMatch(match,round,tournament);
		}
	}

	/**
	 * consistency checks for one match
	 * @param match
	 * @param round
	 * @param tournament
	 */
	private void checkMatch(Match match, Round round, Tournament tournament) {
		assertEquals(tournament.getTeamSize(), match.getHomeTeam().size());
		assertEquals(tournament.getTeamSize(), match.getAwayTeam().size());
		assertTrue(round.getPlayers().containsAll(match.getHomeTeam()));
		assertTrue(round.getPlayers().containsAll(match.getAwayTeam()));
		assertDistinct(match.getHomeTeam(), match.getAwayTeam());
		assertNoDoubles(match.getHomeTeam());
		assertNoDoubles(match.getAwayTeam());
		for (Match otherMatch: round.getMatches()) {
			if (otherMatch.equals(match) == false) {
				assertDistinct(otherMatch.getHomeTeam(), match.getHomeTeam());
				assertDistinct(otherMatch.getHomeTeam(), match.getAwayTeam());
				assertDistinct(otherMatch.getAwayTeam(), match.getHomeTeam());
				assertDistinct(otherMatch.getAwayTeam(), match.getAwayTeam());
			}
		}
	}

	/**
	 * check that all Elements from list1 are not in list2 and
	 * all elements from list2 are not in list1.
	 * @param l1
	 * @param l2
	 */
	private <I extends Id> void assertDistinct (List<I> l1, List<I> l2) {
		for (I i1: l1) {
			assertFalse(l2.contains(i1));
		}
		for (I i2: l2) {
			assertFalse(l1.contains(i2));
		}
	}
	
	/**
	 * check that there are no elements with the same id in a list.
	 * @param l
	 */
	private <I extends Id> void assertNoDoubles(List<I> l) {
		Set<I> set = new HashSet<I>();
		for (I i: l) {
			assertFalse(set.contains(i));
			set.add(i);
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