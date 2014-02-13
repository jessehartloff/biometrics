package settings.coordinatorsettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.settingsvariables.SettingsString;

public class IndexingCoordinatorSettings  extends ComboBoxSettings{

	
	public static String getIndexingCoordinator(){
		ACoordinatorSettings coordinatorSettings = (ACoordinatorSettings) instance.settingsVariables.get(instance.variableString);
		return coordinatorSettings.getCoordinator();
	}
	
	//Singleton
	private static IndexingCoordinatorSettings instance;
	private IndexingCoordinatorSettings() {}
	public static IndexingCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new IndexingCoordinatorSettings();
		}
		return instance;
	}


	@Override
	protected void init() {
		this.variableString = "indexing";
		this.settingsVariables.put(this.variableString, NoCoordinator.getInstance());	
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(NoCoordinator.getInstance());
		this.addToOptions(RAMIndexingSettings.getInstance());	
		this.addToOptions(SQLIndexingSettings.getInstance());	
	}


}
