package de.tigges.tournament.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("serial")
@Theme("tournament")
public class TournamentUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = TournamentUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeFull();
		setContent(layout);
		
		HorizontalSplitPanel hPanel = new HorizontalSplitPanel();
		VerticalSplitPanel vPanel = new VerticalSplitPanel();
		Panel playerPanel = new Panel("Player");
		Panel roundsPanel = new Panel("Rounds");
		Panel matchesPanel = new Panel("Matches");
		Table playersTable = new Table("PlayersTable");
		playersTable.addContainerProperty("Id", Integer.class, null);
		playersTable.addContainerProperty("Name", String.class, null);
		playersTable.addContainerProperty("Score", Integer.class, null);
		playersTable.addContainerProperty("Handicap", Integer.class,null);
		Table roundsTable = new Table("RoundsTable");
		Table matchesTable = new Table("MatchesTable");
		playerPanel.setContent(playersTable);
		HorizontalLayout newPlayerLayout = new HorizontalLayout();
		TextField newPlayer = new TextField(null,"newPlayer");
		Button addPlayerButton = new Button("addPlayer");
		newPlayerLayout.addComponent(newPlayer);
		newPlayerLayout.addComponent(addPlayerButton);
		hPanel.addComponent(newPlayerLayout);
		roundsPanel.setContent(roundsTable);
		matchesPanel.setContent(matchesTable);

		VerticalLayout playerLayout = new VerticalLayout();
		playerLayout.addComponent(playerPanel);
		playerLayout.addComponent(newPlayerLayout);

		VerticalLayout roundsLayout = new VerticalLayout();
		roundsLayout.addComponent(roundsPanel);
		Button addRoundButton = new Button("addRound");
		roundsLayout.addComponent(roundsPanel);
		roundsLayout.addComponent(addRoundButton);
		
		layout.addComponent(hPanel);
		hPanel.setFirstComponent(playerLayout);
		hPanel.setSecondComponent(vPanel);
		vPanel.setFirstComponent(roundsLayout);
		vPanel.setSecondComponent(matchesPanel);
	}
}