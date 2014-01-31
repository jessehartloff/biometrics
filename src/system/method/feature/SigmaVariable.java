package system.method.feature;

import settings.settingsvariables.SettingsMethodVariable;

public class SigmaVariable extends Variable{

	// used as the difference between theta's. varies from 0 to 179 degrees
	
	public SigmaVariable(SettingsMethodVariable variableSettings) {
		super(variableSettings);
	}

	@Override
	public void setPrequantizedValue(Long prequantizedValue) {	
		Long toSet = (prequantizedValue<0) ? -prequantizedValue : prequantizedValue;
		while(toSet>=180){
			toSet -= 180;
		}
		super.setPrequantizedValue(toSet);
	}
	
}
