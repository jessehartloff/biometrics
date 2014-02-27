package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.hasher.Hasher;
import system.method.feature.Feature;
import system.quantizer.Quantizer;
import system.quantizer.QuantizerFactory;

public class HistogramCoordinator extends Coordinator{

	public HistogramCoordinator(Hasher hasher, Users users) {
		super(hasher, users);
	}

	@Override
	public RawScores run() {
		RawScores scores = this.nextCoordinator.run();
		//Histogram
		ArrayList<ArrayList<Long>> allQuantizedValues = new ArrayList<ArrayList<Long>>();
//		Feature blankFeature = Biometric.method.getBlankFeatureForTraining();
		Feature blankFeature = Quantizer.getQuantizer().getBlankFeatureForTraining();
		// FIXME get blank feature from quantizer
		for(String var : blankFeature.variables.keySet()){
			allQuantizedValues.add(new ArrayList<Long>());
			scores.variableHistogramValues.put(var, new ArrayList<Long>());
		}
		
		for(User user : this.users.users){
			for(Biometric bio : user.readings){
//				ArrayList<Feature> features = bio.toFeatures();
				ArrayList<Feature> features = bio.toFeatures();
				for(Feature feature : features){ //FIXME toFeature should give the pca components
					scores.fieldHistogramValues.add(feature.toBigInt());
					for(String var : feature.variables.keySet()){
						scores.variableHistogramValues.get(var).add(feature.quantizedValues.get(var));
					}
				}
			}
		}	
		return scores;
	}

}
