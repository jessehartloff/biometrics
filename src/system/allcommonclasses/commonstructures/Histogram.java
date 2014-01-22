package system.allcommonclasses.commonstructures;

import java.math.BigInteger;
import java.util.HashMap;

public class Histogram {

	private String variableName;
	public HashMap<BigInteger, Long> histogram;
	
	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}



	@Override
	public String toString(){
		String toReturn = ""; // TODO
		
		return toReturn;
	}
}
