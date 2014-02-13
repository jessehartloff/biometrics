package settings.coordinatorsettings;

public class SQLIndexingSettings extends ACoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "SQLINDEXING";
	}

	@Override
	protected void init() {
	}

	
	//Singleton
	private static SQLIndexingSettings instance;
	private SQLIndexingSettings(){
	}
	public static SQLIndexingSettings getInstance(){
		if(instance == null){
			instance = new SQLIndexingSettings();
		}
		return instance;
	}
	
	
	@Override
	public String getLabel(){
		return "SQL Indexing";
	}

}
