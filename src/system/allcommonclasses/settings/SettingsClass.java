package system.allcommonclasses.settings;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;

import system.allcommonclasses.settings.settingsvariables.SettingsVariable;

public abstract class SettingsClass implements Serializable{

	private static final long serialVersionUID = 1L;

	LinkedHashSet<SettingsVariable> settingsVariables;
	
}
