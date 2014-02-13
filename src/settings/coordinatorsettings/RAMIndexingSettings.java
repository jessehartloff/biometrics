package settings.coordinatorsettings;

public class RAMIndexingSettings extends ACoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "RAMINDEXING";
	}

	@Override
	protected void init() {
	}

	
	//Singleton
	private static RAMIndexingSettings instance;
	private RAMIndexingSettings(){
	}
	public static RAMIndexingSettings getInstance(){
		if(instance == null){
			instance = new RAMIndexingSettings();
		}
		return instance;
	}
	
	
	@Override
	public String getLabel(){
		return "RAM Indexing";
	}
}
