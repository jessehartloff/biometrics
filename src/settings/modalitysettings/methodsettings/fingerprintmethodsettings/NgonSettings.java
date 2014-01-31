package settings.modalitysettings.methodsettings.fingerprintmethodsettings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JPanel;

import settings.Settings;
import settings.settingsvariables.*;

public class NgonSettings extends AFingerprintMethodSettings{
	
	private static final long serialVersionUID = 1L;
	
	

	private static NgonSettings instance;
	private NgonSettings(){}
	public static NgonSettings getInstance(){
		if(instance == null){
			instance = new NgonSettings();
		}
		return instance;
	}

	// getters

	public SettingsDouble rotationStep(){
		return (SettingsDouble) this.settingsVariables.get("rotationstep");
	}
	public SettingsDouble rotationStart(){
		return (SettingsDouble) this.settingsVariables.get("rotationstart");
	}
	public SettingsDouble rotationStop(){
		return (SettingsDouble) this.settingsVariables.get("rotationstop");
	}
	public SettingsLong kClosestMinutia(){
		return (SettingsLong) this.settingsVariables.get("kClosestMinutia");
	}
	public SettingsLong getN(){
		return (SettingsLong) this.settingsVariables.get("N");
	}


	@Override
	public String getMethodString() {
		return "NGONS";
	}

	
	


//	public void setAllNumberOfBins(Long xBinNumber, Long yBinNumber, Long thetaBinNumber) {
//		ngonVariableMap.get("theta0").setBins(thetaBinNumber.intValue());
//		for(Long i = 1L; i < this.getN(); i++){
//			ngonVariableMap.get("x"+i.toString()).setBins(xBinNumber.intValue());
//			ngonVariableMap.get("y"+i.toString()).setBins(yBinNumber.intValue());
//			ngonVariableMap.get("theta"+i.toString()).setBins(thetaBinNumber.intValue());
//
//		}
//		
//	}


	@Override
	public String getLabel(){
		return "Ngons";
	}
	@Override
	protected void init() {
		this.settingsVariables.put("N", new SettingsLong()); // when n changes, make a new ngon variables settings. 
		// might have an inner class "N" that extends SettingsLong and overrides action performed
		
//		this.settingsVariables.put("", Settings);
//		this.settingsVariables.put("", Settings);
//		this.settingsVariables.put("", Settings);
		this.settingsVariables.put("rotationStep", new SettingsDouble());
		this.settingsVariables.put("rotationStart", new SettingsDouble());
		this.settingsVariables.put("rotationStop", new SettingsDouble());
		this.settingsVariables.put("kClosestMinutia", new SettingsLong());
	}


}