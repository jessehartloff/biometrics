package system.hasher;

import settings.hashersettings.AllHasherSettings;
import system.hasher.Hasher;
import system.hasher.ShortcutFuzzyVault;
import system.hasher.StraightHasher;
import system.hasher.fuzzyvault.FuzzyVault;

public class HasherFactory {

	public static Hasher makeHasher() {
		switch (HasherEnumerator.valueOf(AllHasherSettings.getHasher())) {
		case STRAIGHTHASHER:
			return new StraightHasher();
		case SHORTCUTFUZZYVAULT:
			return new ShortcutFuzzyVault();
		case FUZZYVAULT:
			return new FuzzyVault();
		default:
			System.out.println("You didn't provide an appropriate hasher");
			return new StraightHasher();
		}
	}

	public enum HasherEnumerator {
		FUZZYVAULT, STRAIGHTHASHER, SHORTCUTFUZZYVAULT;
	}
}