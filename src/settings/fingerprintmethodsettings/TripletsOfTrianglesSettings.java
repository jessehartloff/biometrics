package settings.fingerprintmethodsettings;

import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsLong;

public class TripletsOfTrianglesSettings extends TriangleSettings{

	private static TripletsOfTrianglesSettings instance;
	protected TripletsOfTrianglesSettings(){}
	public static TripletsOfTrianglesSettings getInstance(){
		if(instance == null){
			instance = new TripletsOfTrianglesSettings();
		}
		return instance;
	}
	
	@Override
	protected void init() {
		super.init();
		this.settingsVariables.put("minimumPointsForTripletOfTriangles", new SettingsLong(5));
		this.settingsVariables.put("kClosestTriangles", new SettingsLong(3));
	}
	

	@Override
	public String getMethodString() {
		return "TRIPLESOFTRIANGLES";
	}

	@Override
	public String getLabel(){
		return "Triples of Triangles";
	}
	
}
