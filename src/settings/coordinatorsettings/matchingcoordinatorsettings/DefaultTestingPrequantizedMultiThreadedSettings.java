package settings.coordinatorsettings.matchingcoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.settingsvariables.SettingsLong;

public class DefaultTestingPrequantizedMultiThreadedSettings  extends CoordinatorSettings {

	@Override
	public String getCoordinator() {
		return "DEFAULTTESTINGPREQUANTIZEDMULTITHREADED";
	}

	@Override
	protected void addSettings() {
		this.settingsVariables.put("TestGenerator", AllTestGeneratorSettings.getInstance());
		this.settingsVariables.put("Number of Threads For Jesse", new SettingsLong(9));
	}
	
	public SettingsLong numberOfThreads(){
		return (SettingsLong) this.settingsVariables.get("Number of Threads For Jesse");
	}


	//Singleton
	private static DefaultTestingPrequantizedMultiThreadedSettings instance;
	private DefaultTestingPrequantizedMultiThreadedSettings(){
	}
	public static DefaultTestingPrequantizedMultiThreadedSettings getInstance(){
		if(instance == null){
			instance = new DefaultTestingPrequantizedMultiThreadedSettings();
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
		return "Default Testing Prequantized MultiThreaded";
	}

}