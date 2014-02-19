package settings.modalitysettings;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import settings.Settings;
import settings.UsersIO;
import settings.settingsvariables.SettingsString;
import system.allcommonclasses.commonstructures.Users;
import system.method.Method;

public abstract class ModalitySettings extends Settings{

	
	protected JPanel makeJPanel() {
		JPanel toRet = new JPanel();
		toRet.add(this.thisJPanel(), BorderLayout.WEST);
		toRet.add(this.makeChildrenJPanel(), BorderLayout.EAST);
		return toRet;
	}
	
	
	public Users getTrainingUsers() {
		DatasetSettings train = (DatasetSettings) this.settingsVariables.get("trainingDataset");
		return UsersIO.getUsers(train.getDataset());
	}
	
	
	public Users getTestingUsers() {
		DatasetSettings test  = (DatasetSettings) this.settingsVariables.get("testingDataset");
		return UsersIO.getUsers(test.getDataset());
	}
	
//	public abstract Method getMethod();
	
}
