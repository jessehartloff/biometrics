package settings.modalitysettings;


import javax.swing.JComboBox;
import javax.swing.JPanel;

import settings.ComboBoxSettings;
import settings.Settings;
import settings.SettingsRenderer;
import settings.coordinatorsettings.HistogramCoordinatorSettings;
import settings.hashersettings.FuzzyVaultSettings;
import settings.hashersettings.HasherSettings;
import system.allcommonclasses.commonstructures.Users;
import system.method.Method;

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
		return (AModalitySettings) this.settingsVariables.get("Modality");
	}
	

	

	@Override
	protected void init() {
		this.variableString = "Modality";
		this.settingsVariables.put("Modality", new IrisSettings());
	}
	
	@Override
	protected void addALLOptions() {
		this.addToOptions(FingerprintSettings.getInstance());
		this.addToOptions(new IrisSettings());
		this.addToOptions(new FaceSettings());
		this.addToOptions(new PizzaSettings());
	}


}
