package settings.coordinatorsettings.multiservercoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.settingsvariables.SettingsLong;
import settings.settingsvariables.SettingsString;

public class ServerTwoSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "SERVER2";
	}

	@Override
	protected void addSettings() {
//		this.settingsVariables.put("Port Number", new SettingsLong(10002));
//		this.settingsVariables.put("IP Address", new SettingsString("localhost"));
		this.settingsVariables.put("Port Number", new SettingsLong(8080));
		this.settingsVariables.put("IP Address", new SettingsString("192.168.0.4"));
	}

	public SettingsLong portNumber(){
		return (SettingsLong) this.settingsVariables.get("Port Number");
	}
	
	public SettingsLong chaffPoints(){
		return (SettingsLong) this.settingsVariables.get("Chaff Points");
	}

	public SettingsString ip(){
		return (SettingsString) this.settingsVariables.get("IP Address");
	}
	
	//Singleton
	private static ServerTwoSettings instance;
	private ServerTwoSettings(){
	}
	public static ServerTwoSettings getInstance(){
		if(instance == null){
			instance = new ServerTwoSettings();
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
		return "Server 2";
	}

}
