package settings.settingsvariables;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SettingsString extends SettingsVariable{

	private static final long serialVersionUID = 1L;
	String value;
	final JTextField textField = new JTextField();

	public SettingsString(String value) {
		this.setValue(value);
	}

	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		if(this.value==null){
			final String innerValue = value;
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					textField.setText(innerValue.toString());
				}
			});
		}
		this.value = value;
	}
	
	
	
	
	@Override
	protected JPanel thisJPanel() {
		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout(1,2));
//		panel.setLayout(new FlowLayout());
//		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		

		textField.setColumns(5);
		textField.getDocument().addDocumentListener(new DocumentListener(){
			
			@Override
			public void removeUpdate(DocumentEvent e){
				setValue(textField.getText());
				}			
			@Override
			public void insertUpdate(DocumentEvent e){
				setValue(textField.getText());
				}
			@Override
			public void changedUpdate(DocumentEvent e){
				setValue(textField.getText());
			}
			
		});
		
//		panel.add(new JLabel(this.name));//, BorderLayout.WEST);
		panel.add(textField);//, BorderLayout.EAST);
		
		return panel;
	}

	@Override
	protected void init() {}
	
	
	
}
