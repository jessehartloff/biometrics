package system.allcommonclasses.commonstructures;

import java.math.BigInteger;
import java.util.HashMap;

public class Histogram {

	private String variableName;
	public HashMap<BigInteger, Long> histogram;
	
	public Histogram(){
		variableName = "";
		histogram = new HashMap<BigInteger, Long>();
	}
	
	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public String toString(){
		String toReturn = "\nHistogram for " + this.getVariableName() + ": ";
		for(BigInteger bigInt : this.histogram.keySet()){
			toReturn += "[ " + bigInt + " , " + this.histogram.get(bigInt) + " ]" + '\n';	
		}
		return toReturn;
	}
}
