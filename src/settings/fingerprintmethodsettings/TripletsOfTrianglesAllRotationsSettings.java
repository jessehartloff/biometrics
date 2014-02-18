package settings.fingerprintmethodsettings;

import settings.settingsvariables.SettingsLong;

public class TripletsOfTrianglesAllRotationsSettings extends TripletsOfTrianglesSettings{

	private static TripletsOfTrianglesAllRotationsSettings instance;
	protected TripletsOfTrianglesAllRotationsSettings(){}
	public static TripletsOfTrianglesAllRotationsSettings getInstance(){
		if(instance == null){
			instance = new TripletsOfTrianglesAllRotationsSettings();
		}
		return instance;
	}
	@Override
	protected void init() {
		super.init();
	}
	

	@Override
	public String getMethodString() {
		return "TRIPLESOFTRIANGLESALLROTATIONS";
	}

	@Override
	public String getLabel(){
		return "Triples of Triangles all Rotations";
	}
}
