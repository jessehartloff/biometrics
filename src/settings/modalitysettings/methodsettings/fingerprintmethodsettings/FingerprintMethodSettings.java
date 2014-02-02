package settings.modalitysettings.methodsettings.fingerprintmethodsettings;

import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.HasherSettings;
import settings.hashersettings.StraightHasherSettings;
import settings.modalitysettings.methodsettings.AMethodSettings;
import settings.settingsvariables.SettingsString;
import system.method.fingerprintmethods.FingerprintMethod;

public class FingerprintMethodSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;


	//Singleton
	private FingerprintMethodSettings(){
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
	protected JPanel makeJPanel(){
		JPanel panel = super.makeJPanel();
		//panel.setLayout(new GridLayout(2,1));
		return panel;
	}

	@Override
	protected void init() {
		this.variableString = "fingerprintMethod";
		this.settingsVariables.put("fingerprintMethod", TriangleSettings.getInstance());
	}



	@Override
	protected void addALLOptions() {
		this.addToOptions(TriangleSettings.getInstance());
		this.addToOptions(NgonSettings.getInstance());
//		this.addToOptions(NgonAllRotationsSettings.getInstance());
		this.addToOptions(PathSettings.getInstance());
		this.addToOptions(MinutiaSettings.getInstance());
	}
	
	
}
