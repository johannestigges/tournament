package de.tigges.tournament.ui.view;

import java.util.List;

import de.tigges.tournament.logic.RoundLogic;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;
import de.tigges.ui.wizard.WizardPageController;

public class NewRoundWizardPage extends WizardPageController<Tournament> {
	
	public Round getRound() {
		return getWizard().getRound();
	}
	public List<Match> getRoundMatches() {
		return getRound().getMatches();
	}

	public RoundLogic getRoundLogic() {
		return getWizard().getRoundLogic();
	}

	public Tournament getTournament() {
		return getMainApp().getData();
	}
	
	public NewRoundDialogController getWizard() {
		return (NewRoundDialogController) wizard;
	}
}
