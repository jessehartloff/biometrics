package settings.modalitysettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.irismethodsettings.AllIrisMethodSettings;
import settings.settingsvariables.SettingsLong;

public class IrisSettings extends ModalitySettings{

	// TODO Jesse - Iris settings
	
	//Singleton
	private static IrisSettings instance;
	private IrisSettings() {}
	public static IrisSettings getInstance(){
		if(instance == null){
			instance = new IrisSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
	}
	
	@Override
	public String getLabel(){
		return "Iris";
	}
	
	

	public IrisDatasetSettings trainingDataset() {
		return (IrisDatasetSettings) this.settingsVariables.get("trainingDataset");
	}
	public IrisDatasetSettings testingDataset() {
		return (IrisDatasetSettings) this.settingsVariables.get("testingDataset");
	}



	
	@Override
	protected void addSettings() {
		this.settingsVariables.put("trainingDataset", new IrisDatasetSettings()); 
		this.settingsVariables.put("testingDataset", new IrisDatasetSettings()); 
		this.settingsVariables.put("IrisMethod", AllIrisMethodSettings.getInstance());
	}



	
	
}
