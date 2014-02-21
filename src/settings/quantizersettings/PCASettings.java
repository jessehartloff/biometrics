package settings.quantizersettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.SettingsLong;

public class PCASettings extends QuantizerSettings{

	@Override
	protected void addSettings() {
		this.settingsVariables.put("number of components", new SettingsLong(5));
		this.settingsVariables.put("bits per component", new SettingsLong(8));
	}

	public SettingsLong numberOfComponents(){
		return (SettingsLong) this.settingsVariables.get("number of components");
	}
	
	public SettingsLong bitsPerComponent(){
		return (SettingsLong) this.settingsVariables.get("bits per component");
	}	

	@Override
	public String getFactoryString() {
		return "PCA";
	}

	@Override
	public String getLabel(){
		return "PCA";
	}
	
	private static PCASettings instance;
	private PCASettings(){}
	public static PCASettings getInstance(){
		if(instance == null){
			instance = new PCASettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
	}
}
