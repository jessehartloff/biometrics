package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
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


	@Override
	protected void init() {
		this.variableString = "matcher";
		this.settingsVariables.put(this.variableString, DefaultTestingPrequantizedSettings.getInstance());
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(DefaultTestingPrequantizedSettings.getInstance());
		this.addToOptions(DefaultTestingSettings.getInstance());
		this.addToOptions(NoCoordinator.getInstance());
	}
	
}
