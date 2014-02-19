package settings.modalitysettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.UsersIO;
import settings.coordinatorsettings.NoCoordinator;
import settings.settingsvariables.SettingsDropDownItem;
import settings.settingsvariables.SettingsString;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Minutia;

public abstract class DatasetSettings extends ComboBoxSettings{


	public String getDataset(){
		SettingsDropDownItem dropDownItem = (SettingsDropDownItem) this.settingsVariables.get(this.variableString);
		return dropDownItem.getValue();
	}
	
	
	@Override
	protected void addSettings() {
		this.variableString = "dataset";
		this.settingsVariables.put(this.variableString, new SettingsDropDownItem("no dataset!"));
	}
	
	

	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.settingsVariables);
		out.writeInt(this.settingsBox.getSelectedIndex());
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		this.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
		this.currentIndex = in.readInt();
	}
	
	
}
