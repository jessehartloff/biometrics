package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.hasher.Hasher;
import system.makefeaturevector.feature.Feature;
import system.makefeaturevector.feature.Variable;

public class Histogram extends Coordinator{

	public Histogram(Hasher hasher, Users users) {
		super(hasher, users);
	}

	@Override
	public RawScores run() {
		RawScores scores = this.nextCoordinator.run();
		// TODO check if histogram is empty
		
		
		//Histogram
		ArrayList<ArrayList<Long>> allQuantizedValues = new ArrayList<ArrayList<Long>>();
		for(Variable var : blankFeature.variables.values()){
			allQuantizedValues.add(new ArrayList<Long>());
		}
		
		int j=0;
		for(Variable var : blankFeature.variables.values()){
			for(Long prequantizedValue : allPrequantizedValues.get(j)){
				allQuantizedValues.get(j).add(var.variableSettings.findBin(prequantizedValue));
			}
			j++;
		}
		
		for(User user : this.users){
			for(Biometric bio : user.readings){
				ArrayList<Feature> features = bio.toFeatures();
				for(Feature feature : features){
					feature.toBigInt(); // add this to a histogram
					
				}
			}
		}
		
		// TODO Jim - Histogram
		// this code was copy/pasted from Users so it doesn't make sense here yet
		
		int k=0;
		for(String variableName : blankFeature.variables.keySet()){
			allQuantizedValues.get(k); // and do stuff with it
			k++;
		}
		
		return scores;
	}

}
