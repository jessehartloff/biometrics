package settings.coordinatorsettings.multiservercoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.NoCoordinator;
import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingPrequantizedMultiThreadedSettings;
import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingPrequantizedSettings;
import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingSettings;
import settings.coordinatorsettings.matchingcoordinatorsettings.MultipleEnrollmentSettings;

public class AllMultiserverCoordinatorSettings extends ComboBoxSettings {

	public static String getMultiserverCoordinator() {
		CoordinatorSettings coordinatorSettings = (CoordinatorSettings) instance.settingsVariables
				.get(instance.variableString);
		return coordinatorSettings.getCoordinator();
	}

	// Singleton
	private static AllMultiserverCoordinatorSettings instance;

	private AllMultiserverCoordinatorSettings() {
	}

	public static AllMultiserverCoordinatorSettings getInstance() {
		if (instance == null) {
			instance = new AllMultiserverCoordinatorSettings();
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
		this.variableString = "multiserver";
		this.settingsVariables.put(this.variableString,
				NoCoordinator.getInstance());
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(NoCoordinator.getInstance());
		this.addToOptions(SuperTestingMetaClientSettings.getInstance());
		this.addToOptions(ServerOneSettings.getInstance());
		this.addToOptions(ServerTwoSettings.getInstance());
	}

}
