package system.allcommonclasses.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.utilities.Functions;

public class MethodVariable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer bins;
	private Integer bits;
	private ArrayList<Long> binBoundaries;
	
	public MethodVariable(){
		binBoundaries = new ArrayList<Long>();
	}
	
	public Long findBin(Long prequantizedValue) {
		Integer n = this.binBoundaries.size();
		for(Integer i=0; i<n; i++){
			if(prequantizedValue < this.binBoundaries.get(i)){
				return i.longValue();
			}
		}
		return n.longValue() + 1;
	}
	
	
	public Integer getBins() {
		return bins;
	}
	
	public void setBins(Integer bins) {
		this.bins = bins;
		this.bits = Functions.binsToBits(bins);
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
		for(int i=1; i<this.bins-1; i++){
			Long cutoff = (prequantizedValues.get(binSize*i) + prequantizedValues.get((binSize*i)+1))/2;
			this.binBoundaries.add(cutoff);
		}
	}

}
