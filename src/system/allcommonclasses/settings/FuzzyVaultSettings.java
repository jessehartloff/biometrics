package system.allcommonclasses.settings;

import system.allcommonclasses.modalities.Biometric;

public class FuzzyVaultSettings {

	//settings
	private Long numberOfChaffPoints;
	//
	
	
	//singleton
	private static FuzzyVaultSettings instance;
	private FuzzyVaultSettings(){}
	public static FuzzyVaultSettings getInstance(){
		if(instance == null){
			instance = new FuzzyVaultSettings();
		}
		return instance;
	}

	
	//getters and setters
	public Long getNumberOfChaffPoints() {
		return numberOfChaffPoints;
	}

	public void setNumberOfChaffPoints(Long numberOfChaffPoints) {
		this.numberOfChaffPoints = numberOfChaffPoints;
	}

	public Long getNumberOfBitsForTheField() {
		return Biometric.method.getTotalBits(); 
	}

	
	
	
}
