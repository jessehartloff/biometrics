package settings.settingsvariables;

import javax.swing.JPanel;

import settings.Settings;

public abstract class SettingsVariable extends Settings{
	
	private static final long serialVersionUID = 1L;

	protected String name;
	private String valueAsString;
	// TODO put everything in the map for .ser
	
	//fromString
	//toString
	
	//getter
	
	//setter
	
	//how to display in gui
	

	
	public String getLabel(){
		return name;
	}
	
}
