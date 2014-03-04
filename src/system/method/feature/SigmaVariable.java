package system.method.feature;

import settings.settingsvariables.SettingsMethodVariable;

public class SigmaVariable extends Variable{

	// used as the difference between theta's. varies from 0 to 179 degrees
	
	public SigmaVariable(SettingsMethodVariable variableSettings) {
		super(variableSettings);
	}

	@Override
	public void setPrequantizedValue(Double prequantizedValue) {	
		Double toSet = (prequantizedValue.compareTo(0.0) < 0) ? -prequantizedValue : prequantizedValue;
		while(toSet>=180.0){
			toSet -= 180.0;
		}
		super.setPrequantizedValue(toSet);
	}
	
}
