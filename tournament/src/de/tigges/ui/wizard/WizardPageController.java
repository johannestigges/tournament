package de.tigges.ui.wizard;

import javafx.scene.Node;
import javafx.scene.Parent;
import de.tigges.ui.BaseController;

public class WizardPageController<D> extends BaseController<D> implements WizardPage {
	
	protected Wizard wizard;
	private String title;
	private String resource;
	private Parent content;
	
	public WizardPageController() {
	}
	
	public WizardPageController(Parent content) {
		this();
		setContent(content);
	}
	
	public void setContent(Parent content) {
		this.content = content;
	}
	
	public WizardPageController(String title) {
		this();
		setTitle(title);
	}

	@Override
	public void setWizard (Wizard wizard) {
		this.wizard = wizard;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}

	@Override
	public Node getContent() {
		return content;
	}

	@Override
	public void onActivate(Activate a) {
	}
	
	@Override
	public boolean onPrior() {
		return true;
	}
	
	@Override
	public boolean onNext() {
		return true;
	}
	
	@Override
	public boolean onCancel() {
		return true;
	}
	
	@Override
	public boolean onFinish() {
		return true;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}
