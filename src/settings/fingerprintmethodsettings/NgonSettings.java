package settings.fingerprintmethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JPanel;

import settings.Settings;
import settings.settingsvariables.*;

public class NgonSettings extends FingerprintMethodSettings{
	
	private static final long serialVersionUID = 1L;
	
	

	private static NgonSettings instance;
	protected NgonSettings(){}
	public static NgonSettings getInstance(){
		if(instance == null){
			instance = new NgonSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
	}
	
	// getters

	public SettingsDouble rotationStep(){
		return (SettingsDouble) this.settingsVariables.get("rotationStep");
	}
	public SettingsDouble rotationStart(){
		return (SettingsDouble) this.settingsVariables.get("rotationStart");
	}
	public SettingsDouble rotationStop(){
		return (SettingsDouble) this.settingsVariables.get("rotationStop");
	}
	public SettingsLong kClosestMinutia(){
		return (SettingsLong) this.settingsVariables.get("kClosestMinutia");
	}
	public SettingsLong n(){
		return (SettingsLong) this.settingsVariables.get("N");
	}	
	public SettingsLong thetaBins(){
		return (SettingsLong) this.settingsVariables.get("thetaBins");
	}
	public SettingsLong xBins(){
		return (SettingsLong) this.settingsVariables.get("xBins");
	}
	public SettingsLong yBins(){
		return (SettingsLong) this.settingsVariables.get("yBins");
	}


	@Override
	public String getMethodString() {
		return "NGONSALLROTATIONS";
	}

	private String componentToString(String componentType, Long componentNumber){
		return componentType + componentNumber.toString();
	}
	
	public SettingsMethodVariable getMinutiaComponentVariable(String component, Long i){
		return (SettingsMethodVariable) this.settingsVariables.get(this.componentToString(component, i));
	}

	public void setAllNumberOfBins() {
		this.settingsVariables.put(this.componentToString("theta", 0L), new SettingsMethodVariable(this.thetaBins().getValue()));
		for(Long i = 1L; i < this.n().getValue(); i++){
			this.settingsVariables.put(this.componentToString("x", i), new SettingsMethodVariable(this.xBins().getValue()));
			this.settingsVariables.put(this.componentToString("y", i), new SettingsMethodVariable(this.yBins().getValue()));
			this.settingsVariables.put(this.componentToString("theta", i), new SettingsMethodVariable(this.thetaBins().getValue()));
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
		return "Ngons";
	}
	
	
	@Override
	protected void addSettings() {
		this.settingsVariables.put("N", new SettingsLong(3));
		this.settingsVariables.put("kClosestMinutia", new SettingsLong(4));
		this.settingsVariables.put("thetaBins", new SettingsLong(8));
		this.settingsVariables.put("xBins", new SettingsLong(8));
		this.settingsVariables.put("yBins", new SettingsLong(8));
		this.settingsVariables.put("rotationStep", new SettingsDouble(5.0));
		this.settingsVariables.put("rotationStart", new SettingsDouble(-50.0));
		this.settingsVariables.put("rotationStop", new SettingsDouble(50.0));
	}


}