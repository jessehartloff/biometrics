package system.allcommonclasses.settings.settingsvariables;

public class LongSettingsVariable extends SettingsVariable{

	private static final long serialVersionUID = 1L;

	
	private Long value;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	public void setValue(Integer value) {
		this.value = value.longValue();
	}
	
	
}
