package settings.fingerprintmethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.SettingsLong;

public class TripletsOfTrianglesAllRotationsSettings extends TripletsOfTrianglesSettings{

	private static TripletsOfTrianglesAllRotationsSettings instance;
	protected TripletsOfTrianglesAllRotationsSettings(){}
	public static TripletsOfTrianglesAllRotationsSettings getInstance(){
		if(instance == null){
			instance = new TripletsOfTrianglesAllRotationsSettings();
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
	protected void addSettings() {
		super.addSettings();
	}
	

	@Override
	public String getMethodString() {
		return "TRIPLESOFTRIANGLESALLROTATIONS";
	}

	@Override
	public String getLabel(){
		return "Triples of Triangles all Rotations";
	}
}
