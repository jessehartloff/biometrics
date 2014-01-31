package settings.hashersettings;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.AllSettings;
import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.coordinatorsettings.HistogramCoordinatorSettings;
import settings.coordinatorsettings.IndexingCoordinatorSettings;
import settings.coordinatorsettings.MatchingCoordinatorSettings;
import settings.modalitysettings.ModalitySettings;

public class HasherSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;
	
	
	public AHasherSettings hasher(){
		return (AHasherSettings) this.settingsVariables.get(this.variableString);
	}
	

	//Singleton
	private HasherSettings() {
	}
	
	@Override
	protected void init(){
		this.variableString = "Hasher";
		this.settingsVariables.put(this.variableString, FuzzyVaultSettings.getInstance());
	}
	
	private static HasherSettings instance;
	public static HasherSettings getInstance(){
		if(instance == null){
			instance = new HasherSettings();
		}
		return instance;
	}
	


	public static String getHasher() {
		return instance.hasher().getHasher();
	}




	@Override
	protected void addALLOptions() {
		this.addToOptions(FuzzyVaultSettings.getInstance());
		this.addToOptions(StraightHasherSettings.getInstance());
	}
}
