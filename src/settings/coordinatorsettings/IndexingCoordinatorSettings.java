package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.settingsvariables.SettingsString;

public class IndexingCoordinatorSettings  extends ComboBoxSettings{

	public static String getIndexingCoordinator(){
		SettingsString string = (SettingsString) instance.settingsVariables.get("indexing");
		return string.getValue();
	}
	
	//Singleton
	private static IndexingCoordinatorSettings instance;
	private IndexingCoordinatorSettings() {
	}
	public static IndexingCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new IndexingCoordinatorSettings();
		}
		return instance;
	}


	@Override
	protected void init() {
		this.variableString = "indexing";
		this.settingsVariables.put(this.variableString, new SettingsString("NONE"));	
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(new SettingsString("NONE"));
		this.addToOptions(new SettingsString("INDEXING"));	
	}


}
