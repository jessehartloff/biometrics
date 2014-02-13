package settings.modalitysettings;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import system.allcommonclasses.commonstructures.Users;

public class ModalitySettings extends ComboBoxSettings{

	private static final long serialVersionUID = 1L;

	public static Users getTrainingUsers(){
		return instance.modalitySettings().getTrainingUsers();
	}
	public static Users getTestingUsers(){
		return instance.modalitySettings().getTestingUsers();
	}
	
//	public Method getMethod(){
//		return instance.modalitySettings().getMethod();
//	}
//	@Override
//	protected JPanel thisJPanel(){
//		JPanel currentPanel = super.thisJPanel();
//		currentPanel.setLayout(new GridLayout(5,1));
//		return currentPanel;
//	}
	@Override
	protected JPanel makeJPanel() {
		JPanel toRet = new JPanel();
		//toRet.setBackground(java.awt.Color.BLUE);

		//toRet.setLayout(new GridLayout(1,2));
		toRet.add(this.thisJPanel(), BorderLayout.CENTER);
		toRet.add(this.makeChildrenJPanel(), BorderLayout.EAST);
		return toRet;
	}

	//Singleton
	private static ModalitySettings instance;
	private ModalitySettings() {
	}
	public static ModalitySettings getInstance(){
		if(instance == null){
			instance = new ModalitySettings();
		}
		return instance;
	}

	
	public AModalitySettings modalitySettings(){
		return (AModalitySettings) this.settingsVariables.get(this.variableString);
	}


	

	@Override
	protected void init() {
		this.variableString = "Modality";
		this.settingsVariables.put(this.variableString, new IrisSettings());
	}
	
	@Override
	protected void addALLOptions() {
		this.addToOptions(FingerprintSettings.getInstance());
		this.addToOptions(new IrisSettings());
		this.addToOptions(new FaceSettings());
		this.addToOptions(new PizzaSettings());
	}


}
