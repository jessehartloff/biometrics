package settings.fingerprintmethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsLong;

public class NgonAllRotationsSettings extends NgonSettings{

	private static final long serialVersionUID = 1L;
	
	private static NgonAllRotationsSettings instance;
	protected NgonAllRotationsSettings(){}
	public static NgonAllRotationsSettings getInstance(){
		if(instance == null){
			instance = new NgonAllRotationsSettings();
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
	public String getMethodString() {
		return "NGONSALLROTATIONS";
	}
	
	@Override
	public String getLabel(){
		return "Ngons all rotations";
	}
	
	
//	@Override
//	protected void addSettings() {
//		super.addSettings();
//	}
	
}
