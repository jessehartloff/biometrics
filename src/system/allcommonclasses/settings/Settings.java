package system.allcommonclasses.settings;

import java.io.Serializable;

public class Settings implements Serializable {

	public GlobalSettings globalSettings;
	public MinutiaeSettings minutiaeSettings;
	public PathSettings pathSettings;
	public TriangleSettings triangleSettings;
	public FuzzyVaultSettings fuzzyVaultSettings;
	public PinSketchSettings pinSketchSettings;
	public NgonSettings nGonSettings;
	
	public static final long serialVersionUID = 1L;
	
	public Settings(){
		saveSettings();
	}
	
	public void saveSettings(){
		globalSettings = GlobalSettings.getInstance();
		minutiaeSettings = MinutiaeSettings.getInstance();
		pathSettings = PathSettings.getInstance();
		triangleSettings = TriangleSettings.getInstance();
		fuzzyVaultSettings = FuzzyVaultSettings.getInstance();
		pinSketchSettings = PinSketchSettings.getInstance();
		nGonSettings = NgonSettings.getInstance();
	}
	
	public void loadSettings(){
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
		n = this.nGonSettings;
	}
	
}
