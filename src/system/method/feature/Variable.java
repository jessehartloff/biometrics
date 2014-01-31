package system.method.feature;

import settings.settingsvariables.SettingsMethodVariable;

public class Variable {
 	
	public SettingsMethodVariable variableSettings;
	protected Long prequantizedValue;
	protected Long quantizedValue;
	
	public Variable(SettingsMethodVariable variableSettings){
		this.variableSettings = variableSettings;
	}

	public Long getPrequantizedValue() {
		return prequantizedValue;
	}

	public void setPrequantizedValue(Long prequantizedValue) {
		this.prequantizedValue = prequantizedValue;
		this.setQuantizedValue(variableSettings.findBin(prequantizedValue));
	}

	public Long getQuantizedValue() {
		return quantizedValue;
	}

	public void setQuantizedValue(Long quantizedValue) {
		this.quantizedValue = quantizedValue;
	}
	
	
}
