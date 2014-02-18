package settings.fingerprintmethodsettings;

import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.AllHasherSettings;
import settings.hashersettings.StraightHasherSettings;
import settings.settingsvariables.SettingsString;
import system.method.fingerprintmethods.FingerprintMethod;

public class AllFingerprintMethodSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;


	////////// add new fingerprint methods here //////////
		@Override
		protected void addALLOptions() {
			this.addToOptions(NgonSettings.getInstance());
			this.addToOptions(NgonAllRotationsSettings.getInstance());
			this.addToOptions(TriangleSettings.getInstance());
			this.addToOptions(TripletsOfTrianglesSettings.getInstance());
			this.addToOptions(TripletsOfTrianglesAllRotationsSettings.getInstance());
			this.addToOptions(PathSettings.getInstance());
			this.addToOptions(MinutiaSettings.getInstance());
		}
		
		
		
	//Singleton
	private AllFingerprintMethodSettings(){
	}
	
	private static AllFingerprintMethodSettings instance;
	public static AllFingerprintMethodSettings getInstance(){
		if(instance == null){
			instance = new AllFingerprintMethodSettings();
		}
		return instance;
	}
	//
	
	
	public static String getFingerprintMethod() {
		FingerprintMethodSettings method = (FingerprintMethodSettings) instance.settingsVariables.get("fingerprintMethod");
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



}
