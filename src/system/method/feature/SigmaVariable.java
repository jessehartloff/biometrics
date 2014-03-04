package system.method.feature;

import settings.settingsvariables.SettingsMethodVariable;

public class SigmaVariable extends Variable{

	// used as the difference between theta's. varies from 0 to 179 degrees
	
	public SigmaVariable(SettingsMethodVariable variableSettings) {
		super(variableSettings);
	}

	@Override
	public void setPrequantizedValue(Double prequantizedValue) {	
		Double toSet = Math.abs(prequantizedValue);
		while(toSet>=360.0){
			toSet -= 360.0; 
		}
		toSet = (toSet > 180.0) ? (360.0 - toSet) : toSet;
		super.setPrequantizedValue(toSet);
	}
	
}
