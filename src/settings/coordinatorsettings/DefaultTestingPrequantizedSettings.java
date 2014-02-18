package settings.coordinatorsettings;

public class DefaultTestingPrequantizedSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "DEFAULTTESTINGPREQUANTIZED";
	}

	@Override
	protected void init() {
	}

	
	//Singleton
	private static DefaultTestingPrequantizedSettings instance;
	private DefaultTestingPrequantizedSettings(){
	}
	public static DefaultTestingPrequantizedSettings getInstance(){
		if(instance == null){
			instance = new DefaultTestingPrequantizedSettings();
		}
		return instance;
	}
	
	
	@Override
	public String getLabel(){
		return "Default Testing Prequantized";
	}

}
