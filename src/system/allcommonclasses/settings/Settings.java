package system.allcommonclasses.settings;

import java.io.Serializable;

public class Settings implements Serializable {

	public GlobalSettings globalSettings;
	public MinutiaeSettings minutiaeSettings;
	public PathSettings pathSettings;
	public TriangleSettings triangleSettings;
	public FuzzyVaultSettings fuzzyVaultSettings;
	public PinSketchSettings pinSketchSettings;
	public NgonSettings ngonSettings;
	
	public static final long serialVersionUID = 1L;
	
	public Settings(){
		syncWithSettingsClasses();
	}
	
	public void syncWithSettingsClasses(){
		globalSettings = GlobalSettings.getInstance();
		minutiaeSettings = MinutiaeSettings.getInstance();
		pathSettings = PathSettings.getInstance();
		triangleSettings = TriangleSettings.getInstance();
		fuzzyVaultSettings = FuzzyVaultSettings.getInstance();
		pinSketchSettings = PinSketchSettings.getInstance();
		ngonSettings = NgonSettings.getInstance();
	}
	
	public void loadToSettingsClasses(){
		GlobalSettings g = GlobalSettings.getInstance();
		g = this.globalSettings;
		
		MinutiaeSettings m = MinutiaeSettings.getInstance();
		m = this.minutiaeSettings;
		
		PathSettings p = PathSettings.getInstance();
		p = this.pathSettings;
		
		TriangleSettings t = TriangleSettings.getInstance();
		t = this.triangleSettings;

		FuzzyVaultSettings f = FuzzyVaultSettings.getInstance();
		f = this.fuzzyVaultSettings;
		
		PinSketchSettings ps = PinSketchSettings.getInstance();
		ps = this.pinSketchSettings;
	
		NgonSettings n = NgonSettings.getInstance();
		n = this.ngonSettings;
	}
	
}
