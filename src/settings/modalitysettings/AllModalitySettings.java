package settings.modalitysettings;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import system.allcommonclasses.commonstructures.Users;

public class AllModalitySettings extends ComboBoxSettings{

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
//	@Override
//	protected JPanel makeJPanel() {
//		JPanel toRet = new JPanel();
//		//toRet.setBackground(java.awt.Color.BLUE);
//
//		//toRet.setLayout(new GridLayout(1,2));
//		toRet.add(this.thisJPanel(), BorderLayout.CENTER);
//		toRet.add(this.makeChildrenJPanel(), BorderLayout.EAST);
//		return toRet;
//	}

	//Singleton
	private static AllModalitySettings instance;
	private AllModalitySettings() {
	}
	public static AllModalitySettings getInstance(){
		if(instance == null){
			instance = new AllModalitySettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
		out.writeInt(instance.settingsBox.getSelectedIndex());
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
		instance.currentIndex = in.readInt();
	}


	public ModalitySettings modalitySettings(){
		return (ModalitySettings) this.settingsVariables.get(this.variableString);
	}




	@Override
	protected void addSettings() {
		this.variableString = "Modality";
		this.settingsVariables.put(this.variableString, new IrisSettings());
	}
	
	
	@Override
	protected void addALLOptions() {
		this.addToOptions(FingerprintSettings.getInstance());
		this.addToOptions(new IrisSettings()); //TODO
		this.addToOptions(new FaceSettings());
		this.addToOptions(new PizzaSettings());
	}


}
