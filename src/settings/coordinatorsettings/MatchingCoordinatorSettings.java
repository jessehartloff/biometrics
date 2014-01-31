package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsString;

public class MatchingCoordinatorSettings  extends Settings{

	public static String getMatchingCoordinator(){
		SettingsString string = (SettingsString) instance.settingsVariables.get("matcher");
		return string.getValue();
	}
	
	//Singleton
	private static MatchingCoordinatorSettings instance;
	private MatchingCoordinatorSettings() {
		this.settingsVariables.put("matcher", new SettingsString("DEFAULTTESTINGPREQUANTIZED"));
	}
	public static MatchingCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new MatchingCoordinatorSettings();
		}
		return instance;
	}

	@Override
	protected JPanel thisJPanel() {
		
		JPanel panel = new JPanel();
		
		Settings[] modalityList = new Settings[] {
				new SettingsString("NONE"),
				new SettingsString("DEFAULTTESTING"),
				new SettingsString("DEFAULTTESTINGPREQUANTIZED")	
		};
		
		JComboBox settingsBox = new JComboBox(modalityList);
		settingsBox.addActionListener(new SettingsComboBoxActionListener(this, "matcher"));
		settingsBox.setRenderer(new SettingsRenderer());
		return panel;
	}
	
}
