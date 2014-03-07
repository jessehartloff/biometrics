package settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static interfaces.gui.GUI.BACKGROUNDCOLOR;

public abstract class Settings implements Serializable{

	private static final long serialVersionUID = 1L;

	protected LinkedHashMap<String, Settings> settingsVariables;
	
//	protected transient JPanel panel;
	
	protected Settings(){
		this.settingsVariables = new LinkedHashMap<String, Settings>();
		this.addSettings();
		// init function if needed
//		this.panel = this.makeJPanel();
	}

	protected JPanel makeJPanel() {
		JPanel toRet = new JPanel();
		toRet.setBackground(BACKGROUNDCOLOR);
		toRet.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		toRet.add(this.thisJPanel(), BorderLayout.WEST);
		toRet.add(this.makeChildrenJPanel(), BorderLayout.EAST);
		toRet.validate();
		toRet.repaint();
		toRet.setVisible(true);
		return toRet;
	}


	protected abstract void addSettings();
	



	protected JPanel thisJPanel(){
		JPanel toRet = new JPanel();
		toRet.setBackground(BACKGROUNDCOLOR);
		//toRet.setLayout(new FlowLayout());
		toRet.setMaximumSize(new Dimension(0, 0));
		return toRet;
	}
	

	public JPanel getJPanel(){
//		if(this.panel == null){
//			this.panel = this.makeJPanel();
//		}
//		return this.panel;
		return this.makeJPanel();
	}
	
	
	public JPanel makeChildrenJPanel(){
		JPanel childrenPanel = new JPanel();
		childrenPanel.setLayout(new GridLayout(3,2));
		childrenPanel.setBackground(BACKGROUNDCOLOR);
//		childrenPanel.setBackground(java.awt.Color.BLUE);
//		System.out.println(this.getClass()+"\t"+settingsVariables.keySet().toString());
		BoxLayout boxLayout = new BoxLayout(childrenPanel, BoxLayout.Y_AXIS);
//		boxLayout.
		childrenPanel.setLayout(boxLayout);
//		childrenPanel.setLayout(new FlowLayout());
		//childrenPanel.setLayout(new GridLayout(2,1));

		for(String subSettingsName : settingsVariables.keySet()){
			JPanel childPanel = new JPanel();
			//childPanel.setLayout(new GridLayout(1,2));
//			childPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//			BoxLayout innerBoxLayout = new BoxLayout(childPanel, BoxLayout.X_AXIS);
//			childPanel.setLayout(innerBoxLayout);
			childPanel.setBackground(BACKGROUNDCOLOR);
//			BoxLayout boxLayout2 = new BoxLayout(childPanel, BoxLayout.X_AXIS);
//			childPanel.setLayout(boxLayout2);
			childPanel.add(new JLabel(subSettingsName + ":"), BorderLayout.WEST);
			childPanel.add(this.settingsVariables.get(subSettingsName).getJPanel(), BorderLayout.EAST);
			
//			JLabel nameLabel = new JLabel(subSettingsName + ": ");
//			nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//			childPanel.add(nameLabel);
//			
//			JPanel contents = this.settingsVariables.get(subSettingsName).getJPanel();
//			contents.setAlignmentX(Component.LEFT_ALIGNMENT);
//			childPanel.add(contents);
			
//			childPanel.add(nameLabel, BorderLayout.WEST);
//			childPanel.add(this.settingsVariables.get(subSettingsName).getJPanel(), BorderLayout.CENTER);
//			childPanel.add(this.settingsVariables.get(subSettingsName).getJPanel());
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
	
	public void updateDisplay(){}
	
	public String getLabel(){
		return "getLabel() not overriden";
	}
	
	
}
