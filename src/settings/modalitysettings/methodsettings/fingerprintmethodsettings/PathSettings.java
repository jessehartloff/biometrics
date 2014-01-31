package settings.modalitysettings.methodsettings.fingerprintmethodsettings;


import settings.settingsvariables.SettingsMethodVariable;
import settings.settingsvariables.SettingsLong;
import system.method.fingerprintmethods.FingerprintMethod;
import system.method.fingerprintmethods.PathsMethod;

public class PathSettings extends AFingerprintMethodSettings{

	private static final long serialVersionUID = 1L;

	private static PathSettings instance;
	private PathSettings(){}
	public static PathSettings getInstance(){
		if(instance == null){
			instance = new PathSettings();
		}
		return instance;
	}
	
	public SettingsLong kClosestMinutia(){
		return (SettingsLong) this.settingsVariables.get("kClosestMinutia");
	}
	
	public SettingsMethodVariable d0(){
		return (SettingsMethodVariable) this.settingsVariables.get("d0");
	}
	public SettingsMethodVariable d1(){
		return (SettingsMethodVariable) this.settingsVariables.get("d1");
	}
	public SettingsMethodVariable d2(){
		return (SettingsMethodVariable) this.settingsVariables.get("d2");
	}
	public SettingsMethodVariable d3(){
		return (SettingsMethodVariable) this.settingsVariables.get("d3");
	}
	public SettingsMethodVariable sigma0(){
		return (SettingsMethodVariable) this.settingsVariables.get("sigma0");
	}
	public SettingsMethodVariable sigma1(){
		return (SettingsMethodVariable) this.settingsVariables.get("sigma1");
	}
	public SettingsMethodVariable sigma2(){
		return (SettingsMethodVariable) this.settingsVariables.get("sigma2");
	}
	public SettingsMethodVariable sigma3(){
		return (SettingsMethodVariable) this.settingsVariables.get("sigma3");
	}
	public SettingsMethodVariable phi1(){
		return (SettingsMethodVariable) this.settingsVariables.get("phi1");
	}
	public SettingsMethodVariable phi2(){
		return (SettingsMethodVariable) this.settingsVariables.get("phi2");
	}
	public SettingsMethodVariable phi3(){
		return (SettingsMethodVariable) this.settingsVariables.get("phi3");
	}


	@Override
	public String getMethodString() {
		return "PATHSMETHOD";
	}
	

	@Override
	public String getLabel(){
		return "Paths";
	}
	@Override
	protected void init() {
		this.settingsVariables.put("kClosestMinutia", new SettingsLong());

		this.settingsVariables.put("d0", new SettingsMethodVariable());
		this.settingsVariables.put("d1", new SettingsMethodVariable());
		this.settingsVariables.put("d2", new SettingsMethodVariable());
		this.settingsVariables.put("d3", new SettingsMethodVariable());
		this.settingsVariables.put("sigma0", new SettingsMethodVariable());
		this.settingsVariables.put("sigma1", new SettingsMethodVariable());
		this.settingsVariables.put("sigma2", new SettingsMethodVariable());
		this.settingsVariables.put("sigma3", new SettingsMethodVariable());
		this.settingsVariables.put("phi1", new SettingsMethodVariable());
		this.settingsVariables.put("phi2", new SettingsMethodVariable());
		this.settingsVariables.put("phi3", new SettingsMethodVariable());
	}
	
}
