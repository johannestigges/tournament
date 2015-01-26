package de.tigges.tournament.ui.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.util.PlayerUtil;
import de.tigges.ui.wizard.Activate;

public class CreateRoundMatchesPage extends NewRoundWizardPage {

	@FXML
	private TableView<Match> matchesTable;
	@FXML
	private TableColumn<Match, String> matchHomeTeamColumn;
	@FXML
	private TableColumn<Match, String> matchAwayTeamColumn;
	
	@FXML
	private void initialize() {
		matchHomeTeamColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				PlayerUtil.formatPlayerList(cellData.getValue().getHomeTeam()," / ")));
		matchAwayTeamColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				PlayerUtil.formatPlayerList(cellData.getValue().getAwayTeam()," / ")));
	}
	
	@Override
	public void onStart() {
		matchesTable.setItems(getRoundMatches());
	}
	
	@Override
	public void onActivate(Activate a) {
		if (Activate.PRIOR != a) {
			handleCalculateAllMatches();
		}
	}
	
	@Override
	public boolean onFinish() {
		handleCalculateAllMatches();
		return super.onFinish();
	}
	
	@FXML
	private void handleCalculateOneMatch() {
		Match match = getRoundLogic().createMatch(getTournament(), getRound());
		if (match != null) {
			getRoundMatches().add(match);
		}
	}
	
	@FXML
	private void handleRemoveMatch() {
		Match match = matchesTable.getSelectionModel().getSelectedItem();
		if (match != null) {
			getRoundMatches().remove(match);
		}
	}
	
	@FXML
	private void handleCalculateAllMatches() {
		getRoundLogic().createAllRoundMatches(getTournament(), getRound());
	}
}