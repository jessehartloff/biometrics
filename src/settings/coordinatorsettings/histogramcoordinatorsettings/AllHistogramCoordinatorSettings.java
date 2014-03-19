package settings.coordinatorsettings.histogramcoordinatorsettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.NoCoordinator;
import settings.hashersettings.HasherSettings;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.AllHasherSettings;
import settings.hashersettings.StraightHasherSettings;
import settings.settingsvariables.SettingsString;

public class AllHistogramCoordinatorSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;
	

	
	
	public static String getHistogramCoordinator(){
		CoordinatorSettings coordinatorSettings = (CoordinatorSettings) instance.settingsVariables.get(instance.variableString);
		return coordinatorSettings.getCoordinator();
	}
	
	
	//Singleton
	private static AllHistogramCoordinatorSettings instance;
	private AllHistogramCoordinatorSettings() {}
	public static AllHistogramCoordinatorSettings getInstance(){
		if(instance == null){
			instance = new AllHistogramCoordinatorSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
		out.writeInt(instance.settingsBox.getSelectedIndex());
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
		instance.currentIndex = in.readInt();
	}


	
	@Override
	protected void addSettings() {
		this.variableString = "histogram";
		this.settingsVariables.put("histogram", NoCoordinator.getInstance());
	}

	@Override
	protected void addALLOptions() {
		this.addToOptions(HistogramSettings.getInstance());
		this.addToOptions(NoCoordinator.getInstance());
	}


}
