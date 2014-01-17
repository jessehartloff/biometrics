package system.hasher;

public class HasherFactory{
	public Hasher hasher;
	public HasherFactory(String hasherString){
		this.makeHasher(hasherString);
	}
	public Hasher makeHasher(String hasherString) {
		HasherEnumerator he = HasherEnumerator.valueOf(hasherString);
		switch(he){
			case STRAIGHTHASHER:
				this.hasher = new StraightHasher();
				break;
			case SHORTCUTFUZZYVAULT:
				this.hasher = new ShortcutFuzzyVault();// FV and SH don't get exactly the same EER with no chaff points
				break;
			default:
				System.out.println("You didn't provide an appropriate hasher");
				this.hasher = new StraightHasher();
				break;
		}		
		return this.hasher;
	}
	public Hasher returnMadeHasher(){
		return this.hasher;
	}
	private enum HasherEnumerator{
		STRAIGHTHASHER, SHORTCUTFUZZYVAULT;
	}
}