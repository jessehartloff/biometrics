package settings.quantizersettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.FingerprintMethodSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FaceSettings;
import settings.modalitysettings.FingerprintSettings;
import settings.modalitysettings.IrisSettings;
import settings.modalitysettings.PizzaSettings;

public class AllQuantizerSettings extends ComboBoxSettings{

	public static String getQuantizer() {
		QuantizerSettings quantizer = (QuantizerSettings) instance.settingsVariables.get("Quantizer");
		return quantizer.getFactoryString();
	}


	//Singleton
	private static AllQuantizerSettings instance;
	private AllQuantizerSettings() {
	}
	public static AllQuantizerSettings getInstance(){
		if(instance == null){
			instance = new AllQuantizerSettings();
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
	
	@Override
	protected void addSettings() {
		this.variableString = "Quantizer";
		this.settingsVariables.put(this.variableString, BinningSettings.getInstance());
	}
	
	
	@Override
	protected void addALLOptions() {
		this.addToOptions(BinningSettings.getInstance());
		this.addToOptions(PCASettings.getInstance());
	}

}
