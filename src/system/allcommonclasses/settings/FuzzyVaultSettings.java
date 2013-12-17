package system.allcommonclasses.settings;

public class FuzzyVaultSettings {

	private Long numberOfChaffPoints;
	private Long numberOfBitsForTheField;
	
	private static FuzzyVaultSettings instance;
	
	private FuzzyVaultSettings(){}
	
	public static FuzzyVaultSettings getInstance(){
		if(instance == null){
			instance = new FuzzyVaultSettings();
		}
		return instance;
	}

	
	
	public Long getNumberOfChaffPoints() {
		return numberOfChaffPoints;
	}

	public void setNumberOfChaffPoints(Long numberOfChaffPoints) {
		this.numberOfChaffPoints = numberOfChaffPoints;
	}

	public Long getNumberOfBitsForTheField() {
		return GlobalSettings.fingerprintMethod.getTotalBits();
	}

	public void setNumberOfBitsForTheField(Long numberOfBitsForTheField) {
		this.numberOfBitsForTheField = numberOfBitsForTheField;
	}
	
	
	
}
