package de.tigges.ui;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogFactory {

	public static void showDialog (Window owner, String title, String format, Object...args) {
		final Stage dialog = new Stage();
		dialog.setTitle(title);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(owner);
		VBox dialogVBox = new VBox();
		dialogVBox.getChildren().add(new Text(String.format(format, args)));
		Scene dialogScene = new Scene (dialogVBox, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}
}
