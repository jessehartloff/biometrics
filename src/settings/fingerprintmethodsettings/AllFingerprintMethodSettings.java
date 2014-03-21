package settings.fingerprintmethodsettings;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.AllSettings;
import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.hashersettings.ShortcutFuzzyVaultSettings;
import settings.hashersettings.AllHasherSettings;
import settings.hashersettings.StraightHasherSettings;
import settings.settingsvariables.SettingsString;
import system.method.fingerprintmethods.FingerprintMethod;

public class AllFingerprintMethodSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;


	////////// add new fingerprint methods here //////////
		@Override
		protected void addALLOptions() {
			this.addToOptions(NgonAllRotationsSettings.getInstance());
			this.addToOptions(TripletsOfTrianglesAllRotationsSettings.getInstance());
			this.addToOptions(NgonSettings.getInstance());
			this.addToOptions(PRINTSettings.getInstance());
			this.addToOptions(TriangleSettings.getInstance());
			this.addToOptions(TripletsOfTrianglesSettings.getInstance());
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
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
		out.writeInt(instance.settingsBox.getSelectedIndex());
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
		instance.currentIndex = in.readInt();
	}



	public static String getFingerprintMethod() {
		FingerprintMethodSettings method = (FingerprintMethodSettings) instance.settingsVariables.get("fingerprintMethod");
		return method.getMethodString();
	}


//	@Override
//	protected JPanel makeJPanel(){
//		JPanel panel = super.makeJPanel();
//		//panel.setLayout(new GridLayout(2,1));
//		return panel;
//	}

	@Override
	protected void addSettings() {
		this.variableString = "fingerprintMethod";
		this.settingsVariables.put(this.variableString, TriangleSettings.getInstance());
	}



}
