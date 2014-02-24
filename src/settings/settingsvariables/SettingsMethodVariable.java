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

	private transient ArrayList<Double> binBoundaries;
	
	
	public SettingsMethodVariable(){
		this(8L);
	}
	
	public SettingsMethodVariable(Long numberOfBins){
		this.setBins(numberOfBins);
		binBoundaries = new ArrayList<Double>();
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
	
	public static Long binsToBits(Long bins){
		Double d = Math.ceil(Math.log10(bins.doubleValue())/Math.log10(2.0));
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
	}	
	public void setBins(Integer value){
		this.setBins(value.longValue());
	}

	public Long getBits() {
		return SettingsMethodVariable.binsToBits(this.getBins());
	}
	
	public Double getLogOfBins() {
		return Math.log10(this.getBins().doubleValue())/Math.log10(2.0);

	}

	public void computeBinBoundaries(ArrayList<Double> prequantizedValues){
		this.binBoundaries = new ArrayList<Double>();
		Long n = new Long(prequantizedValues.size());
		Long binSize = n/this.getBins();
		Collections.sort(prequantizedValues);
		for(int i=1; i<this.getBins(); i++){
			Double cutoff = (prequantizedValues.get((binSize.intValue()*i)-1) + prequantizedValues.get(binSize.intValue()*i))/2.0;
			this.binBoundaries.add(cutoff);
		}
	}

	
	public ArrayList<Double> getBinBoundaries() {
		return this.binBoundaries;
	}
	

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
	    in.defaultReadObject();
	    
		binBoundaries = new ArrayList<Double>();
	}

//	protected JPanel makeJPanel() {
//		JPanel panel = this.getBinsSettings().getJPanel();
//		return panel;
//	}
	


	@Override
	protected void addSettings() {
		this.settingsVariables.put("bins", new SettingsLong());
	}


	
	
}
