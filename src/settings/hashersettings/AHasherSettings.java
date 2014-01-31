package settings.hashersettings;

import settings.Settings;
import system.hasher.Hasher;
import system.method.fingerprintmethods.FingerprintMethod;

public abstract class AHasherSettings extends Settings{

	public abstract String getHasher();
	
}
