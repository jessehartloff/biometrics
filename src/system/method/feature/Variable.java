package system.method.feature;

import settings.settingsvariables.SettingsMethodVariable;

public class Variable {
 	
	public SettingsMethodVariable variableSettings;
	protected Double prequantizedValue;
//	protected Long quantizedValue;
	
	public Variable(SettingsMethodVariable variableSettings){
		this.variableSettings = variableSettings;
	}

	public Double getPrequantizedValue() {
		return prequantizedValue;
	}

	public void setPrequantizedValue(Double prequantizedValue) {
		this.prequantizedValue = prequantizedValue;
//		this.setQuantizedValue(variableSettings.findBin(prequantizedValue));
	}


	
	
}
