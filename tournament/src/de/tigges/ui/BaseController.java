package de.tigges.ui;


public class BaseController<D> implements Controller<D> {

	protected MainApp<D> mainApp;
	
	public void setMainApp(MainApp<D> mainApp) {
		this.mainApp = mainApp;
	}
	
	public String resolve (String txt) {
		return mainApp.resolve(txt);
	}

	public String resolve (String format, Object...args) {
		return String.format(resolve(format), args);
	}

	@Override
	public D getData() {
		return mainApp.getData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A extends MainApp<D>> A getMainApp() {
		return (A) mainApp;
	}

	@Override
	public void onStart() {
	}
}
