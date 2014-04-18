package settings.coordinatorsettings.multiservercoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.hashersettings.AllHasherSettings;
import settings.settingsvariables.SettingsLong;
import settings.settingsvariables.SettingsNote;
import settings.settingsvariables.SettingsString;

public class ServerOneSettings extends CoordinatorSettings {

	@Override
	public String getCoordinator() {
		return "SERVER1";
	}

	@Override
	protected void addSettings() {
		this.settingsVariables.put("Port Number", new SettingsLong(10001));
		this.settingsVariables.put("IP Address", new SettingsString("localhost"));
		this.settingsVariables.put("Don't Forget", new SettingsNote("Set hasher to fuzzy vault and choose its parameters"));
		this.settingsVariables.put("Note", new SettingsNote("Must set the method to get the correct number of bits"));
	}

	public SettingsLong portNumber() {
		return (SettingsLong) this.settingsVariables.get("Port Number");
	}

	public SettingsString ip(){
		return (SettingsString) this.settingsVariables.get("IP Address");
	}
	// Singleton
	private static ServerOneSettings instance;

	private ServerOneSettings() {
	}

	public static ServerOneSettings getInstance() {
		if (instance == null) {
			instance = new ServerOneSettings();
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
	public String getLabel() {
		return "Server 1";
	}

}
