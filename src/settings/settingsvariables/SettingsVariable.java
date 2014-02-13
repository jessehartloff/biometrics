package settings.settingsvariables;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import settings.Settings;

public abstract class SettingsVariable extends Settings{
	
	private static final long serialVersionUID = 1L;

	protected String name;
	

	
	public String getLabel(){
		return name;
	}
	


	
}
