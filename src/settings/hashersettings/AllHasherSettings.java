package settings.hashersettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.AllSettings;
import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.coordinatorsettings.histogramcoordinatorsettings.AllHistogramCoordinatorSettings;
import settings.coordinatorsettings.indexingcoordinatorsettings.AllIndexingCoordinatorSettings;
import settings.coordinatorsettings.matchingcoordinatorsettings.AllMatchingCoordinatorSettings;
import settings.modalitysettings.AllModalitySettings;

public class AllHasherSettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;
	
	
	public HasherSettings hasher(){
		return (HasherSettings) this.settingsVariables.get(this.variableString);
	}
	

	//Singleton
	private AllHasherSettings() {
	}
	
	@Override
	protected void addSettings(){
		this.variableString = "Hasher";
		this.settingsVariables.put(this.variableString, ShortcutFuzzyVaultSettings.getInstance());
	}
	
	private static AllHasherSettings instance;
	public static AllHasherSettings getInstance(){
		if(instance == null){
			instance = new AllHasherSettings();
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
	


	public static String getHasher() {
		return instance.hasher().getHasher();
	}



	@Override
	protected void addALLOptions() {
		this.addToOptions(FuzzyVaultSettings.getInstance());
		this.addToOptions(StraightHasherSettings.getInstance());
		this.addToOptions(ShortcutFuzzyVaultSettings.getInstance());
	}
}
