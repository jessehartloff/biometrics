package settings.coordinatorsettings.matchingcoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.settingsvariables.SettingsLong;

public class DefaultTestingPrequantizedSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "DEFAULTTESTINGPREQUANTIZED";
	}

	@Override
	protected void addSettings() {
		this.settingsVariables.put("TestGenerator", AllTestGeneratorSettings.getInstance());
		this.settingsVariables.put("KeySize", new SettingsLong(0));
	}

	public SettingsLong keySize(){
		return (SettingsLong) this.settingsVariables.get("KeySize");
	}
	
	//Singleton
	private static DefaultTestingPrequantizedSettings instance;
	private DefaultTestingPrequantizedSettings(){
	}
	public static DefaultTestingPrequantizedSettings getInstance(){
		if(instance == null){
			instance = new DefaultTestingPrequantizedSettings();
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
		return "Default Testing Prequantized";
	}

}
