package de.tigges.tournament.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import de.tigges.tournament.model.Tournament;
import de.tigges.tournament.ui.TournamentApp;
import de.tigges.ui.BaseController;

public class NewTournamentDialogController extends BaseController<Tournament> {
	@FXML
	private TextField teamSize;
	@FXML
	private TextField courts;

	@FXML
	private void handleOK() {
		if (isInputValid()) {
			Tournament tournament = getData();
			tournament.setTeamSize(Integer.valueOf(teamSize.getText()));
			tournament.setCourts(Integer.valueOf(courts.getText()));
			mainApp.getDialogStage().close();
			TournamentApp mainApp = getMainApp();
			mainApp.showGui("view/Tournament.fxml");
		}
	}
	
	@FXML
	private void handleCancel() {
		Tournament tournament = getData();
		tournament.setTeamSize(2);
		tournament.setCourts(6);
		mainApp.getDialogStage().close();
	}
	
	private boolean isInputValid() {
		try {
			int teamSize = Integer.valueOf(this.teamSize.getText());
			int courts = Integer.valueOf(this.courts.getText());
			if (teamSize < 1) {
				return false;
			}
			if (courts < 1) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
