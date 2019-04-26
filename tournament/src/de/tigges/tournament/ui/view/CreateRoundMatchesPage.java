package de.tigges.tournament.ui.view;

import de.tigges.tournament.model.Match;
import de.tigges.tournament.util.PlayerUtil;
import de.tigges.ui.wizard.Activate;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
				PlayerUtil.formatPlayerList(cellData.getValue().getHomeTeam(), " / ")));
		matchAwayTeamColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				PlayerUtil.formatPlayerList(cellData.getValue().getAwayTeam(), " / ")));
	}

	@Override
	public void onStart() {
		matchesTable.setItems((ObservableList<Match>) getRoundMatches());
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
	private void handleEnterOneMatch() {
		if (getRoundLogic().canAddMatch(getTournament(), getRound())) {
			FXMLLoader loader = mainApp.getLoader("view/NewMatch.fxml");
			Parent gui = mainApp.load(loader);
			NewRoundWizardPage controller = loader.getController();
			controller.setMainApp(getMainApp());
			controller.setWizard(getWizard());
			controller.onStart();
			mainApp.showDialog(resolve("EnterMatchTitle"), gui);
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