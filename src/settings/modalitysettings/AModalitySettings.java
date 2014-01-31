package settings.modalitysettings;

import settings.Settings;
import settings.UsersIO;
import settings.settingsvariables.SettingsString;
import system.allcommonclasses.commonstructures.Users;
import system.method.Method;

public abstract class AModalitySettings extends Settings{

	
	public Users getTrainingUsers() {
		SettingsString train = (SettingsString) this.settingsVariables.get("trainingDataset");
		return UsersIO.getUsers(train.getValue()); // TODO look into training somewhere else
	}
	
	
	public Users getTestingUsers() {
		SettingsString test  = (SettingsString) this.settingsVariables.get("testingDataset");
		return UsersIO.getUsers(test.getValue()); // TODO look into training somewhere else
	}
	
//	public abstract Method getMethod();
	
}
