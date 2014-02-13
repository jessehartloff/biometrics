package settings.modalitysettings;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import settings.Settings;
import settings.UsersIO;
import settings.settingsvariables.SettingsString;
import system.allcommonclasses.commonstructures.Users;
import system.method.Method;

public abstract class AModalitySettings extends Settings{

	
	protected JPanel makeJPanel() {
		JPanel toRet = new JPanel();
		//toRet.setLayout(new GridLayout(2,1));
		toRet.add(this.thisJPanel(), BorderLayout.WEST);
		toRet.add(this.makeChildrenJPanel(), BorderLayout.EAST);

		return toRet;
	}
	
	public Users getTrainingUsers() {
		ADatasetSettings train = (ADatasetSettings) this.settingsVariables.get("trainingDataset");
		return UsersIO.getUsers(train.getDataset()); // TODO look into training somewhere else
	}
	
	
	public Users getTestingUsers() {
		ADatasetSettings test  = (ADatasetSettings) this.settingsVariables.get("testingDataset");
		return UsersIO.getUsers(test.getDataset()); // TODO look into training somewhere else
	}
	
//	public abstract Method getMethod();
	
}
