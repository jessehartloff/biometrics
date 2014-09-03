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
		for(String var : blankFeature.variables.keySet()){
			allQuantizedValues.add(new ArrayList<Long>());
			scores.variableHistogramValues.put(var, new ArrayList<Long>());
		}
		
		for(User user : this.users.users){
			for(Biometric bio : user.readings){
                //TODO refactor: make these calls of the flavor method.toFeatures(bio)
				ArrayList<Feature> features = bio.toFeatures();
				ArrayList<Feature> quantizedFeatures = bio.toQuantizedFeatures();
//				System.out.println("######" + features.size());
				for(Feature feature : features){
					scores.fieldHistogramValues.add(feature.toBigInt());
				}
				for(Feature feature : quantizedFeatures){ 
//					System.out.println("&&&&&&&" + feature.quantizedValues.size());
//					System.out.println(feature.toBigInt());
					for(String var : feature.quantizedValues.keySet()){
						scores.variableHistogramValues.get(var).add(feature.quantizedValues.get(var));
//						System.out.println("THITHIT######" + feature.quantizedValues.get(var));
					}
				}
			}
		}	
		return scores;
	}

}
