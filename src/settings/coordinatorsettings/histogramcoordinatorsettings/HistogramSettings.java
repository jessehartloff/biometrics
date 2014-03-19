package settings.coordinatorsettings.histogramcoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.coordinatorsettings.CoordinatorSettings;

public class HistogramSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "HISTOGRAM";
	}

	@Override
	protected void addSettings() {
	}

	
	//Singleton
	private static HistogramSettings instance;
	private HistogramSettings(){
	}
	public static HistogramSettings getInstance(){
		if(instance == null){
			instance = new HistogramSettings();
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
		return "Histogram";
	}
}
