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
		this.init();
		this.panel = new JPanel();
		this.panel.add(this.thisJPanel(), BorderLayout.WEST);
		this.panel.add(this.makeChildrenJPanel(), BorderLayout.EAST);
	}

	protected abstract void init();
	

//	public Settings makeCopy(){
//		return /*a copy of*/ this;
//	}
	
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
		return this.panel;
	}
	
	
	public JPanel makeChildrenJPanel(){
		JPanel childrenPanel = new JPanel();
		childrenPanel.setLayout(new BoxLayout(childrenPanel, BoxLayout.PAGE_AXIS));
		
		for(String subSettingsName : settingsVariables.keySet()){
			JPanel childPanel = new JPanel();
			childPanel.add(new JLabel(subSettingsName), BorderLayout.WEST);
			childPanel.add(this.settingsVariables.get(subSettingsName).getJPanel(), BorderLayout.EAST);
			childrenPanel.add(childPanel);
		}
		
		return childrenPanel;
	}
	
	
	public String getLabel(){
		return "getLabel() not overriden";
	}
	
	
}
