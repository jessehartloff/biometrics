package system.allcommonclasses.commonstructures;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;

public class Histogram {

	private String variableName;
	private Long numberSamples;
	public HashMap<BigInteger, Long> histogram;
	
	public Histogram(){
		variableName = "";
		histogram = new HashMap<BigInteger, Long>();
	}
	
	public Double getMinEntropy() {
		Collection<Long> longs = this.histogram.values();
		Long max = 0L;
		Long sum = 0L;
		for(Long value : longs){
			sum += value;
			if(value.compareTo(max) > 0){
				max = value;
			}
		}

		Double Prob = max.doubleValue()/sum.doubleValue();
//		Double Prob = 1.0/sum.doubleValue(); // minEntropy depends on how many samples we have!! This line gives the best case
		Double minEntropy = -Math.log10(Prob)/Math.log10(2.0);
		
		return minEntropy;
	}
	
	public Long getNumberSamples() {
		//if not already set, calculate it
		if (numberSamples == null) {
			if (histogram.size() == 0) {
				System.out.println("No samples...");
				return null;
			}
			else {
				Long sampleSize = 0L;
				for (Long l : histogram.values()) {
					sampleSize += l;
				}
				return sampleSize;
			}
		}
		else {
			return numberSamples;
		}
	}

	
	
	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	
	@Override
	public String toString(){
		String toReturn = this.getVariableName() + ": \n";
		for(BigInteger bigInt : this.histogram.keySet()){
			toReturn += "[ " + bigInt + " , " + this.histogram.get(bigInt) + " ]" + '\n';	
		}
		return toReturn;
	}
}
