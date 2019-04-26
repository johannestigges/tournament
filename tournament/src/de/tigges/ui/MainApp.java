package de.tigges.ui;

import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public interface MainApp<D> {

	public D getData();

	public Stage getPrimaryStage();

	public Stage getDialogStage();

	public <T> T load(String resource);

	public <T> T load(FXMLLoader loader);

	public FXMLLoader getLoader(String resource);

	public void showDialog(String title, String resource);

	public void showDialog(String title, Parent content);

	public ResourceBundle getBundle();

	public String resolve(String txt);
}
