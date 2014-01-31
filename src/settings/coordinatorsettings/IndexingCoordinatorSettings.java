package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.settingsvariables.SettingsString;

public class IndexingCoordinatorSettings  extends Settings{

	public static String getIndexingCoordinator(){
		SettingsString string = (SettingsString) instance.settingsVariables.get("indexing");
		return string.getValue();
	}
	
	//Singleton
	private static IndexingCoordinatorSettings instance;
	private IndexingCoordinatorSettings() {
		this.settingsVariables.put("indexing", new SettingsString("NONE"));
	}
	public static IndexingCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new IndexingCoordinatorSettings();
		}
		return instance;
	}

	@Override
	protected JPanel thisJPanel() {
		
		JPanel panel = new JPanel();
		
		Settings[] modalityList = new Settings[] {
				new SettingsString("NONE"),
				new SettingsString("INDEXING")	
		};
		
		JComboBox settingsBox = new JComboBox(modalityList);
		settingsBox.addActionListener(new SettingsComboBoxActionListener(this, "indexing"));
		settingsBox.setRenderer(new SettingsRenderer());
		return panel;
	}


}
