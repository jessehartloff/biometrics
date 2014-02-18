package settings.coordinatorsettings;

public class DefaultTestingSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "DEFAULTTESTING";
	}

	@Override
	protected void init() {
	}

	
	//Singleton
	private static DefaultTestingSettings instance;
	private DefaultTestingSettings(){
	}
	public static DefaultTestingSettings getInstance(){
		if(instance == null){
			instance = new DefaultTestingSettings();
		}
		return instance;
	}
	
	
	@Override
	public String getLabel(){
		return "Default Testing";
	}

}
