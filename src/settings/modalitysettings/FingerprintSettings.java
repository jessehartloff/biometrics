package settings.modalitysettings;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.UsersIO;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.MinutiaSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.fingerprintmethodsettings.PathSettings;
import settings.fingerprintmethodsettings.TriangleSettings;
import settings.settingsvariables.SettingsLong;
import settings.settingsvariables.SettingsString;
import system.allcommonclasses.commonstructures.Users;
import system.method.Method;

public class FingerprintSettings extends ModalitySettings{

	private static final long serialVersionUID = 1L;



	//Singleton
	private static FingerprintSettings instance;
	private FingerprintSettings() {}
	public static FingerprintSettings getInstance(){
		if(instance == null){
			instance = new FingerprintSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
	}


	public FingerprintDatasetSettings trainingDataset() {
		return (FingerprintDatasetSettings) this.settingsVariables.get("trainingDataset");
	}
	public FingerprintDatasetSettings testingDataset() {
		return (FingerprintDatasetSettings) this.settingsVariables.get("testingDataset");
	}

	public SettingsLong minimumMinutia() {
		return (SettingsLong) this.settingsVariables.get("minimumMinutia");
	}

	@Override
	public String getLabel(){
		return "Fingerprints";
	}
	
	@Override
	protected void addSettings() {
		this.settingsVariables.put("trainingDataset", new FingerprintDatasetSettings()); 
		this.settingsVariables.put("testingDataset", new FingerprintDatasetSettings()); 
		this.settingsVariables.put("minimumMinutia", new SettingsLong(10)); 
		this.settingsVariables.put("FingerprintMethod", AllFingerprintMethodSettings.getInstance());
	}



	

}
