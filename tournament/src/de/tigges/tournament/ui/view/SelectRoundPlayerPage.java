package de.tigges.tournament.ui.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import de.tigges.tournament.model.Player;

public class SelectRoundPlayerPage extends NewRoundWizardPage {

	@FXML
	private TableView<Player> roundPlayerTable;
	@FXML
	private TableColumn<Player, String> roundPlayerNameColumn;

	@FXML
	private void initialize() {
		roundPlayerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	}
	
	@Override
	public void onStart() {
		ObservableList<Player> players = getRound().getPlayers();
		players.clear();
		players.addAll(getTournament().getPlayers());
		roundPlayerTable.setItems(players);
	}
	
	@FXML
	private void handleRemovePlayer() {
		int playerIndex = roundPlayerTable.getSelectionModel().getSelectedIndex();
		if (playerIndex >= 0) {
			getRound().getPlayers().remove(playerIndex);
		}
	}
}
