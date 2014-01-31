package settings.hashersettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.AllSettings;
import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.coordinatorsettings.HistogramCoordinatorSettings;
import settings.coordinatorsettings.IndexingCoordinatorSettings;
import settings.coordinatorsettings.MatchingCoordinatorSettings;
import settings.modalitysettings.ModalitySettings;

public class HasherSettings extends Settings{


	public AHasherSettings hasher(){
		return (AHasherSettings) this.settingsVariables.get("Hasher");
	}
	

	//Singleton
	private static HasherSettings instance;
	private HasherSettings() {
		this.settingsVariables.put("Hasher", new FuzzyVaultSettings());
	}
	public static HasherSettings getInstance(){
		if(instance == null){
			instance = new HasherSettings();
		}
		return instance;
	}
	
	
	@Override
	protected JPanel thisJPanel() {
		
		JPanel panel = new JPanel();

		Settings[] settingsList = new Settings[] {
				new FuzzyVaultSettings(),
				new StraightHasherSettings(),		
		};
		
		JComboBox settingsBox = new JComboBox(settingsList);
		settingsBox.addActionListener(new SettingsComboBoxActionListener(this, "Hasher"));
		settingsBox.setRenderer(new SettingsRenderer());
		
		panel.add(settingsBox);
		
		return panel;
	}


	public static String getHasher() {
		return instance.hasher().getHasher();
	}
}
