package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsString;

public class MatchingCoordinatorSettings  extends ComboBoxSettings{

	
	public static String getMatchingCoordinator(){
		ACoordinatorSettings coordinatorSettings = (ACoordinatorSettings) instance.settingsVariables.get(instance.variableString);
		return coordinatorSettings.getCoordinator();
	}
	
	//Singleton
	private static MatchingCoordinatorSettings instance;
	private MatchingCoordinatorSettings() {}
	public static MatchingCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new MatchingCoordinatorSettings();
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
