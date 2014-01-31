package settings;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import settings.modalitysettings.methodsettings.fingerprintmethodsettings.PathSettings;


public abstract class Settings implements Serializable{

	private static final long serialVersionUID = 1L;

	protected LinkedHashMap<String, Settings> settingsVariables;
	
	protected JPanel panel;
	
	protected Settings(){
		this.settingsVariables = new LinkedHashMap<String, Settings>();
		this.panel = this.thisJPanel();
	}

	public Settings makeCopy(){
		return /*a copy of*/ this;
	}
	
//	private Settings(String filename){
//		
//	}


	//add to map and handle naming
	
	
	
	//fromString
	//toString
	
	//getter
	
	//setter


	protected JPanel thisJPanel(){
		return new JPanel();
	}
	

	public JPanel getJPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(this.panel); 
		
		for(String subSettingsName : settingsVariables.keySet()){
			JPanel innerPanel = new JPanel();
			innerPanel.add(new JLabel(subSettingsName), BorderLayout.WEST);
			innerPanel.add(this.settingsVariables.get(subSettingsName).getJPanel(), BorderLayout.EAST);
			panel.add(innerPanel);
		}
		return panel;
	}
	
	
	public String getLabel(){
		return "getLabel() not overriden";
	}
	
	
}
