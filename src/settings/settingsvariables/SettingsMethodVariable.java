 package settings.settingsvariables;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;


public class SettingsMethodVariable extends SettingsVariable{
	
	private static final long serialVersionUID = 1L;

	private transient Long bits;
	private transient ArrayList<Long> binBoundaries; // TODO compute things when deserialized, or not...
	
	
	public SettingsMethodVariable(){
		this(8L);
	}
	
	public SettingsMethodVariable(Long numberOfBins){
		this.setBins(numberOfBins);
		binBoundaries = new ArrayList<Long>();
		bits = this.binsToBits(this.getBins());
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
		SettingsLong binsSettings = (SettingsLong) this.settingsVariables.get("bins");
		return binsSettings.getValue();
	}
	public SettingsLong getBinsSettings(){
		SettingsLong binsSettings = (SettingsLong) this.settingsVariables.get("bins");
		return binsSettings;
	}
	
	public void setBins(Long value){
		SettingsLong binsLong = (SettingsLong) this.settingsVariables.get("bins");
		binsLong.setValue(value);
		bits = this.binsToBits(value);
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
		bits = this.binsToBits(this.getBins());
	}

	protected JPanel makeJPanel() {
		JPanel panel = this.getBinsSettings().getJPanel();
		return panel;
	}
	


	@Override
	protected void addSettings() {
		this.settingsVariables.put("bins", new SettingsLong());
	}


	
	
}
