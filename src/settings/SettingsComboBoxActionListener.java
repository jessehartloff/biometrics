package settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class SettingsComboBoxActionListener implements ActionListener{
	
	Settings sourceSettings;
	String variableName;
	
	
	public SettingsComboBoxActionListener(Settings sourceSettings, String variableName) {
		this.sourceSettings = sourceSettings;
		this.variableName = variableName;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox){
			JComboBox sourceBox = (JComboBox) e.getSource();
			if(sourceBox.getSelectedItem() instanceof Settings){
				System.out.println("l");
				sourceSettings.settingsVariables.put(variableName, (Settings) sourceBox.getSelectedItem());
				System.out.println("l");
				AllSettings.updateGUI();
				System.out.println("l");
			} else{
				System.out.println("Error: JComboBox components must extend Settings.");
			}
		} else{
			System.out.println("Error: Used JComboBox action listener on wrong component type.");
		}
	}
		

}
