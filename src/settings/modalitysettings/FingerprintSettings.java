package settings.modalitysettings;


import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.UsersIO;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.MinutiaSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.fingerprintmethodsettings.PathSettings;
import settings.fingerprintmethodsettings.TriangleSettings;
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



	

	@Override
	public String getLabel(){
		return "Fingerprints";
	}
	@Override
	protected void init() {
		this.settingsVariables.put("trainingDataset", new FingerprintDatasetSettings()); 
		this.settingsVariables.put("testingDataset", new FingerprintDatasetSettings()); 
		this.settingsVariables.put("FingerprintMethod", AllFingerprintMethodSettings.getInstance());
	}


	

}
