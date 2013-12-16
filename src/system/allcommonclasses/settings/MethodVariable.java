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
		this.binBoundaries = new ArrayList<Long>();
		for(Long i=0L; i<1000; i++){
			this.binBoundaries.add(i); // TODO TODO TODO this code sucks					  
			// This has to be done better. Currently it initializes to a fixed number of
			// uniform bins 
		}
		this.setBins(1001);
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
		for(int i=1; i<this.bins; i++){
			Long cutoff = (prequantizedValues.get((binSize*i)-1) + prequantizedValues.get(binSize*i))/2;
			this.binBoundaries.add(cutoff);
		}
	}

}
