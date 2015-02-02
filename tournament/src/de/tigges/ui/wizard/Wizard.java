package de.tigges.ui.wizard;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Wizard extends BorderPane {

	private ObservableList<WizardPage> pages = FXCollections.observableArrayList();
	private int currentPageIndex = -1;

	private final Button priorButton;
	private final Button nextButton;
	private final Button cancelButton;
	private final Button finishButton;
	private final Label pageTitle;
	private final ResourceBundle bundle;

	public Wizard(WizardPage... wizardPages) {
		bundle = ResourceBundle.getBundle(Wizard.class.getName());
		priorButton = new Button(resolve("Previous"));
		nextButton = new Button(resolve("Next"));
		cancelButton = new Button(resolve("Cancel"));
		finishButton = new Button(resolve("Finish"));
		pageTitle = new Label();

		for (WizardPage page : wizardPages) {
			addPage(page);
		}
		navTo(0);
		
		initButtons();
		setTop(pageTitle);
	}
	
	private String resolve(String key) {
		return bundle.getString(key);
	}

	public void addPage(WizardPage page) {
		page.setWizard(this);
		pages.add(page);
	}

	private void initButtons() {
		priorButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				prior();
			}
		});
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				next();
			}
		});
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancel();
			}
		});
		cancelButton.setCancelButton(true);

		finishButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				finish();
			}
		});
		HBox buttonBox = new HBox(priorButton, nextButton, cancelButton, finishButton);
		buttonBox.setPadding(new Insets(5.0,5.0,5.0,5.0));
		buttonBox.setSpacing(10.0);
		setBottom(buttonBox);
	}

	public boolean hasNextPage() {
		return (currentPageIndex < pages.size() - 1);
	}

	public boolean hasPriorPage() {
		return currentPageIndex > 0;
	}

	public void navTo(int pageIndex) {
		if (pageIndex < 0 || pageIndex >= pages.size())
			return;

		WizardPage page = pages.get(pageIndex);
		if (currentPageIndex < pageIndex) {
			page.onActivate(Activate.NEXT);
		} else {
			page.onActivate(Activate.PRIOR);
		}
		showPage(pageIndex);
	}
	
	public void showPage(int pageIndex) {
		currentPageIndex = pageIndex;
		WizardPage page = pages.get(currentPageIndex);
		setCenter(page.getContent());
		pageTitle.setText(page.getTitle());

		nextButton.setDisable(hasNextPage() == false);
		priorButton.setDisable(hasPriorPage() == false);
		if (hasNextPage()) {
			nextButton.setDefaultButton(true);
			finishButton.setDefaultButton(false);
		} else {
			nextButton.setDefaultButton(false);
			finishButton.setDefaultButton(true);
		}
	}

	public WizardPage getCurrentPage() {
		return pages.get(currentPageIndex);
	}

	public void prior() {
		if (hasPriorPage() && getCurrentPage().onPrior()) {
			navTo(currentPageIndex - 1);
		}
	}

	public void next() {
		if (hasNextPage() && getCurrentPage().onNext()) {
			navTo(currentPageIndex + 1);
		}
	}

	public void finish() {
		for (int pageIndex = currentPageIndex++; pageIndex < pages.size(); pageIndex++) {
			WizardPage page = pages.get(pageIndex);
			page.onActivate(Activate.FINISH);
			if (page.onFinish() == false) {
				showPage(pageIndex);
				return;
			}
		}
		doFinish();
	}

	protected void doFinish() {
	}

	public void cancel() {
		if (getCurrentPage().onCancel()) {
			doCancel();
		}
	}

	protected void doCancel() {
	}
}
