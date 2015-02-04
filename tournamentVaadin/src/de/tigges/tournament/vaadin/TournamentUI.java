package de.tigges.tournament.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
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
		Table roundsTable = new Table("RoundsTable");
		Table matchesTable = new Table("MatchesTable");
		playerPanel.setContent(playersTable);
		roundsPanel.setContent(roundsTable);
		matchesPanel.setContent(matchesTable);
		
		layout.addComponent(hPanel);
		hPanel.setFirstComponent(playerPanel);
		hPanel.setSecondComponent(vPanel);
		vPanel.setFirstComponent(roundsPanel);
		vPanel.setSecondComponent(matchesPanel);
		
		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		layout.addComponent(button);
	}
}