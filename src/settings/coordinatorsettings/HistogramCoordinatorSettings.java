package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.hashersettings.AHasherSettings;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.HasherSettings;
import settings.hashersettings.StraightHasherSettings;
import settings.settingsvariables.SettingsString;

public class HistogramCoordinatorSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;
	

	
	
	public static String getHistogramCoordinator(){
		ACoordinatorSettings coordinatorSettings = (ACoordinatorSettings) instance.settingsVariables.get(instance.variableString);
		return coordinatorSettings.getCoordinator();
	}
	
	
	//Singleton
	private static HistogramCoordinatorSettings instance;
	private HistogramCoordinatorSettings() {}
	public static HistogramCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new HistogramCoordinatorSettings();
		}
		return instance;
	}


	
	@Override
	protected void init() {
		this.variableString = "histogram";
		this.settingsVariables.put("histogram", NoCoordinator.getInstance());
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(NoCoordinator.getInstance());
		this.addToOptions(HistogramSettings.getInstance());
	}


}
