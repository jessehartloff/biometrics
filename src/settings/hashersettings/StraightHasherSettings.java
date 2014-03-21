package settings.hashersettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.SettingsLong;
import system.hasher.Hasher;

public class StraightHasherSettings extends HasherSettings {

	private static final long serialVersionUID = 1L;

	// Singleton
	private static StraightHasherSettings instance;

	private StraightHasherSettings() {
	}

	public static StraightHasherSettings getInstance() {
		if (instance == null) {
			instance = new StraightHasherSettings();
		}
		return instance;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(instance.settingsVariables);
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in
				.readObject();
	}

	@Override
	public String getHasher() {
		return "STRAIGHTHASHER";
	}

	@Override
	public String getLabel() {
		return "Straight Hashing";
	}

	@Override
	protected void addSettings() {

	}

}
