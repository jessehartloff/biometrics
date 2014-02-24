package settings.irismethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.FingerprintMethodSettings;
import settings.fingerprintmethodsettings.MinutiaSettings;
import settings.fingerprintmethodsettings.NgonAllRotationsSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.fingerprintmethodsettings.PRINTSettings;
import settings.fingerprintmethodsettings.PathSettings;
import settings.fingerprintmethodsettings.TriangleSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesAllRotationsSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesSettings;

public class AllIrisMethodSettings extends ComboBoxSettings{
	private static final long serialVersionUID = 1L;


	////////// add new iris methods here //////////
		@Override
		protected void addALLOptions() {
			this.addToOptions(IrisSegmentationSettings.getInstance());
		}
		
		
		
	//Singleton
	private AllIrisMethodSettings(){
	}
	private static AllIrisMethodSettings instance;
	public static AllIrisMethodSettings getInstance(){
		if(instance == null){
			instance = new AllIrisMethodSettings();
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



	public static String getIrisMethod() {
		IrisMethodSettings method = (IrisMethodSettings) instance.settingsVariables.get("irisMethod");
		return method.getMethodString();
	}



	@Override
	protected void addSettings() {
		this.variableString = "irisMethod";
		this.settingsVariables.put(this.variableString, IrisSegmentationSettings.getInstance());
	}



}
