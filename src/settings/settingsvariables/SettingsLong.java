package settings.settingsvariables;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsLong extends SettingsVariable{

	private static final long serialVersionUID = 1L;

	private Long value;
	private JSpinner numberBox;
	
	public SettingsLong(){
		this(800L);
	}
	public SettingsLong(Integer value){
		this(value.longValue());
	}
	public SettingsLong(Long value){
		this.setValue(value);
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
		this.numberBox.setValue(new Integer(value.intValue()));
	}
	
	public void setValue(Integer value) {
		this.setValue(value.longValue());
	}

	
	@Override
	protected JPanel thisJPanel() {
		JPanel panel = new JPanel();
		this.numberBox = new JSpinner(new SpinnerNumberModel(value.intValue(),0,1000,1));
		this.numberBox.setSize(0, 0);
		numberBox.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				SpinnerNumberModel numberModel = (SpinnerNumberModel) numberBox.getModel();
				setValue(numberModel.getNumber().longValue());
			}
			
		});
		
		panel.add(numberBox);
//		panel.setMaximumSize(new Dimension((int) panel.getMaximumSize().getWidth(),10));
		panel.setMaximumSize(new Dimension(0,0));
		return panel;
	}
	
	protected JPanel makeJPanel() {
		return this.thisJPanel();
	}
	
	@Override
	protected void init() {
		value = new Long(0);
	}
	

}
