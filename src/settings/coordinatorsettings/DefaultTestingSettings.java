package settings.coordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.modalitysettings.AllModalitySettings;

public class DefaultTestingSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "DEFAULTTESTING";
	}

	@Override
	protected void addSettings() {
		this.settingsVariables.put("TestGenerator", AllTestGeneratorSettings.getInstance());
	}

	
	//Singleton
	private static DefaultTestingSettings instance;
	private DefaultTestingSettings(){
	}
	public static DefaultTestingSettings getInstance(){
		if(instance == null){
			instance = new DefaultTestingSettings();
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
		return "Default Testing";
	}

}
