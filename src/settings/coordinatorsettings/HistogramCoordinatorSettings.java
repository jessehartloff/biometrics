package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.HasherSettings;
import settings.settingsvariables.SettingsString;

public class HistogramCoordinatorSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;
	
	public static String getHistogramCoordinator(){
		SettingsString string = (SettingsString) instance.settingsVariables.get("histogram");
		return string.getValue();
	}
	
	
	//Singleton
	private static HistogramCoordinatorSettings instance;
	private HistogramCoordinatorSettings() {
		this.settingsVariables.put("histogram", new SettingsString("HISTOGRAM")); //can be NONE

	}
	public static HistogramCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new HistogramCoordinatorSettings();
		}
		return instance;
	}


	
	@Override
	protected void init() {
		this.variableString = "histogram";
		this.settingsVariables.put("histogram", new SettingsString("NONE"));
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(new SettingsString("NONE"));
		this.addToOptions(new SettingsString("HISTOGRAM"));
	}


}
