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
		SettingsString string = (SettingsString) instance.settingsVariables.get("matcher");
		return string.getValue();
	}
	
	//Singleton
	private static MatchingCoordinatorSettings instance;
	private MatchingCoordinatorSettings() {
	}
	public static MatchingCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new MatchingCoordinatorSettings();
		}
		return instance;
	}


	@Override
	protected void init() {
		this.variableString = "matcher";
		this.settingsVariables.put(this.variableString, new SettingsString("DEFAULTTESTINGPREQUANTIZED"));
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(new SettingsString("NONE"));
		this.addToOptions(new SettingsString("DEFAULTTESTING"));
		this.addToOptions(new SettingsString("DEFAULTTESTINGPREQUANTIZED"));
	}
	
}
