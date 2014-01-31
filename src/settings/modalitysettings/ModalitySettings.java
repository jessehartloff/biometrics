package settings.modalitysettings;


import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.coordinatorsettings.HistogramCoordinatorSettings;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.HasherSettings;
import system.allcommonclasses.commonstructures.Users;
import system.method.Method;

public class ModalitySettings extends Settings {

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

	

	//Singleton
	private static ModalitySettings instance;
	private ModalitySettings() {
		this.settingsVariables.put("Modality", FingerprintSettings.getInstance());
	}
	public static ModalitySettings getInstance(){
		if(instance == null){
			instance = new ModalitySettings();
		}
		return instance;
	}

	
	public AModalitySettings modalitySettings(){
		return (AModalitySettings) this.settingsVariables.get("Modality");
	}
	
	@Override
	protected JPanel thisJPanel() {
		
		JPanel panel = new JPanel();
		
		Settings[] modalityList = new Settings[] {
				FingerprintSettings.getInstance(),
				new IrisSettings(),
				new FaceSettings(),
				new PizzaSettings()		
		};
		
		JComboBox modalitiesBox = new JComboBox(modalityList);
		modalitiesBox.addActionListener(new SettingsComboBoxActionListener(this, "Modality"));
		modalitiesBox.setRenderer(new SettingsRenderer());
		
		panel.add(modalitiesBox);
		
		return panel;
	}


}
