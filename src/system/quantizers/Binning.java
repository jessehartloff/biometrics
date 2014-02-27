package system.quantizers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import settings.settingsvariables.SettingsMethodVariable;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.method.feature.Feature;
import system.method.feature.Variable;

public class Binning extends Quantizer{

	
	private ArrayList<SettingsMethodVariable> variableSettings; 
	
	
	public Binning(){
		this.variableSettings = new ArrayList<SettingsMethodVariable>();
	}

	
	@Override
	public void train(Users trainingUsers) {
		
		ArrayList<ArrayList<Double>> allPrequantizedValues = new ArrayList<ArrayList<Double>>();
		Feature blankFeature = Biometric.method.getBlankFeatureForTraining();
		for(Variable var : blankFeature.variables.values()){
			variableSettings.add(var.variableSettings);
			allPrequantizedValues.add(new ArrayList<Double>());
		}
		
		for(User user : trainingUsers.users){
			for(Biometric bio : user.readings){
				ArrayList<Feature> features = bio.toFeatures();
				for(Feature feature : features){
					int i=0;
					for(Variable var : feature.variables.values()){
						allPrequantizedValues.get(i).add(var.getPrequantizedValue().doubleValue());
						i++;
					}
				}
			}
		}
		
		for(int i=0; i<blankFeature.variables.size(); i++){
//			var.variableSettings.computeBinBoundaries(allPrequantizedValues.get(i));
			variableSettings.get(i).computeBinBoundaries(allPrequantizedValues.get(i));
		}	
	}

	
	@Override
	public BigInteger featureToBigInt(Feature feature, LinkedHashMap<String, Long> quantizedValues) {
		BigInteger toReturn = BigInteger.valueOf(0);
		int i=0;
		for(String varName : feature.variables.keySet()){
			toReturn = toReturn.shiftLeft(variableSettings.get(i).getBits().intValue());
			Long quantizedValue = variableSettings.get(i).findBin(feature.variables.get(varName).getPrequantizedValue().doubleValue());
			quantizedValues.put(varName, quantizedValue);
			toReturn = toReturn.add(BigInteger.valueOf(quantizedValue));
			i++;
		}
		return toReturn;
	}


	@Override
	public Double getTotalLogOfBins() {
		Double totalBits = 0.0;
		for(SettingsMethodVariable variable : variableSettings){
			totalBits += variable.getLogOfBins();
		}
		return totalBits;
	}


	@Override
	public Long getTotalBits() {
		Long totalBits = 0L;
		for(SettingsMethodVariable variable : variableSettings){
			totalBits += variable.getBits();
		}
		return totalBits;
	}


	@Override
	public Feature getBlankFeatureForTraining() {
		return Biometric.method.getBlankFeatureForTraining();
	}
	
	


}
