package settings.coordinatorsettings.matchingcoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.AllSettings;
import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.NoCoordinator;
import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsString;

public class AllMatchingCoordinatorSettings  extends ComboBoxSettings{

	
	public static String getMatchingCoordinator(){
		CoordinatorSettings coordinatorSettings = (CoordinatorSettings) instance.settingsVariables.get(instance.variableString);
		return coordinatorSettings.getCoordinator();
	}
	
	//Singleton
	private static AllMatchingCoordinatorSettings instance;
	private AllMatchingCoordinatorSettings() {}
	public static AllMatchingCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new AllMatchingCoordinatorSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
		out.writeInt(instance.settingsBox.getSelectedIndex());
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
		instance.currentIndex = in.readInt();
	}


	@Override
	protected void addSettings() {
		this.variableString = "matcher";
		this.settingsVariables.put(this.variableString, DefaultTestingPrequantizedSettings.getInstance());
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(FeatureCounterSettings.getInstance());
		this.addToOptions(DefaultTestingPrequantizedSettings.getInstance());
		this.addToOptions(DefaultTestingPrequantizedMultiThreadedSettings.getInstance());
		this.addToOptions(DefaultTestingSettings.getInstance());
		this.addToOptions(MultipleEnrollmentSettings.getInstance());
		this.addToOptions(NoCoordinator.getInstance());
	}
	
}
