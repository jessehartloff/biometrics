package settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.swing.Box;
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
		this.panel = this.makeJPanel();
	}

	protected JPanel makeJPanel() {
		JPanel toRet = new JPanel();
		toRet.add(this.thisJPanel(), BorderLayout.WEST);
		toRet.add(this.makeChildrenJPanel(), BorderLayout.EAST);
		return toRet;
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
		JPanel toRet = new JPanel();
		toRet.setMaximumSize(new Dimension(0, 0));
		return toRet;
	}
	

	public JPanel getJPanel(){
		return this.panel;
	}
	
	
	public JPanel makeChildrenJPanel(){
		JPanel childrenPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(childrenPanel, BoxLayout.Y_AXIS);
		childrenPanel.setLayout(boxLayout);
		
		for(String subSettingsName : settingsVariables.keySet()){
			JPanel childPanel = new JPanel();
			childPanel.add(new JLabel(subSettingsName), BorderLayout.WEST);
			childPanel.add(this.settingsVariables.get(subSettingsName).getJPanel(), BorderLayout.EAST);
			childPanel.validate();
//			childPanel.setMaximumSize(new Dimension((int) childPanel.getMaximumSize().getWidth(), 0));
//			childPanel.add(Box.createVerticalGlue());
			childrenPanel.add(childPanel);
		}
		childrenPanel.validate();

//		if(childrenPanel.getComponentCount() <= 2){
			childrenPanel.setSize(0,0);
//		}
		childrenPanel.setMaximumSize(new Dimension(0, 0));
		
		return childrenPanel;
	}
	
	
	public String getLabel(){
		return "getLabel() not overriden";
	}
	
	
}
