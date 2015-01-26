package de.tigges.ui;

import java.util.prefs.Preferences;

public class UserPreferences {
	private Preferences prefs;

	public UserPreferences(Class<?> preferenceClass) {
		this.prefs = Preferences.userNodeForPackage(preferenceClass);
	}

	public void set(String key, String value) {
		if (value == null) {
			prefs.remove(key);
		} else {
			prefs.put(key, value);
		}
	}
	
	public String get(String key) {
		return prefs.get(key, null);
	}
}
