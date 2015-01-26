package de.tigges.tournament.ui;

import java.io.IOException;

import de.tigges.tournament.model.Tournament;
import de.tigges.ui.BorderPaneApp;

public class TournamentApp extends BorderPaneApp<Tournament> {

	private final Tournament tournament;
	
	public TournamentApp() throws IOException {
		super("view/RootLayout.fxml");
		setStyleSheet("de/tigges/tournament/ui/tournament.css");
		this.tournament = new Tournament();
	}

	@Override
	public Tournament getData() {
		return tournament;
	}

	public static void main(String[] args) {
		launch(args);
	}
}