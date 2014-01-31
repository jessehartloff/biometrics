 package settings.settingsvariables;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;


public class SettingsMethodVariable extends SettingsVariable{
	
	private static final long serialVersionUID = 1L;

	private transient Long bits;
	private transient ArrayList<Long> binBoundaries; // TODO compute things when deserialized
	
	
	public SettingsMethodVariable(){
		this.settingsVariables.put("bins", new SettingsLong());
		binBoundaries = new ArrayList<Long>();
		bits = 5L;
	}
	
	public SettingsMethodVariable(Long numberOfBins){
		this.settingsVariables.put("bins", new SettingsLong(numberOfBins));
		binBoundaries = new ArrayList<Long>();
		bits = this.binsToBits(numberOfBins);
	}
	
	public SettingsMethodVariable(Integer numberOfBins){
		this(numberOfBins.longValue());
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
	
	public Long binsToBits(Long bins){
		Double d = Math.ceil(Math.log10(bins)/Math.log10(2));
		return d.longValue();
	}
	
	public Long getBins(){
		SettingsLong sl = (SettingsLong) this.settingsVariables.get("bins");
		return sl.getValue();
	}
	
	public void setBins(Long value){
		SettingsLong sl = (SettingsLong) this.settingsVariables.get("bins");
		bits = this.binsToBits(value);
		sl.setValue(value);
	}	
	public void setBins(Integer value){
		this.setBins(value.longValue());
	}
	
	public Long getBits() {
		return bits;
	}


	public void computeBinBoundaries(ArrayList<Long> prequantizedValues){
		this.binBoundaries = new ArrayList<Long>();
		Long n = new Long(prequantizedValues.size());
		Long binSize = n/this.getBins();
		Collections.sort(prequantizedValues);
		for(int i=1; i<this.getBins(); i++){
			Long cutoff = (prequantizedValues.get((binSize.intValue()*i)-1) + prequantizedValues.get(binSize.intValue()*i))/2;
			this.binBoundaries.add(cutoff);
		}
	}

	
	public ArrayList<Long> getBinBoundaries() {
		return this.binBoundaries;
	}
	

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
	    in.defaultReadObject();

		binBoundaries = new ArrayList<Long>();
		bits = 5L;
	    // TODO populate variables from deserialized set
	    // might not need to all the time. maybe just for compute bits
	}


	
	
}
