package system.makefeaturevector.feature;

import system.allcommonclasses.settings.MethodVariableSettings;

public class Variable {
	
	public MethodVariableSettings variableSettings;
	protected Long prequantizedValue;
	protected Long quantizedValue;
	
	public Variable(MethodVariableSettings variableSettings){
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
