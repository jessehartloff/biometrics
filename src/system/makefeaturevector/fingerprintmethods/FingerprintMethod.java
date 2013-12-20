package system.makefeaturevector.fingerprintmethods;

import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.modalities.*;
import system.makefeaturevector.Method;
import system.makefeaturevector.feature.Feature;
import system.makefeaturevector.feature.Variable;

public abstract class FingerprintMethod extends Method{

	
	/**
	 * Converts a fingerprint into a Template containing hashable data
	 * 
	 * @return a template
	 */
	public abstract Template quantizeOne(Fingerprint fingerprint); 

	
	/**
	 * Converts a fingerprint into a template containing hashable data including all variations 
	 * (rotations, translations) if applicable. For invariant methods, this should just call 
	 * quantizeOne(fingerprint).
	 * 
	 * @return set of all templates. For invariant methods, this set will be of size 1.
	 */
	public abstract ArrayList<Template> quantizeAll(Fingerprint fingerprint);
	
	
	/**
	 * Finds the bin cutoffs that fills them uniformly
	 * 
	 * @param fingerprints
	 */
	public void doAllTheBinning(ArrayList<Fingerprint> fingerprints){
		ArrayList<ArrayList<Long>> allPrequantizedValues = new ArrayList<ArrayList<Long>>();
		Feature blankFeature = this.getBlankFeatureForBinning();
		for(Variable var : blankFeature.variables.values()){
			allPrequantizedValues.add(new ArrayList<Long>());
		}
		
		for(Fingerprint fingerprint : fingerprints){
			ArrayList<Feature> features = this.fingerprintToFeatures(fingerprint);
			for(Feature feature : features){
				int i=0;
				for(Variable var : feature.variables.values()){
					allPrequantizedValues.get(i).add(var.getPrequantizedValue());
					i++;
				}
			}
		}
		
		int i=0;
		for(Variable var : blankFeature.variables.values()){
			var.variableSettings.computeBinBoundaries(allPrequantizedValues.get(i));
			i++;
		}
	}
	
	
	protected abstract ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint);


	
}
