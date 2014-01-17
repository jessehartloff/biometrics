 package system.allcommonclasses.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class MethodVariableSettings implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//settings
	private Integer bins;
	private Integer bits;
	private ArrayList<Long> binBoundaries;
	//
	
	
	public MethodVariableSettings(){
		this.binBoundaries = new ArrayList<Long>();
	}
	
	public Long findBin(Long prequantizedValue) {
		Integer n = this.binBoundaries.size();	
		for(Integer i=0; i<n; i++){
			if(prequantizedValue < this.binBoundaries.get(i)){
				return i.longValue();
			}
		}
		return n.longValue();
	}
	
	public Integer binsToBits(Integer bins){
		Double d = Math.ceil(Math.log10(bins)/Math.log10(2));
		return d.intValue();
	}
	
	//getters and setters
	public Integer getBins() {
		return bins;
	}
	
	public void setBins(Integer bins) {
		this.bins = bins;
		this.bits = this.binsToBits(bins);
	}
	
	public Integer getBits() {
		return bits;
	}
	
	public ArrayList<Long> getBinBoundaries() {
		return binBoundaries;
	}

	public void setBinBoundaries(ArrayList<Long> binBoundaries) {
		this.binBoundaries = binBoundaries;
	}

	public void computeBinBoundaries(ArrayList<Long> prequantizedValues){
		this.binBoundaries.clear();
		Integer n = prequantizedValues.size();
		Integer binSize = n/this.bins;
		Collections.sort(prequantizedValues);
		for(int i=1; i<this.bins; i++){
			Long cutoff = (prequantizedValues.get((binSize*i)-1) + prequantizedValues.get(binSize*i))/2;
			this.binBoundaries.add(cutoff);
		}
	}

}
