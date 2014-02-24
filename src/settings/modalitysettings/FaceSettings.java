package settings.modalitysettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;

public class FaceSettings extends Settings{

	//LATER Face settings
	
	//Singleton
	private static FaceSettings instance;
	private FaceSettings() {}
	public static FaceSettings getInstance(){
		if(instance == null){
			instance = new FaceSettings();
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
		return "Face";
	}

	@Override
	protected void addSettings() {
		
	}
}
