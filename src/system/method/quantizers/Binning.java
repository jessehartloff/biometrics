package system.method.quantizers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.method.feature.Feature;
import system.method.feature.Variable;

public class Binning extends Quantizer{

	//TODO make binning with Doubles
	
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
