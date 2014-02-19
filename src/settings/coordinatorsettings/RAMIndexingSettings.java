package settings.coordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;

public class RAMIndexingSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "RAMINDEXING";
	}

	@Override
	protected void addSettings() {
	}

	
	//Singleton
	private static RAMIndexingSettings instance;
	private RAMIndexingSettings(){
	}
	public static RAMIndexingSettings getInstance(){
		if(instance == null){
			instance = new RAMIndexingSettings();
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
		return "RAM Indexing";
	}
}
