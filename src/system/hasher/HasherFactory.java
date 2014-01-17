package system.hasher;

import system.allcommonclasses.settings.GlobalSettings;

public class HasherFactory{
	
	public static Hasher makeHasher() {
		switch(HasherEnumerator.valueOf(GlobalSettings.getInstance().getHasher())){
			case STRAIGHTHASHER:
				return new StraightHasher();
			case SHORTCUTFUZZYVAULT:
				return new ShortcutFuzzyVault();
			default:
				System.out.println("You didn't provide an appropriate hasher");
				return new StraightHasher();
		}		
	}
	
	private enum HasherEnumerator{
		STRAIGHTHASHER, SHORTCUTFUZZYVAULT;
	}
}