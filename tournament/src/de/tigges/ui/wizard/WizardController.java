package de.tigges.ui.wizard;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import de.tigges.ui.Controller;
import de.tigges.ui.MainApp;

public class WizardController<D> extends Wizard implements Controller<D> {

	private MainApp<D> mainApp;

	public void addPage(String resource) {
		FXMLLoader loader = mainApp.getLoader(resource);
		Parent content= null;
		try {
			content = loader.load();
		} catch (IOException e) {
			System.out.printf("cannot load resource '%s: %s'%n",resource, e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		WizardPageController<D> pageController = loader.getController();
		pageController.setContent(content);
		addPage(pageController);
		pageController.setResource(resource);
		pageController.setMainApp(getMainApp());
		pageController.onStart();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <A extends MainApp<D>> A getMainApp() {
		return (A) mainApp;
	}

	@Override
	public void setMainApp(MainApp<D> mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public D getData() {
		return mainApp.getData();
	}

	@Override
	public void onStart() {
	}
}
