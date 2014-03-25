package settings.fingerprintmethodsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;

public class NgonsSingleEnrollAllRotationsSettings extends NgonAllRotationsSettings{

	private static final long serialVersionUID = 1L;
	
	private static NgonsSingleEnrollAllRotationsSettings instance;
	protected NgonsSingleEnrollAllRotationsSettings(){}
	public static NgonsSingleEnrollAllRotationsSettings getInstance(){ //TODO - Tom changed from NgonsAllRotations to NgonsSingleEnrollAllRotations
		if(instance == null){
			instance = new NgonsSingleEnrollAllRotationsSettings();
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
	public String getMethodString() {
		return "NGONSSINGLEENROLLALLROTATIONS";
	}
	
	@Override
	public String getLabel(){
		return "Ngons single enroll all rotations";
	}
}
