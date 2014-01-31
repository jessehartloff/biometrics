package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.HasherSettings;
import settings.settingsvariables.SettingsString;

public class HistogramCoordinatorSettings extends Settings{

	public static String getHistogramCoordinator(){
		SettingsString string = (SettingsString) instance.settingsVariables.get("histogram");
		return string.getValue();
	}
	
	
	//Singleton
	private static HistogramCoordinatorSettings instance;
	private HistogramCoordinatorSettings() {
		this.settingsVariables.put("histogram", new SettingsString("NONE"));
	}
	public static HistogramCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new HistogramCoordinatorSettings();
		}
		return instance;
	}

	@Override
	protected JPanel thisJPanel() {
		
		JPanel panel = new JPanel();
		
		Settings[] modalityList = new Settings[] {
				new SettingsString("NONE"),
				new SettingsString("HISTOGRAM")	
		};
		
		JComboBox settingsBox = new JComboBox(modalityList);
		settingsBox.addActionListener(new SettingsComboBoxActionListener(this, "histogram"));
		settingsBox.setRenderer(new SettingsRenderer());
		return panel;
	}


}
