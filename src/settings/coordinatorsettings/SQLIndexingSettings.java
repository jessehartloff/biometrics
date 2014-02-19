package settings.coordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;

public class SQLIndexingSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "SQLINDEXING";
	}

	@Override
	protected void addSettings() {
	}

	
	//Singleton
	private static SQLIndexingSettings instance;
	private SQLIndexingSettings(){
	}
	public static SQLIndexingSettings getInstance(){
		if(instance == null){
			instance = new SQLIndexingSettings();
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
		return "SQL Indexing";
	}

}
