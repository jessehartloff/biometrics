package settings.hashersettings;

import settings.Settings;
import settings.settingsvariables.SettingsLong;
import system.hasher.Hasher;

public class StraightHasherSettings extends AHasherSettings{

	private static final long serialVersionUID = 1L;
	
	
	//Singleton
	private static StraightHasherSettings instance;
	private StraightHasherSettings(){
	}
	public static StraightHasherSettings getInstance(){
		if(instance == null){
			instance = new StraightHasherSettings();
		}
		return instance;
	}
	
	
	@Override
	public String getHasher() {
		return "STRAIGHTHASHER";
	}


	@Override
	public String getLabel(){
		return "Straight Hashing";
	}
	@Override
	protected void init() {
		
	}
	
}
