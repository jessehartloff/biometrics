package system.allcommonclasses.settings;

public class PinSketchSettings {

	
	
	private static PinSketchSettings instance;
	
	private PinSketchSettings(){}
	
	public static PinSketchSettings getInstance(){
		if(instance == null){
			instance = new PinSketchSettings();
		}
		return instance;
	}
	
}
