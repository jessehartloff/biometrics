package settings.settingsvariables;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsNote extends SettingsVariable{

	private static final long serialVersionUID = 1L;

	private transient JLabel label;
	
	public SettingsNote(){
		this("");
	}
	public SettingsNote(String value){
		this.label = new JLabel(value);
	}
	
	@Override
	protected JPanel thisJPanel() {
		JPanel panel = new JPanel();
		panel.add(this.label);
		return panel;
	}

	@Override
	protected void addSettings() {
	}
	
}
