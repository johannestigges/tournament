package de.tigges.ui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class BaseMainApp<D> extends Application implements MainApp<D> {

	private Stage primaryStage;
	private Stage dialogStage;
	private ResourceBundle bundle;
	private String styleSheet;

	public BaseMainApp() {
		bundle = ResourceBundle.getBundle(getClass().getName());
	}
	
	public BaseMainApp(ResourceBundle bundle) {
		setBundle(bundle);
	}
	
	public BaseMainApp(ResourceBundle bundle, String styleSheet) {
		this(bundle);
		setStyleSheet(styleSheet);
	}
	
	protected void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
	@Override
	public ResourceBundle getBundle() {
		return bundle;
	}
	
	protected void setStyleSheet(String styleSheet) {
		this.styleSheet = styleSheet;
	}
	protected String getStyleSheet() {
		return styleSheet;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
	}


	@Override
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public Stage getDialogStage() {
		return dialogStage;
	}

	@Override
	public <T> T load(String resource) {
		FXMLLoader loader = getLoader(resource);
		T gui = null;
		try {
			gui = loader.load();
		} catch (IOException e) {
			System.out.printf("cannot load resource '%s'%n", resource);
			e.printStackTrace();
			System.exit(1);
			return null;
		}
		Controller<D> controller = loader.getController();
		controller.setMainApp(this);
		controller.onStart();
		return gui;
	}

	@Override
	public FXMLLoader getLoader(String resource) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(resource));
		loader.setResources(bundle);
		return loader;
	}

	@Override
	public void showDialog(String title, String resource) {
		showDialog(title, (Parent)load(resource));
	}

	@Override
	public void showDialog(String title, Parent content) {
		dialogStage = new Stage();
		dialogStage.setTitle(title);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(content);
		if (styleSheet != null) {
			scene.getStylesheets().add(styleSheet);
		}
		dialogStage.setScene(scene);
		dialogStage.showAndWait();
	}

	@Override
	public String resolve(String txt) {
		return bundle.getString(txt);
	}
}
