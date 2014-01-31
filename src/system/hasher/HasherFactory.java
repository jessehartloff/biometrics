package system.hasher;

import settings.hashersettings.HasherSettings;
import system.hasher.Hasher;
import system.hasher.ShortcutFuzzyVault;
import system.hasher.StraightHasher;

public class HasherFactory{
	
	public static Hasher makeHasher() {
		switch(HasherEnumerator.valueOf(HasherSettings.getHasher())){
			case STRAIGHTHASHER:
				return new StraightHasher();
			case SHORTCUTFUZZYVAULT:
				return new ShortcutFuzzyVault();
			default:
				System.out.println("You didn't provide an appropriate hasher");
				return new StraightHasher();
		}		
	}
	
	public enum HasherEnumerator{
		STRAIGHTHASHER, SHORTCUTFUZZYVAULT;
	}
}