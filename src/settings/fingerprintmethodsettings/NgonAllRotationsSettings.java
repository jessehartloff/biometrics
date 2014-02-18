package settings.fingerprintmethodsettings;

import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsLong;

public class NgonAllRotationsSettings extends NgonSettings{

	private static final long serialVersionUID = 1L;
	
	private static NgonAllRotationsSettings instance;
	protected NgonAllRotationsSettings(){}
	public static NgonAllRotationsSettings getInstance(){
		if(instance == null){
			instance = new NgonAllRotationsSettings();
			
		}
		return instance;
	}

	
	@Override
	public String getMethodString() {
		return "NGONSALLROTATIONS";
	}
	
	@Override
	public String getLabel(){
		return "Ngons all rotations";
	}
	
	
	@Override
	protected void init() {
		super.init();
	}
}
