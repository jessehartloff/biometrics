package settings.hashersettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.AllSettings;
import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.coordinatorsettings.AllHistogramCoordinatorSettings;
import settings.coordinatorsettings.AllIndexingCoordinatorSettings;
import settings.coordinatorsettings.AllMatchingCoordinatorSettings;
import settings.modalitysettings.AllModalitySettings;

public class AllHasherSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;
	
	
	public HasherSettings hasher(){
		return (HasherSettings) this.settingsVariables.get(this.variableString);
	}
	

	//Singleton
	private AllHasherSettings() {
	}
	
	@Override
	protected void init(){
		this.variableString = "Hasher";
		this.settingsVariables.put(this.variableString, FuzzyVaultSettings.getInstance());
	}
	
	private static AllHasherSettings instance;
	public static AllHasherSettings getInstance(){
		if(instance == null){
			instance = new AllHasherSettings();
		}
		return instance;
	}
	


	public static String getHasher() {
		return instance.hasher().getHasher();
	}



	@Override
	protected void addALLOptions() {
		this.addToOptions(StraightHasherSettings.getInstance());
		this.addToOptions(FuzzyVaultSettings.getInstance());
	}
}
