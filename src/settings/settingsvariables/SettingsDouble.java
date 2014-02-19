package settings.settingsvariables;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import settings.Settings;
import settings.SettingsRenderer;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.modalitysettings.FaceSettings;
import settings.modalitysettings.FingerprintSettings;
import settings.modalitysettings.IrisSettings;
import settings.modalitysettings.PizzaSettings;

public class SettingsDouble extends SettingsVariable{

	private static final long serialVersionUID = 1L;
	
	private Double value;
	private transient JTextField textField;
	
	@Override
	protected void addSettings(){}
	
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
//	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
//		in.defaultReadObject();
//		this.setValue(5.0);
//	}
	
	public void setValue(Double value) {
		if(this.textField == null){
			textField = new JTextField();
		}
		if(textField.getText().isEmpty() || this.value==null || value.compareTo(new Double(textField.getText())) != 0){
			final Double innerValue = value;
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					textField.setText(innerValue.toString());
					textField.validate();
				}
			});
		}
		this.value = value;
	}
	
	public void setValue(Float value) {
		this.setValue(value.doubleValue());
	}

	
	@Override
	protected JPanel thisJPanel() {
		JPanel panel = new JPanel();
		if(this.textField == null){
			textField = new JTextField();
		}
		
		//panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//		textField = new JTextField();
//		final JTextField textField = new JTextField();
//		textField.setText(this.getValue().toString());
		textField.setColumns(5);
		textField.getDocument().addDocumentListener(new DocumentListener(){
			
			@Override
			public void removeUpdate(DocumentEvent e){
				setValue(new Double(textField.getText()));
				
			}			
			@Override
			public void insertUpdate(DocumentEvent e){
				setValue(new Double(textField.getText()));
				textField.validate();
				textField.repaint();
				textField.revalidate();
				textField.setVisible(true);
//				textField.replaceSelection(textField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e){
				setValue(new Double(textField.getText()));
			}
			
		});
		
//		panel.add(new JLabel(this.name));//, BorderLayout.WEST);
		panel.add(textField);//, BorderLayout.EAST);
		
		return panel;
	}
	
}
