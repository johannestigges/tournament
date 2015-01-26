package de.tigges.ui;

import java.io.File;
import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * manage the handling of a file
 * 
 * @author johannes
 *
 * @param <T>
 */
public class FileHandler<T> {
	
	private Window ownerWindow;
	private ResourceBundle resourceBundle;
	private ExtensionFilter extensionFilter;
	private Class<T> jaxbClass;
	private UserPreferences userPreferences;
	
	public static final String PREF_KEY_FILE = "fileName";
	
	/**
	 * Constructor
	 * 
	 * @param ownerWindow
	 * @param resourceBundle
	 * @param extensionFilter
	 * @param jaxbClass
	 */
	public FileHandler (Window ownerWindow, ResourceBundle resourceBundle, ExtensionFilter extensionFilter, Class<T> jaxbClass) {
		this.ownerWindow = ownerWindow;
		this.resourceBundle = resourceBundle;
		this.extensionFilter = extensionFilter;
		this.jaxbClass = jaxbClass;
		this.userPreferences = new UserPreferences(jaxbClass);
	}

	/**
	 * <li>show a dialog to let the user select a file
	 * <li>load th file from the filesystem
	 * <li>convert the content of the file into a java object using JAXB technology
	 * <li>set the loaded filename as preference 
	 * @return
	 */
	public T openFile() {
		File file = createFileChooser().showOpenDialog(ownerWindow);
		if (file != null) {
			return load(file);
		}
		return null;
	}
	
	/**
	 * 
	 * @param data
	 */
	public void saveFile(T data) {
		String fileName = getPreferenceFileName();
		if (fileName != null) {
			save (data, new File(fileName));
		} else {
			saveFileAs(data);
		}
	}
	
	public void saveFileAs(T data) {
		File file = createFileChooser().showSaveDialog(ownerWindow);
		if (file != null) {
			save(data, file);
		}
	}
	
	public T loadFromPreferences() {
		if (getPreferenceFileName() != null) {
			return load(new File(getPreferenceFileName()));
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private T load (File file) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(jaxbClass);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			T result = (T) unmarshaller.unmarshal(file);
			setPreferenceFileName(file.getPath());
			return result;
		} catch (Exception e) {
			DialogFactory.showDialog(ownerWindow,
					resourceBundle.getString("Error"),
					String.format(resourceBundle.getString("CannotLoadFile"), file.getPath(), e.getMessage()));
			return null;
		}
	}
	
	private void save(T data, File file) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(jaxbClass);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(data, file);
			setPreferenceFileName(file.getPath());
		} catch (Exception e) {
			DialogFactory.showDialog(ownerWindow, 
					resourceBundle.getString("Error"), 
					String.format(resourceBundle.getString("CannotSaveFile"), file.getPath(), e.getMessage()));
		}
	}
	
	private FileChooser createFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extensionFilter);
		String defaultFile = getPreferenceFileName();
		if (defaultFile != null) {
			fileChooser.setInitialDirectory(new File(defaultFile).getParentFile());
		}
		return fileChooser;
	}

	public String getPreferenceFileName() {
		return userPreferences.get(PREF_KEY_FILE);
	}
	public void setPreferenceFileName(String fileName) {
		userPreferences.set(PREF_KEY_FILE, fileName);
	}	
}
