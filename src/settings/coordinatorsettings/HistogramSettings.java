package settings.coordinatorsettings;

public class HistogramSettings extends CoordinatorSettings{

	@Override
	public String getCoordinator() {
		return "HISTOGRAM";
	}

	@Override
	protected void init() {
	}

	
	//Singleton
	private static HistogramSettings instance;
	private HistogramSettings(){
	}
	public static HistogramSettings getInstance(){
		if(instance == null){
			instance = new HistogramSettings();
		}
		return instance;
	}
	
	
	@Override
	public String getLabel(){
		return "Histogram";
	}
}
