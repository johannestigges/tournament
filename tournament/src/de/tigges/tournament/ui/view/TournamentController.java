package de.tigges.tournament.ui.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import de.tigges.tournament.logic.ScoreLogic;
import de.tigges.tournament.model.Match;
import de.tigges.tournament.model.Player;
import de.tigges.tournament.model.Round;
import de.tigges.tournament.model.Tournament;
import de.tigges.tournament.util.PlayerUtil;
import de.tigges.ui.BaseController;

public class TournamentController extends BaseController<Tournament> {

	@FXML
	private TableView<Player> playerTable;
	@FXML
	private TableColumn<Player, Number> playerIdColumn;
	@FXML
	private TableColumn<Player, String> playerNameColumn;
	@FXML
	private TableColumn<Player, Number> playerHandicapColumn;
	@FXML
	private TableColumn<Player, Number> playerScoreColumn;
	@FXML
	private TextField newPlayerName;
	
	@FXML
	private TableView<Round> roundsTable;
	@FXML 
	private TableColumn<Round, Number> roundsRoundColumn;
	@FXML 
	private TableColumn<Round, Number> roundsMatchesColumn;
	@FXML
	private TableColumn<Round, String> roundsPausedPlayers;
	
	@FXML
	private TableView<Match> matchesTable;
	@FXML
	private TableColumn<Match, String> matchHomeTeamColumn;
	@FXML
	private TableColumn<Match, String> matchAwayTeamColumn;
	@FXML
	private TableColumn<Match, Number> matchHomeScoreColumn;
	@FXML
	private TableColumn<Match, Number> matchAwayScoreColumn;
	@FXML
	private Label matchesLabel;

	
	@FXML
	private void initialize() {
		playerIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		playerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		playerNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player,String>>() {
			@Override
			public void handle(CellEditEvent<Player, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());;
			}
		});
		playerHandicapColumn.setCellValueFactory(cellData -> cellData.getValue().handicapProperty());
		playerScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());
		
		roundsRoundColumn.setCellValueFactory(cellData -> cellData.getValue().roundProperty());
		roundsMatchesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Round,Number>, ObservableValue<Number>>() {
			IntegerProperty ip = new SimpleIntegerProperty();
			@Override
			public ObservableValue<Number> call(CellDataFeatures<Round, Number> param) {
				ip.set(param.getValue().getMatches().size());
				return ip;
			}
		});
		roundsPausedPlayers.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				PlayerUtil.formatPlayerList(cellData.getValue().getPausedPlayers(),", ")));

		matchHomeTeamColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				PlayerUtil.formatPlayerList(cellData.getValue().getHomeTeam()," / ")));
		matchAwayTeamColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
				PlayerUtil.formatPlayerList(cellData.getValue().getAwayTeam()," / ")));
		matchHomeScoreColumn.setCellValueFactory(cellData -> cellData.getValue().homeScoreProperty());
		matchHomeScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		matchHomeScoreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Match,Number>>() {
			@Override
			public void handle(CellEditEvent<Match, Number> event) {
				if (event.getNewValue() != null) {
					event.getTableView().getItems().get(event.getTablePosition().getRow()).setHomeScore(event.getNewValue().intValue());
					new ScoreLogic().calculateScores(getData());
				}
			}
		});
		matchAwayScoreColumn.setCellValueFactory(cellData -> cellData.getValue().awayScoreProperty());
		matchAwayScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		matchAwayScoreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Match,Number>>() {
			@Override
			public void handle(CellEditEvent<Match, Number> event) {
				if (event.getNewValue() != null) {
					event.getTableView().getItems().get(event.getTablePosition().getRow()).setAwayScore(event.getNewValue().intValue());
					new ScoreLogic().calculateScores(getData());
				}
			}
		});
		
		roundsTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showMatches(newValue));
	}

	@Override
	public void onStart() {
		playerTable.setItems((ObservableList<Player>) mainApp.getData().getPlayers());
		roundsTable.setItems((ObservableList<Round>) mainApp.getData().getRounds());
	}

	private Object showMatches(Round round) {
		if (round != null) {
			matchesTable.setItems((ObservableList<Match>) round.getMatches());
			matchesLabel.setText(resolve("MatchesInRound", round.getRound()));
		} else {
			matchesTable.setItems(null);
			matchesLabel.setText(resolve("NoRoundSelected"));
		}
		return null;
	}
	
	@FXML
	private void handleNewPlayer() {
		if (newPlayerName.getText() != null && newPlayerName.getText().trim().isEmpty() == false) {
			getData().getPlayers().add(new Player(newPlayerName.getText()));
			newPlayerName.setText(null);
			newPlayerName.setPromptText(resolve("NewPlayer"));
		}
	}

	@FXML
	private void handlePlayerDetails() {
//		mainApp.showDialog(resolve("PlayerDetails"), "view/Player.fxml");
		mainApp.showDialog(resolve("PlayerDetails"), "view/Overview.fxml");
	}
	
	@FXML
	private void handleNewRound() {
		NewRoundDialogController c = new NewRoundDialogController();
		c.setMainApp(getMainApp());
		c.onStart();
		mainApp.showDialog(resolve("AddRound"), c);
	}
}