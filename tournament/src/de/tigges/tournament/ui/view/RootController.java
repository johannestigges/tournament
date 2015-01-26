package de.tigges.tournament.ui.view;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import de.tigges.tournament.model.Tournament;
import de.tigges.tournament.ui.TournamentApp;
import de.tigges.ui.BaseController;
import de.tigges.ui.BorderPaneApp;
import de.tigges.ui.FileHandler;

public class RootController extends BaseController<Tournament> {

	private FileHandler<Tournament> fileHandler;

	@Override
	public void onStart() {
		fileHandler = new FileHandler<Tournament>(
				mainApp.getPrimaryStage(), 
				mainApp.getBundle(),
				new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"), 
				Tournament.class);
		Tournament tournament = fileHandler.loadFromPreferences();
		if (tournament != null) {
			mainApp.getData().init(tournament);
			setTitle(fileHandler.getPreferenceFileName());
			BorderPaneApp<?> app = getMainApp();
			app.showGui("view/Tournament.fxml");
		}
	}

	@FXML
	public void handleNew() {
		mainApp.getData().clear();
		fileHandler.setPreferenceFileName(null);
		setTitle(null);
		mainApp.showDialog(resolve("NewTournament"), "view/NewTournamentDialog.fxml");
	}

	@FXML
	private void handleOpen() {
		Tournament loadedTournament = fileHandler.openFile();
		if (loadedTournament != null) {
			mainApp.getData().init(loadedTournament);
			setTitle(fileHandler.getPreferenceFileName());
			((TournamentApp)mainApp).showGui("view/Tournament.fxml");
		}
	}

	@FXML
	private void handleSave() {
		fileHandler.saveFile(mainApp.getData());
		setTitle(fileHandler.getPreferenceFileName());
	}

	@FXML
	private void handleSaveAs() {
		fileHandler.saveFileAs(mainApp.getData());
		setTitle(fileHandler.getPreferenceFileName());
	}
	
	@FXML
	private void handleExit() {
		System.exit(0);
	}
	
	@FXML
	private void handleAbout() {
	}
	
	private void setTitle (String filename) {
		if (filename == null) {
			mainApp.getPrimaryStage().setTitle(resolve("Tournament"));
		} else {
			mainApp.getPrimaryStage().setTitle(
					String.format("%s - %s (%d,%d)", 
					resolve("Tournament"), filename,
					mainApp.getData().getTeamSize(),
					mainApp.getData().getCourts()));
		}
	}
}
