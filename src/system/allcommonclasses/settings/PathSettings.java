package system.allcommonclasses.settings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashSet;

import system.allcommonclasses.settings.settingsvariables.LongSettingsVariable;
import system.allcommonclasses.settings.settingsvariables.SettingsVariable;

public class PathSettings extends SettingsClass implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//settings
	public MethodVariableSettings d0;
	public MethodVariableSettings d1;
	public MethodVariableSettings d2;
	public MethodVariableSettings d3;
	public MethodVariableSettings sigma0;
	public MethodVariableSettings sigma1;
	public MethodVariableSettings sigma2;
	public MethodVariableSettings sigma3;
	public MethodVariableSettings phi1;
	public MethodVariableSettings phi2;
	public MethodVariableSettings phi3;
	
	public transient LongSettingsVariable kClosestMinutia;
	//
	
	//loop through the hashset?
	//get a copy of the hashset?
	
	//singleton
	private static PathSettings instance;
	private PathSettings(){
		kClosestMinutia = new LongSettingsVariable();
		this.settingsVariables.add(kClosestMinutia);
		
		d0 = new MethodVariableSettings();
		d1 = new MethodVariableSettings();
		d2 = new MethodVariableSettings();
		d3 = new MethodVariableSettings();
		sigma0 = new MethodVariableSettings();
		sigma1 = new MethodVariableSettings();
		sigma2 = new MethodVariableSettings();
		sigma3 = new MethodVariableSettings();
		phi1 = new MethodVariableSettings();
		phi2 = new MethodVariableSettings();
		phi3 = new MethodVariableSettings();
	}
	public static PathSettings getInstance(){
		if(instance == null){
			instance = new PathSettings();
		}
		return instance;
	}


	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
	    in.defaultReadObject();
	    // Jesse - play with this
	    // populate variables from deserialized set
	}
	
	
}
