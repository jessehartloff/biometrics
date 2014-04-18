package system.hasher;

import settings.coordinatorsettings.multiservercoordinatorsettings.AllMultiserverCoordinatorSettings;
import settings.hashersettings.AllHasherSettings;
import system.coordinator.CoordinatorFactory.MultiserverCoordinatorEnumerator;
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
//			if(MultiserverCoordinatorEnumerator.valueOf(AllMultiserverCoordinatorSettings.getMultiserverCoordinator()).
//					equals(MultiserverCoordinatorEnumerator.SERVER1)){
//				return new FuzzyVault(true); // server1 chaff injection from server2
//			}else{
				return new FuzzyVault();
//			}
		default:
			System.out.println("You didn't provide an appropriate hasher");
			return new StraightHasher();
		}
	}

	public enum HasherEnumerator {
		FUZZYVAULT, STRAIGHTHASHER, SHORTCUTFUZZYVAULT;
	}
}