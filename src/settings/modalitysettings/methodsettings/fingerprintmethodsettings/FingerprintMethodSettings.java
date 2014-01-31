package settings.modalitysettings.methodsettings.fingerprintmethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.HasherSettings;
import settings.hashersettings.StraightHasherSettings;
import settings.modalitysettings.methodsettings.AMethodSettings;
import settings.settingsvariables.SettingsString;
import system.method.fingerprintmethods.FingerprintMethod;

public class FingerprintMethodSettings extends AMethodSettings{

	private static final long serialVersionUID = 1L;


	//Singleton
	private FingerprintMethodSettings(){
		this.settingsVariables.put("fingerprintMethod", PathSettings.getInstance());
	}
	
	private static FingerprintMethodSettings instance;
	public static FingerprintMethodSettings getInstance(){
		if(instance == null){
			instance = new FingerprintMethodSettings();
		}
		return instance;
	}
	//
	
	
	public static String getFingerprintMethod() {
		AFingerprintMethodSettings method = (AFingerprintMethodSettings) instance.settingsVariables.get("fingerprintMethod");
		return method.getMethodString();
	}

	
	@Override
	protected JPanel thisJPanel() {
		
		JPanel panel = new JPanel();

		Settings[] settingsList = new Settings[] {
				TriangleSettings.getInstance(),
				NgonSettings.getInstance(),		
//				NgonAllRotationsSettings.getInstance(),
				PathSettings.getInstance(),
				MinutiaSettings.getInstance(),
		};
		
		JComboBox settingsBox = new JComboBox(settingsList);
		settingsBox.addActionListener(new SettingsComboBoxActionListener(this, "fingerprintMethod"));
		settingsBox.setRenderer(new SettingsRenderer());

		panel.add(settingsBox);
		
		return panel;
	}


	
	
}
