package settings.quantizersettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.fingerprintmethodsettings.PathSettings;

public class BinningSettings extends QuantizerSettings{

	@Override
	protected void addSettings() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFactoryString() {
		return "BINNING";
	}

	@Override
	public String getLabel(){
		return "Binning";
	}
	
	private static BinningSettings instance;
	private BinningSettings(){}
	public static BinningSettings getInstance(){
		if(instance == null){
			instance = new BinningSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
	}
}
