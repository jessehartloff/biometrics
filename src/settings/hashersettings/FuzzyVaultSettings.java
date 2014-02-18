package settings.hashersettings;

import settings.settingsvariables.SettingsLong;
import system.allcommonclasses.modalities.Biometric;
import system.hasher.Hasher;

public class FuzzyVaultSettings  extends HasherSettings{

	private static final long serialVersionUID = 1L;

	
	//Singleton
	private static FuzzyVaultSettings instance;
	private FuzzyVaultSettings(){
	}
	public static FuzzyVaultSettings getInstance(){
		if(instance == null){
			instance = new FuzzyVaultSettings();
		}
		return instance;
	}
	
	@Override
	protected void init(){
		this.settingsVariables.put("numberOfChaffPoints", new SettingsLong());
	}
	

	public SettingsLong numberOfChaffPoints(){
		return (SettingsLong) this.settingsVariables.get("numberOfChaffPoints");
	}
	


	public Long getNumberOfBitsForTheField() {
		return Biometric.method.getTotalBits(); 
	}

	@Override
	public String getHasher() {
		return "SHORTCUTFUZZYVAULT";
	}


	@Override
	public String getLabel(){
		return "Fuzzy Vault";
	}
	
	
}
