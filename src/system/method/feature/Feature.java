package system.method.feature;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import system.vectordistance.MeasurableDistance;

public abstract class Feature implements MeasurableDistance<Feature> {

	public LinkedHashMap<String, Variable> variables;

	public Feature(){
			this.variables = new LinkedHashMap<String, Variable>();
		}
		
	public BigInteger toBigInt(){
		BigInteger toReturn = BigInteger.valueOf(0);
		Collection<Variable> vars = variables.values();
		for(Variable var : vars){
			toReturn = toReturn.shiftLeft(var.variableSettings.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(var.getQuantizedValue()));
		}
		return toReturn;
	}
		
	
	public void fromBigInt(BigInteger bigInt){
		BigInteger bigTwo = BigInteger.valueOf(2);
		ArrayList<Variable> vars = new ArrayList<Variable>(variables.values());
		Collections.reverse(vars);
		for(Variable var : vars){
			var.setQuantizedValue(bigInt.and(bigTwo.pow(var.variableSettings.getBits()).add(BigInteger.valueOf(-1))).longValue());
			bigInt = bigInt.shiftRight(var.variableSettings.getBits());
		}
	}
		
	@Override
	public Double distanceFrom(Feature compareFeature/*,Distance distanceFunction*/) {
		//return distanceFunction.distance(this,compareFeature);
        Double result = 0.0;
        for(String varKey : variables.keySet()){
        	result += Math.pow(this.variables.get(varKey).getQuantizedValue().doubleValue() -
        			 compareFeature.variables.get(varKey).getQuantizedValue().doubleValue(),2);
        }
        return Math.sqrt(result);
	}

	public Long getTotalBits() {
		Long toReturn = 0L;
		for(Variable var : variables.values()){
			toReturn += var.variableSettings.getBits();
		}
		return toReturn;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Feature))
            return false;

        Feature otherFeature = (Feature) obj;
        Boolean result = true;
        
        for(String varKey : variables.keySet()){
        	result &= this.variables.get(varKey).getQuantizedValue().equals(otherFeature.variables.get(varKey).getQuantizedValue());
        }
        return result;
    }
	
	@Override
	public String toString(){
		String output = "";
		for(Entry<String, Variable> var : variables.entrySet()){
			output += var.getKey() + ": " + var.getValue().quantizedValue.toString() +" ";
		}
		return output;
	}

    public boolean prequantizedEquals(Feature otherFeature) {
        Boolean result = true;
        for(String varKey : variables.keySet()){
        	result &= this.variables.get(varKey).getPrequantizedValue().equals(otherFeature.variables.get(varKey).getPrequantizedValue());
        }
        return result;
    }
    
	
	public String prequantizedToString(){
		String output = "";
		for(Entry<String, Variable> var : variables.entrySet()){
			output += var.getKey() + ": " + var.getValue().prequantizedValue.toString() +" ";
		}
		return output;
	}
	
}
