package system.method.feature;

import settings.settingsvariables.SettingsMethodVariable;

public class Variable {
 	
	public SettingsMethodVariable variableSettings;
	protected Long prequantizedValue; // TODO Jesse - make these Double
//	protected Long quantizedValue;
	
	public Variable(SettingsMethodVariable variableSettings){
		this.variableSettings = variableSettings;
	}

	public Long getPrequantizedValue() {
		return prequantizedValue;
	}

	public void setPrequantizedValue(Long prequantizedValue) {
		this.prequantizedValue = prequantizedValue;
//		this.setQuantizedValue(variableSettings.findBin(prequantizedValue));
	}


	
	
}
