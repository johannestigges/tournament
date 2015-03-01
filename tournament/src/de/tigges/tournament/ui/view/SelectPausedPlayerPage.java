package de.tigges.tournament.ui.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import de.tigges.tournament.model.Player;
import de.tigges.ui.wizard.Activate;


public class SelectPausedPlayerPage extends NewRoundWizardPage {
	
	@FXML
	private TableView<Player> roundPlayerTable;
	@FXML
	private TableColumn<Player, String> roundPlayerNameColumn;

	@FXML
	private TableView<Player> pausedPlayerTable;
	@FXML
	private TableColumn<Player, String> pausedPlayerNameColumn;

	@FXML
	private void initialize() {
		roundPlayerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		pausedPlayerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	}
	
	@Override
	public void onStart() {
		roundPlayerTable.setItems((ObservableList<Player>) getRound().getPlayers());
		pausedPlayerTable.setItems((ObservableList<Player>) getRound().getPausedPlayers());
	}

	@FXML
	private void handleCalculatePausedPlayers() {
		getRoundLogic().calculatePausedPlayers(getTournament(), getRound());
	}
	
	@FXML
	private void handleAddPausedPlayer() {
		Player player = roundPlayerTable.getSelectionModel().getSelectedItem();
		if (player != null) {
			getRound().getPlayers().remove(player);
			getRound().getPausedPlayers().add(player);
		}
	}
	
	@FXML
	private void handleRemovePausedPlayer() {
		Player player = pausedPlayerTable.getSelectionModel().getSelectedItem();
		if (player != null) {
			getRound().getPausedPlayers().remove(player);
			getRound().getPlayers().add(player);
		}		
	}
	
	@FXML
	private void handleRemoveAllPausePlayers() {
		for (Player player: getRound().getPausedPlayers()) {
			getRound().getPlayers().add(player);
		}
		getRound().getPausedPlayers().clear();
	}
	
	@Override
	public void onActivate(Activate a) {
		if (Activate.PRIOR.equals(a) == false) {
			handleCalculatePausedPlayers();
		}
	}
	
	@Override
	public boolean onFinish() {
		handleCalculatePausedPlayers();
		return true;
	}
}