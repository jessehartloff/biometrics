package system.method.quantizers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import settings.settingsvariables.SettingsLong;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.method.feature.Feature;
import system.method.feature.Variable;

public class Binning extends Quantizer{

	//TODO total bits
	//TODO make binning with Doubles
	//TODO move SettingsMethodVariable stuff to here
	
	private transient Long bits;
	private transient ArrayList<Long> binBoundaries; // TODO compute things when deserialized, or not...
	
//	
//	
//	public SettingsMethodVariable(Long numberOfBins){
//		this.setBins(numberOfBins);
//		binBoundaries = new ArrayList<Long>();
//		bits = this.binsToBits(this.getBins());
//	}
//	
//	public SettingsMethodVariable(Integer numberOfBins){
//		this(numberOfBins.longValue());
//	}
//	
//	public Long findBin(Long prequantizedValue) {
//		Integer n = this.binBoundaries.size();	
//		for(Integer i=0; i<n; i++){
//			if(prequantizedValue < this.binBoundaries.get(i)){
//				return i.longValue();
//			}
//		}
//		return n.longValue();
//	}
//	
//	public Long binsToBits(Long bins){
//		Double d = Math.ceil(Math.log10(bins)/Math.log10(2));
//		return d.longValue();
//	}
//
//	public Long getBins(){
//		SettingsLong binsSettings = (SettingsLong) this.settingsVariables.get("bins");
//		return binsSettings.getValue();
//	}
//	public SettingsLong getBinsSettings(){
//		SettingsLong binsSettings = (SettingsLong) this.settingsVariables.get("bins");
//		return binsSettings;
//	}
//	
//	public void setBins(Long value){
//		SettingsLong binsLong = (SettingsLong) this.settingsVariables.get("bins");
//		binsLong.setValue(value);
//		bits = this.binsToBits(value);
//	}	
//	public void setBins(Integer value){
//		this.setBins(value.longValue());
//	}
//
//
//	public void computeBinBoundaries(ArrayList<Long> prequantizedValues){
//		this.binBoundaries = new ArrayList<Long>();
//		Long n = new Long(prequantizedValues.size());
//		Long binSize = n/this.getBins();
//		Collections.sort(prequantizedValues);
//		for(int i=1; i<this.getBins(); i++){
//			Long cutoff = (prequantizedValues.get((binSize.intValue()*i)-1) + prequantizedValues.get(binSize.intValue()*i))/2;
//			this.binBoundaries.add(cutoff);
//		}
//	}




	
	
	
	
	
	
	@Override
	public void train(Users trainingUsers) {
		ArrayList<ArrayList<Long>> allPrequantizedValues = new ArrayList<ArrayList<Long>>();
		Feature blankFeature = Biometric.method.getBlankFeatureForBinning();
		for(Variable var : blankFeature.variables.values()){
			allPrequantizedValues.add(new ArrayList<Long>());
		}
		
		for(User user : trainingUsers.users){
			for(Biometric bio : user.readings){
				ArrayList<Feature> features = bio.toFeatures();
				for(Feature feature : features){
					int i=0;
					for(Variable var : feature.variables.values()){
						allPrequantizedValues.get(i).add(var.getPrequantizedValue());
						i++;
					}
				}
			}
		}
		int i=0;
		for(Variable var : blankFeature.variables.values()){
			var.variableSettings.computeBinBoundaries(allPrequantizedValues.get(i));
			i++;
		}	
	}

	
	@Override
	public BigInteger featureToBigInt(Feature feature) {
		BigInteger toReturn = BigInteger.valueOf(0);
		Collection<Variable> vars = feature.variables.values();
		for(Variable var : vars){
			toReturn = toReturn.shiftLeft(var.variableSettings.getBits().intValue());
			toReturn = toReturn.add(BigInteger.valueOf(var.getQuantizedValue()));
		}
		return toReturn;
	}


}
