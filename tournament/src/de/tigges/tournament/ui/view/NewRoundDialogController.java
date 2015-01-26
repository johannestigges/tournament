package de.tigges.tournament.ui.view;

import de.tigges.tournament.logic.RoundLogic;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;
import de.tigges.tournament.ui.TournamentApp;
import de.tigges.ui.wizard.WizardController;

public class NewRoundDialogController extends WizardController<Tournament> {
	
	private Round round;
	private RoundLogic roundLogic;
	
	@Override
	public void onStart() {
		roundLogic = new RoundLogic();
		round = roundLogic.createRound(getMainApp().getData());
		addPage("view/SelectRoundPlayerPage.fxml");
		addPage("view/SelectPausedPlayerPage.fxml");
		addPage("view/CreateRoundMatchesPage.fxml");
		navTo(0);
	}
	
	@Override
	protected void doFinish() {
		getData().getRounds().add(round);
		getMainApp().getDialogStage().close();
		TournamentApp mainApp = getMainApp();
		mainApp.showGui("view/Tournament.fxml");
	}
	
	@Override
	protected void doCancel() {
		getMainApp().getDialogStage().close();
	}
	
	public Round getRound() {
		return round;
	}
	public RoundLogic getRoundLogic() {
		return roundLogic;
	}
}