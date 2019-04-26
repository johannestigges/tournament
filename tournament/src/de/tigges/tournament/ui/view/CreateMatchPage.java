package de.tigges.tournament.ui.view;

import java.util.List;

import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CreateMatchPage extends NewRoundWizardPage {
	@FXML
	private TableView<Player> availablePlayerTable;
	@FXML
	private TableColumn<Player, String> availablePlayerNameColumn;
	@FXML
	private TableView<Player> homePlayerTable;
	@FXML
	private TableColumn<Player, String> homePlayerNameColumn;
	@FXML
	private TableView<Player> awayPlayerTable;
	@FXML
	private TableColumn<Player, String> awayPlayerNameColumn;

	@FXML
	private void initialize() {
		availablePlayerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		homePlayerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		awayPlayerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	}

	@Override
	public void onStart() {
		Round round = getRound();
		List<Player> availablePlayers = getRoundLogic().getAvailablePlayers(round);
		availablePlayerTable.setItems((ObservableList<Player>) availablePlayers);
	}

	@FXML
	private void handleOk() {
		if (homePlayerTable.getItems().size() == getTournament().getTeamSize()
				&& awayPlayerTable.getItems().size() == getTournament().getTeamSize()) {
			getRound().getMatches().add(new Match(homePlayerTable.getItems(), awayPlayerTable.getItems()));
			getMainApp().getDialogStage().close();
		}
	}

	@FXML
	private void handleCancel() {
		getMainApp().getDialogStage().close();
	}

	@FXML
	private void handleAddHomePlayer() {
		if (homePlayerTable.getItems().size() < getTournament().getTeamSize()) {
			movePlayer(availablePlayerTable, homePlayerTable);
		}
	}

	@FXML
	private void handleAddAwayPlayer() {
		if (awayPlayerTable.getItems().size() < getTournament().getTeamSize()) {
			movePlayer(availablePlayerTable, awayPlayerTable);
		}
	}

	@FXML
	private void handleRemoveHomePlayer() {
		movePlayer(homePlayerTable, availablePlayerTable);
	}

	@FXML
	private void handleRemoveAwayPlayer() {
		movePlayer(awayPlayerTable, availablePlayerTable);
	}

	private void movePlayer(TableView<Player> source, TableView<Player> destination) {
		Player player = source.getSelectionModel().getSelectedItem();
		if (player != null) {
			source.getItems().remove(player);
			destination.getItems().add(player);
		}
	}
}
