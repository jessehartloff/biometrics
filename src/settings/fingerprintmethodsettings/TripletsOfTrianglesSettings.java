package settings.fingerprintmethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsLong;

public class TripletsOfTrianglesSettings extends TriangleSettings{

	private static TripletsOfTrianglesSettings instance;
	protected TripletsOfTrianglesSettings(){}
	public static TripletsOfTrianglesSettings getInstance(){
		if(instance == null){
			instance = new TripletsOfTrianglesSettings();
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
		this.settingsVariables.put("minimumPointsForTripletOfTriangles", new SettingsLong(7));
		this.settingsVariables.put("kClosestTriangles", new SettingsLong(5));
	}
	

	@Override
	public String getMethodString() {
		return "TRIPLESOFTRIANGLES";
	}

	@Override
	public String getLabel(){
		return "Triples of Triangles";
	}
	
}
