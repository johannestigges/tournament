package de.tigges.ui.wizard;

import javafx.scene.Node;

public interface WizardPage {
	public void setWizard (Wizard wizard);
	public String getTitle();
	
	public Node getContent();

	public void onActivate(Activate a);
	public boolean onPrior();
	public boolean onNext();
	public boolean onCancel();
	public boolean onFinish();
}
