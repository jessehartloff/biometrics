package settings.hashersettings;

import settings.Settings;
import system.hasher.Hasher;

public class StraightHasherSettings extends AHasherSettings{

	@Override
	public String getHasher() {
		return "STRAIGHTHASHER";
	}


	@Override
	public String getLabel(){
		return "Straight Hashing";
	}
	
}
