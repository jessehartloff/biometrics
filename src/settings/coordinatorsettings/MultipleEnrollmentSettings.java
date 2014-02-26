package settings.coordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.SettingsLong;

public class MultipleEnrollmentSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "MULTIPLEENROLLMENT";
	}

	@Override
	protected void addSettings() {
		this.settingsVariables.put("readingsForEnrollment", new SettingsLong(4));
	}

	
	public SettingsLong readingsForEnrollment(){
		return (SettingsLong) this.settingsVariables.get("readingsForEnrollment");
	}
	
	//Singleton
	private static MultipleEnrollmentSettings instance;
	private MultipleEnrollmentSettings(){
	}
	public static MultipleEnrollmentSettings getInstance(){
		if(instance == null){
			instance = new MultipleEnrollmentSettings();
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
		return "Multiple Enrollment";
	}

}
