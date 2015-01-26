package de.tigges.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class BorderPaneApp<D> extends BaseMainApp<D> {

	private BorderPane rootPane;
	private String rootLayout;
	
	public BorderPaneApp(String rootLayout) {
		this.rootLayout = rootLayout;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			super.start(primaryStage);
			initRootLayout();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = getLoader(rootLayout);
			rootPane = loader.load();
			Scene scene = new Scene(rootPane);
			scene.getStylesheets().add(getStyleSheet());
			getPrimaryStage().setScene(scene);
			getPrimaryStage().show();
			Controller<D> controller = loader.getController();
			controller.setMainApp(this);
			controller.onStart();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public BorderPane getRootPane() {
		return rootPane;
	}
	
	public void showGui(String resource) {
		rootPane.setCenter(load(resource));
	}
}
