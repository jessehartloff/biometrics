package settings.modalitysettings.methodsettings.fingerprintmethodsettings;

import javax.swing.JPanel;

import settings.Settings;
import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsLong;
import settings.settingsvariables.SettingsMethodVariable;

public class NgonVariablesSettings extends Settings{

	private static final long serialVersionUID = 1L;
	
	// TODO

	private static NgonVariablesSettings instance;
	private NgonVariablesSettings(int n){
		this.settingsVariables.put("theta0", new SettingsMethodVariable());
		for(Long i = 1L; i < n; i++){
			this.settingsVariables.put("x" + i.toString(), new SettingsMethodVariable());
			this.settingsVariables.put("y" + i.toString(), new SettingsMethodVariable());
			this.settingsVariables.put("theta" + i.toString(), new SettingsMethodVariable());
		}
	}
	public static NgonVariablesSettings getInstance(){
		if(instance == null){
			instance = new NgonVariablesSettings(3); //TODO
		}
		return instance;
	}


	
	public SettingsMethodVariable getMinutiaComponentVariable(String component, Long i){
		return (SettingsMethodVariable) this.settingsVariables.get(component+i.toString());
	}
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
	
}
