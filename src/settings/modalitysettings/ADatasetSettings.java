package settings.modalitysettings;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.UsersIO;
import settings.coordinatorsettings.NoCoordinator;
import settings.settingsvariables.SettingsDropDownItem;
import settings.settingsvariables.SettingsString;
import system.allcommonclasses.commonstructures.Users;

public abstract class ADatasetSettings extends ComboBoxSettings{


	public String getDataset(){
		SettingsDropDownItem dropDownItem = (SettingsDropDownItem) this.settingsVariables.get(this.variableString);
		return dropDownItem.getValue();
	}
	
	
	@Override
	protected void init() {
		this.variableString = "dataset";
		this.settingsVariables.put(this.variableString, new SettingsDropDownItem("no dataset!"));
	}
	
	
}
