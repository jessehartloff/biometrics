package settings.hashersettings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import settings.Settings;
import settings.settingsvariables.SettingsLong;
import system.allcommonclasses.modalities.Biometric;
import system.hasher.Hasher;

public class FuzzyVaultSettings  extends HasherSettings{

	private static final long serialVersionUID = 1L;

	
	//Singleton
	private static FuzzyVaultSettings instance;
	private FuzzyVaultSettings(){
	}
	public static FuzzyVaultSettings getInstance(){
		if(instance == null){
			instance = new FuzzyVaultSettings();
		}
		return instance;
	}
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(instance.settingsVariables);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		instance.settingsVariables = (LinkedHashMap<String, Settings>) in.readObject();
	}
	
	@Override
	protected void addSettings(){
		this.settingsVariables.put("numberOfChaffPoints", new SettingsLong(100));
		this.settingsVariables.put("numberOfTermsInPolynomial", new SettingsLong(3));
	}


	public SettingsLong numberOfChaffPoints(){
		return (SettingsLong) this.settingsVariables.get("numberOfChaffPoints");
	}

	public SettingsLong numberOfTermsInPolynomial(){
		return (SettingsLong) this.settingsVariables.get("numberOfTermsInPolynomial");
	}
	


	public Long getNumberOfBitsForTheField() {
		return Biometric.method.getTotalBits(); 
	}

	@Override
	public String getHasher() {
		return "SHORTCUTFUZZYVAULT";
	}


	@Override
	public String getLabel(){
		return "Fuzzy Vault";
	}
	
	
}
