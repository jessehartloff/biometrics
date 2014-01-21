package system.coordinator;

import java.util.ArrayList;
import java.util.Collection;

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

		
		//Histogram
		ArrayList<ArrayList<Long>> allQuantizedValues = new ArrayList<ArrayList<Long>>();
		Feature blankFeature = Biometric.method.getBlankFeatureForBinning();
		for(String var : blankFeature.variables.keySet()){
			allQuantizedValues.add(new ArrayList<Long>());
			scores.variableHistograms.put(var, new ArrayList<Long>());
		}

		for(User user : this.users.users){
			for(Biometric bio : user.readings){
				ArrayList<Feature> features = bio.toFeatures();
				for(Feature feature : features){
					scores.fieldHistogram.add(feature.toBigInt());
					for(String var : feature.variables.keySet()){
						scores.variableHistograms.get(var).add(feature.variables.get(var).getQuantizedValue());
					}
				}
			}
		}
		
		return scores;
	}

}
