package settings.coordinatorsettings.multiservercoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.settingsvariables.SettingsLong;

public class TestingSTMCSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "TESTINGSTMC";
	}

	@Override
	protected void addSettings() {
//		this.settingsVariables.put("Port Number", new SettingsLong(10000));
		this.settingsVariables.put("Port Number", new SettingsLong(8080));
	}

	public SettingsLong portNumber(){
		return (SettingsLong) this.settingsVariables.get("Port Number");
	}

	
	//Singleton
	private static TestingSTMCSettings instance;
	private TestingSTMCSettings(){
	}
	public static TestingSTMCSettings getInstance(){
		if(instance == null){
			instance = new TestingSTMCSettings();
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
		return "TestingSTMC";
	}

}
