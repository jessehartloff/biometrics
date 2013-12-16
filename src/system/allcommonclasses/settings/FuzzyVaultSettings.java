package system.allcommonclasses.settings;

public class FuzzyVaultSettings {

	
	
	private static FuzzyVaultSettings instance;
	
	private FuzzyVaultSettings(){}
	
	public static FuzzyVaultSettings getInstance(){
		if(instance == null){
			instance = new FuzzyVaultSettings();
		}
		return instance;
	}
	
}
