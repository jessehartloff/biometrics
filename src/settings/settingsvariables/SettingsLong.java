package settings.settingsvariables;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsLong extends SettingsVariable{

	private static final long serialVersionUID = 1L;

	private Long value;
	
	public SettingsLong(){
		this.setValue(16);
	}
	public SettingsLong(Integer value){
		this.setValue(value);
	}
	public SettingsLong(Long value){
		this.setValue(value);
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	public void setValue(Integer value) {
		this.value = value.longValue();
	}

	
	@Override
	protected JPanel thisJPanel() {
		JPanel panel = new JPanel();
		final JSpinner numberBox = new JSpinner(new SpinnerNumberModel(4,0,100,1));
		numberBox.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				SpinnerNumberModel numberModel = (SpinnerNumberModel) numberBox.getModel();
				setValue(numberModel.getNumber().longValue());
			}
			
		});
		
		panel.add(numberBox);
		return panel;
	}
	

}
