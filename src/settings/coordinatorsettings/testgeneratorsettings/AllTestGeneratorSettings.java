package settings.coordinatorsettings.testgeneratorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.ComboBoxSettings;
import settings.Settings;

public class AllTestGeneratorSettings extends ComboBoxSettings {

	public static String getTestGenerator() {
		TestGeneratorSettings testGeneratorSettings = (TestGeneratorSettings) instance.settingsVariables
				.get(instance.variableString);
		return testGeneratorSettings.getTestGenerator();
	}

	// Singleton
	private static AllTestGeneratorSettings instance;

	private AllTestGeneratorSettings() {
	}

	public static AllTestGeneratorSettings getInstance() {
		if (instance == null) {
			instance = new AllTestGeneratorSettings();
		}
		return instance;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(instance.settingsVariables);
		out.writeInt(instance.settingsBox.getSelectedIndex());
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in
				.readObject();
		instance.currentIndex = in.readInt();
	}

	@Override
	protected void addSettings() {
		this.variableString = "testGenerator";
		this.settingsVariables.put(this.variableString,
				new TestGeneratorFVCTestsSettings());
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(new TestGeneratorFVCTestsSettings()); // TODO
																// Singleton
		this.addToOptions(new TestGeneratorAllTestsSettings());
	}

}
