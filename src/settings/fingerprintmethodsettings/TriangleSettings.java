package settings.fingerprintmethodsettings;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.*;
import system.method.fingerprintmethods.FingerprintMethod;

public class TriangleSettings extends FingerprintMethodSettings{

	private static final long serialVersionUID = 1L;
	
	private static TriangleSettings instance;
	protected TriangleSettings(){}
	public static TriangleSettings getInstance(){
		if(instance == null){
			instance = new TriangleSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
	}
	
	
	public SettingsMethodVariable theta0(){
		return (SettingsMethodVariable) this.settingsVariables.get("theta0");
	}
	
	public SettingsMethodVariable x1(){
		return (SettingsMethodVariable) this.settingsVariables.get("x1");
	}
	
	public SettingsMethodVariable y1(){
		return (SettingsMethodVariable) this.settingsVariables.get("y1");
	}
	
	public SettingsMethodVariable theta1(){
		return (SettingsMethodVariable) this.settingsVariables.get("theta1");
	}
	
	public SettingsMethodVariable x2(){
		return (SettingsMethodVariable) this.settingsVariables.get("x2");
	}
	
	public SettingsMethodVariable y2(){
		return (SettingsMethodVariable) this.settingsVariables.get("y2");
	}
	
	public SettingsMethodVariable theta2(){
		return (SettingsMethodVariable) this.settingsVariables.get("theta2");
	}
	
	
	public SettingsDouble rotationStep(){
		return (SettingsDouble) this.settingsVariables.get("rotationStep");
	}

	public SettingsDouble rotationStart(){
		return (SettingsDouble) this.settingsVariables.get("rotationStart");
	}

	public SettingsDouble rotationStop(){
		return (SettingsDouble) this.settingsVariables.get("rotationStop");
	}

	public SettingsLong minimumPointsForTripletOfTriangles(){
		return (SettingsLong) this.settingsVariables.get("minimumPointsForTripletOfTriangles");
	}

	public SettingsLong kClosestMinutia(){
		return (SettingsLong) this.settingsVariables.get("kClosestMinutia");
	}

	public SettingsLong kClosestTriangles(){
		return (SettingsLong) this.settingsVariables.get("kClosestTriangles");
	}


	@Override
	public String getMethodString() {
		return "TRIANGLES";
	}

	@Override
	public String getLabel(){
		return "Triangles";
	}
	@Override
	protected void addSettings() {
		this.settingsVariables.put("theta0", new SettingsMethodVariable(8));
		this.settingsVariables.put("x1", new SettingsMethodVariable(8));
		this.settingsVariables.put("y1", new SettingsMethodVariable(8));
		this.settingsVariables.put("theta1", new SettingsMethodVariable(8));
		this.settingsVariables.put("x2", new SettingsMethodVariable(8));
		this.settingsVariables.put("y2", new SettingsMethodVariable(8));
		this.settingsVariables.put("theta2", new SettingsMethodVariable(8));
		this.settingsVariables.put("rotationStep", new SettingsDouble(5.0));
		this.settingsVariables.put("rotationStart", new SettingsDouble(-50.0));
		this.settingsVariables.put("rotationStop", new SettingsDouble(50.0));
		this.settingsVariables.put("kClosestMinutia", new SettingsLong(2));
		
	}
	
}
