package de.tigges.ui;

public interface Controller<D> {
	
	public <A extends MainApp<D>> A getMainApp();
	public void setMainApp(MainApp<D> mainApp);
	public void onStart();
	public D getData();
}
