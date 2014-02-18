package settings.fingerprintmethodsettings;

import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsLong;
import settings.settingsvariables.SettingsMethodVariable;

public class PRINTSettings extends FingerprintMethodSettings{

	
	private static final long serialVersionUID = 1L;
	
	

	private static PRINTSettings instance;
	protected PRINTSettings(){}
	public static PRINTSettings getInstance(){
		if(instance == null){
			instance = new PRINTSettings();
		}
		return instance;
	}

	
	// getters
	public SettingsLong n(){
		return (SettingsLong) this.settingsVariables.get("N");
	}	
	public SettingsLong distanceBins(){
		return (SettingsLong) this.settingsVariables.get("distanceBins");
	}
	public SettingsLong sigmaBins(){
		return (SettingsLong) this.settingsVariables.get("sigmaBins");
	}
	public SettingsLong phiBins(){
		return (SettingsLong) this.settingsVariables.get("phiBins");
	}
	public SettingsLong kClosestMinutia(){
		return (SettingsLong) this.settingsVariables.get("kClosestMinutia");
	}
	public SettingsLong rotationRegions(){
		return (SettingsLong) this.settingsVariables.get("rotationRegions");
	}

	@Override
	public String getMethodString() {
		return "PRINTS";
	}

	private String componentToString(String componentType, Long componentNumber){
		return componentType + componentNumber.toString();
	}
	
	public SettingsMethodVariable getMinutiaComponentVariable(String component, Long i){
		return (SettingsMethodVariable) this.settingsVariables.get(this.componentToString(component, i));
	}

	public void setAllNumberOfBins() {
		this.settingsVariables.put(this.componentToString("distance", 0L), new SettingsMethodVariable(this.distanceBins().getValue()));
		for(Long i = 1L; i < this.n().getValue(); i++){
			this.settingsVariables.put(this.componentToString("distance", i), new SettingsMethodVariable(this.distanceBins().getValue()));
			this.settingsVariables.put(this.componentToString("sigma", i), new SettingsMethodVariable(this.sigmaBins().getValue()));
			this.settingsVariables.put(this.componentToString("phi", i), new SettingsMethodVariable(this.phiBins().getValue()));
		}
	}
	
//	public void setAllNumberOfBins(Long xBinNumber, Long yBinNumber, Long thetaBinNumber) {
//		this.settingsVariables.put(this.componentToString("theta", 0L), new SettingsMethodVariable());
//		ngonVariableMap.get("theta0").setBins(thetaBinNumber.intValue());
//		for(Long i = 1L; i < this.getN(); i++){
//			ngonVariableMap.get("x"+i.toString()).setBins(xBinNumber.intValue());
//			ngonVariableMap.get("y"+i.toString()).setBins(yBinNumber.intValue());
//			ngonVariableMap.get("theta"+i.toString()).setBins(thetaBinNumber.intValue());
//		}
//	}


	@Override
	public String getLabel(){
		return "PRINTS";
	}
	
	@Override
	protected void init() {
		this.settingsVariables.put("N", new SettingsLong(3));
		this.settingsVariables.put("kClosestMinutia", new SettingsLong(4));
		this.settingsVariables.put("distanceBins", new SettingsLong(8));
		this.settingsVariables.put("sigmaBins", new SettingsLong(8));
		this.settingsVariables.put("phiBins", new SettingsLong(8));
		this.settingsVariables.put("rotationRegions", new SettingsLong(5));
	}


	
}
