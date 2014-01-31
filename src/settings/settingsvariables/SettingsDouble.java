package settings.settingsvariables;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.modalitysettings.FaceSettings;
import settings.modalitysettings.FingerprintSettings;
import settings.modalitysettings.IrisSettings;
import settings.modalitysettings.PizzaSettings;

public class SettingsDouble extends SettingsVariable{

	private static final long serialVersionUID = 1L;
	
	private Double value;

	public SettingsDouble(){
		this.setValue(5.0);
	}
	public SettingsDouble(Double value){
		this.setValue(value);
	}
	public SettingsDouble(Float value){
		this.setValue(value);
	}
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public void setValue(Float value) {
		this.value = value.doubleValue();
	}

	
	@Override
	protected JPanel thisJPanel() {
		JPanel panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		final JTextField textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener(){
			
			@Override
			public void removeUpdate(DocumentEvent e){}			
			@Override
			public void insertUpdate(DocumentEvent e){}
			@Override
			public void changedUpdate(DocumentEvent e){
				setValue(new Double(textField.getText()));
			}
			
		});
		
		panel.add(new JLabel(this.name), BorderLayout.WEST);
		panel.add(textField, BorderLayout.EAST);
		
		return panel;
	}
	
}
