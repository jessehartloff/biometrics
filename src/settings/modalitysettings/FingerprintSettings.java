package settings.modalitysettings;


import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.UsersIO;
import settings.modalitysettings.methodsettings.fingerprintmethodsettings.FingerprintMethodSettings;
import settings.modalitysettings.methodsettings.fingerprintmethodsettings.MinutiaSettings;
import settings.modalitysettings.methodsettings.fingerprintmethodsettings.NgonSettings;
import settings.modalitysettings.methodsettings.fingerprintmethodsettings.PathSettings;
import settings.modalitysettings.methodsettings.fingerprintmethodsettings.TriangleSettings;
import settings.settingsvariables.SettingsString;
import system.allcommonclasses.commonstructures.Users;
import system.method.Method;

public class FingerprintSettings extends AModalitySettings{

	private static final long serialVersionUID = 1L;

//	@Override
//	public static Method getMethod() {
//		FingerprintMethodSettings fingerprintMethodSettings = (FingerprintMethodSettings) this.settingsVariables.get("FingerprintMethod");
//		return fingerprintMethodSettings.getMethod();
//	}
	
	

	//Singleton
	private static FingerprintSettings instance;
	private FingerprintSettings() {
		this.settingsVariables.put("FingerprintMethod", FingerprintMethodSettings.getInstance());
		this.settingsVariables.put("trainingDataset", new SettingsString("FVC2002DB1")); //eventually a file list from dataset folder
		this.settingsVariables.put("testingDataset", new SettingsString("FVC2002DB2")); //eventually a file list from dataset folder
	}
	public static FingerprintSettings getInstance(){
		if(instance == null){
			instance = new FingerprintSettings();
		}
		return instance;
	}

	
//	@Override
//	protected JPanel thisJPanel() {
//		
//
//		
//		// TODO add datasets
//		
//	}

	

	@Override
	public String getLabel(){
		return "Fingerprints";
	}
	

}
