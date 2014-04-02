package settings.coordinatorsettings.multiservercoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.settingsvariables.SettingsLong;
import settings.settingsvariables.SettingsString;

public class ClientSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "CLIENT";
	}

	@Override
	protected void addSettings() {
		this.settingsVariables.put("Port Number", new SettingsLong(10000));
		this.settingsVariables.put("Port NumberL", new SettingsLong(10000));
		this.settingsVariables.put("IP Address", new SettingsString("localhost"));

	}

	public SettingsLong portNumber(){
		return (SettingsLong) this.settingsVariables.get("Port Number");
	}
	
	public SettingsLong portNumberL(){
		return (SettingsLong) this.settingsVariables.get("Port NumberL");
	}
	
	public SettingsString ip(){
		return (SettingsString) this.settingsVariables.get("IP Address");
	}
	
	//Singleton
	private static ClientSettings instance;
	private ClientSettings(){
	}
	public static ClientSettings getInstance(){
		if(instance == null){
			instance = new ClientSettings();
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
		return "Client";
	}

}
