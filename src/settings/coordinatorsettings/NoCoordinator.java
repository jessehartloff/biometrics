package settings.coordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.hashersettings.FuzzyVaultSettings;

public class NoCoordinator extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "NONE";
	}

	@Override
	protected void addSettings() {
	}

	
	//Singleton
	private static NoCoordinator instance;
	private NoCoordinator(){
	}
	public static NoCoordinator getInstance(){
		if(instance == null){
			instance = new NoCoordinator();
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
		return "None";
	}
	
}
