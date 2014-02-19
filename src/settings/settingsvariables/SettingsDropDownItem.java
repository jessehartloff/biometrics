package settings.settingsvariables;

public class SettingsDropDownItem extends SettingsVariable{

	private String value;
	
	public SettingsDropDownItem(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	@Override
	public String getLabel(){
		return this.value;
	}
	
	@Override
	protected void addSettings() {
		
	}

	
	
}
