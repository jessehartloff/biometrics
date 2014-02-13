package settings.coordinatorsettings;

import settings.hashersettings.FuzzyVaultSettings;

public class NoCoordinator extends ACoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "NONE";
	}

	@Override
	protected void init() {
	}

	
	//Singleton
	private static NoCoordinator instance;
	private NoCoordinator(){
	}
	public static NoCoordinator getInstance(){
		if(instance == null){
			instance = new NoCoordinator();
		}
		return instance;
	}
	
	
	@Override
	public String getLabel(){
		return "None";
	}
	
}
