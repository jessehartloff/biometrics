package settings.coordinatorsettings.multiservercoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.settingsvariables.SettingsLong;
import settings.settingsvariables.SettingsNote;

public class SuperTestingMetaClientSettings extends CoordinatorSettings{
	
	@Override
	public String getCoordinator() {
		return "SUPERTESTINGMETACLIENT";
	}

	@Override
	protected void addSettings() {
//		this.settingsVariables.put("Port Number", new SettingsLong(10000));
		this.settingsVariables.put("Port Number", new SettingsLong(8080));
		this.settingsVariables.put("TestGenerator", AllTestGeneratorSettings.getInstance());
		this.settingsVariables.put("Note", new SettingsNote("Must also set the method"));
	}

	public SettingsLong portNumber(){
		return (SettingsLong) this.settingsVariables.get("Port Number");
	}

	
	//Singleton
	private static SuperTestingMetaClientSettings instance;
	private SuperTestingMetaClientSettings(){
	}
	public static SuperTestingMetaClientSettings getInstance(){
		if(instance == null){
			instance = new SuperTestingMetaClientSettings();
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
	public String getLabel(){
		return "SuperTestingMetaClient";
	}

}
